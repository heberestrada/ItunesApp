package com.example.itunesapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Realm realmRock;

    public RealmConfiguration realmRockConf;


    public int[] icons= {
            R.drawable.ic_home,
            R.drawable.ic_moss,
            R.drawable.ic_pop
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new RockFragment(), "Rock");
        adapter.addFragment(new ClassicFragment(), "Classic");
        adapter.addFragment(new PopFragment(), "Pop");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager,false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        for (int iconsLoad = 0; iconsLoad < tabLayout.getTabCount(); iconsLoad++) {
            tabLayout.getTabAt(iconsLoad).setIcon(icons[iconsLoad]);
        }


    }

}