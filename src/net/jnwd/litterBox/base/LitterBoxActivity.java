
package net.jnwd.litterBox.base;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.ui.MaintainAttributes;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LitterBoxActivity extends Activity {
    protected final String TAG = "LitterBox";
    protected final int myLayout = R.layout.litter_box;
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
            case R.id.action_edit_entities:
                return true;
            case R.id.action_edit_classes:
                return true;
            case R.id.action_edit_attributes:
                intent = new Intent(this, MaintainAttributes.class);

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
