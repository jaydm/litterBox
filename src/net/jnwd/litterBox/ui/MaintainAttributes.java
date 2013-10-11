
package net.jnwd.litterBox.ui;

import java.util.Locale;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.base.LitterBoxActivity;
import net.jnwd.litterBox.base.LitterBoxFragment;
import net.jnwd.litterBox.contentProvider.Box;
import net.jnwd.litterBox.contentProvider.BoxContract;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterAttributeValue;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
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

    private LitterAttribute selected;

    public MaintainAttributes() {
        super();

        selected = new LitterAttribute();
    }

    public LitterAttribute getSelected() {
        return selected;
    }

    public void setSelected(LitterAttribute selected) {
        this.selected = selected;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                        break;
                    case 1:

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

            switch (index) {
                case 0:
                    page = (page != null ? page : new AttributeFragment());

                    break;
                default:
                    page = (page != null ? page : new ValueFragment());

                    ((ValueFragment) page).resetAdapter();

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

    public static class AttributeFragment extends LitterBoxFragment implements OnClickListener,
            OnItemSelectedListener {
        private final String Tag = "maAttribute";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onCreate(Bundle bundle) {
            Log.i(Tag, "Creating the attribute fragment...");

            Log.i(Tag, "Calling the super class onCreate...");

            super.onCreate(bundle);

            mCallbacks = this;

            if (bundle == null) {
                // do first time things
            }

            Log.i(Tag, "Create the adapter for the attribute list...");

            {
                // create the adapter to populate the attribute list
                String[] from = {
                        BoxContract.Attribute.showColumn
                };

                int[] to = {
                        android.R.id.text1
                };

                adapters.put(Box.Attribute_List, new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_spinner_item, null, from, to));

                try {
                    Log.i(Tag, "Initializing the loader for the attribute list...");

                    getActivity().getLoaderManager().initLoader(Box.Attribute_List, null,
                            mCallbacks);
                } catch (Exception e) {
                    Log.e(Tag, "Exception initializing the loader: " + e.getMessage());
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Log.i(Tag, "Create the root view of the attribute fragment...");

            View rootView = inflater.inflate(R.layout.maintain_attribute_tab1, container, false);

            Log.i(Tag, "Get a reference to the attribute type spinner on the page...");

            Spinner attributeType = (Spinner) rootView.findViewById(R.id.maAttributeType);

            Log.i(Tag, "Build an array adapter against the attribute type xml file...");

            ArrayAdapter<CharSequence> attributeTypeAdapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.attribute_types,
                    android.R.layout.simple_spinner_item);

            attributeTypeAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Log.i(Tag, "Attach the array adapter to the attribute type spinner...");

            attributeType.setAdapter(attributeTypeAdapter);

            Log.i(Tag, "Get a reference to the save button...");

            Button saveAttribute = (Button) rootView.findViewById(R.id.maButton1);

            Log.i(Tag, "Set the listener to the attribute fragment...");

            saveAttribute.setOnClickListener(this);

            {
                Log.i(Tag, "Create reference to attribute spinner...");

                Spinner attributeID = (Spinner) rootView.findViewById(R.id.maAttributeID);

                Log.i(Tag, "Spinner is " + (attributeID == null ? "null" : "not null"));

                Log.i(Tag, "Adapter is "
                        + (adapters.get(Box.Attribute_List) == null ? "null" : "not null"));

                attributeID.setAdapter(adapters.get(Box.Attribute_List));

                Log.i(Tag, "Creating the itemSelectedListener...");

                attributeID.setOnItemSelectedListener(this);
            }

            return rootView;
        }

        @Override
        public void onClick(View view) {
            Log.i(Tag, "Grabbing the value of the spinner...");

            Spinner attributeType = (Spinner) getActivity().findViewById(
                    R.id.maAttributeType);

            if (attributeType.getSelectedItemId() == 0) {
                Log.i(Tag, "No type selected...Change nothing...");

                Toast.makeText(getActivity(), "You must select an attribute type",
                        Toast.LENGTH_LONG).show();

                return;
            }

            String typeDescription = attributeType.getSelectedItem().toString();

            Log.i(Tag,
                    "Checking the description field to determine if this is a change to an existing attribute or a completely new one...");

            EditText attributeDescription = (EditText) getActivity().findViewById(
                    R.id.maAttributeDesc);

            String description = attributeDescription.getText().toString().trim();

            if ("".equals(description)) {
                Log.i(Tag, "No description set...Do not add...");

                Toast.makeText(getActivity(), "You must select an attribute description",
                        Toast.LENGTH_LONG).show();

                return;
            }

            Log.i(Tag, "Creating a new attribute...");

            LitterAttribute newAttribute = new LitterAttribute(description, typeDescription);

            Log.i(Tag, "Getting a reference to the content resolver...");

            ContentResolver resolver = getActivity().getContentResolver();

            Log.i(Tag, "Attempting to insert the new attribute into the resolver...");

            resolver.insert(BoxContract.Attribute.Content_Uri, newAttribute.addNew());

            Log.i(Tag, "Successfully inserted...Clear the description...");

            attributeDescription.setText("");
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(Tag, "Pull the currently selected attribute ID from the activity...");

            ((MaintainAttributes) getActivity()).setSelected(new LitterAttribute());

            String typeDescription = "";

            Log.i(Tag, "Get the reference to the type field (to echo the attribue type...");

            TextView showType = (TextView) getActivity().findViewById(R.id.maAttributeTypeLabel);

            Log.i(Tag, "Get a reference to the content resolver...");

            ContentResolver resolver = getActivity().getContentResolver();

            Log.i(Tag, "Build the URI to load the currently selected attribute...");

            Uri getAttribute = Uri.withAppendedPath(BoxContract.Attribute.Content_Uri,
                    String.valueOf(id));

            Log.i(Tag, "Now get a cursor pointing at it...");

            Cursor currentAttribute = resolver.query(getAttribute,
                    BoxContract.Attribute.allColumns, null, null, null);

            if ((currentAttribute != null) && (currentAttribute.moveToFirst())) {
                LitterAttribute attribute = new LitterAttribute(currentAttribute);

                ((MaintainAttributes) getActivity()).setSelected(attribute);

                Log.i(Tag, "Get the attribute type label...");

                String label = getResources().getString(R.string.ma_attribute_type_label);

                typeDescription = "" +
                        label + " " +
                        "(currently: " +
                        attribute.getType() +
                        ")";
            }

            showType.setText(typeDescription);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    public static class ValueFragment extends LitterBoxFragment implements OnClickListener,
            OnItemSelectedListener {
        private final String Tag = "maValue";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onCreate(Bundle bundle) {
            Log.i(Tag, "Creating the value fragment...");

            Log.i(Tag, "Calling the super class onCreate...");

            super.onCreate(bundle);

            mCallbacks = this;

            if (bundle == null) {
                // do first time things
            }

            Log.i(Tag, "Create the adapter for the values list...");

            {
                String[] from = {
                        BoxContract.AttributeValue.showColumn
                };

                int[] to = {
                        android.R.id.text1
                };

                adapters.put(Box.Attribute_Value_List, new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, null, from, to));
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Log.i(Tag, "Create the root view of the values fragment...");

            View rootView = inflater.inflate(R.layout.maintain_attribute_tab2, container, false);

            Log.i(Tag, "Create a reference to the add value button...");

            Button addValueButton = (Button) rootView.findViewById(R.id.maButton2);

            addValueButton.setOnClickListener(this);

            getActivity().getLoaderManager().initLoader(Box.Attribute_Value_List, null, mCallbacks);

            {
                Log.i(Tag, "Load all of the attribute values for the selected attribute...");

                Log.i(Tag, "Get a reference to the values listview...");

                ListView values = (ListView) getActivity().findViewById(R.id.maAttributeValues);

                Log.i(Tag, "Load the stored current selected attribute ID...");

                LitterAttribute attribute = ((MaintainAttributes) getActivity()).getSelected();

                Log.i(Tag,
                        "Setting display value of attribute name to: " + attribute.getDescription());

                EditText addValue = (EditText) getActivity().findViewById(R.id.maAttributeAddValue);

                addValue.setText("");
                addValue.setEnabled(false);

                if (attribute.isEnumerated()) {
                    addValue.setEnabled(true);
                }

                TextView description = (TextView) getActivity().findViewById(
                        R.id.maAttributeDescShow);

                description.setText(attribute.getDescription());

                Log.i(Tag, "Connect adapter to the spinner...");

                values.setAdapter(adapters.get(Box.Attribute_Value_List));

                values.setOnItemSelectedListener(this);
            }

            return rootView;
        }

        private void resetAdapter() {
            LitterAttribute attribute = ((MaintainAttributes) getActivity()).getSelected();

            adapters.put(Box.Attribute_Value_List, null);

            EditText addValue = (EditText) getActivity().findViewById(R.id.maAttributeAddValue);

            addValue.setText("");
            addValue.setEnabled(false);

            if (attribute.isEnumerated()) {
                addValue.setEnabled(true);
            }

            TextView description = (TextView) getActivity().findViewById(R.id.maAttributeDescShow);

            description.setText(attribute.getDescription());
        }

        @Override
        public void onClick(View v) {
            EditText addValue = (EditText) getActivity().findViewById(R.id.maAttributeAddValue);

            String value = addValue.getText().toString();

            if ("".equals(value)) {
                return;
            }

            Log.i(Tag, "Create a reference to the list view...");

            ListView valueList = (ListView) getActivity().findViewById(R.id.maAttributeValues);

            Log.i(Tag, "Grab the selected attribute...");

            LitterAttribute attribute = ((MaintainAttributes) getActivity()).getSelected();

            Log.i(Tag, "Get the count of values...");

            int valueCount = valueList.getCount();

            Log.i(Tag, "Create the new attribute value...");

            LitterAttributeValue newValue = new LitterAttributeValue(attribute.getId(),
                    (valueCount + 1) * 10, value);

            Log.i(Tag, "Getting a reference to the content resolver...");

            ContentResolver resolver = getActivity().getContentResolver();

            Log.i(Tag, "Attempting to insert the new attribute into the resolver...");

            resolver.insert(BoxContract.AttributeValue.Content_Uri, newValue.addNew());

            addValue.setText("");
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
