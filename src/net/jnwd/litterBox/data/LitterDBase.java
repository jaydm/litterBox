package net.jnwd.litterBox.data;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LitterDBase {
	private static final String TAG = "LitterBox";

	private static final String DATABASE_NAME = "LitterBox";
	private static final int DATABASE_VERSION = 3;

	private final Context mContext;

	private DatabaseOpenHelper mDatabaseOpenHelper;
	private SQLiteDatabase mDb;

	public LitterDBase(Context context) {
		super();

		mContext = context;
	}

	public LitterDBase open() throws SQLException {
		Log.i(TAG, "Inside the open routine of the database handler...");

		Log.i(TAG, "Establishing the connection to the database...");

		mDatabaseOpenHelper = new DatabaseOpenHelper(mContext);

		Log.i(TAG, "Getting a writeable database instance...");

		mDb = mDatabaseOpenHelper.getWritableDatabase();

		Log.i(TAG, "Checking the database..." + (mDb == null ? "Null!!!!" : "Database Okay!"));

		return this;
	}

	public void close() {
		if (mDatabaseOpenHelper != null) {
			mDatabaseOpenHelper.close();
		}
	}

	public Cursor getClassList() {
		Cursor mCursor = mDb.query(LitterClass.table, LitterClass.allColumns(), null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getAttributeList() {
		return getAttributeList(null);
	}

	public Cursor getAttributeList(Long classID) {
		// the actual implementation of getting all of the attributes
		// that belong to a class is somewhat more complicated...
		Cursor mCursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getAttribute(Long attributeID) {
		Cursor cursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns, "_id = " + attributeID.toString(), null, null, null, null, null);

		if (cursor == null) {
			return null;
		}

		cursor.moveToFirst();

		return cursor;
	}

	public Cursor getAttributeValues(Long attributeID) {
		Cursor cursor = mDb.query(LitterAttributeValue.table, LitterAttributeValue.allColumns, "attributeID = " + attributeID.toString(), null, null, null, null, "sequence");

		if (cursor == null) {
			return null;
		}

		cursor.moveToFirst();

		return cursor;
	}

	private static class DatabaseOpenHelper extends SQLiteOpenHelper {
		@SuppressWarnings("unused")
		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;

		DatabaseOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

			Log.i(TAG, "Trying to create the database instance...");

			mHelperContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "About to perform the onCreate method...");

			Log.i(TAG, "The database instance coming in is: " + db);

			mDatabase = db;

			Log.i(TAG, "Executing the create table scripts...");

			Log.i(TAG, "Create class table: " + LitterClass.createCommand());

			mDatabase.execSQL(LitterClass.createCommand());

			Log.i(TAG, "Create attribute table: " + LitterAttribute.createCommand());

			mDatabase.execSQL(LitterAttribute.createCommand());

			Log.i(TAG, "Create class attribute xref table: " + LitterClassAttribute.createCommand());

			mDatabase.execSQL(LitterClassAttribute.createCommand());

			Log.i(TAG, "Create attribute value table: " + LitterAttributeValue.createCommand());

			mDatabase.execSQL(LitterAttributeValue.createCommand());

			Log.i(TAG, "Create entity table: " + LitterEntity.createCommand());

			mDatabase.execSQL(LitterEntity.createCommand());

			Log.i(TAG, "Create entity attribute (value) table: " + LitterEntityAttribute.createCommand());

			mDatabase.execSQL(LitterEntityAttribute.createCommand());

			Log.i(TAG, "Finished creating tables!");

			Log.i(TAG, "Now put something into it...");

			loadDatabase();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

			db.execSQL("drop table if exists " + LitterClass.table);
			db.execSQL("drop table if exists " + LitterClassAttribute.table);
			db.execSQL("drop table if exists " + LitterAttribute.table);
			db.execSQL("drop table if exists " + LitterAttributeValue.table);
			db.execSQL("drop table if exists " + LitterEntity.table);
			db.execSQL("drop table if exists " + LitterEntityAttribute.table);

			Log.i(TAG, "Now...recreate the database...");

			onCreate(db);
		}

		public void loadDatabase() {
			Log.i(TAG, "Start the load database thread...");

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						loadBases();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}).start();
		}

		private void loadBases() throws IOException {
			Log.i(TAG, "Create a base class: Address");

			long addressClassID = mDatabase.insert(LitterClass.table, null, new LitterClass("Address").addNew());

			Log.i(TAG, "Give it some attributes...");

			long attributeID;

			Log.i(TAG, "Address line 1...");

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Address1", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 10, null, attributeID).addNew());

			Log.i(TAG, "Address line 2...");

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Address2", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 20, null, attributeID).addNew());

			Log.i(TAG, "Address line 3...");

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Address3", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 30, null, attributeID).addNew());

			Log.i(TAG, "City...");

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("City", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 40, null, attributeID).addNew());

			Log.i(TAG, "State...");

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("State", "enumText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 50, null, attributeID).addNew());

			Log.i(TAG, "Fill in some state values...");

			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 10, "AK").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 20, "AL").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 30, "AR").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 40, "AZ").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 50, "IA").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 60, "IL").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 70, "IN").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 80, "NJ").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 90, "NM").addNew());
			mDatabase.insert(LitterAttributeValue.table, null, new LitterAttributeValue(attributeID, 100, "NY").addNew());

			Log.i(TAG, "Zip code...");

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Zip", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 60, null, attributeID).addNew());
		}
	}
}
