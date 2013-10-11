
package net.jnwd.litterBox.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Box extends ContentProvider {
    private static final String Tag = "box (ContentProvider)";

    public static final int Attribute_List = 1;
    public static final int Attribute_ID = 2;
    public static final int Attribute_Value_List = 11;
    public static final int Attribute_Value_ID = 12;
    public static final int Class_List = 21;
    public static final int Class_ID = 22;
    public static final int Class_Attribute_List = 31;
    public static final int Class_Attribute_ID = 32;
    public static final int Entity_List = 41;
    public static final int Entity_ID = 42;
    public static final int Entity_Attribute_List = 51;
    public static final int Entity_Attribute_ID = 52;

    private static final UriMatcher Uri_Matcher;

    static {
        Uri_Matcher = new UriMatcher(UriMatcher.NO_MATCH);
        Uri_Matcher.addURI(BoxContract.Authority, "attributes", Attribute_List);
        Uri_Matcher.addURI(BoxContract.Authority, "attributes/#", Attribute_ID);
        Uri_Matcher.addURI(BoxContract.Authority, "attributeValues/attribute/#",
                Attribute_Value_List);
        Uri_Matcher.addURI(BoxContract.Authority, "attributeValues/#", Attribute_Value_ID);
        Uri_Matcher.addURI(BoxContract.Authority, "classes", Class_List);
        Uri_Matcher.addURI(BoxContract.Authority, "classes/#", Class_ID);
        Uri_Matcher.addURI(BoxContract.Authority, "classAttributes/class/#", Class_Attribute_List);
        Uri_Matcher.addURI(BoxContract.Authority, "classAttributes/#", Class_Attribute_ID);
        Uri_Matcher.addURI(BoxContract.Authority, "entities", Entity_List);
        Uri_Matcher.addURI(BoxContract.Authority, "entities/#", Entity_ID);
        Uri_Matcher.addURI(BoxContract.Authority, "entityAttributes/entity/#",
                Entity_Attribute_List);
        Uri_Matcher.addURI(BoxContract.Authority, "entityAttributes/#", Entity_Attribute_ID);
    }

    private BoxOpenHelper mHelper = null;

    @Override
    public boolean onCreate() {
        Log.i(Tag, "Creating the content provider...");

        mHelper = new BoxOpenHelper(getContext());

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(Tag, "Delete called for URI: " + uri.toString());

        SQLiteDatabase db = mHelper.getWritableDatabase();

        int delCount = 0;

        String idStr;
        String where;

        switch (Uri_Matcher.match(uri)) {
            case Attribute_List:
                delCount = db.delete(BoxContract.Attribute.table, selection, selectionArgs);

                break;
            case Attribute_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Attribute.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.Attribute.table, where, selectionArgs);

                break;
            case Attribute_Value_List:
                idStr = uri.getLastPathSegment();
                where = BoxContract.AttributeValue.column_AttributeID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.AttributeValue.table, selection, selectionArgs);

                break;
            case Attribute_Value_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.AttributeValue.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.AttributeValue.table, where, selectionArgs);

                break;
            case Class_List:
                delCount = db.delete(BoxContract.Class.table, selection, selectionArgs);

                break;
            case Class_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Class.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.Class.table, where, selectionArgs);

                break;
            case Class_Attribute_List:
                delCount = db.delete(BoxContract.ClassAttribute.table, selection, selectionArgs);

                break;
            case Class_Attribute_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.ClassAttribute.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.ClassAttribute.table, where, selectionArgs);

                break;
            case Entity_List:
                delCount = db.delete(BoxContract.Entity.table, selection, selectionArgs);

                break;
            case Entity_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Attribute.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.Entity.table, where, selectionArgs);

                break;
            case Entity_Attribute_List:
                delCount = db.delete(BoxContract.EntityAttribute.table, selection, selectionArgs);

                break;
            case Entity_Attribute_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Attribute.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.EntityAttribute.table, where, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        if (delCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delCount;
    }

    @Override
    public String getType(Uri uri) {
        Log.i(Tag, "Get Type called for URI: " + uri.toString());

        switch (Uri_Matcher.match(uri)) {
            case Attribute_List:
                return BoxContract.Attribute.Content_Type;
            case Attribute_ID:
                return BoxContract.Attribute.Content_Item_Type;
            case Attribute_Value_List:
                return BoxContract.AttributeValue.Content_Type;
            case Attribute_Value_ID:
                return BoxContract.AttributeValue.Content_Item_Type;
            case Class_List:
                return BoxContract.Class.Content_Type;
            case Class_ID:
                return BoxContract.Class.Content_Item_Type;
            case Class_Attribute_List:
                return BoxContract.ClassAttribute.Content_Type;
            case Class_Attribute_ID:
                return BoxContract.ClassAttribute.Content_Item_Type;
            case Entity_List:
                return BoxContract.Entity.Content_Type;
            case Entity_ID:
                return BoxContract.Entity.Content_Item_Type;
            case Entity_Attribute_List:
                return BoxContract.EntityAttribute.Content_Type;
            case Entity_Attribute_ID:
                return BoxContract.EntityAttribute.Content_Item_Type;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(Tag, "Insert called for URI: " + uri.toString());

        SQLiteDatabase db = mHelper.getWritableDatabase();

        long id = 0;

        switch (Uri_Matcher.match(uri)) {
            case Attribute_List:
                id = db.insert(BoxContract.Attribute.table, null, values);

                break;
            case Class_List:
                id = db.insert(BoxContract.Class.table, null, values);

                break;
            case Class_Attribute_List:
                id = db.insert(BoxContract.ClassAttribute.table, null, values);

                break;
            case Entity_List:
                id = db.insert(BoxContract.Entity.table, null, values);

                break;
            case Entity_Attribute_List:
                id = db.insert(BoxContract.EntityAttribute.table, null, values);

                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }

        return getUriForID(id, uri);
    }

    private Uri getUriForID(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);

            getContext().getContentResolver().notifyChange(itemUri, null);

            return itemUri;
        }

        throw new SQLException("Problem while inserting into uri: " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs,
            String sortOrder) {
        Log.i(Tag, "Query called for URI: " + uri.toString());

        SQLiteDatabase db = mHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (Uri_Matcher.match(uri)) {
            case Attribute_List:
                Log.i(Tag, "Building the attribute list query...");

                builder.setTables(BoxContract.Attribute.table);

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BoxContract.Attribute.defaultSortOrder;
                }

                break;
            case Attribute_ID:
                Log.i(Tag, "Building the attribute ID query...");

                builder.setTables(BoxContract.Attribute.table);

                builder.appendWhere(BoxContract.Attribute.column_ID + " = "
                        + uri.getLastPathSegment());

                break;
            case Attribute_Value_List:
                Log.i(Tag, "Building the attribute value list query...");

                builder.setTables(BoxContract.AttributeValue.table);

                builder.appendWhere(BoxContract.AttributeValue.column_AttributeID + " = "
                        + uri.getLastPathSegment());

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BoxContract.AttributeValue.defaultSortOrder;
                }

                break;
            case Attribute_Value_ID:
                Log.i(Tag, "Building the attribute value ID query...");

                builder.setTables(BoxContract.AttributeValue.table);

                builder.appendWhere(BoxContract.AttributeValue.column_ID + " = "
                        + uri.getLastPathSegment());

                break;
            case Class_List:
                Log.i(Tag, "Building the class list query...");

                builder.setTables(BoxContract.Class.table);

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BoxContract.Class.defaultSortOrder;
                }

                break;
            case Class_ID:
                Log.i(Tag, "Building the class ID query...");

                builder.setTables(BoxContract.Class.table);

                builder.appendWhere(BoxContract.Class.column_ID + " = "
                        + uri.getLastPathSegment());

                break;
            case Class_Attribute_List:
                Log.i(Tag, "Building the class attribute list query...");

                builder.setTables(BoxContract.ClassAttribute.table);

                builder.appendWhere(BoxContract.ClassAttribute.column_ClassID + " = "
                        + uri.getLastPathSegment());

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BoxContract.ClassAttribute.defaultSortOrder;
                }

                break;
            case Class_Attribute_ID:
                Log.i(Tag, "Building the class attribute ID query...");

                builder.setTables(BoxContract.ClassAttribute.table);

                builder.appendWhere(BoxContract.ClassAttribute.column_ID + " = "
                        + uri.getLastPathSegment());

                break;
            case Entity_List:
                Log.i(Tag, "Building the entity list query...");

                builder.setTables(BoxContract.Entity.table);

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BoxContract.Entity.defaultSortOrder;
                }

                break;
            case Entity_ID:
                Log.i(Tag, "Building the entity ID query...");

                builder.setTables(BoxContract.Entity.table);

                builder.appendWhere(BoxContract.Entity.column_ID + " = "
                        + uri.getLastPathSegment());

                break;
            case Entity_Attribute_List:
                Log.i(Tag, "Building the entity attribute list query...");

                builder.setTables(BoxContract.EntityAttribute.table);

                builder.appendWhere(BoxContract.EntityAttribute.column_EntityID + " = "
                        + uri.getLastPathSegment());

                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = BoxContract.Class.defaultSortOrder;
                }

                break;
            case Entity_Attribute_ID:
                Log.i(Tag, "Building the entity attribute ID query...");

                builder.setTables(BoxContract.EntityAttribute.table);

                builder.appendWhere(BoxContract.EntityAttribute.column_ID + " = "
                        + uri.getLastPathSegment());

                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = builder.query(db, columns, selection, selectionArgs, null, null, sortOrder);

        if (cursor == null) {
            return null;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String baseWhere, String[] whereArgs) {
        Log.i(Tag, "Update called for URI: " + uri.toString());

        SQLiteDatabase db = mHelper.getWritableDatabase();

        int updateCount = 0;

        String idStr;
        String where;

        switch (Uri_Matcher.match(uri)) {
            case Attribute_List:
                Log.i(Tag, "Updating the attribute list...");

                updateCount = db.update(BoxContract.Attribute.table, values, baseWhere, whereArgs);

                break;
            case Attribute_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Attribute.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(baseWhere)) {
                    where += " and " + baseWhere;
                }

                updateCount = db.update(BoxContract.Attribute.table, values, where, whereArgs);

                break;
            case Class_List:
                updateCount = db.update(BoxContract.Class.table, values, baseWhere, whereArgs);

                break;
            case Class_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Class.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(baseWhere)) {
                    where += " and " + baseWhere;
                }

                updateCount = db.update(BoxContract.Class.table, values, where, whereArgs);

                break;
            case Entity_List:
                updateCount = db.update(BoxContract.Entity.table, values, baseWhere, whereArgs);

                break;
            case Entity_ID:
                idStr = uri.getLastPathSegment();
                where = BoxContract.Entity.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(baseWhere)) {
                    where += " and " + baseWhere;
                }

                updateCount = db.update(BoxContract.Entity.table, values, where, whereArgs);

                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updateCount;
    }
}
