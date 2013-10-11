
package net.jnwd.litterBox.contentProvider;

import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import net.jnwd.litterBox.data.LitterClass;
import net.jnwd.litterBox.data.LitterClassAttribute;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BoxOpenHelper extends SQLiteOpenHelper {
    private final String Tag = "BoxOpenHelper";

    public BoxOpenHelper(Context context) {
        super(context, BoxContract.Db_Name, null, BoxContract.Db_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Tag, "Create the application tables...");

        Log.i(Tag, "Create the attribute table...");

        db.execSQL(BoxContract.Attribute.createCommand());

        Log.i(Tag, "Create the attribute value table...");

        db.execSQL(BoxContract.AttributeValue.createCommand());

        Log.i(Tag, "Create the class table...");

        db.execSQL(BoxContract.Class.createCommand());

        Log.i(Tag, "Create the class attribute table...");

        db.execSQL(BoxContract.ClassAttribute.createCommand());

        Log.i(Tag, "Create the entity table...");

        db.execSQL(BoxContract.Entity.createCommand());

        Log.i(Tag, "Create the entity attribute table...");

        db.execSQL(BoxContract.EntityAttribute.createCommand());

        loadDemoData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(Tag, "Upgrading...delete the old tables and recreate...");

        Log.i(Tag, "Delete the attribute table...");

        db.execSQL("drop table if exists " + BoxContract.Attribute.table);

        Log.i(Tag, "Delete the attribute value table...");

        db.execSQL("drop table if exists " + BoxContract.AttributeValue.table);

        Log.i(Tag, "Delete the class table...");

        db.execSQL("drop table if exists " + BoxContract.Class.table);

        Log.i(Tag, "Delete the class attribute table...");

        db.execSQL("drop table if exists " + BoxContract.ClassAttribute.table);

        Log.i(Tag, "Delete the entity table...");

        db.execSQL("drop table if exists " + BoxContract.Entity.table);

        Log.i(Tag, "Delete the entity attribute table...");

        db.execSQL("drop table if exists " + BoxContract.EntityAttribute.table);

        onCreate(db);
    }

    public long insertClass(SQLiteDatabase mDb, LitterClass clazz) {
        return mDb.insert(LitterClass.table, null, clazz.addNew());
    }

    public long insertAttribute(SQLiteDatabase mDb, LitterAttribute attribute) {
        return mDb.insert(LitterAttribute.table, null, attribute.addNew());
    }

    public long insertAttributeValue(SQLiteDatabase mDb, LitterAttributeValue value) {
        return mDb.insert(LitterAttributeValue.table, null, value.addNew());
    }

    public long insertClassAttribute(SQLiteDatabase mDb, LitterClassAttribute classAttribute) {
        return mDb.insert(LitterClassAttribute.table, null, classAttribute.addNew());
    }

    private void loadDemoData(SQLiteDatabase mDb) {
        // create address class
        LitterClass address = new LitterClass("Address");

        long addressClassID = insertClass(mDb, address);

        long address1ID = insertAttribute(mDb, new LitterAttribute("Address1", "freeformText"));
        long address2ID = insertAttribute(mDb, new LitterAttribute("Address2", "freeformText"));
        long address3ID = insertAttribute(mDb, new LitterAttribute("Address3", "freeformText"));
        long cityID = insertAttribute(mDb, new LitterAttribute("City", "freeformText"));
        long stateID = insertAttribute(mDb, new LitterAttribute("State", "enumText"));
        long zipID = insertAttribute(mDb, new LitterAttribute("Zip", "freeformText"));

        insertClassAttribute(mDb, new LitterClassAttribute(addressClassID, 10, null, address1ID,
                "Address 1"));
        insertClassAttribute(mDb, new LitterClassAttribute(addressClassID, 20, null, address2ID,
                "Address 2"));
        insertClassAttribute(mDb, new LitterClassAttribute(addressClassID, 30, null, address3ID,
                "Address 3"));
        insertClassAttribute(mDb,
                new LitterClassAttribute(addressClassID, 40, null, cityID, "City"));
        insertClassAttribute(mDb, new LitterClassAttribute(addressClassID, 50, null, stateID,
                "State"));
        insertClassAttribute(mDb, new LitterClassAttribute(addressClassID, 60, null, zipID,
                "Zip Code"));

        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 10, "AK"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 20, "AL"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 30, "AR"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 40, "AZ"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 50, "IA"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 60, "IL"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 70, "IN"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 80, "NJ"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 90, "NM"));
        insertAttributeValue(mDb, new LitterAttributeValue(stateID, 100, "NY"));

        // create person class
        LitterClass person = new LitterClass("Person");
        long personClassID = insertClass(mDb, person);

        long firstNameID = insertAttribute(mDb, new LitterAttribute("FirstName", "freeformText"));
        long middleNameID = insertAttribute(mDb, new LitterAttribute("MiddleName", "freeformText"));
        long lastNameID = insertAttribute(mDb, new LitterAttribute("LastName", "freeformText"));

        insertClassAttribute(mDb, new LitterClassAttribute(personClassID, 10, null, firstNameID,
                "First Name"));
        insertClassAttribute(mDb, new LitterClassAttribute(personClassID, 20, null, middleNameID,
                "Middle Name"));
        insertClassAttribute(mDb, new LitterClassAttribute(personClassID, 30, null, lastNameID,
                "Last Name"));
        insertClassAttribute(mDb, new LitterClassAttribute(personClassID, 40, address.getId(),
                null,
                "Home Address"));

        // generic attributes - Dates
        insertAttribute(mDb, new LitterAttribute("StartDate", "date"));
        insertAttribute(mDb, new LitterAttribute("EndDate", "date"));
        insertAttribute(mDb, new LitterAttribute("BirthDate", "date"));

        // generic attributes - General
        insertAttribute(mDb, new LitterAttribute("Comment", "freeformText"));
    }
}
