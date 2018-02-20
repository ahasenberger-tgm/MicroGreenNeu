package iot.microgreenneu;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TestSensor sensor;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    static double[] sensordata;
    static int punktestand;
    static int seite;
    String notificationText;
    static MainActivity globalInstance;
    static int counter;
    Boolean istbodenzutrocken;
    Boolean istbodenzunass;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        setContentView(R.layout.activity_main);
        seite = 0;
        counter = 0;
        istbodenzunass = false;
        istbodenzutrocken = false;
        punktestand = 0;
        notificationText = "";
        globalInstance = new MainActivity();
        sensordata = new double[4];






        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton share = (FloatingActionButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","clicked");
                /*ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://stackoverflow.com/questions/16780294/how-to-print-to-the-console-in-android-studio"))
                        .build();*/
                Bitmap image = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                Log.d("Pic", "Picture");
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                shareDialog.show(content);
                //bildAendern();
                //sendNotification();
                Log.d("content","Content: "+content);

            }
        });

        FloatingActionButton share2 = (FloatingActionButton) findViewById(R.id.share2);
        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter == 5)
                    counter = 0;
                else counter = 5;
                checkdata();

                sensordata = TestSensor.getAllSensorData();
                //richtigeBilder();


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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

            String [] ausgabe = {"Feuchtigkeit im Boden(%)","Temperatur Luft(°C)","Temperatur Boden(°C)","Feuchtigkeit Luft(%)","Erfolge"/*,"SocialMedia"*/};
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            ImageView image = (ImageView) rootView.findViewById(R.id.imageView);

            try{
                seite = getArguments().getInt(ARG_SECTION_NUMBER)-1;
                textView.setText(ausgabe[seite]+": "+Double.toString(sensordata[seite]));/*TestSensor.getSensorData(getArguments().getInt(ARG_SECTION_NUMBER)-1)+//*sen.getSensorData(0)/*getString(R.string.section_format, (int) sen.getSensorData(getArguments().getInt(ARG_SECTION_NUMBER)-1)*/
                if(seite == 4) textView.setText(ausgabe[seite]+": "+Integer.toString(punktestand));
                textView.setTextSize(40);
                String uri = "";
                if (getArguments().getInt(ARG_SECTION_NUMBER)-1 == 0) {//Feuchtigkeit im Boden
                    if(sensordata[0]<30){
                        uri = "@mipmap/singledrop_xhdpi";
                    }else if (sensordata[0]>=30&&sensordata[0]<=90){
                        uri = "@mipmap/doubledrop_xhdpi";
                    }
                    else if (sensordata[0]>90) {
                        uri = "@mipmap/tripledrop_xhdpi";
                    }
                }else if(getArguments().getInt(ARG_SECTION_NUMBER)-1 == 1){//Temp in der Luft
                    if(sensordata[1]<10){
                        uri = "@mipmap/thermometer_low_xhdpi";
                    }else if (sensordata[1]>=10&&sensordata[1]<=30){
                        uri = "@mipmap/thermometer_halb_xhdpi";
                    }
                    else if (sensordata[1]>30) {
                        uri = "@mipmap/thermometer_xhdpi";
                    }

                }else if(getArguments().getInt(ARG_SECTION_NUMBER)-1 == 2){//Bodentemp
                    if(sensordata[2]<10){
                        uri = "@mipmap/thermometer_low_xhdpi";
                    }else if (sensordata[2]>=10&&sensordata[2]<=30){
                        uri = "@mipmap/thermometer_halb_xhdpi";
                    }
                    else if (sensordata[2]>30) {
                        uri = "@mipmap/thermometer_xhdpi";
                    }
                }else if(getArguments().getInt(ARG_SECTION_NUMBER)-1 == 3){//Luftfeuchtigkeit
                    if(sensordata[3]<30){
                        uri = "@mipmap/singledrop_xhdpi";
                    }else if (sensordata[3]>=30&&sensordata[3]<=90){
                        uri = "@mipmap/doubledrop_xhdpi";
                    }
                    else if (sensordata[3]>90) {
                        uri = "@mipmap/tripledrop_xhdpi";
                    }
                }else if(getArguments().getInt(ARG_SECTION_NUMBER)-1 == 4){//Erfolg
                    if(punktestand==0) {
                        uri = "@mipmap/bronze_erfolg_xhdpi";
                    }else if(punktestand==1){
                        uri = "@mipmap/silber_erfolg_xhdpi";
                    }else if(punktestand>=2){
                        uri = "@mipmap/gold_erfolg_xhdpi";
                    }
                }
                int imageR = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                Drawable res = getResources().getDrawable(imageR);
                image.setImageDrawable(res);

            }catch(Exception e){
                e.printStackTrace();
            }

            return rootView;
        }

    }


    public void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.flower_bronze_hdpi)
                        .setContentTitle("MicroGreen")
                        .setContentText(notificationText);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }

    public void checkdata(){

        double bodenfeuchtigkeit = 0;
        bodenfeuchtigkeit = sensordata[0];
        //try{bodenfeuchtigkeit = TestSensor.getSensorData(0,counter);}catch (Exception e){}
        double luftfeuchtigkeit = 0;
        double lufttemperatur = 0;

        if(bodenfeuchtigkeit < 30){
            notificationText = "Die Erde ist zu trocken. Gieße deine Pflanze.";
            istbodenzutrocken = true;
            sendNotification();

            //notification aufrufen, icon in der tabbar ändern
        }

        if ((istbodenzutrocken||istbodenzunass) && bodenfeuchtigkeit > 30 && bodenfeuchtigkeit < 90){// Beides damit man nur Punkte bekommt wenn man die Pflanze gießt wenn es vorher zu wenig war
            istbodenzutrocken = false;
            istbodenzunass = false;
            notificationText = "Die Pflanze wurde gegossen.";
            punktestand ++;
            sendNotification();
            bildAendern();
            //notification aufrufen, icon in der tabbar ändern
        }
        if(bodenfeuchtigkeit > 90) {
            notificationText = "Die Erde ist zu nass. Warte mit dem weiteren Gießen.";
            istbodenzunass = true;
            sendNotification();
        }
    }


    public void bildAendern(){
        ImageView image = (ImageView) findViewById(R.id.media_image);
        //image.setImageBitmap(BitmapFactory.decodeFile("@mipmap/flower_silver_xxxhdpi"));
        String uri = "";
        if (punktestand == 0) {
            uri = "@mipmap/flower_bronze_xxxhdpi";
        }else if(punktestand == 1){
            uri = "@mipmap/flower_silver_xxxhdpi";
        }else if(punktestand>=2){
            uri = "@mipmap/flower_gold_xxxhdpi";
        }
        int imageR = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageR);
        image.setImageDrawable(res);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
                case 5:
                    return "SECTION 6";
            }
            return null;
        }
    }
}
