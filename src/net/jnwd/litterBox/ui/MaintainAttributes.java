package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MaintainAttributes extends Activity implements OnItemSelectedListener {
	private final String TAG = "Maintain Attributes";

	private LitterDBase dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_maintain_attributes);

		Log.i(TAG, "Get database connection...");

		dbHelper = new LitterDBase(this);
		dbHelper.open();

		Log.i(TAG, "Create reference to attribute spinner...");

		Spinner attributes = (Spinner) findViewById(R.id.lstMaintainAttributeID);

		Log.i(TAG, "Create a cursor of all attributes...");

		Cursor attributeCursor = dbHelper.getAttributeList();

		Log.i(TAG, "Create from and to references to connect the cursor to the spinner...");

		String[] from = {
							LitterAttribute.showColumn
		};

		int[] to = {
					android.R.id.text1
		};

		Log.i(TAG, "Create the cursor adapter...");

		SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, attributeCursor, from, to);

		Log.i(TAG, "Connect adapter to the spinner...");

		attributes.setAdapter(attributeAdapter);

		attributes.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.i(TAG, "Get the selected attribute row: " + id);

				Cursor selectedAttribute = dbHelper.getAttribute(id);

				// Log.i(TAG, "Try to pull out the type column...");

				// String type =
				// selectedAttribute.getString(selectedAttribute.getColumnIndex(LitterAttribute.column_Type));

				// Log.i(TAG, "Got type: " + type);

				// Toast.makeText(parent.getContext(), "Selected ID: " + id +
				// " type: " + type, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		Log.i(TAG, "Create a reference to the attribute type spinner...");

		Spinner attributeTypes = (Spinner) findViewById(R.id.listMaintainAttributeType);

		Log.i(TAG, "Create the arrayAdapter connected to the resource file...");

		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.attribute_types, android.R.layout.simple_spinner_item);

		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Log.i(TAG, "Connect the adapter to the attribute type spinner...");

		attributeTypes.setAdapter(typeAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintain_attributes, menu);

		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
