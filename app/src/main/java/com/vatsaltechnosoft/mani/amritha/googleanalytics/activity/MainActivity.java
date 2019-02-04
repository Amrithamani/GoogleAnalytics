package com.vatsaltechnosoft.mani.amritha.googleanalytics.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vatsaltechnosoft.mani.amritha.googleanalytics.R;
import com.vatsaltechnosoft.mani.amritha.googleanalytics.app.FooterFragment;
import com.vatsaltechnosoft.mani.amritha.googleanalytics.app.MyApplication;

public class MainActivity extends AppCompatActivity {


    private static String TAG = MainActivity.class.getSimpleName();

    private Button btnSendEvent, btnException, btnAppCrash, btnLoadFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendEvent = findViewById(R.id.btnSendEvent);
        btnException = findViewById(R.id.btnException);
        btnAppCrash = findViewById(R.id.btnAppCrash);
        btnLoadFragment = findViewById(R.id.btnLoadFragment);

        /**
         * Tracking Fragment View
         */
        btnLoadFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FooterFragment footerFragment = new FooterFragment();
                fragmentTransaction.replace(R.id.frame_container, footerFragment);
                fragmentTransaction.commit();
            }
        });

        /**
         * Event tracking
         * Event(Category, Action, Label)
         */
        btnSendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tracking Event
                MyApplication.getInstance().trackEvent("Book", "Download", "Track event example");

                Toast.makeText(getApplicationContext(), "Event \'Book\' \'Download\' \'Event example\' is sent. Check it on Google Analytics Dashboard!", Toast.LENGTH_LONG).show();
            }
        });

        /**
         * Tracking Exception Manually
         * All known exceptions can be tracking this way
         * using Try & Catch
         */
        btnException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = null;
                    if (name.equals("amritha")) {
                        /* Never comes here as it throws null pointer exception */
                    }
                } catch (Exception e) {
                    // Tracking exception
                    MyApplication.getInstance().trackException(e);

                    Toast.makeText(getApplicationContext(), getString(R.string.toast_track_exception), Toast.LENGTH_LONG).show();

                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        });

        /**
         * Tracking App Crashes
         * Manually generation app crash by dividing with zero
         */
        btnAppCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), getString(R.string.toast_app_crash), Toast.LENGTH_LONG).show();

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        int answer = 12 / 0;
                    }
                };

                Handler h = new Handler();
                h.postDelayed(r, 1500);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        MyApplication.getInstance().trackScreenView("Home Screen");

    }
}
