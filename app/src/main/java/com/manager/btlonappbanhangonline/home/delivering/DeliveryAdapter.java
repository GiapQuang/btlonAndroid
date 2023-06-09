package com.manager.btlonappbanhangonline.home.delivering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.model.Delivery;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.Holder>{
    List<Delivery> data;
    Context context;
    DeliveryOnclickListener listener;
    Boolean isReceived;

    public DeliveryAdapter(List<Delivery> data, Context context, DeliveryOnclickListener listener, Boolean isReceived) {
        this.data = data;
        this.context = context;
        this.listener = listener;
        this.isReceived = isReceived;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.onBind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView dateText;
        AppCompatButton receivedButton;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateOrder);
            receivedButton = itemView.findViewById(R.id.receivedButton);
        }

        void onBind(Delivery delivery, DeliveryOnclickListener listener){
            dateText.setText(context.getResources().getString(R.string.order_at) + delivery.getOrder().getOrderDate());

            receivedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.changeStateDeliveryListener(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

            if (isReceived){
                receivedButton.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
