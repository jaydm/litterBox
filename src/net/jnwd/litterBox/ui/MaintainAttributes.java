package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MaintainAttributes extends Activity implements OnItemSelectedListener {
	private final String TAG = "Maintain Attributes";

	private LitterDBase dbHelper;

	private long selectedAttribute = - 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_maintain_attributes);

		Log.i(TAG, "Get database connection...");

		dbHelper = new LitterDBase(this);
		dbHelper.open();

		Button addValue = (Button) findViewById(R.id.btnAddAttributeValue);

		addValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText addValue = (EditText) findViewById(R.id.newAttributeValue);
				ListView listView = (ListView) findViewById(R.id.lstAttributeValues);

				int count = listView.getCount();

				LitterAttributeValue newValue = new LitterAttributeValue(selectedAttribute, (count + 1) * 10, addValue.getText().toString());

				dbHelper.insertAttributeValue(newValue);

				addValue.setText(null);

				fillValues(selectedAttribute);
			}
		});

		fillAttributes();
	}

	private void fillAttributes() {
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

		@SuppressWarnings("deprecation") SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, attributeCursor, from, to);

		Log.i(TAG, "Connect adapter to the spinner...");

		attributes.setAdapter(attributeAdapter);

		attributes.setOnItemSelectedListener(this);
	}

	private void fillValues(long attributeID) {
		Log.i(TAG, "Loading the stored values for the attribute...");

		Cursor valueCursor = dbHelper.getAttributeValues(attributeID);

		Log.i(TAG, "Got the cursor? " + (valueCursor == null ? "Null!?!?!?" : "Cursor Okay!"));

		String[] from = {
							LitterAttributeValue.showColumn
		};

		int[] to = {
					android.R.id.text1
		};

		Log.i(TAG, "Create the cursor adapter...");

		@SuppressWarnings("deprecation") SimpleCursorAdapter valueAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, valueCursor, from, to);

		ListView listView = (ListView) findViewById(R.id.lstAttributeValues);

		listView.setAdapter(valueAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintain_attributes, menu);

		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() != R.id.lstMaintainAttributeID) {
			Log.i(TAG,"Ignore the item selected signal - wrong spinner...");
			
			return;
		}

		selectedAttribute = id;

		Log.i(TAG, "Get the selected attribute row: " + id);

		Cursor attribute = dbHelper.getAttribute(id);

		if (attribute == null) {
			Log.i(TAG, "The cursor is null!!!");

			return;
		}

		String type = attribute.getString(attribute.getColumnIndex(LitterAttribute.column_Type));

		Log.i(TAG, "Got type: " + type);

		EditText addValue = (EditText) findViewById(R.id.newAttributeValue);

		addValue.setEnabled(false);

		if (type.startsWith("enum")) {
			addValue.setEnabled(true);
		}

		TextView attributeType = (TextView) findViewById(R.id.txtMaintainAttributeType);

		attributeType.setText("Type: " + type.toString());

		fillValues(id);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}
