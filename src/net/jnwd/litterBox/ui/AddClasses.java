package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.base.LitterBoxActivity;
import net.jnwd.litterBox.data.LitterClass;
import net.jnwd.litterBox.data.LitterDBase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddClasses extends LitterBoxActivity {
	protected final String TAG = "Add Classes";
	protected final int myLayout = R.layout.activity_add_class;

	private LitterDBase dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Button saveClass = (Button) findViewById(R.id.btnAddClassSave);

		saveClass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "Adding a new class...");

				Log.i(TAG, "Grabbing description...");

				EditText newDescription = (EditText) findViewById(R.id.txtAddClassDescription);

				Log.i(TAG, "Creating new class: " + newDescription.getText());

				LitterClass litterClass = new LitterClass(newDescription
						.getText().toString());

				Log.i(TAG, "Class values: " + litterClass);

				Log.i(TAG, "Attempt to insert the class...");

				dbHelper.insertClass(litterClass);

				Log.i(TAG, "Finished adding the class...");

				newDescription.setText("");

				Log.i(TAG, "Added...clear the description field...");
			}

		});
	}
}
