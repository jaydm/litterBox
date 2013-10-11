
package net.jnwd.litterBox.base;

import net.jnwd.litterBox.contentProvider.Box;
import net.jnwd.litterBox.contentProvider.BoxContract;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.SimpleCursorAdapter;

public class LitterBoxFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public final String Tag = "litterBoxFragment";

    public Context mContext;

    public SparseArray<SimpleCursorAdapter> adapters;

    public String mAttributeFilter;
    public String mClassFilter;
    public String mClassAttributeFilter;
    public String mEntityFilter;
    public String mEntityAttributeFilter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (bundle == null) {

        }

        adapters = new SparseArray<SimpleCursorAdapter>();
    }

    public LitterBoxFragment() {
        super();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Log.i(Tag, "Creating a loader for ID: " + id);

        CursorLoader loader = null;

        switch (id) {
            case Box.Attribute_List:
                Log.i(Tag, "Creating attribute list loader...");

                loader = new CursorLoader(
                        mContext,
                        BoxContract.Attribute.Content_Uri,
                        BoxContract.Attribute.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Attribute_ID:
                Log.i(Tag, "Creating attribute ID loader...");

                Log.i(Tag, "attributeID: " + mAttributeFilter);

                loader = new CursorLoader(
                        mContext,
                        Uri.withAppendedPath(BoxContract.Attribute.Content_Uri, mAttributeFilter),
                        BoxContract.Attribute.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Attribute_Value_List:
                Log.i(Tag, "Creating attribute value list loader (not really)...");

                Log.i(Tag, "attributeID: " + mAttributeFilter);

                if (mAttributeFilter == null) {
                    mAttributeFilter = "0";
                }

                loader = new CursorLoader(
                        mContext,
                        Uri.withAppendedPath(BoxContract.AttributeValue.Content_List_Uri,
                                mAttributeFilter),
                        BoxContract.AttributeValue.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Attribute_Value_ID:
                Log.i(Tag, "Creating attribute value ID loader (not really)...");

                Log.i(Tag, "attributeValueID: " + mAttributeFilter);

                if (mAttributeFilter == null) {
                    mAttributeFilter = "0";
                }

                loader = new CursorLoader(
                        mContext,
                        Uri.withAppendedPath(BoxContract.AttributeValue.Content_Uri,
                                mAttributeFilter),
                        BoxContract.AttributeValue.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Class_List:
                Log.i(Tag, "Creating class list loader...");

                loader = new CursorLoader(
                        mContext,
                        BoxContract.Class.Content_Uri,
                        BoxContract.Class.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Class_ID:
                Log.i(Tag, "Creating class ID loader (not really)...");

                Log.i(Tag, "classID: " + mClassFilter);

                if (mClassFilter == null) {
                    mClassFilter = "0";
                }

                loader = new CursorLoader(
                        mContext,
                        Uri.withAppendedPath(BoxContract.Class.Content_Uri, mClassFilter),
                        BoxContract.Class.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Class_Attribute_List:
                Log.i(Tag, "Creating class attribute list loader...");

                Log.i(Tag, "classID: " + mClassFilter);

                if (mClassFilter == null) {
                    mClassFilter = "0";
                }

                loader = new CursorLoader(
                        mContext,
                        Uri.withAppendedPath(BoxContract.ClassAttribute.Content_List_Uri,
                                mClassFilter),
                        BoxContract.Class.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Class_Attribute_ID:
                Log.i(Tag, "Creating class attribute ID loader (not really)...");

                Log.i(Tag, "classAttributeID: " + mClassAttributeFilter);

                if (mClassAttributeFilter == null) {
                    mClassAttributeFilter = "0";
                }

                loader = new CursorLoader(
                        mContext,
                        Uri.withAppendedPath(BoxContract.ClassAttribute.Content_Uri,
                                mClassAttributeFilter),
                        BoxContract.Class.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Entity_List:
                Log.i(Tag, "Creating entity list loader...");

                loader = new CursorLoader(
                        mContext,
                        BoxContract.Entity.Content_Uri,
                        BoxContract.Entity.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Entity_ID:
                Log.i(Tag, "Creating entity ID loader (not really)...");

                break;
            case Box.Entity_Attribute_List:
                Log.i(Tag, "Creating entity attribute list loader (not really)...");

                break;
            case Box.Entity_Attribute_ID:
                Log.i(Tag, "Creating entity attribute ID loader (not really)...");

                break;
            default:
                throw new IllegalArgumentException("Unsupported Loader ID: " + id);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.i(Tag, "Cursor load finished...");

        int id = loader.getId();

        try {
            switch (id) {
                case Box.Attribute_List:
                    Log.i(Tag, "Swapping out cursor for attribute list adapter...");

                    break;
                case Box.Attribute_ID:
                    Log.i(Tag, "Swapping out cursor for attribute ID adapter...");

                    break;
                case Box.Attribute_Value_List:
                    Log.i(Tag, "Swapping out cursor for attribute value list adapter...");

                    break;
                case Box.Attribute_Value_ID:
                    Log.i(Tag, "Swapping out cursor for attribute value ID adapter...");

                    break;
                case Box.Class_List:
                    Log.i(Tag, "Swapping out cursor for class list adapter...");

                    break;
                case Box.Class_ID:
                    Log.i(Tag, "Swapping out cursor for class ID adapter...");

                    break;
                case Box.Class_Attribute_List:
                    Log.i(Tag, "Swapping out cursor for class attribute list adapter...");

                    break;
                case Box.Class_Attribute_ID:
                    Log.i(Tag, "Swapping out cursor for class attribute ID adapter...");

                    break;
                case Box.Entity_List:
                    Log.i(Tag, "Swapping out cursor for entity list adapter...");

                    break;
                case Box.Entity_ID:
                    Log.i(Tag, "Swapping out cursor for entity ID adapter...");

                    break;
                case Box.Entity_Attribute_List:
                    Log.i(Tag, "Swapping out cursor for entity attribute list adapter...");

                    break;
                case Box.Entity_Attribute_ID:
                    Log.i(Tag, "Swapping out cursor for entity attribute ID adapter...");

                    break;
                default:
                    throw new IllegalArgumentException("Unsupported Loader ID: " + id);
            }

            Log.i(Tag, "(Actually doing the swap now)...");

            adapters.get(id).swapCursor(cursor);
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("Adapter not yet initialized: " + id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(Tag, "Resetting loader...");

        int id = loader.getId();

        Log.i(Tag, "Loader ID: " + id);

        try {
            switch (id) {
                case Box.Attribute_List:
                    Log.i(Tag, "Setting up to clear the attribute list adapter...");

                    break;
                case Box.Attribute_ID:
                    Log.i(Tag, "Setting up to clear the attribute ID adapter...");

                    break;
                case Box.Attribute_Value_List:
                    Log.i(Tag, "Setting up to clear the attribute value list adapter...");

                    break;
                case Box.Attribute_Value_ID:
                    Log.i(Tag, "Setting up to clear the attribute value ID adapter...");

                    break;
                case Box.Class_List:
                    Log.i(Tag, "Setting up to clear the class list adapter...");

                    break;
                case Box.Class_ID:
                    Log.i(Tag, "Setting up to clear the class ID adapter...");

                    break;
                case Box.Class_Attribute_List:
                    Log.i(Tag, "Setting up to clear the class attribute list adapter...");

                    break;
                case Box.Class_Attribute_ID:
                    Log.i(Tag, "Setting up to clear the class attribute id adapter...");

                    break;
                case Box.Entity_List:
                    Log.i(Tag, "Setting up to clear the entity list adapter...");

                    break;
                case Box.Entity_ID:
                    Log.i(Tag, "Setting up to clear the entity ID adapter...");

                    break;
                case Box.Entity_Attribute_List:
                    Log.i(Tag, "Setting up to clear the entity attribute list adapter...");

                    break;
                case Box.Entity_Attribute_ID:
                    Log.i(Tag, "Setting up to clear the entity attribute ID adapter...");

                    break;
                default:
                    throw new IllegalArgumentException("Unsupported Loader ID: " + id);
            }

            Log.i(Tag, "(Actually clearing the cursor)...");

            adapters.get(id).swapCursor(null);
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("Adapter not yet initialized: " + id);
        }
    }
}
