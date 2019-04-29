package com.affinityclick.mvvm;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;

  private NavController navController;
  private AppBarConfiguration appBarConfiguration;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    // Setup the app bar configuration.
    // Define our custom top level destinations.
    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    Set<Integer> topLevelDestinations = new HashSet<>();
    topLevelDestinations.add(R.id.movieListFragment);
    topLevelDestinations.add(R.id.popularMovieListFragment);
    appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).setDrawerLayout(drawerLayout).build();

    NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);

    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public boolean onSupportNavigateUp() {
    // Use our custom app bar configuration.
    return NavigationUI.navigateUp(navController, appBarConfiguration);
  }

  @Override
  public void onBackPressed() {
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

    if (id == R.id.nav_top_rated) {
      navController.navigate(R.id.movieListFragment);
      // Handle the camera action
    } else if (id == R.id.nav_most_popular) {
      navController.navigate(R.id.popularMovieListFragment);

    } else if (id == R.id.nav_gallery) {

    }

    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
