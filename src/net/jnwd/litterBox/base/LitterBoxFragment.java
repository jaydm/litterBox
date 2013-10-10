
package net.jnwd.litterBox.base;

import net.jnwd.litterBox.contentProvider.Box;
import net.jnwd.litterBox.contentProvider.BoxContract;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class LitterBoxFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    private Context mContext;

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

        mCallbacks = this;
    }

    public LitterBoxFragment() {
        super();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        CursorLoader loader = null;

        switch (id) {
            case Box.Attribute_List:
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

                break;
            case Box.Attribute_Value_List:

                break;

            case Box.Attribute_Value_ID:

                break;
            case Box.Class_List:
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

                break;
            case Box.Class_Attribute_List:
                loader = new CursorLoader(
                        mContext,
                        BoxContract.ClassAttribute.Content_Uri,
                        BoxContract.ClassAttribute.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case Box.Class_Attribute_ID:

                break;
            case Box.Entity_List:
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

                break;
            case Box.Entity_Attribute_List:

                break;
            case Box.Entity_Attribute_ID:

                break;
            default:
                throw new IllegalArgumentException("Unsupported Loader ID: " + id);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int id = loader.getId();

        switch (id) {
            case Box.Attribute_List:

                break;
            case Box.Attribute_ID:

                break;
            case Box.Attribute_Value_List:

                break;

            case Box.Attribute_Value_ID:

                break;
            case Box.Class_List:

                break;
            case Box.Class_ID:

                break;
            case Box.Class_Attribute_List:

                break;
            case Box.Class_Attribute_ID:

                break;
            case Box.Entity_List:

                break;
            case Box.Entity_ID:

                break;
            case Box.Entity_Attribute_List:

                break;
            case Box.Entity_Attribute_ID:

                break;
            default:
                throw new IllegalArgumentException("Unsupported Loader ID: " + id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        int id = loader.getId();

        switch (id) {
            case Box.Attribute_List:

                break;
            case Box.Attribute_ID:

                break;
            case Box.Attribute_Value_List:

                break;

            case Box.Attribute_Value_ID:

                break;
            case Box.Class_List:

                break;
            case Box.Class_ID:

                break;
            case Box.Class_Attribute_List:

                break;
            case Box.Class_Attribute_ID:

                break;
            case Box.Entity_List:

                break;
            case Box.Entity_ID:

                break;
            case Box.Entity_Attribute_List:

                break;
            case Box.Entity_Attribute_ID:

                break;
            default:
                throw new IllegalArgumentException("Unsupported Loader ID: " + id);
        }
    }
}
