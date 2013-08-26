package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddAttribute extends Activity {
	public final String TAG = "(AddAttribute)";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_attribute);

		Spinner spinner = (Spinner) findViewById(R.id.spinAddAttributeType);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.attribute_types, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);

		Button saveAttribute = (Button) findViewById(R.id.btnAddNewAttribute);

		saveAttribute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "Adding a new attribute...");

				Log.i(TAG, "Added...clear the description field...");
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_attribute, menu);
		return true;
	}

}
