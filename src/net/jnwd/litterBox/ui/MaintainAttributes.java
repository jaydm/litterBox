
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
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public static class AttributePagerAdapter extends FragmentStatePagerAdapter {
        private final String Tag = "pager";

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
            Log.i(Tag, "Changing the tab...");

            Fragment page;

            page = pages.get(index);

            switch (index) {
                case 0:
                    if (page != null) {
                        // do those things that occur when returning to the
                        // existing page
                    } else {
                        page = new AttributeFragment();
                    }

                    break;
                default:
                    if (page != null) {
                        Log.i(Tag, "The value fragment is already created...");
                        Log.i(Tag,
                                "Reset the adapters (and fill in the attribute specific stuff...");

                        ((ValueFragment) page).resetAdapter();
                    } else {
                        page = new ValueFragment();
                    }

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

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);

            if (bundle == null) {
                // do first time things
            }
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // create the adapter to populate the attribute list
            String[] from = {
                    BoxContract.Attribute.showColumn
            };

            int[] to = {
                    android.R.id.text1
            };

            adapters.put(Box.Attribute_List, new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item, null, from, to, 0));

            Spinner attributeID = (Spinner) getActivity().findViewById(R.id.maAttributeID);

            attributeID.setAdapter(adapters.get(Box.Attribute_List));

            attributeID.setOnItemSelectedListener(this);

            getActivity().getLoaderManager().initLoader(Box.Attribute_List, null, this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle bundle) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab1, container, false);

            Spinner attributeType = (Spinner) rootView.findViewById(R.id.maAttributeType);

            ArrayAdapter<CharSequence> attributeTypeAdapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.attribute_types,
                    android.R.layout.simple_spinner_item);

            attributeTypeAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            attributeType.setAdapter(attributeTypeAdapter);

            Button saveAttribute = (Button) rootView.findViewById(R.id.maButton1);

            saveAttribute.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View view) {
            Spinner attributeType = (Spinner) getActivity().findViewById(
                    R.id.maAttributeType);

            if (attributeType.getSelectedItemId() == 0) {
                Log.e(Tag, "Attribute type is not set...");

                Toast.makeText(getActivity(), "You must select an attribute type",
                        Toast.LENGTH_LONG).show();

                return;
            }

            String typeDescription = attributeType.getSelectedItem().toString();

            EditText attributeDescription = (EditText) getActivity().findViewById(
                    R.id.maAttributeDesc);

            String description = attributeDescription.getText().toString().trim();

            if ("".equals(description)) {
                Log.e(Tag, "Description is not set...");

                Toast.makeText(getActivity(), "You must select an attribute description",
                        Toast.LENGTH_LONG).show();

                return;
            }

            LitterAttribute newAttribute = new LitterAttribute(description, typeDescription);

            ContentResolver resolver = getActivity().getContentResolver();

            resolver.insert(BoxContract.Attribute.Content_Uri, newAttribute.addNew());

            attributeDescription.setText("");

            getActivity().getLoaderManager().restartLoader(Box.Attribute_List, null, this);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((MaintainAttributes) getActivity()).setSelected(new LitterAttribute());

            String typeDescription = "";

            TextView showType = (TextView) getActivity().findViewById(R.id.maAttributeTypeLabel);

            ContentResolver resolver = getActivity().getContentResolver();

            Uri getAttribute = Uri.withAppendedPath(BoxContract.Attribute.Content_Uri,
                    String.valueOf(id));

            Cursor currentAttribute = resolver.query(getAttribute,
                    BoxContract.Attribute.allColumns, null, null, null);

            if ((currentAttribute != null) && (currentAttribute.moveToFirst())) {
                LitterAttribute attribute = new LitterAttribute(currentAttribute);

                ((MaintainAttributes) getActivity()).setSelected(attribute);

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

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);

            if (bundle == null) {
                // do first time things
            }
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            String[] from = {
                    BoxContract.AttributeValue.showColumn
            };

            int[] to = {
                    android.R.id.text1
            };

            adapters.put(Box.Attribute_Value_List, new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, null, from, to, 0));

            ListView values = (ListView) getActivity().findViewById(R.id.maAttributeValues);

            values.setAdapter(adapters.get(Box.Attribute_Value_List));

            values.setOnItemSelectedListener(this);

            getActivity().getLoaderManager().initLoader(Box.Attribute_Value_List, null, this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.maintain_attribute_tab2, container, false);

            Button addValueButton = (Button) rootView.findViewById(R.id.maButton2);

            addValueButton.setOnClickListener(this);

            return rootView;
        }

        private void resetAdapter() {
            if (getActivity() == null) {
                Log.e(Tag, "It is not...just return...");

                return;
            }

            LitterAttribute attribute = ((MaintainAttributes) getActivity()).getSelected();

            if (attribute == null) {
                Log.e(Tag, "Attribute not set...return...");

                return;
            }

            if (attribute.getId() == null) {
                Log.e(Tag, "Attribute is set...But, the ID is null...return...");

                return;
            }

            Log.i(Tag, "Current Attribute: " + attribute);

            TextView description = (TextView) getActivity().findViewById(R.id.maAttributeDescShow);

            description.setText(attribute.getDescription());

            EditText addValue = (EditText) getActivity().findViewById(R.id.maAttributeAddValue);

            addValue.setText("");
            addValue.setEnabled(false);

            if (attribute.isEnumerated()) {
                addValue.setEnabled(true);
            }

            mAttributeFilter = String.valueOf(attribute.getId());

            getActivity().getLoaderManager().restartLoader(Box.Attribute_Value_List, null, this);
        }

        @Override
        public void onClick(View v) {
            EditText addValue = (EditText) getActivity().findViewById(R.id.maAttributeAddValue);

            String value = addValue.getText().toString();

            if ("".equals(value)) {
                return;
            }

            ListView valueList = (ListView) getActivity().findViewById(R.id.maAttributeValues);

            LitterAttribute attribute = ((MaintainAttributes) getActivity()).getSelected();

            int valueCount = valueList.getCount();

            LitterAttributeValue newValue = new LitterAttributeValue(attribute.getId(),
                    (valueCount + 1) * 10, value);

            ContentResolver resolver = getActivity().getContentResolver();

            resolver.insert(BoxContract.AttributeValue.Content_Uri, newValue.addNew());

            addValue.setText("");

            mAttributeFilter = String.valueOf(attribute.getId());

            getActivity().getLoaderManager().restartLoader(Box.Attribute_Value_List, null, this);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
