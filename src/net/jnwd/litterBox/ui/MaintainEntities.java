package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MaintainEntities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_entities);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintain_entities, menu);
		return true;
	}

}
