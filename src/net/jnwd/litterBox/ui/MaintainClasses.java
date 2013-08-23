package net.jnwd.litterBox.ui;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class MaintainClasses extends Activity implements OnItemSelectedListener {
	private final String TAG = "(Classes): ";

	private LitterDBase dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_maintain_classes);

		dbHelper = new LitterDBase(this);
		dbHelper.open();

		Button addClass = (Button) findViewById(R.id.btnAddClass);
		Button addAttribute = (Button) findViewById(R.id.btnAddAttribute);

		addClass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner selectedClass = (Spinner) findViewById(R.id.lstMaintainClassID);

				long classID = selectedClass.getSelectedItemId();

				Spinner addClass = (Spinner) findViewById(R.id.lstClassAddClassID);

				Long addClassID = addClass.getSelectedItemId();

				ListView listView = (ListView) findViewById(R.id.lstClassAttributes);

				String labelText = ((EditText) findViewById(R.id.txtClassAttributeLabel)).getText().toString();

				int count = listView.getCount();

				LitterClassAttribute newClassAttribute = new LitterClassAttribute(classID, (count + 1) * 10, addClassID, null, labelText);

				dbHelper.insertClassAttribute(newClassAttribute);

				fillClassAttributes(classID);
			}
		});

		addAttribute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner selectedClass = (Spinner) findViewById(R.id.lstMaintainClassID);

				long classID = selectedClass.getSelectedItemId();

				Spinner addAttribute = (Spinner) findViewById(R.id.lstClassAddAttributeID);

				Long addAttributeID = addAttribute.getSelectedItemId();

				ListView listView = (ListView) findViewById(R.id.lstClassAttributes);

				String labelText = ((EditText) findViewById(R.id.txtClassAttributeLabel)).getText().toString();

				int count = listView.getCount();

				LitterClassAttribute newClassAttribute = new LitterClassAttribute(classID, (count + 1) * 10, null, addAttributeID, labelText);

				dbHelper.insertClassAttribute(newClassAttribute);

				fillClassAttributes(classID);
			}
		});

		fillClasses();

		fillAddAttributes();
	}

	private void fillClasses() {
		Cursor classCursor = dbHelper.getClassList();

		Spinner classes = (Spinner) findViewById(R.id.lstMaintainClassID);

		Spinner addClasses = (Spinner) findViewById(R.id.lstClassAddClassID);

		String[] from = {
							LitterClass.showColumn
		};

		int[] to = {
					android.R.id.text1
		};

		@SuppressWarnings("deprecation") SimpleCursorAdapter classAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, classCursor, from, to);

		classes.setAdapter(classAdapter);
		addClasses.setAdapter(classAdapter);

		classes.setOnItemSelectedListener(this);
	}

	private void fillAddAttributes() {
		Cursor attributeCursor = dbHelper.getAttributeList();

		Spinner attributes = (Spinner) findViewById(R.id.lstClassAddAttributeID);

		String[] from = {
							LitterAttribute.showColumn
		};

		int[] to = {
					android.R.id.text1
		};

		@SuppressWarnings("deprecation") SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, attributeCursor, from, to);

		attributes.setAdapter(attributeAdapter);

		attributes.setOnItemSelectedListener(this);
	}

	private void fillClassAttributes(long classID) {
		Log.i(TAG, "In fillClassAttributes for class: " + classID);

		Log.i(TAG, "Calling getClassAttributes...");

		Cursor classAttributeCursor = dbHelper.getClassAttributes(classID);

		Log.i(TAG, "Creating a list to hold the class attributes...");

		List<String> classAttributes = new ArrayList<String>();

		Log.i(TAG, "Moving the cursor up to the beginning of the list...");

		Long classAttributeClassID;
		Long classAttributeAttributeID;
		String labelText;

		Log.i(TAG, "Beginning to spin through the classAttribute cursor...");

		while (! classAttributeCursor.isAfterLast()) {
			classAttributeClassID = classAttributeCursor.getLong(classAttributeCursor.getColumnIndex(LitterClassAttribute.column_ClassID));
			classAttributeAttributeID = classAttributeCursor.getLong(classAttributeCursor.getColumnIndex(LitterClassAttribute.column_AttributeID));
			labelText = classAttributeCursor.getString(classAttributeCursor.getColumnIndex(LitterClassAttribute.column_Label));

			Log.i(TAG, "Try to grab the sub-class (" + classAttributeClassID + ")...");

			if ((classAttributeClassID != null) && (classAttributeClassID != 0)) {
				Cursor showClass = dbHelper.getClass(classAttributeClassID);

				classAttributes.add(labelText + " (c): " + showClass.getString(showClass.getColumnIndex(LitterClass.column_Description)));
			}

			Log.i(TAG, "Try to grab the sub-attribute (" + classAttributeAttributeID + ")...");

			if ((classAttributeAttributeID != null) && (classAttributeAttributeID != 0)) {
				Cursor showAttribute = dbHelper.getAttribute(classAttributeAttributeID);

				classAttributes.add(labelText + " (a): " + showAttribute.getString(showAttribute.getColumnIndex(LitterAttribute.column_Description)));
			}

			classAttributeCursor.moveToNext();
		}

		Log.i(TAG, "Create the array adapter based on the array list...");

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classAttributes);

		Log.i(TAG, "Grab a reference to the listView...");

		ListView listView = (ListView) findViewById(R.id.lstClassAttributes);

		Log.i(TAG, "Connect the arrayAdapter to the listView...");

		listView.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintain_classes, menu);

		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() != R.id.lstMaintainClassID) {
			Log.i(TAG, "Ignore the item selected signal - wrong spinner...");

			return;
		}

		fillClassAttributes(id);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}