
package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.base.LitterBoxActivity;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import net.jnwd.litterBox.data.LitterDBase;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MaintainAttributes extends LitterBoxActivity implements OnItemSelectedListener {
    protected final String TAG = "Maintain Attributes";
    protected final int myLayout = R.layout.activity_maintain_attributes;

    private LitterDBase dbHelper;

    private long selectedAttribute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Get database connection...");

        dbHelper = new LitterDBase(this);
        dbHelper.open();

        fillAttributes();
    }

    private void fillAttributes() {
        Log.i(TAG, "Create reference to attribute spinner...");

        Log.i(TAG, "Create a cursor of all attributes...");

        Cursor attributeCursor = dbHelper.getAttributeList();

        Log.i(TAG,
                "Create from and to references to connect the cursor to the spinner...");

        String[] from = {
                LitterAttribute.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        Log.i(TAG, "Create the cursor adapter...");

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, attributeCursor, from, to);

        Log.i(TAG, "Connect adapter to the spinner...");
    }

    private void fillValues(long attributeID) {
        Log.i(TAG, "Loading the stored values for the attribute...");

        Cursor valueCursor = dbHelper.getAttributeValues(attributeID);

        Log.i(TAG, "Got the cursor? "
                + (valueCursor == null ? "Null!?!?!?" : "Cursor Okay!"));

        String[] from = {
                LitterAttributeValue.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        Log.i(TAG, "Create the cursor adapter...");

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter valueAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, valueCursor, from, to);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedAttribute = id;

        Log.i(TAG, "Get the selected attribute row: " + id);

        Cursor attribute = dbHelper.getAttribute(id);

        if (attribute == null) {
            Log.i(TAG, "The cursor is null!!!");

            return;
        }

        String type = attribute.getString(attribute
                .getColumnIndex(LitterAttribute.column_Type));

        Log.i(TAG, "Got type: " + type);

        fillValues(id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
