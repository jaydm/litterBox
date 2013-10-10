
package net.jnwd.litterBox.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BoxOpenHelper extends SQLiteOpenHelper {
    public BoxOpenHelper(Context context) {
        super(context, BoxContract.Db_Name, null, BoxContract.Db_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BoxContract.Attribute.createCommand());
        db.execSQL(BoxContract.AttributeValue.createCommand());
        db.execSQL(BoxContract.Class.createCommand());
        db.execSQL(BoxContract.ClassAttribute.createCommand());
        db.execSQL(BoxContract.Entity.createCommand());
        db.execSQL(BoxContract.EntityAttribute.createCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + BoxContract.Attribute.table);
        db.execSQL("drop table if exists " + BoxContract.AttributeValue.table);
        db.execSQL("drop table if exists " + BoxContract.Class.table);
        db.execSQL("drop table if exists " + BoxContract.ClassAttribute.table);
        db.execSQL("drop table if exists " + BoxContract.Entity.table);
        db.execSQL("drop table if exists " + BoxContract.EntityAttribute.table);

        onCreate(db);
    }
}
