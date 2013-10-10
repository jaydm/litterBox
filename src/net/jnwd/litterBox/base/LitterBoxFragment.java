
package net.jnwd.litterBox.base;

import net.jnwd.litterBox.contentProvider.BoxContract;
import net.jnwd.litterBox.data.LitterClass;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class LitterBoxFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String Tag = "lbFragment";

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
    }

    public LitterBoxFragment() {
        super();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        CursorLoader loader = null;

        switch (id) {
            case LitterBoxActivity.Loader_Entity_Data:

                break;
            case LitterBoxActivity.Loader_Class_Data:
                loader = new CursorLoader(
                        mContext,
                        BoxContract.Class.Content_Uri,
                        LitterClass.allColumns,
                        null,
                        null,
                        null
                        );

                break;
            case LitterBoxActivity.Loader_Class_Attribute_Data:
                break;
            case LitterBoxActivity.Loader_Attribute_Data:
                break;
            case LitterBoxActivity.Loader_Attribute_Value_Data:
                break;
            default:
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursor) {

    }
}
