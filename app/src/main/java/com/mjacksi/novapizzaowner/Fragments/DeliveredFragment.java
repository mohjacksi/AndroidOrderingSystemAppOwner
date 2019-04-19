package com.mjacksi.novapizzaowner.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mjacksi.novapizzaowner.Adapters.OrdersAdapter;
import com.mjacksi.novapizzaowner.Models.FirebaseOrder;
import com.mjacksi.novapizzaowner.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveredFragment extends Fragment {

    final String FRAGMENT_TYPE = OrdersAdapter.DELIVERED;

    OrdersAdapter adapter;
    FirebaseDatabase firebaseDatabase;

    public DeliveredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_in_process, container, false);
        adapter = new OrdersAdapter(getActivity(), FRAGMENT_TYPE);
        RecyclerView recyclerView = v.findViewById(R.id.wating_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = firebaseDatabase.getReference(getString(R.string.firebase_orders));

        ordersRef.orderByChild(getString(R.string.firebase_time)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FirebaseOrder> orders = new ArrayList<>();
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    FirebaseOrder order = orderSnapshot.getValue(FirebaseOrder.class);
                    if (order.getStatus().equals(FRAGMENT_TYPE))
                        orders.add(order);
                    Collections.reverse(orders);
                }
                adapter.setNewList(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

}
