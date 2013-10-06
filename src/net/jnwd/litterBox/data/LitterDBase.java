
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

    private DatabaseOpenHelper mHelper;
    private SQLiteDatabase mDb;

    public LitterDBase(Context context) {
        super();

        mContext = context;
    }

    public LitterDBase open() throws SQLException {
        mHelper = new DatabaseOpenHelper(mContext);

        mDb = mHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        if (mHelper != null) {
            mHelper.close();
        }
    }

    public Cursor getClassList() {
        Cursor mCursor = mDb.query(LitterClass.table, LitterClass.allColumns, null, null, null,
                null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor getClass(Long classID) {
        Cursor cursor = mDb.query(LitterClass.table, LitterClass.allColumns,
                "_id = " + classID.toString(), null, null, null, null, null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();

        return cursor;
    }

    public Cursor getAttributeList() {
        Cursor mCursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns, null, null,
                null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor getAttribute(Long attributeID) {
        Cursor cursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns, "_id = "
                + attributeID.toString(), null, null, null, null, null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();

        return cursor;
    }

    public Cursor getAttributeValues(Long attributeID) {
        Cursor cursor = mDb.query(LitterAttributeValue.table, LitterAttributeValue.allColumns,
                "attributeID = " + attributeID.toString(), null, null, null, "sequence");

        if (cursor == null) {
            Log.i(TAG, "Null results set...Return null...");

            return null;
        }

        cursor.moveToFirst();

        return cursor;
    }

    public Cursor getClassAttributes(Long classID) {
        Cursor cursor = mDb.query(LitterClassAttribute.table, LitterClassAttribute.allColumns,
                "parentID = " + classID.toString(), null, null, null, null);

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

    public LitterEntity getEntity(long entityID) {
        return (LitterEntity) null;
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {
        @SuppressWarnings("unused")
        private final Context mHelperContext;
        private SQLiteDatabase mDb;

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

            Log.i(TAG, "Trying to create the database instance...");

            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDb = db;

            mDb.execSQL(LitterClass.createCommand());

            mDb.execSQL(LitterAttribute.createCommand());

            mDb.execSQL(LitterClassAttribute.createCommand());

            mDb.execSQL(LitterAttributeValue.createCommand());

            mDb.execSQL(LitterEntity.createCommand());

            mDb.execSQL(LitterEntityAttribute.createCommand());

            loadDatabase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");

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
                        createAddressClass();
                        createPersonClass();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void createPersonClass() throws IOException {
            // define person class
            long personClassID = mDb.insert(LitterClass.table, null,
                    new LitterClass("Person").addNew());

            long firstNameID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "FirstName", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null, new LitterClassAttribute(
                    personClassID, 10, null, firstNameID, "First Name").addNew());

            long middleNameID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "MiddleName", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null, new LitterClassAttribute(
                    personClassID, 20, null, middleNameID, "Middle Name").addNew());

            long lastNameID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "LastName", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null, new LitterClassAttribute(
                    personClassID, 30, null, lastNameID, "Last Name").addNew());

            mDb.insert(LitterClassAttribute.table, null, new LitterClassAttribute(
                    personClassID, 40, addressClassID, null, "Home Address").addNew());

            // generic attributes - Dates
            attributeID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "StartDate", "date").addNew());
            attributeID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "EndDate", "date").addNew());
            attributeID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "BirthDate", "date").addNew());

            // generic attributes - General
            attributeID = mDb.insert(LitterAttribute.table, null, new LitterAttribute(
                    "Comment", "freeformText").addNew());
        }

        private void createAddressClass() throws IOException {
            long addressClassID = mDb.insert(LitterClass.table, null,
                    new LitterClass(
                            "Address").addNew());

            long address1ID = mDb.insert(LitterAttribute.table, null,
                    new LitterAttribute(
                            "Address1", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null,
                    new LitterClassAttribute(
                            addressClassID, 10, null, address1ID, "Address 1").addNew());

            long address2ID = mDb.insert(LitterAttribute.table, null,
                    new LitterAttribute("Address2", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null,
                    new LitterClassAttribute(addressClassID, 20, null, address2ID, "Address 2")
                            .addNew());

            long address3ID = mDb.insert(LitterAttribute.table, null,
                    new LitterAttribute("Address3", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null,
                    new LitterClassAttribute(addressClassID, 30, null, address3ID, "Address 3")
                            .addNew());

            long cityID = mDb.insert(LitterAttribute.table, null,
                    new LitterAttribute("City", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null,
                    new LitterClassAttribute(addressClassID, 40, null, cityID, "City").addNew());

            long stateID = mDb.insert(LitterAttribute.table, null,
                    new LitterAttribute("State", "enumText").addNew());
            mDb.insert(LitterClassAttribute.table, null,
                    new LitterClassAttribute(addressClassID, 50, null, stateID, "State").addNew());

            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 10, "AK").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 20, "AL").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 30, "AR").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 40, "AZ").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 50, "IA").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 60, "IL").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 70, "IN").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 80, "NJ").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 90, "NM").addNew());
            mDb.insert(LitterAttributeValue.table, null,
                    new LitterAttributeValue(stateID, 100, "NY").addNew());

            long zipID = mDb.insert(LitterAttribute.table, null,
                    new LitterAttribute("Zip", "freeformText").addNew());
            mDb.insert(LitterClassAttribute.table, null,
                    new LitterClassAttribute(addressClassID, 60, null, zipID, "Zip Code").addNew());
        }
    }
}
