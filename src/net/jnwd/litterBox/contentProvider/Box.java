
package net.jnwd.litterBox.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class Box extends ContentProvider {
    private static final int Attribute_List = 1;
    private static final int Attribute_ID = 2;
    private static final int Attribute_Value_List = 11;
    private static final int Attribute_Value_ID = 12;
    private static final int Class_List = 21;
    private static final int Class_ID = 22;
    private static final int Class_Attribute_List = 31;
    private static final int Class_Attribute_ID = 32;
    private static final int Entity_List = 41;
    private static final int Entity_ID = 42;
    private static final int Entity_Attribute_List = 51;
    private static final int Entity_Attribute_ID = 52;

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
        mHelper = new BoxOpenHelper(getContext());

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        int delCount = 0;

        switch (Uri_Matcher.match(uri)) {
            case Attribute_List:
                delCount = db.delete(BoxContract.Attribute.table, selection, selectionArgs);

                break;
            case Attribute_ID:
                String idStr = uri.getLastPathSegment();
                String where = BoxContract.Attribute.column_ID + " = " + idStr;

                if (!TextUtils.isEmpty(selection)) {

                }

                delCount = db.delete(BoxContract.Attribute.table, where, selectionArgs);

                break;
            case Attribute_Value_List:

                break;
            case Attribute_Value_ID:

                break;
            case Class_List:

                break;
            case Class_ID:

                break;
            case Class_Attribute_List:

                break;
            case Class_Attribute_ID:

                break;
            case Entity_List:

                break;
            case Entity_ID:

                break;
            case Entity_Attribute_List:

                break;
            case Entity_Attribute_ID:

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

        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs,
            String sortOrder) {

        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String arg2, String[] arg3) {

        return 0;
    }
}
