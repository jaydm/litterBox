
package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MaFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maintain_attributes);
    }
}
