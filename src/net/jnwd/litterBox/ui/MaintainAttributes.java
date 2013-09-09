
package net.jnwd.litterBox.ui;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MaintainAttributes extends FragmentActivity implements ActionBar.TabListener {
    private AttributePagerAdapter attributePager;

    private ViewPager mViewPager;

    protected final String TAG = "Maintain Attributes";
    protected final int myLayout = R.layout.activity_maintain_attributes;

    public LitterDBase dbHelper;

    private long selectedAttributeID;

    public LitterDBase getDbHelper() {
        return dbHelper;
    }

    public long getSelectedAttributeID() {
        return selectedAttributeID;
    }

    public void setSelectedAttributeID(long id) {
        Log.i(TAG, "Setting the selected attributeID to: " + id);

        selectedAttributeID = id;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new LitterDBase(this);
        dbHelper.open();

        setContentView(R.layout.activity_maintain_attributes);

        attributePager = new AttributePagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(attributePager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

                Log.i(TAG, "Moving to page: " + position);

                switch (position) {
                    case 0:
                        // main page (controller)
                        return;
                    case 1:
                        fillValues();

                        break;
                    default:
                        // if no special response
                        // do nothing
                        break;
                }
            }
        });

        for (int i = 0; i < attributePager.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(attributePager.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
            Fragment page;

            switch (i) {
                case 0:
                    page = new AttributeFragment();

                    return page;
                default:
                    page = new ValueFragment();

                    return page;
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
    public static class AttributeFragment extends Fragment implements OnItemSelectedListener {
        private final String TAG = "maAttribute";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab1, container, false);

            Spinner attributeType = (Spinner) rootView.findViewById(R.id.maAttributeType);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.attribute_types,
                    android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            attributeType.setAdapter(adapter);

            Button saveAttribute = (Button) rootView.findViewById(R.id.maButton1);

            saveAttribute.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Adding a new attribute...");

                    Log.i(TAG, "Added...clear the description field...");
                }

            });

            fillAttributes(rootView);

            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            MaintainAttributes activity = ((MaintainAttributes) getActivity());

            activity.setSelectedAttributeID(id);

            Cursor currentAttribute = activity.getDbHelper()
                    .getAttribute(id);

            TextView showType = (TextView) getActivity().findViewById(R.id.maAttributeTypeLabel);

            String label = getActivity().getResources().getString(R.string.ma_attribute_type_label);

            showType.setText(label + " (currently: " + currentAttribute.getString(currentAttribute
                    .getColumnIndex(LitterAttribute.column_Type)) + ")");
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

        private void fillAttributes(View view) {
            MaintainAttributes activity = ((MaintainAttributes) getActivity());

            Log.i(TAG, "Create reference to attribute spinner...");

            Spinner attributeID = (Spinner) view.findViewById(R.id.maAttributeID);

            Log.i(TAG, "Spinner is " + (attributeID == null ? "null" : "not null"));

            Log.i(TAG, "Create a cursor of all attributes...");

            Cursor attributeCursor = activity.getDbHelper().getAttributeList();

            Log.i(TAG, "Cursor is " + (attributeCursor == null ? "null" : "not null"));

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
            SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item, attributeCursor, from, to);

            Log.i(TAG, "Connect adapter to the spinner...");

            attributeID.setAdapter(attributeAdapter);

            attributeID.setOnItemSelectedListener(this);
        }
    }

    public static class ValueFragment extends Fragment {
        private final String TAG = "maValue";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab2, container, false);

            return rootView;
        }
    }

    private void fillValues() {
        ListView values = (ListView) findViewById(R.id.maAttributeValues);

        Log.i(TAG, "Loading the stored values for the attribute...");

        long attributeID = getSelectedAttributeID();

        Log.i(TAG, "Attribute ID (used to show values): " + attributeID);

        if (attributeID <= 0) {
            return;
        }

        Cursor attributeCursor = getDbHelper().getAttribute(attributeID);

        String attributeDescription = attributeCursor.getString(attributeCursor
                .getColumnIndex(LitterAttribute.column_Description));

        Log.i(TAG, "Setting display value of attribute name to: " + attributeDescription);

        boolean isEnumerated = attributeCursor.getString(
                attributeCursor.getColumnIndex(LitterAttribute.column_Type)).startsWith("enum");

        EditText addValue = (EditText) findViewById(R.id.maAttributeAddValue);

        addValue.setText("");
        addValue.setEnabled(false);

        if (isEnumerated) {
            addValue.setEnabled(true);
        }

        TextView description = (TextView) findViewById(R.id.maAttributeDescShow);

        description.setText(attributeDescription);

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

        Log.i(TAG, "Connect adapter to the spinner...");

        values.setAdapter(valueAdapter);

        values.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
}
