
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

    public LitterDBase(Context context) {
        super();

        mContext = context;
    }

    public LitterDBase open() throws SQLException {
        mHelper = new DatabaseOpenHelper(mContext);

        mHelper.open();

        return this;
    }

    public void close() {
        if (mHelper != null) {
            mHelper.close();
        }
    }

    public Cursor getClassListCursor() {
        return mHelper.getClassListCursor();
    }

    public Cursor getClassCursor(Long classID) {
        return mHelper.getClassCursor(classID);
    }

    public LitterClass getClass(Long classID) {
        return mHelper.getClass(classID);
    }

    public LitterClass getClass(String className) {
        return mHelper.getClass(className);
    }

    public Cursor getAttributeListCursor() {
        return mHelper.getAttributeListCursor();
    }

    public Cursor getAttributeCursor(Long attributeID) {
        return mHelper.getAttributeCursor(attributeID);
    }

    public LitterAttribute getAttribute(Long attributeID) {
        return mHelper.getAttribute(attributeID);
    }

    public LitterAttribute getAttribute(String attributeName) {
        return mHelper.getAttribute(attributeName);
    }

    public Cursor getAttributeValuesCursor(Long attributeID) {
        return mHelper.getAttributeValuesCursor(attributeID);
    }

    public Cursor getClassAttributesCursor(Long classID) {
        return mHelper.getClassAttributesCursor(classID);
    }

    public long insertClass(LitterClass clazz) {
        return mHelper.insertClass(clazz);
    }

    public long insertAttribute(LitterAttribute attribute) {
        return mHelper.insertAttribute(attribute);
    }

    public long insertAttributeValue(LitterAttributeValue value) {
        return mHelper.insertAttributeValue(value);
    }

    public long insertClassAttribute(LitterClassAttribute classAttribute) {
        return mHelper.insertClassAttribute(classAttribute);
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

        public void open() {
            mDb = this.getWritableDatabase();
        }

        public void close() {
            mDb.close();

            super.close();
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

        public Cursor getClassListCursor() {
            Cursor mCursor = mDb.query(LitterClass.table, LitterClass.allColumns, null, null, null,
                    null, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
            }

            return mCursor;
        }

        public Cursor getClassCursor(Long classID) {
            Cursor cursor = mDb.query(LitterClass.table, LitterClass.allColumns,
                    LitterClass.column_ID + " = " + classID.toString(), null, null, null, null,
                    null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            return cursor;
        }

        public LitterClass getClass(Long classID) {
            Cursor cursor = mDb.query(LitterClass.table, LitterClass.allColumns,
                    LitterClass.column_ID + " = " + classID.toString(), null, null, null, null,
                    null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            return new LitterClass(cursor);
        }

        public LitterClass getClass(String className) {
            Cursor cursor = mDb.query(LitterClass.table, LitterClass.allColumns,
                    LitterClass.column_Description + " like ?", new String[] {
                        className + "%"
                    }, null, null, null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            return new LitterClass(cursor);
        }

        public Cursor getAttributeListCursor() {
            Cursor mCursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns, null,
                    null,
                    null, null, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
            }

            return mCursor;
        }

        public Cursor getAttributeCursor(Long attributeID) {
            Cursor cursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns,
                    LitterAttribute.column_ID + " = "
                            + attributeID.toString(), null, null, null, null, null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            return cursor;
        }

        public LitterAttribute getAttribute(Long attributeID) {
            Cursor cursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns,
                    LitterAttribute.column_ID + " = "
                            + attributeID.toString(), null, null, null, null, null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            return new LitterAttribute(cursor);
        }

        public LitterAttribute getAttribute(String attributeName) {
            Cursor cursor = mDb.query(LitterAttribute.table, LitterAttribute.allColumns,
                    LitterAttribute.column_Description + " like ?", new String[] {
                        attributeName + "%"
                    }, null, null, null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            return new LitterAttribute(cursor);
        }

        public Cursor getAttributeValuesCursor(Long attributeID) {
            Cursor cursor = mDb.query(LitterAttributeValue.table, LitterAttributeValue.allColumns,
                    "attributeID = " + attributeID.toString(), null, null, null, "sequence");

            if (cursor == null) {
                Log.i(TAG, "Null results set...Return null...");

                return null;
            }

            cursor.moveToFirst();

            return cursor;
        }

        public Cursor getClassAttributesCursor(Long classID) {
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

        public void loadDatabase() {
            Log.i(TAG, "Start the load database thread...");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        createAddressClass();
                        createPersonClass();
                        createGeneralAttributes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void createAddressClass() throws IOException {
            long addressClassID = insertClass(new LitterClass("Address"));

            long address1ID = insertAttribute(new LitterAttribute("Address1", "freeformText"));
            long address2ID = insertAttribute(new LitterAttribute("Address2", "freeformText"));
            long address3ID = insertAttribute(new LitterAttribute("Address3", "freeformText"));
            long cityID = insertAttribute(new LitterAttribute("City", "freeformText"));
            long stateID = insertAttribute(new LitterAttribute("State", "enumText"));
            long zipID = insertAttribute(new LitterAttribute("Zip", "freeformText"));

            insertClassAttribute(new LitterClassAttribute(addressClassID, 10, null, address1ID,
                    "Address 1"));
            insertClassAttribute(new LitterClassAttribute(addressClassID, 20, null, address2ID,
                    "Address 2"));
            insertClassAttribute(new LitterClassAttribute(addressClassID, 30, null, address3ID,
                    "Address 3"));
            insertClassAttribute(new LitterClassAttribute(addressClassID, 40, null, cityID, "City"));
            insertClassAttribute(new LitterClassAttribute(addressClassID, 50, null, stateID,
                    "State"));
            insertClassAttribute(new LitterClassAttribute(addressClassID, 60, null, zipID,
                    "Zip Code"));

            insertAttributeValue(new LitterAttributeValue(stateID, 10, "AK"));
            insertAttributeValue(new LitterAttributeValue(stateID, 20, "AL"));
            insertAttributeValue(new LitterAttributeValue(stateID, 30, "AR"));
            insertAttributeValue(new LitterAttributeValue(stateID, 40, "AZ"));
            insertAttributeValue(new LitterAttributeValue(stateID, 50, "IA"));
            insertAttributeValue(new LitterAttributeValue(stateID, 60, "IL"));
            insertAttributeValue(new LitterAttributeValue(stateID, 70, "IN"));
            insertAttributeValue(new LitterAttributeValue(stateID, 80, "NJ"));
            insertAttributeValue(new LitterAttributeValue(stateID, 90, "NM"));
            insertAttributeValue(new LitterAttributeValue(stateID, 100, "NY"));
        }

        private void createPersonClass() throws IOException {
            long personClassID = insertClass(new LitterClass("Person"));

            LitterClass address = getClass("address");

            long firstNameID = insertAttribute(new LitterAttribute("FirstName", "freeformText"));
            long middleNameID = insertAttribute(new LitterAttribute("MiddleName", "freeformText"));
            long lastNameID = insertAttribute(new LitterAttribute("LastName", "freeformText"));

            insertClassAttribute(new LitterClassAttribute(personClassID, 10, null, firstNameID,
                    "First Name"));
            insertClassAttribute(new LitterClassAttribute(personClassID, 20, null, middleNameID,
                    "Middle Name"));
            insertClassAttribute(new LitterClassAttribute(personClassID, 30, null, lastNameID,
                    "Last Name"));
            insertClassAttribute(new LitterClassAttribute(personClassID, 40, address.getId(), null,
                    "Home Address"));
        }

        private void createGeneralAttributes() throws IOException {
            // generic attributes - Dates
            insertAttribute(new LitterAttribute("StartDate", "date"));
            insertAttribute(new LitterAttribute("EndDate", "date"));
            insertAttribute(new LitterAttribute("BirthDate", "date"));

            // generic attributes - General
            insertAttribute(new LitterAttribute("Comment", "freeformText"));
        }
    }
}
