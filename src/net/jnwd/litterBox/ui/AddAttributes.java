package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.base.LitterBoxActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddAttributes extends LitterBoxActivity {
	protected final String TAG = "Add Attributes";
	protected final int myLayout = R.layout.activity_add_attribute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Spinner spinner = (Spinner) findViewById(R.id.spinAddAttributeType);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.attribute_types,
				android.R.layout.simple_spinner_item);

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
}
