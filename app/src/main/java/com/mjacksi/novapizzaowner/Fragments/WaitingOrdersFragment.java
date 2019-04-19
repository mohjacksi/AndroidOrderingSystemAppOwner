package com.mjacksi.novapizzaowner.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mjacksi.novapizzaowner.Activites.OrdersActivity;
import com.mjacksi.novapizzaowner.Adapters.OrdersAdapter;
import com.mjacksi.novapizzaowner.Models.FirebaseOrder;
import com.mjacksi.novapizzaowner.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingOrdersFragment extends Fragment {
    OrdersAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    final String FRAGMENT_TYPE = OrdersAdapter.WAITING;
    final String NEXT_FRAGMENT_TYPE = OrdersAdapter.IN_PROCESS;


    public WaitingOrdersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_waiting_orders, container, false);
        adapter = new OrdersAdapter(getActivity(), FRAGMENT_TYPE);
        RecyclerView recyclerView = v.findViewById(R.id.wating_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = firebaseDatabase.getReference("orders");

        ordersRef.orderByChild(getString(R.string.firebase_time)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FirebaseOrder> orders = new ArrayList<>();
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    FirebaseOrder order = orderSnapshot.getValue(FirebaseOrder.class);
                    if (order.getStatus().equals(FRAGMENT_TYPE))
                        orders.add(order);
                }
                adapter.setNewList(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FirebaseOrder food, int position) {
                DatabaseReference orderStatusRef = firebaseDatabase.getReference(getString(R.string.firebsae_orders_slash) + food.getOrderId() + getString(R.string.firebase_slash_status));
                DatabaseReference userStatusRef = firebaseDatabase.getReference(getString(R.string.firebase_users_slash) + food.getUserId() + getString(R.string.firebase_slash_orders_slash) + food.getOrderId() + getString(R.string.firebase_slash_status));
                orderStatusRef.setValue(NEXT_FRAGMENT_TYPE);
                userStatusRef.setValue(NEXT_FRAGMENT_TYPE);
                Toast.makeText(getContext(), NEXT_FRAGMENT_TYPE, Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }

}
