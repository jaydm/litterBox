
package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MaintainAttributes extends FragmentActivity implements ActionBar.TabListener,
        OnItemSelectedListener {
    AttributePagerAdapter attributePager;

    ViewPager mViewPager;

    protected final String TAG = "Maintain Attributes";
    protected final int myLayout = R.layout.activity_maintain_attributes;

    private LitterDBase dbHelper;

    private long selectedAttribute = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_attributes);

        attributePager = new AttributePagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(attributePager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < attributePager.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter.
            // Also specify this Activity object, which implements the
            // TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(attributePager.getPageTitle(i))
                            .setTabListener(this));
        }

        Log.i(TAG, "Get database connection...");

        dbHelper = new LitterDBase(this);
        dbHelper.open();

        fillAttributes();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public static class AttributePagerAdapter extends FragmentPagerAdapter {
        private final int pageCount = 2;
        private final String[] pageTitle = {
                "Attributes",
                "Values"
        };

        public AttributePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new AttributeFragment();
                default:
                    return new ValueFragment();
            }
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class AttributeFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab1, container, false);

            return rootView;
        }
    }

    public static class ValueFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab2, container, false);

            return rootView;
        }
    }

    private void fillAttributes() {
        Log.i(TAG, "Create reference to attribute spinner...");

        Log.i(TAG, "Create a cursor of all attributes...");

        Cursor attributeCursor = dbHelper.getAttributeList();

        Log.i(TAG,
                "Create from and to references to connect the cursor to the spinner...");

        String[] from = {
                LitterAttribute.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        Log.i(TAG, "Create the cursor adapter...");

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, attributeCursor, from, to);

        Log.i(TAG, "Connect adapter to the spinner...");
    }

    private void fillValues(long attributeID) {
        Log.i(TAG, "Loading the stored values for the attribute...");

        Cursor valueCursor = dbHelper.getAttributeValues(attributeID);

        Log.i(TAG, "Got the cursor? "
                + (valueCursor == null ? "Null!?!?!?" : "Cursor Okay!"));

        String[] from = {
                LitterAttributeValue.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        Log.i(TAG, "Create the cursor adapter...");

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter valueAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, valueCursor, from, to);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedAttribute = id;

        Log.i(TAG, "Get the selected attribute row: " + id);

        Cursor attribute = dbHelper.getAttribute(id);

        if (attribute == null) {
            Log.i(TAG, "The cursor is null!!!");

            return;
        }

        String type = attribute.getString(attribute
                .getColumnIndex(LitterAttribute.column_Type));

        Log.i(TAG, "Got type: " + type);

        fillValues(id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
