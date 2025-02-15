package com.example.smartmart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartmart.Adapter.OrderWaitAdapter;
import com.example.smartmart.DBHelper.DatabaseHelper;
import com.example.smartmart.R;
import com.example.smartmart.models.Order;
import java.util.List;

public class fgcholayhang extends Fragment {

    private RecyclerView recyclerViewOrders;
    private OrderWaitAdapter orderWaitAdapter;
    private DatabaseHelper databaseHelper;
    private int maUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fgcholayhang, container, false);
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseHelper = new DatabaseHelper(getContext());

        if (getArguments() != null) {
            maUser = getArguments().getInt("maUser");
        }

        List<Order> orders = databaseHelper.getOrdersByUser(maUser, 1, 2);
        orderWaitAdapter = new OrderWaitAdapter(getContext(), orders);
        recyclerViewOrders.setAdapter(orderWaitAdapter);
        return view;
    }

    public static fgcholayhang newInstance(int maUser) {
        fgcholayhang fragment = new fgcholayhang();
        Bundle args = new Bundle();
        args.putInt("maUser", maUser);
        fragment.setArguments(args);
        return fragment;
    }
}