package com.mjacksi.novapizzaowner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mjacksi.novapizzaowner.Models.FirebaseOrder;
import com.mjacksi.novapizzaowner.R;
import com.mjacksi.novapizzaowner.Utilites.Utilises;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>{


    List<FirebaseOrder> orders = new ArrayList<>();
    Context context;
    public static final String WAITING = "waiting";
    public static final String IN_PROCESS = "in_process";
    public static final String TO_DELIVER = "to_deliver";
    public static final String DELIVERED = "delivered";

    String type = "";
    private OnItemClickListener listener;

    public OrdersAdapter(FragmentActivity activity,String type) {
        context = activity;
        this.type = type;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.waiting_order_item, parent, false);
        OrderViewHolder holder = new OrderViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        FirebaseOrder currentOrder = orders.get(position);

        holder.orderPosition.setText("#" + (position+1));
        holder.time.setText(Utilises.getTime(currentOrder.getTime()));

        String details = "";
        for (Map.Entry<String, Integer> entry : currentOrder.getOrders().entrySet()) {
            details = details + entry.getKey() + " x " + entry.getValue() + "\n";
        }
        details = details.substring(0, details.length() - 1);
        holder.orderDetails.setText(details);

        DecimalFormat df = new DecimalFormat("#.##");

        holder.total.setText("$" + df.format(currentOrder.getTotal()));
        holder.address.setText(currentOrder.getLocation());
        holder.phone.setText(currentOrder.getUserPhone());

        int buttonColor = 0;
        String buttonTitle = "";

        if (type.equals(WAITING)){
            buttonTitle = "Move to process";
        } else if(type.equals(IN_PROCESS)) {
            buttonTitle = "Move to delivery unit";
        } else if(type.equals(TO_DELIVER)) {
            buttonTitle = "Move to delivery ";
        }else if(type.equals(DELIVERED)) {
            holder.button.setVisibility(View.GONE);
        }

        holder.button.setText(buttonTitle);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setNewList(List<FirebaseOrder> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderPosition;
        TextView time;
        TextView orderDetails;
        TextView total;
        TextView address;
        TextView phone;
        Button button;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderPosition = itemView.findViewById(R.id.order_position);
            time = itemView.findViewById(R.id.order_time);
            orderDetails = itemView.findViewById(R.id.order_details);
            total = itemView.findViewById(R.id.order_total);
            address = itemView.findViewById(R.id.order_address);
            phone = itemView.findViewById(R.id.order_phone);
            button = itemView.findViewById(R.id.order_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(orders.get(position), position);
                    }
                }
            });
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(FirebaseOrder food, int position);
    }
}
