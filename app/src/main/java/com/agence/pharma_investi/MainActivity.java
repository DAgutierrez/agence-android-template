package com.agence.pharma_investi;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.agence.pharma_investi.fragments.HomeFragment;
import com.agence.pharma_investi.fragments.login.LoginFragment;
import com.agence.pharma_investi.managers.SessionManagerImpl;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Context context;
    AppCompatActivity appCompatActivity;

    SessionManagerImpl sessionManager;

    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        context = this;

        fm = this.getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {

            sessionManager =  new SessionManagerImpl(context);

            if(sessionManager.isLoggedIn()) {
                fm.beginTransaction()
                        .add(R.id.main_container, new HomeFragment())
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.main_container, new LoginFragment())
                        .commit();

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_1) {

        } else if (id == R.id.item_2) {

        } else if (id == R.id.item_3) {

        } else if (id == R.id.item_4) {

        } else if (id == R.id.item_5) {
            // Log.d("item_5", Integer.toString(navigationView.getMenu().findItem(R.id.group_item_5).getItemId()));

        } else if (id == R.id.item_6_1) {

        }

        if (id != R.id.item_5){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }


    @Override
    public void onBackPressed(){
        System.out.println("back");
        int backStack = getSupportFragmentManager().getBackStackEntryCount();

        getSupportFragmentManager().popBackStack();
    }
}
