
package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MaTab1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        // return super.onCreateView(inflater, container, savedInstanceState);

        return (LinearLayout) inflater.inflate(R.layout.tab_frag1_layout, container, false);
    }
}
