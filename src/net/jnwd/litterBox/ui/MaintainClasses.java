
package net.jnwd.litterBox.ui;

import java.util.Locale;

import net.jnwd.litterBox.R;
import net.jnwd.litterBox.data.LitterDBase;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MaintainClasses extends FragmentActivity implements ActionBar.TabListener {
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

    public LitterDBase getDbHelper() {
        return dbHelper;
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

                // special handling?
            }
        });

        for (int i = 0; i < classPager.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
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
            View rootView = inflater.inflate(R.layout.maintain_class_tab1, container, false);

            return rootView;
        }
    }

    public static class AttributeFragment extends Fragment {
        private final String TAG = "mcAttribute";

        public AttributeFragment() {
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
            View rootView = inflater.inflate(R.layout.maintain_class_tab2, container, false);

            return rootView;
        }
    }
}
