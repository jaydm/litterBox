
package net.jnwd.litterBox.base;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.ui.MaintainAttributes;
import net.jnwd.litterBox.ui.MaintainClasses;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class LitterBoxActivity extends FragmentActivity {
    protected final String TAG = "LitterBox";
    protected final int myLayout = R.layout.litter_box;
    protected final int myMenu = R.menu.litter_box;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(myMenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_maintain_entities:
                // intent = new Intent(getBaseContext(),MaintainEntities.class);

                // startActivity(intent);

                return true;
            case R.id.action_maintain_classes:
                intent = new Intent(getBaseContext(), MaintainClasses.class);

                startActivity(intent);

                return true;
            case R.id.action_maintain_attributes:
                intent = new Intent(getBaseContext(), MaintainAttributes.class);

                startActivity(intent);

                return true;
            default:
                return true;
        }
    }
}
