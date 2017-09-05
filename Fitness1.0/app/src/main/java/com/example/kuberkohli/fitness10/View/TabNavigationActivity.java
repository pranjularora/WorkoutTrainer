package com.example.kuberkohli.fitness10.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuberkohli.fitness10.Model.MapsActivity;
import com.example.kuberkohli.fitness10.R;
import com.example.kuberkohli.fitness10.animation.AnimationFactory;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class TabNavigationActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener, ChallengesFragment.InterfaceWorkoutData,InstructionTabFragment.InterfaceWorkoutDataInstructionPage {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private String userName, userId, url;
    private Bundle userData;
    ImageView imageView;
    TextView drawer_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_tab_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabNavigationActivity.this,TimerActivity.class);
                String bool = "true";
                intent.putExtra("bool",bool);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(TabNavigationActivity.this, view ,"ActivityTransition" );
                TabNavigationActivity.this.startActivity(intent, options.toBundle());
            }
        });
        
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        userData = new Bundle();
        userData = getIntent().getBundleExtra("userData");
        userName = userData.getString("userName");
        url = userData.getString("url");
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_navigation_main);

        if(navHeaderView != null){

            imageView = (ImageView)navHeaderView.findViewById(R.id.user_pic);
            drawer_userName = (TextView) navHeaderView.findViewById(R.id.drawer_userName);
            Picasso.with(getApplicationContext())
                    .load(url)
                    .into(imageView);
            drawer_userName.setText(userName);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class); startActivity(intent);
            return true;
        } else if (id == R.id.logout_settings) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this,InitialViewActivity.class); startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home_drawer) {
            // do nothing
        } else if (id == R.id.logout_drawer) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this,InitialViewActivity.class); startActivity(intent);
        }else if (id == R.id.setting_drawer) {
            Intent intent = new Intent(this,SettingsActivity.class); startActivity(intent);
        }else if (id == R.id.map_drawer) {
            // code to navigate to map activity
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }else if (id == R.id.profile) {
            Intent intent = new Intent(this,UserProfileActivity.class);
            intent.putExtra("userData", userData);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void DisplayWorkoutData(int position, HashMap<String, ?> workoutDetails) {
        Intent intent = new Intent(getApplicationContext(), ChallengesDetailActivity.class);
        intent.putExtra("workOutData", workoutDetails);
        startActivity(intent);
    }

    @Override
    public void DisplayWorkoutDataInstruction(int position, HashMap<String, ?> workoutDetails_instructions) {
        Intent intent = new Intent(getApplicationContext(), InstrcutionTabActivity.class);
        intent.putExtra("workOutDataInstruction", workoutDetails_instructions);
        startActivity(intent);
    }


    public static class PlaceholderFragment extends Fragment {


        private static final String ARG_SECTION_NUMBER = "section_number";
        private static RecyclerView recView1;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.science_page, container, false);
            final android.widget.ViewAnimator viewAnimator = (android.widget.ViewAnimator) rootView.findViewById(R.id.animator2);
            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);

            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ChallengesFragment.newInstance(position + 1);
                case 1:
                    return InstructionTabFragment.newInstance(position + 1);
                case 2:
                    return PlaceholderFragment.newInstance(position + 1);
            }
            return PlaceholderFragment.newInstance(1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Challenges";
                case 1:
                    return "Instructions";
                case 2:
                    return "Science";
            }
            return null;
        }
    }
}
