package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LitterBox extends ListActivity {
	final private String TAG = "LitterBox";

	final private String[] menuChoices = {
		"Maintain Entities",
		"Maintain Classes",
		"Maintain Attributes"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_litter_box);

		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuChoices));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		switch (position) {
		case 0: // maintain entities
			Log.i(TAG, "Starting the maintain entities screen...");

			startActivity(new Intent(this, MaintainEntities.class));

			break;
		case 1: // maintain classes
			Log.i(TAG, "Starting the maintain classes screen...");

			startActivity(new Intent(this, MaintainClasses.class));

			break;
		case 2: // maintain attributes
			Log.i(TAG, "Starting the maintain attributes screen...");

			startActivity(new Intent(this, MaintainAttributes.class));

			break;
		default:
			// how did I get here?

			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.litter_box, menu);

		return true;
	}

}
