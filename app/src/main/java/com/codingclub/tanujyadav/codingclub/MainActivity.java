package com.codingclub.tanujyadav.codingclub;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.codingclub.tanujyadav.codingclub.R;
import com.codingclub.tanujyadav.codingclub.GalleryAdapter;
import com.codingclub.tanujyadav.codingclub.AppController;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

import static java.lang.Long.parseLong;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static SharedPreferences ps;
    public static SharedPreferences.Editor pe;
    public static final int PREFERENCE_MODE_PRIVATE=0;
    PopupWindow popUpWindow;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout mainLayout;
    Button btnClickHere;
    LinearLayout containerLayout;
    TextView tvMsg;
    int flag=0,flag1=0;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    static int flagg=0,flagg1=0,ff=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        containerLayout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        popUpWindow = new PopupWindow(this);
        tvMsg = new TextView(this);
        tvMsg.setText(R.string.success);

        tvMsg.setTextSize(20);
        tvMsg.setTextColor(getResources().getColor(android.R.color.black));


        layoutParams = new LinearLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        containerLayout.setOrientation(LinearLayout.HORIZONTAL);

        containerLayout.addView(tvMsg, layoutParams);
      //  containerLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        containerLayout.setBackground(getResources().getDrawable(R.drawable.butto));
        popUpWindow.setContentView(containerLayout);
        //mainLayout.addView(btnClickHere, layoutParams);
        //setContentView(mainLayout);





        ps=getPreferences(PREFERENCE_MODE_PRIVATE);
        pe=ps.edit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        open(R.id.home);
    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!",
                        Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
            //drawer.closeDrawer(GravityCompat.START);
        } else {

            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id==R.id.open_profile)
        {Intent intent = new Intent(this,profile.class);

            startActivityForResult(intent,2);}
        else if(id==R.id.exit)
        {Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);}
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
Fragment fragment=null;
        int id = item.getItemId();
         if(id==R.id.home)
         {
             flag1=0;ff=0;
             fragment=new HomeFragment();
         }
        else if (id == R.id.events) {
          fragment=new EventsFragment();
             flag1=0;ff=0;
        } else if (id == R.id.tutorials) {
           // Intent intent=new Intent(this,TutorialsActivity.class);
            //startActivity(intent);
           fragment=new MyListFragment();
             flag1=0;ff=0;
        }
         else if (id == R.id.discussion) {
             // Intent intent=new Intent(this,TutorialsActivity.class);
             //startActivity(intent);
             fragment=new discussion_fragment();
             flag1=0;ff=1;
         }
         else if (id == R.id.gallery) {
             // Intent intent=new Intent(this,TutorialsActivity.class);
             //startActivity(intent);
             fragment=new gallery_fragment();
             flag1=0;ff=0;
         }
         else if (id == R.id.achievements) {
           fragment=new AboutFragment();
          flag1=1;ff=0;
        }
         else if (id == R.id.about) {
             fragment=new AboutrealFragment();
             flag1=2;ff=0;
         }
         else if (id == R.id.facebook) {

            Uri url = Uri.parse("https://www.facebook.com/Coding-Club" +
                    "-NIT-Delhi-1036658203032774/?fref=ts&ref=br_tf");
            Intent facebook = new Intent(Intent.ACTION_VIEW, url);
            PackageManager packageManager = getPackageManager();
            List activities = packageManager.queryIntentActivities(facebook,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;
            if(isIntentSafe)
                startActivity(facebook);

        } else if (id == R.id.mail) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", "codingclub@nitdelhi.ac.in", null));

            startActivity(emailIntent);

        }
        else if(id==R.id.phone)
        {
            String phnumber="tel:+919716755258";
            Uri number = Uri.parse(phnumber);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        }
        else if(id==R.id.locateus)
        {String location="geo:"+"28.843021,77.1053242"+"?z=18";
            Uri number = Uri.parse(location);
            Intent map = new Intent(Intent.ACTION_VIEW, number);
            PackageManager packageManager = getPackageManager();
            List activities = packageManager.queryIntentActivities(map,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;
            if(isIntentSafe)
                startActivity(map);
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();

            if(flag==0&&id==R.id.achievements)
            {
                final FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                final Fragment f=new AboutFragment();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //ft = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.flContent, f);
                        ft1.commit();
                        flag=1;
                        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                }, 800);

            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(item.getTitle());
            }
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void open(int a)
    {
        Fragment fragment=new HomeFragment();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();
        }
    }

