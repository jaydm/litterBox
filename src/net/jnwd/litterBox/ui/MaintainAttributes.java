package net.jnwd.litterBox.ui;

import java.util.List;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MaintainAttributes extends Activity {
	private LitterDBase dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_maintain_attributes);

		dbHelper = new LitterDBase(this);
		dbHelper.open();

		Spinner attributes = (Spinner) findViewById(R.id.lstMaintainAttributeID);

		List<String> attributeList = dbHelper.getAttributeList();

		ArrayAdapter<String> attributeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, attributeList);

		attributes.setAdapter(attributeAdapter);

		Spinner attributeTypes = (Spinner) findViewById(R.id.listMaintainAttributeType);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.attribute_types, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		attributeTypes.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maintain_attributes, menu);

		return true;
	}

}
