package net.jnwd.litterBox.base;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.ui.AddAttributes;
import net.jnwd.litterBox.ui.AddClasses;
import net.jnwd.litterBox.ui.AddEntities;
import net.jnwd.litterBox.ui.MaintainAttributes;
import net.jnwd.litterBox.ui.EditClasses;
import net.jnwd.litterBox.ui.EditEntities;
import net.jnwd.litterBox.ui.ViewAttributes;
import net.jnwd.litterBox.ui.ViewClasses;
import net.jnwd.litterBox.ui.ViewEntities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LitterBoxActivity extends Activity {
	protected final String TAG = "LitterBox";
	protected final int myLayout = R.layout.activity_litter_box;
	protected final int myMenu = R.menu.litter_box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(myLayout);

		setupActionBar();
	}

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(myMenu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
		case R.id.action_view_entities:
			intent = new Intent(this, ViewEntities.class);

			startActivity(intent);

			return true;
		case R.id.action_view_classes:
			intent = new Intent(this, ViewClasses.class);

			startActivity(intent);

			return true;
		case R.id.action_view_attributes:
			intent = new Intent(this, ViewAttributes.class);

			startActivity(intent);

			return true;
		case R.id.action_edit_entities:
			intent = new Intent(this, EditEntities.class);

			startActivity(intent);

			return true;
		case R.id.action_edit_classes:
			intent = new Intent(this, EditClasses.class);

			startActivity(intent);

			return true;
		case R.id.action_edit_attributes:
			intent = new Intent(this, MaintainAttributes.class);

			startActivity(intent);

			return true;
		case R.id.action_add_entities:
			intent = new Intent(this, AddEntities.class);

			startActivity(intent);

			return true;
		case R.id.action_add_classes:
			intent = new Intent(this, AddClasses.class);

			startActivity(intent);

			return true;
		case R.id.action_add_attributes:
			intent = new Intent(this, AddAttributes.class);

			startActivity(intent);

			return true;
		case R.id.action_settings:
			// currently unsupported :(
			// intent = new Intent(this, MaintainSettings.class);

			// startActivity(intent);

			return true;
		default:
			return true;
		}
	}
}
