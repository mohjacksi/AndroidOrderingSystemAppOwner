package com.mjacksi.novapizzaowner.Activites;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mjacksi.novapizzaowner.Fragments.DeliveredFragment;
import com.mjacksi.novapizzaowner.Fragments.InProcessFragment;
import com.mjacksi.novapizzaowner.Fragments.ToDeliverFragment;
import com.mjacksi.novapizzaowner.Fragments.WaitingOrdersFragment;
import com.mjacksi.novapizzaowner.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;

public class OrdersActivity extends AppCompatActivity {
    final Fragment waitingOrdersFragment = new WaitingOrdersFragment();
    final Fragment inProcessFragment = new InProcessFragment();
    final Fragment toDeliverFragment = new ToDeliverFragment();
    final Fragment deliveredFragment = new DeliveredFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = waitingOrdersFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_waiting:
                    fm.beginTransaction().hide(active).show(waitingOrdersFragment).commit();
                    active = waitingOrdersFragment;
                    return true;
                case R.id.navigation_in_process:
                    fm.beginTransaction().hide(active).show(inProcessFragment).commit();
                    active = inProcessFragment;
                    return true;
                case R.id.navigation_ready:
                    fm.beginTransaction().hide(active).show(toDeliverFragment).commit();
                    active = toDeliverFragment;

                    return true;
                case R.id.navigation_delivered:
                    fm.beginTransaction().hide(active).show(deliveredFragment).commit();
                    active = deliveredFragment;

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.fragment_container, deliveredFragment, "4").hide(deliveredFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, toDeliverFragment, "3").hide(toDeliverFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, inProcessFragment, "2").hide(inProcessFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, waitingOrdersFragment, "1").commit();
    }
}
