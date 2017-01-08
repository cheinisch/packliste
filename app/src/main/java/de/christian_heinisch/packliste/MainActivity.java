package de.christian_heinisch.packliste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.packliste.database.TravelDataSource;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private TravelDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getReiseName();

        stuffListFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_travellist) {
            Intent intent = new Intent(this, CityActivity.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_newtrip){
            Intent intent = new Intent(this, AddNewActivity.class);
            startActivity(intent);

        } */else if (id == R.id.nav_aboutapp){

            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void stuffListFragment(){
        StuffFragment stuffFragment = new StuffFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                stuffFragment,
                stuffFragment.getTag()
        )
                .commit();
    }

    public void getReiseName(){

        SharedPreferences settings = this.getSharedPreferences("Packliste", MODE_PRIVATE);

        long id = 1;

        String idString = settings.getString("id", "1");

        id = Long.parseLong(idString);

        String reisebezeichnung = "Februar - 2017";

        dataSource = new TravelDataSource(this);
        dataSource.open();
        String city = dataSource.getTravelCity(id);


        long time = Long.parseLong(dataSource.getStartDate(id));
        dataSource.close();
        reisebezeichnung = city + " - " + getMonth(time) + " " + getYear(time);

        Menu header=navigationView.getMenu();
        MenuItem test = header.findItem(R.id.nav_travellist);
        test.setTitle(reisebezeichnung);


    }

    public String getMonth(long time){

        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("MMMM", cal).toString();
        return date;

    }

    public String getYear(long time){

        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy", cal).toString();
        return date;

    }

}
