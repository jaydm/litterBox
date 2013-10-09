
package net.jnwd.litterBox.base;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.ui.MaintainAttributes;
import net.jnwd.litterBox.ui.MaintainClasses;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class LitterBoxActivity extends FragmentActivity {
    protected final String Tag = "LitterBox";
    protected final int myLayout = R.layout.litter_box;
    protected final int myMenu = R.menu.litter_box;

    public static final int Loader_Entity_Data = 0;
    public static final int Loader_Class_Data = 1;
    public static final int Loader_Attribute_Data = 2;
    public static final int Loader_Class_Attribute_Data = 3;
    public static final int Loader_Attribute_Value_Data = 4;

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