public void register(View v) {

   // scheduleNotification(getNotification("You have an event today"), 0);
    Button bv=(Button)v;
   // v.setBackground(getResources().getDrawable(R.drawable.butcol));
    //View vv=v.getRootView();
   //TextView tv1 = (TextView) vv.findViewById(R.id.name);
    String aa=bv.getText().toString();
    final String ss=aa.substring(0,aa.length()-13);
    String xx=aa.substring(aa.length()-13,aa.length());
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(parseLong(xx));
    cal.set(Calendar.HOUR_OF_DAY, 0); //set hours to zero
    cal.set(Calendar.MINUTE, 0); // set minutes to zero
    cal.set(Calendar.SECOND, 0); //set seconds to zero
    Log.i("Time", cal.getTime().toString());
    final long de=  (cal.getTimeInMillis()-System.currentTimeMillis());
    //Timestamp ts=(Timestamp)parseLong(xx);
    //if(de>0)
    //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
    if (ps.getString(ss, "n/a").equals("n/a")) {

        //TextView tv2 = (TextView) findViewById(R.id.txtName);
        if (ps.getString("name", "n/a").equals("n/a") || ps.getString("branch", "n/a").equals("n/a") || ps.getString("year", "n/a").equals("n/a") || ps.getString("institute", "n/a").equals("n/a") || ps.getString("phone", "n/a").equals("n/a")) {
            String w = "Please Login with Google and complete your profile to register for the event";
            Toast.makeText(getApplicationContext(), w, Toast.LENGTH_SHORT).show();
        } else {


            //TextView tv3 = (TextView) findViewById(R.id.txtEmail);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ss);
            String userId = mDatabase.push().getKey();
            //  user user=new user("ssss","cccc","xcxcx","ssss","cccc","xcxcx");
            String a1 = ps.getString("name", "n/a").toString();
            String a2 = ps.getString("branch", "n/a");
            String a3 = ps.getString("year", "n/a");
            String a4 = ps.getString("institute", "n/a");
            String a5 = ps.getString("phone", "n/a");
            String a6 = ps.getString("email", "n/a");
            user user = new user(a1, a2, a3, a4, a5, a6);
            mDatabase.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
               public void onComplete(DatabaseError error, DatabaseReference ref) {
                   if(error==null)
                   {
                       pe.putString(ss,"yes");
                       pe.commit();
                       openpop();
                       scheduleNotification(getNotification("You have an event today("+ss+")"), de);
                       scheduleNotification(getNotification("You have an event today("+ss+")"), de-21600000);
                   }
                   else
                   Toast.makeText(getApplicationContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                   //System.out.println("Value was set. Error = "+error);
                }
            });



        }
    }
    else  if (ps.getString(ss, "n/a").equals("yes"))
    {
        String w = "You have already registered for the event";
        Toast.makeText(getApplicationContext(), w, Toast.LENGTH_SHORT).show();
    }
    }
    public void openpop()
    {
        popUpWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popUpWindow.showAtLocation(mainLayout, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 10, 40);
        popUpWindow.update(8, 50, 300, 150);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
              popUpWindow.dismiss();
            }
        }, 1000);
    }

  public void solve(View v)
    {
        Uri url = Uri.parse("https://www.hackerearth.com/challenge/college/nitd-programming-league/problems/");
        Intent facebook = new Intent(Intent.ACTION_VIEW, url);
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(facebook,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if(isIntentSafe)
            startActivity(facebook);
    }

    private void scheduleNotification(Notification notification, long delay) {

        //Intent notificationIntent = new Intent(this, MainActivity.class);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("dCoders");
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setSmallIcon(R.drawable.ic_dc);
        return builder.build();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait
       if(flag1==2)
       {
           Fragment fragment=new AboutrealFragment();
           FragmentTransaction ft;// = getSupportFragmentManager().beginTransaction();
           ft = getSupportFragmentManager().beginTransaction();
           ft.replace(R.id.flContent, fragment);
           ft.commit();
           final Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
           toast.show();
           toast.cancel();
           //Log.d("Time", "change");
           //Toast.makeText(getApplicationContext(), "change", Toast.LENGTH_SHORT).show();
       }
        else  if(flag1==1)
       {
           Fragment fragment=new AboutFragment();
           FragmentTransaction ft;// = getSupportFragmentManager().beginTransaction();
           ft = getSupportFragmentManager().beginTransaction();
           ft.replace(R.id.flContent, fragment);
           ft.commit();
          // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
           final Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
           toast.show();
           toast.cancel();

          // View view = Toast.getView();
           //view.setBackgroundResource(/*your background resid*/);
           //toast.setView(view);
           //Toast.show();

       }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if(ff==1)
            {
                Fragment fragment=new discussion_fragment();
                FragmentTransaction ft;// = getSupportFragmentManager().beginTransaction();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flContent, fragment);
                ft.commit();
            }
        }
    }
}
