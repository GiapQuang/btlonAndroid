package com.manager.btlonappbanhangonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.Interface.ItemClickListener;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.activity.DetailActivity;
import com.manager.btlonappbanhangonline.model.EventBus.SuaXoaEvent;
import com.manager.btlonappbanhangonline.model.NewProduct;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewHolder> {
    Context context;
    List<NewProduct> array;
    public NewProductAdapter(Context context, List<NewProduct> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_product,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewProduct newProduct = array.get(position);
        holder.nameText.setText(newProduct.getName());
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.costText.setText(newProduct.getPrice()+"Đ");
        if(newProduct.getImg().contains("http")){
            Glide.with(context).load(newProduct.getImg()).into(holder.productImage);
        }else{
            /*String hinh = Utils.BASE_URL+"images/"+sanPhamMoi.getImg();*/
            /*Glide.with(context).load(hinh).into(holder.imghinhanh);*/
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    //click
                    Intent intent=new Intent(context, DetailActivity.class);
                    intent.putExtra("chitiet",newProduct);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    EventBus.getDefault().postSticky(new SuaXoaEvent(newProduct));
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView costText,nameText;
        ImageView productImage;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            costText = itemView.findViewById(R.id.costItemProduct);
            nameText = itemView.findViewById(R.id.nameItemProduct);
            productImage = itemView.findViewById(R.id.productItemImage);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0,0,getAdapterPosition(),"Sửa");
            contextMenu.add(0,1,getAdapterPosition(),"Xóa");
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);

            return false;
        }
    }
}
