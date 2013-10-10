
package net.jnwd.litterBox.ui;

import java.util.Locale;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.base.LitterBoxActivity;
import net.jnwd.litterBox.base.LitterBoxFragment;
import net.jnwd.litterBox.contentProvider.BoxContract;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import net.jnwd.litterBox.data.LitterClass;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class MaintainAttributes extends LitterBoxActivity implements ActionBar.TabListener {
    protected final String Tag = "Maintain Attributes";

    private AttributePagerAdapter attributePager;
    private ViewPager mViewPager;

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
        Log.i(Tag, "Setting the selected attributeID to: " + id);

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

                Log.i(Tag, "Moving to page: " + position);

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

        Log.i(Tag, "Selected tab is: " + tab.getPosition());
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
    public static class AttributeFragment extends LitterBoxFragment {
        private final String Tag = "maAttribute";

        private LoaderManager.LoaderCallbacks<Cursor> mAttributeCallbacks;

        private SimpleCursorAdapter mAllAttributesAdapter;
        
        private Context mContext;

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            
            mContext = activity;
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
                    Log.i(Tag, "Grabbing the value of the spinner...");

                    Spinner attributeType = (Spinner) getActivity().findViewById(
                            R.id.maAttributeType);

                    long typeID = attributeType.getSelectedItemId();
                    String typeDescription = attributeType.getSelectedItem().toString();

                    Log.i(Tag,
                            "Checking the description field to determine if this is a change to an existing attribute or a completely new one...");

                    EditText attributeDescription = (EditText) getActivity().findViewById(
                            R.id.maAttributeDesc);

                    String description = attributeDescription.getText().toString();

                    if ("".equals(description)) {
                        Log.i(Tag, "Updating the type of an existing attribute...");

                        if (typeID == 0) {
                            Log.i(Tag, "No type selected...Don't change anything...");

                            return;
                        }
                    } else {
                        Log.i(Tag, "Adding a new attribute...");

                        if (typeID == 0) {
                            Log.i(Tag, "No type selected...Don't add the new attribute...");

                            Toast.makeText(getActivity(), "You must select an attribute type",
                                    Toast.LENGTH_LONG).show();

                            return;
                        }

                        LitterAttribute newAttribute = new LitterAttribute(description,
                                typeDescription);

                        long newAttributeID = ((MaintainAttributes) getActivity()).dbHelper
                                .insertAttribute(newAttribute);

                        if (newAttributeID == 0) {
                            Log.i(Tag, "Exception trying to add new attribute...");
                        }

                        attributeDescription.setText("");
                    }

                    ((MaintainAttributes) getActivity()).fillAttributes();
                }
            });

            {
                MaintainAttributes activity = (MaintainAttributes) getActivity();

                Log.i(Tag, "Create reference to attribute spinner...");

                Spinner attributeID = (Spinner) rootView.findViewById(R.id.maAttributeID);

                Log.i(Tag, "Spinner is " + (attributeID == null ? "null" : "not null"));

                Log.i(Tag, "Create a cursor of all attributes...");

                String[] from = {
                        LitterAttribute.showColumn
                };

                int[] to = {
                        android.R.id.text1
                };

                Log.i(Tag, "Create the cursor adapter...");

                @SuppressWarnings("deprecation")
                SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(activity,
                        android.R.layout.simple_spinner_item, null, from, to);

                Log.i(Tag, "Connect adapter to the spinner...");

                attributeID.setAdapter(attributeAdapter);

                Log.i(Tag, "Creating the itemSelectedListener...");

                attributeID.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                            long id) {
                        MaintainAttributes activity = ((MaintainAttributes) getActivity());

                        activity.setSelectedAttributeID(id);

                        LitterAttribute currentAttribute = activity.getDbHelper().getAttribute(id);

                        TextView showType = (TextView) activity
                                .findViewById(R.id.maAttributeTypeLabel);

                        String label = getResources().getString(R.string.ma_attribute_type_label);

                        showType.setText(label + " (currently: " + currentAttribute.getType() + ")");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

                ((MaintainAttributes) getActivity()).setAttributesLoaded(true);
            }

            return rootView;
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader loader;
            
            switch (id) {
                case LitterBoxActivity.Loader_Entity_Data:

                    break;
                case LitterBoxActivity.Loader_Class_Data:
                    loader = new CursorLoader(
                            mContext,
                            BoxContract.Class.Content_Uri,
                            LitterClass.allColumns,
                            null,
                            null,
                            null
                            );
                    
                    break;
                case LitterBoxActivity.Loader_Class_Attribute_Data:
                    break;
                case LitterBoxActivity.Loader_Attribute_Data:
                    break;
                case LitterBoxActivity.Loader_Attribute_Value_Data:
                    break;
                default:
            }
        }
        
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            switch (loader.getId()) {
                case 
            }
        }
    }

    public static class ValueFragment extends Fragment {
        @SuppressWarnings("unused")
        private final String Tag = "maValue";

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
        Log.i(Tag, "Create reference to attribute spinner...");

        Spinner attributeID = (Spinner) findViewById(R.id.maAttributeID);

        Log.i(Tag, "Spinner is " + (attributeID == null ? "null" : "not null"));

        Log.i(Tag, "Create a cursor of all attributes...");

        Cursor attributeCursor = getDbHelper().getAttributeListCursor();

        Log.i(Tag, "Cursor is " + (attributeCursor == null ? "null" : "not null"));

        Log.i(Tag,
                "Create from and to references to connect the cursor to the spinner...");

        String[] from = {
                LitterAttribute.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        Log.i(Tag, "Create the cursor adapter...");

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, attributeCursor, from, to);

        Log.i(Tag, "Connect adapter to the spinner...");

        attributeID.setAdapter(attributeAdapter);

        Log.i(Tag, "Creating the itemSelectedListener...");

        attributeID.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSelectedAttributeID(id);

                LitterAttribute currentAttribute = getDbHelper().getAttribute(id);

                TextView showType = (TextView) findViewById(R.id.maAttributeTypeLabel);

                String label = getResources().getString(R.string.ma_attribute_type_label);

                showType.setText(label + " (currently: " + currentAttribute.getType() + ")");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        setAttributesLoaded(true);
    }

    private void fillValues() {
        ListView values = (ListView) findViewById(R.id.maAttributeValues);

        Log.i(Tag, "Loading the stored values for the attribute...");

        long attributeID = getSelectedAttributeID();

        Log.i(Tag, "Attribute ID (used to show values): " + attributeID);

        if (attributeID <= 0) {
            return;
        }

        LitterAttribute attribute = getDbHelper().getAttribute(attributeID);

        Log.i(Tag, "Setting display value of attribute name to: " + attribute.getDescription());

        boolean isEnumerated = attribute.getType().toLowerCase(Locale.getDefault())
                .startsWith("enum");

        EditText addValue = (EditText) findViewById(R.id.maAttributeAddValue);

        addValue.setText("");
        addValue.setEnabled(false);

        if (isEnumerated) {
            addValue.setEnabled(true);
        }

        TextView description = (TextView) findViewById(R.id.maAttributeDescShow);

        description.setText(attribute.getDescription());

        Cursor valueCursor = dbHelper.getAttributeValues(attributeID);

        Log.i(Tag, "Got the cursor? "
                + (valueCursor == null ? "Null!?!?!?" : "Cursor Okay!"));

        String[] from = {
                LitterAttributeValue.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        Log.i(Tag, "Create the cursor adapter...");

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter valueAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, valueCursor, from, to);

        Log.i(Tag, "Connect adapter to the spinner...");

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
