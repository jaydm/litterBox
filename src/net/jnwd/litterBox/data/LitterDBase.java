package net.jnwd.litterBox.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
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

	private static class DatabaseOpenHelper extends SQLiteOpenHelper {
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

			Log.i(TAG, "Create class table...");

			Log.i(TAG, "Command: " + LitterClass.createCommand());

			mDatabase.execSQL(LitterClass.createCommand());

			Log.i(TAG, "Create class attribute xref table...");

			mDatabase.execSQL(LitterClassAttribute.createCommand());

			Log.i(TAG, "Create attribute table...");

			mDatabase.execSQL(LitterAttribute.createCommand());

			Log.i(TAG, "Finished creating tables!");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

			db.execSQL("drop table if exists " + LitterClass.table);
			db.execSQL("drop table if exists " + LitterClassAttribute.table);
			db.execSQL("drop table if exists " + LitterAttribute.table);

			Log.i(TAG, "Now...recreate the database...");

			onCreate(db);
		}

		public void loadDatabase() {
			Log.i(TAG, "Start the load database thread...");

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						loadModels();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}).start();
		}

		private void loadModels() throws IOException {
			Log.i(TAG, "Open up the raw data file...");
			Log.i(TAG, "Open a reference to the app resources...");

			final Resources resources = mHelperContext.getResources();

			Log.i(TAG, "Open the raw data file...Get an inputStream...");

			InputStream inputStream = resources.openRawResource(R.raw.model);

			Log.i(TAG, "Set up a buffered reader...");

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			try {
				String line;

				List<Model> allModels = new ArrayList<Model>();

				Log.i(TAG, "Spin through the file...Only grab one copy of each model!");

				while ((line = reader.readLine()) != null) {
					Model model = new Model(line);

					if (! allModels.contains(model)) {
						allModels.add(model);

						long id = addModel(model);

						if (id < 0) {
							Log.e(TAG, "unable to add model: " + model.toString());
						}
					}
				}

				Log.i(TAG, "Finished building model list...");
			} finally {
				reader.close();
			}

			Log.i(TAG, "Finished loading the raw file into the database...");
		}

		public long addModel(Model model) {
			ContentValues initialValues = new ContentValues();

			initialValues.put("_id", model.id);
			initialValues.put(Model.COL_MODEL_NAME, model.name);
			initialValues.put(Model.COL_MODEL_TYPE, model.modelType);
			initialValues.put(Model.COL_CREATOR, model.creator);
			initialValues.put(Model.COL_BOOK_TITLE, model.bookTitle);
			initialValues.put(Model.COL_ISBN, model.ISBN);
			initialValues.put(Model.COL_ON_PAGE, model.page);
			initialValues.put(Model.COL_DIFFICULTY, model.difficulty);
			initialValues.put(Model.COL_PAPER, model.paper);
			initialValues.put(Model.COL_PIECES, model.pieces);
			initialValues.put(Model.COL_GLUE, model.glue);
			initialValues.put(Model.COL_CUTS, model.cuts);

			return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
		}
	}
}
