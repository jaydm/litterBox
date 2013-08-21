package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterClass;
import net.jnwd.litterBox.data.LitterClassAttribute;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class MaintainClasses extends Activity implements OnItemSelectedListener {
	private final String TAG = "Maintain Classes";

	private LitterDBase dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_maintain_classes);

		Log.i(TAG, "Get database connection...");

		dbHelper = new LitterDBase(this);
		dbHelper.open();

		Button addValue = (Button) findViewById(R.id.btnAddAttribute);

		addValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner selectedClass = (Spinner) findViewById(R.id.lstMaintainClassID);

				long classID = selectedClass.getSelectedItemId();

				Spinner addClass = (Spinner) findViewById(R.id.lstClassAddClassID);

				Long addClassID = addClass.getSelectedItemId();

				Spinner addAttribute = (Spinner) findViewById(R.id.lstClassAddAttributeID);

				Long addAttributeID = addAttribute.getSelectedItemId();

				ListView listView = (ListView) findViewById(R.id.lstClassAttributes);

				int count = listView.getCount();

				// LitterClassAttribute(Long parentID, long sequence, Long
				// classID, Long attributeID)

				LitterClassAttribute newClassAttribute = new LitterClassAttribute(classID, (count + 1) * 10, addClassID, addAttributeID);

				dbHelper.insertClassAttribute(newClassAttribute);

				fillClassAttributes(classID);
			}
		});

		fillClasses();
	}

	private void fillClasses() {
		Log.i(TAG, "Create reference to class spinner...");

		Spinner classes = (Spinner) findViewById(R.id.lstMaintainClassID);

		Log.i(TAG, "Create a cursor of all classes...");

		Cursor classCursor = dbHelper.getClassList();

		Log.i(TAG, "Create from and to references to connect the cursor to the spinner...");

		String[] from = {
							LitterClass.showColumn
		};

		int[] to = {
					android.R.id.text1
		};

		Log.i(TAG, "Create the cursor adapter...");

		@SuppressWarnings("deprecation") SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, classCursor, from, to);

		Log.i(TAG, "Connect adapter to the spinner...");

		classes.setAdapter(attributeAdapter);

		classes.setOnItemSelectedListener(this);

		fillAddClasses();
		fillAddAttributes();
	}

	private void fillAddClasses() {
		Log.i(TAG, "Create reference to add class spinner...");

		Spinner classes = (Spinner) findViewById(R.id.lstClassAddClassID);

		Log.i(TAG, "Create a cursor of all classes...");

		Cursor classCursor = dbHelper.getClassList();

		Log.i(TAG, "Create from and to references to connect the cursor to the spinner...");

		String[] from = {
							LitterClass.showColumn
		};

		int[] to = {
					android.R.id.text1
		};

		Log.i(TAG, "Create the cursor adapter...");

		@SuppressWarnings("deprecation") SimpleCursorAdapter classAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, classCursor, from, to);

		Log.i(TAG, "Connect adapter to the spinner...");

		classes.setAdapter(classAdapter);

		classes.setOnItemSelectedListener(this);
	}

	private void fillAddAttributes() {
		Log.i(TAG, "Create reference to attribute spinner...");

		Spinner attributes = (Spinner) findViewById(R.id.lstClassAddAttributeID);

		Log.i(TAG, "Create a cursor of all attributes...");

		Cursor attributeCursor = dbHelper.getClassList();

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

	private void fillClassAttributes(long classID) {
		Log.i(TAG, "Loading the class/attribute list for this class...");

		@SuppressWarnings("unused") Cursor classAttributeCursor = dbHelper.getClassAttributes(classID);

		@SuppressWarnings("unused") ListView listView = (ListView) findViewById(R.id.lstClassAttributes);

		// listView.setAdapter(classAttributeAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintain_classes, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.lstAttributeValues) {
			return;
		}

		Log.i(TAG, "Get the selected class row: " + id);

		Cursor attribute = dbHelper.getAttribute(id);

		if (attribute == null) {
			Log.i(TAG, "The cursor is null!!!");

			return;
		}

		this.fillClassAttributes(id);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}
