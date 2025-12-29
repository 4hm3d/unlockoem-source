package com.example.oemunlock;


import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.oemunlock.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setContentView(R.layout.activity_main);

        // 1. Check current status
        boolean isLocked = SystemSecurityUtils.isBootloaderLocked();
        Log.d("OMEUNLOCK", "Is Bootloader Locked? " + isLocked);

        // 2. Try to flip the switch (Pass 'this' as the context)
        SystemSecurityUtils.setOemUnlockEnabled(this, false);

        isLocked = SystemSecurityUtils.isBootloaderLocked();
        Log.d("OMEUNLOCK", "tried to turn it on " + isLocked);

        // 3. Check current status
        isLocked = SystemSecurityUtils.isBootloaderLocked();
        Log.d("OMEUNLOCK", "Is Bootloader Locked? " + isLocked);


        TextView statusText = findViewById(R.id.status_text);
        statusText.setText("Done!");


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



}

