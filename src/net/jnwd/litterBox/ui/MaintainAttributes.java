
package net.jnwd.litterBox.ui;

import java.util.Locale;

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
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
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
import android.widget.Toast;

public class MaintainAttributes extends FragmentActivity implements ActionBar.TabListener {
    private AttributePagerAdapter attributePager;
    private ViewPager mViewPager;

    protected final String TAG = "Maintain Attributes";
    protected final int myLayout = R.layout.activity_maintain_attributes;

    public LitterDBase dbHelper;

    private long selectedAttributeID;
    private boolean attributesLoaded;

    public MaintainAttributes() {
        super();

        attributesLoaded = false;
    }

    private boolean areAttributesLoaded() {
        return attributesLoaded;
    }

    private void setAttributesLoaded(boolean loaded) {
        attributesLoaded = loaded;
    }

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

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        attributePager = new AttributePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(attributePager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

                Log.i(TAG, "Moving to page: " + position);

                switch (position) {
                    case 0:
                        if (!areAttributesLoaded()) {
                            fillAttributes();
                        }

                        break;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.litter_box, menu);

        return true;
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());

        Log.i(TAG, "Selected tab is: " + tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public static class AttributePagerAdapter extends FragmentStatePagerAdapter {
        private final int pageCount = 2;
        private final String[] pageTitle = {
                "Attributes",
                "Values"
        };

        SparseArray<Fragment> pages = new SparseArray<Fragment>();

        public AttributePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            Fragment page;

            page = pages.get(index);

            if (page != null) {
                return page;
            }

            switch (index) {
                case 0:
                    page = new AttributeFragment();

                    break;
                default:
                    page = new ValueFragment();

                    break;
            }

            pages.put(index, page);

            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

            pages.delete(position);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            @SuppressWarnings("unused")
            Locale l = Locale.getDefault();

            return pageTitle[position];
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class AttributeFragment extends Fragment {
        private final String TAG = "maAttribute";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);

            if (bundle == null) {
            }
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
                    Log.i(TAG, "Grabbing the value of the spinner...");

                    Spinner attributeType = (Spinner) getActivity().findViewById(
                            R.id.maAttributeType);

                    long typeID = attributeType.getSelectedItemId();
                    String typeDescription = attributeType.getSelectedItem().toString();

                    Log.i(TAG,
                            "Checking the description field to determine if this is a change to an existing attribute or a completely new one...");

                    EditText attributeDescription = (EditText) getActivity().findViewById(
                            R.id.maAttributeDesc);

                    String description = attributeDescription.getText().toString();

                    if ("".equals(description)) {
                        Log.i(TAG, "Updating the type of an existing attribute...");

                        if (typeID == 0) {
                            Log.i(TAG, "No type selected...Don't change anything...");

                            return;
                        }
                    } else {
                        Log.i(TAG, "Adding a new attribute...");

                        if (typeID == 0) {
                            Log.i(TAG, "No type selected...Don't add the new attribute...");

                            Toast.makeText(getActivity(), "You must select an attribute type",
                                    Toast.LENGTH_LONG).show();

                            return;
                        }

                        LitterAttribute newAttribute = new LitterAttribute(description,
                                typeDescription);

                        long newAttributeID = ((MaintainAttributes) getActivity()).dbHelper
                                .insertAttribute(newAttribute);

                        if (newAttributeID == 0) {
                            Log.i(TAG, "Exception trying to add new attribute...");
                        }

                        attributeDescription.setText("");
                    }

                    ((MaintainAttributes) getActivity()).fillAttributes();
                }
            });

            {
                MaintainAttributes activity = (MaintainAttributes) getActivity();

                Log.i(TAG, "Create reference to attribute spinner...");

                Spinner attributeID = (Spinner) rootView.findViewById(R.id.maAttributeID);

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
                SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(activity,
                        android.R.layout.simple_spinner_item, attributeCursor, from, to);

                Log.i(TAG, "Connect adapter to the spinner...");

                attributeID.setAdapter(attributeAdapter);

                Log.i(TAG, "Creating the itemSelectedListener...");

                attributeID.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                            long id) {
                        ((MaintainAttributes) getActivity()).setSelectedAttributeID(id);

                        Cursor currentAttribute = ((MaintainAttributes) getActivity())
                                .getDbHelper().getAttribute(id);

                        TextView showType = (TextView) ((MaintainAttributes) getActivity())
                                .findViewById(R.id.maAttributeTypeLabel);

                        String label = getResources().getString(R.string.ma_attribute_type_label);

                        showType.setText(label + " (currently: "
                                + currentAttribute.getString(currentAttribute
                                        .getColumnIndex(LitterAttribute.column_Type)) + ")");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

                ((MaintainAttributes) getActivity()).setAttributesLoaded(true);
            }

            return rootView;
        }

    }

    public static class ValueFragment extends Fragment {
        @SuppressWarnings("unused")
        private final String TAG = "maValue";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);

            if (bundle == null) {
                // do first time things
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab2, container, false);

            Button addValueButton = (Button) rootView.findViewById(R.id.maButton2);

            addValueButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    MaintainAttributes activity = (MaintainAttributes) getActivity();

                    EditText addValue = (EditText) activity.findViewById(R.id.maAttributeAddValue);

                    String value = addValue.getText().toString();

                    if ("".equals(value)) {
                        return;
                    }

                    ListView valueList = (ListView) activity.findViewById(R.id.maAttributeValues);

                    long attributeID = activity.getSelectedAttributeID();

                    int valueCount = valueList.getCount();

                    LitterAttributeValue newValue = new LitterAttributeValue(attributeID,
                            (valueCount + 1) * 10, value);

                    activity.dbHelper.insertAttributeValue(newValue);

                    addValue.setText("");

                    ((MaintainAttributes) getActivity()).fillValues();
                }

            });

            return rootView;
        }
    }

    private void fillAttributes() {
        Log.i(TAG, "Create reference to attribute spinner...");

        Spinner attributeID = (Spinner) findViewById(R.id.maAttributeID);

        Log.i(TAG, "Spinner is " + (attributeID == null ? "null" : "not null"));

        Log.i(TAG, "Create a cursor of all attributes...");

        Cursor attributeCursor = getDbHelper().getAttributeList();

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
        SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, attributeCursor, from, to);

        Log.i(TAG, "Connect adapter to the spinner...");

        attributeID.setAdapter(attributeAdapter);

        Log.i(TAG, "Creating the itemSelectedListener...");

        attributeID.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSelectedAttributeID(id);

                Cursor currentAttribute = getDbHelper().getAttribute(id);

                TextView showType = (TextView) findViewById(R.id.maAttributeTypeLabel);

                String label = getResources().getString(R.string.ma_attribute_type_label);

                showType.setText(label + " (currently: "
                        + currentAttribute.getString(currentAttribute
                                .getColumnIndex(LitterAttribute.column_Type)) + ")");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        setAttributesLoaded(true);
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
                attributeCursor.getColumnIndex(LitterAttribute.column_Type))
                .toLowerCase(Locale.getDefault())
                .startsWith("enum");

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
