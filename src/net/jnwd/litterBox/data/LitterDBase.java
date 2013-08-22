package net.jnwd.litterBox.data;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LitterDBase {
	private static final String TAG = "LitterDBase";

	private static final String DATABASE_NAME = "LitterBox";
	private static final int DATABASE_VERSION = 1;

	private final Context mContext;

	private DatabaseOpenHelper mDatabaseOpenHelper;
	private SQLiteDatabase mDb;

	public LitterDBase(Context context) {
		super();

		mContext = context;
	}

	public LitterDBase open() throws SQLException {
		mDatabaseOpenHelper = new DatabaseOpenHelper(mContext);

		mDb = mDatabaseOpenHelper.getWritableDatabase();

		return this;
	}

	public void close() {
		if (mDatabaseOpenHelper != null) {
			mDatabaseOpenHelper.close();
		}
	}

	public Cursor getClassList() {
		Cursor mCursor = mDb.query(LitterClass.table, LitterClass.allColumns, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getClass(Long classID) {
		Cursor cursor = mDb.query(LitterClass.table, LitterClass.allColumns, "_id = " + classID.toString(), null, null, null, null, null);

		if (cursor == null) {
			return null;
		}

		cursor.moveToFirst();

		return cursor;
	}

	public Cursor getAttributeList() {
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
		Cursor cursor = mDb.query(LitterAttributeValue.table, LitterAttributeValue.allColumns, "attributeID = " + attributeID.toString(), null, null, null, "sequence");

		if (cursor == null) {
			Log.i(TAG, "Null results set...Return null...");

			return null;
		}

		cursor.moveToFirst();

		return cursor;
	}

	public Cursor getClassAttributes(Long classID) {
		Cursor cursor = mDb.query(LitterClassAttribute.table, LitterClassAttribute.allColumns, "parentID = " + classID.toString(), null, null, null, null);

		if (cursor == null) {
			Log.i(TAG, "Null results set...Return null...");

			return null;
		}

		cursor.moveToFirst();

		return cursor;
	}

	public long insertClass(LitterClass clazz) {
		return mDb.insert(LitterClass.table, null, clazz.addNew());
	}

	public long insertAttribute(LitterAttribute attribute) {
		return mDb.insert(LitterAttribute.table, null, attribute.addNew());
	}

	public long insertAttributeValue(LitterAttributeValue value) {
		return mDb.insert(LitterAttributeValue.table, null, value.addNew());
	}

	public long insertClassAttribute(LitterClassAttribute classAttribute) {
		return mDb.insert(LitterClassAttribute.table, null, classAttribute.addNew());
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
			mDatabase = db;

			mDatabase.execSQL(LitterClass.createCommand());

			mDatabase.execSQL(LitterAttribute.createCommand());

			mDatabase.execSQL(LitterClassAttribute.createCommand());

			mDatabase.execSQL(LitterAttributeValue.createCommand());

			mDatabase.execSQL(LitterEntity.createCommand());

			mDatabase.execSQL(LitterEntityAttribute.createCommand());

			loadDatabase();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

			db.execSQL("drop table if exists " + LitterClass.table);
			db.execSQL("drop table if exists " + LitterAttribute.table);
			db.execSQL("drop table if exists " + LitterClassAttribute.table);
			db.execSQL("drop table if exists " + LitterAttributeValue.table);
			db.execSQL("drop table if exists " + LitterEntity.table);
			db.execSQL("drop table if exists " + LitterEntityAttribute.table);

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
			// define an address class
			long addressClassID = mDatabase.insert(LitterClass.table, null, new LitterClass("Address").addNew());

			long attributeID;

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Address1", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 10, null, attributeID).addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Address2", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 20, null, attributeID).addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Address3", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 30, null, attributeID).addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("City", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 40, null, attributeID).addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("State", "enumText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 50, null, attributeID).addNew());

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

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Zip", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(addressClassID, 60, null, attributeID).addNew());

			// define person class
			long personClassID = mDatabase.insert(LitterClass.table, null, new LitterClass("Person").addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("FirstName", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(personClassID, 10, null, attributeID).addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("MiddleInitial", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(personClassID, 20, null, attributeID).addNew());

			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("LastName", "freeformText").addNew());
			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(personClassID, 30, null, attributeID).addNew());

			mDatabase.insert(LitterClassAttribute.table, null, new LitterClassAttribute(personClassID, 40, addressClassID, null).addNew());

			// generic attributes - Dates
			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("StartDate", "date").addNew());
			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("EndDate", "date").addNew());
			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("BirthDate", "date").addNew());

			// generic attributes - General
			attributeID = mDatabase.insert(LitterAttribute.table, null, new LitterAttribute("Comment", "freeformText").addNew());
		}
	}
}