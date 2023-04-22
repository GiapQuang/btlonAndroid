package com.manager.btlonappbanhangonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.model.GioHang;
import com.manager.btlonappbanhangonline.model.NewProduct;
import com.manager.btlonappbanhangonline.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    NewProduct sanPhamMoi;
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        Log.i("activity: ", "Detail");
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                themGioHang();
            }
        });
    }
    private void themGioHang() {
        if(Utils.manggiohang.size()>0){
            boolean flag=false;
            int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i=0;i<Utils.manggiohang.size();i++){
                if(Utils.manggiohang.get(i).getIdsp()==sanPhamMoi.getId()) {
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getPrice())*Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag=true;
                }
            }
            if(flag==false){
                long gia = Long.parseLong(sanPhamMoi.getPrice())*soluong;
                GioHang gioHang= new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getName());
                gioHang.setHinhsp(sanPhamMoi.getImg());
                Utils.manggiohang.add(gioHang);
            }
        }else{
            int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getPrice())*soluong;
            GioHang gioHang= new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getName());
            gioHang.setHinhsp(sanPhamMoi.getImg());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            totalItem= totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi= sanPhamMoi=(NewProduct) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getName());
        mota.setText(sanPhamMoi.getDetail());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getImg()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá:"+sanPhamMoi.getPrice()+"Đ");
        Integer[] so =new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp=findViewById(R.id.txttensp);
        giasp=findViewById(R.id.txtgiasp);
        mota= findViewById(R.id.txtmotachitiet);
        btnthem=findViewById(R.id.btnthemvaogiohang);
        spinner=findViewById(R.id.spinner);
        imghinhanh=findViewById(R.id.imgchitiet);
        toolbar=findViewById(R.id.toobar);
        badge= findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang= findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang= new Intent(getApplicationContext(), CartActivity.class);
                startActivity(giohang);
            }
        });
        if(Utils.manggiohang != null){
            int totalItem=0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem= totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang != null){
            int totalItem=0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem= totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}