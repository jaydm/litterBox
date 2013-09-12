
package net.jnwd.litterBox.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.base.LitterBoxActivity;
import net.jnwd.litterBox.data.LitterAttribute;
import net.jnwd.litterBox.data.LitterClass;
import net.jnwd.litterBox.data.LitterClassAttribute;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

public class MaintainClasses extends LitterBoxActivity implements ActionBar.TabListener {
    ClassPagerAdapter classPager;
    ViewPager mViewPager;

    protected final String TAG = "Maintain Classes";
    protected final int myLayout = R.layout.activity_maintain_classes;

    public LitterDBase dbHelper;

    private long selectedClass;
    private boolean classesLoaded;

    public MaintainClasses() {
        super();

        classesLoaded = false;
    }

    private boolean areClassesLoaded() {
        return classesLoaded;
    }

    private void setClassesLoaded(boolean loaded) {
        classesLoaded = loaded;
    }

    private long getSelectedClass() {
        return selectedClass;
    }

    private void setSelectedClass(long id) {
        Log.i(TAG, "Setting the selected classID to: " + id);

        selectedClass = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new LitterDBase(this);
        dbHelper.open();

        setContentView(R.layout.activity_maintain_classes);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        classPager = new ClassPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(classPager);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

                Log.i(TAG, "Moving to page: " + position);

                switch (position) {
                    case 0:
                        if (!areClassesLoaded()) {
                            fillClasses();
                        }

                        break;
                    case 1:
                        long classID = getSelectedClass();

                        Cursor cursor = null;

                        if (classID != 0) {
                            cursor = dbHelper.getClass(classID);

                            TextView classDesc = (TextView) findViewById(R.id.mcClassDescShow);

                            classDesc.setText(cursor.getString(cursor
                                    .getColumnIndex(LitterClass.column_Description)));

                            fillClassAttributes(classID);
                        }

                        fillAddClasses();
                        fillAddAttributes();

                        break;
                    default:
                        break;
                }
            }
        });

        for (int i = 0; i < classPager.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(classPager.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.litter_box, menu);

        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());

        Log.i(TAG, "Selected tab is: " + tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class ClassPagerAdapter extends FragmentPagerAdapter {
        private final int pageCount = 2;
        private final String[] pageTitle = {
                "Classes",
                "Attributes"
        };

        public ClassPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment page;

            switch (position) {
                case 0:
                    page = new ClassFragment();

                    break;
                default:
                    page = new AttributeFragment();

                    break;
            }

            Bundle args = new Bundle();
            page.setArguments(args);

            return page;
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

    public static class ClassFragment extends Fragment {
        private final String TAG = "mcClass";

        public ClassFragment() {
            super();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Log.i(TAG, "Creating the class page...");

            View rootView = inflater.inflate(R.layout.maintain_class_tab1, container, false);

            Button addClass = (Button) rootView.findViewById(R.id.mcButton1);

            addClass.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText addClassDescription = (EditText) getActivity().findViewById(
                            R.id.mcClassDesc);

                    String classDescription = addClassDescription.getText().toString();

                    LitterClass newClass = new LitterClass(classDescription);

                    ((MaintainClasses) getActivity()).dbHelper.insertClass(newClass);

                    addClassDescription.setText("");

                    ((MaintainClasses) getActivity()).fillClasses();
                }
            });

            return rootView;
        }
    }

    public static class AttributeFragment extends Fragment {
        private final String TAG = "mcAttribute";

        public AttributeFragment() {
            super();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Log.i(TAG, "Creating the class attribute page...");

            View rootView = inflater.inflate(R.layout.maintain_class_tab2, container, false);

            Button addClass = (Button) rootView.findViewById(R.id.mcAddClassButton);
            Button addAttribute = (Button) rootView.findViewById(R.id.mcAddAttributeButton);

            addClass.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Adding a sub-class...");

                    Log.i(TAG, "Grab the activity context...");

                    MaintainClasses activity = (MaintainClasses) getActivity();

                    long classID = activity.getSelectedClass();

                    if (classID == 0) {
                        return;
                    }

                    String addDescription = ((EditText) activity
                            .findViewById(R.id.mcAddDescription)).getText().toString();

                    if (addDescription == null) {
                        return;
                    }

                    if (addDescription.trim().equals("")) {
                        return;
                    }

                    Spinner classSpinner = (Spinner) activity.findViewById(R.id.mcAddClass);

                    ListView allAttributes = (ListView) activity.findViewById(R.id.mcAttributeList);

                    // public LitterClassAttribute(Long parentID, long sequence,
                    // Long classID, Long attributeID, String label)

                    LitterClassAttribute addAttribute = new LitterClassAttribute(classID,
                            (allAttributes.getCount() + 1) * 50, classSpinner.getSelectedItemId(),
                            null, addDescription);

                    activity.dbHelper.insertClassAttribute(addAttribute);

                    activity.fillClassAttributes(classID);
                }
            });

            addAttribute.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaintainClasses activity = (MaintainClasses) getActivity();

                    long classID = activity.getSelectedClass();

                    String addDescription = ((EditText) activity
                            .findViewById(R.id.mcAddDescription)).getText().toString();

                    if (addDescription == null) {
                        return;
                    }

                    if (addDescription.trim().equals("")) {
                        return;
                    }

                    Spinner attributeSpinner = (Spinner) activity.findViewById(R.id.mcAddAttribute);

                    ListView allAttributes = (ListView) activity.findViewById(R.id.mcAttributeList);

                    // public LitterClassAttribute(Long parentID, long sequence,
                    // Long classID, Long attributeID, String label)

                    LitterClassAttribute addAttribute = new LitterClassAttribute(classID,
                            (allAttributes.getCount() + 1) * 50, attributeSpinner
                                    .getSelectedItemId(),
                            null, addDescription);

                    activity.dbHelper.insertClassAttribute(addAttribute);

                    activity.fillClassAttributes(classID);
                }
            });

            return rootView;
        }
    }

    private void fillClasses() {
        Cursor classCursor = dbHelper.getClassList();

        Spinner classes = (Spinner) findViewById(R.id.mcClassID);

        String[] from = {
                LitterClass.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter classAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, classCursor, from, to);

        classes.setAdapter(classAdapter);

        classes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                    long id) {
                if (parent.getId() != R.id.mcClassID) {
                    Log.i(TAG, "Ignore the item selected signal - wrong spinner...");

                    return;
                }

                setSelectedClass(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        setClassesLoaded(true);
    }

    private void fillAddClasses() {
        Cursor classCursor = dbHelper.getClassList();

        Spinner addClasses = (Spinner) findViewById(R.id.mcAddClass);

        String[] from = {
                LitterClass.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter classAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, classCursor, from, to);

        addClasses.setAdapter(classAdapter);
    }

    private void fillAddAttributes() {
        Cursor attributeCursor = dbHelper.getAttributeList();

        Spinner attributes = (Spinner) findViewById(R.id.mcAddAttribute);

        String[] from = {
                LitterAttribute.showColumn
        };

        int[] to = {
                android.R.id.text1
        };

        @SuppressWarnings("deprecation")
        SimpleCursorAdapter attributeAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, attributeCursor, from, to);

        attributes.setAdapter(attributeAdapter);
    }

    private void fillClassAttributes(long classID) {
        Log.i(TAG, "In fillClassAttributes for class: " + classID);

        Log.i(TAG, "Calling getClassAttributes...");

        Cursor classAttributeCursor = dbHelper.getClassAttributes(classID);

        Log.i(TAG, "Creating a list to hold the class attributes...");

        List<String> classAttributes = new ArrayList<String>();

        Log.i(TAG, "Moving the cursor up to the beginning of the list...");

        Long classAttributeClassID;
        Long classAttributeAttributeID;
        String labelText;

        Log.i(TAG, "Beginning to spin through the classAttribute cursor...");

        while (!classAttributeCursor.isAfterLast()) {
            classAttributeClassID = classAttributeCursor
                    .getLong(classAttributeCursor
                            .getColumnIndex(LitterClassAttribute.column_ClassID));
            classAttributeAttributeID = classAttributeCursor
                    .getLong(classAttributeCursor
                            .getColumnIndex(LitterClassAttribute.column_AttributeID));
            labelText = classAttributeCursor.getString(classAttributeCursor
                    .getColumnIndex(LitterClassAttribute.column_Label));

            Log.i(TAG, "Try to grab the sub-class (" + classAttributeClassID
                    + ")...");

            if ((classAttributeClassID != null) && (classAttributeClassID != 0)) {
                Cursor showClass = dbHelper.getClass(classAttributeClassID);

                classAttributes
                        .add(labelText
                                + " (c): "
                                + showClass.getString(showClass
                                        .getColumnIndex(LitterClass.column_Description)));
            }

            Log.i(TAG, "Try to grab the sub-attribute ("
                    + classAttributeAttributeID + ")...");

            if ((classAttributeAttributeID != null)
                    && (classAttributeAttributeID != 0)) {
                Cursor showAttribute = dbHelper
                        .getAttribute(classAttributeAttributeID);

                classAttributes
                        .add(labelText
                                + " (a): "
                                + showAttribute.getString(showAttribute
                                        .getColumnIndex(LitterAttribute.column_Description)));
            }

            classAttributeCursor.moveToNext();
        }

        Log.i(TAG, "Create the array adapter based on the array list...");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, classAttributes);

        Log.i(TAG, "Grab a reference to the listView...");

        ListView listView = (ListView) findViewById(R.id.mcAttributeList);

        Log.i(TAG, "Connect the arrayAdapter to the listView...");

        listView.setAdapter(arrayAdapter);
    }
}
