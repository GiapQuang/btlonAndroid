package com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.manager.btlonappbanhangonline.home.cart.cartdatabase.CartDatabase;
import com.manager.btlonappbanhangonline.home.cart.cartdatabase.repository.dao.CartDao;
import com.manager.btlonappbanhangonline.model.Cart;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public class CartRepository {
    private CartDao cartDao;
    private LiveData<List<Cart>> allCarts;
    private LiveData<Long> cost;
    private CartDatabase cartDatabase;

    public CartRepository(Application application) {
        cartDatabase = CartDatabase.getInstance(application);
        cartDao = cartDatabase.cartDao();
        allCarts = cartDao.selectAllCart();
    }

    public void insertCart(Cart cart){
        new InsertCartAsyncTask(cartDao).execute(cart);
    }

    public void updateCart(Cart cart){
        new UpdateCartAsyncTask(cartDao).execute(cart);
    }

    public void deleteCart(Cart cart){
        new DeleteCartAsyncTask(cartDao).execute(cart);
    }

    public LiveData<List<Cart>> getAllCarts() {
        return allCarts;
    }
    public void deleteAll(){
        cartDatabase.clearAllTables();
    }
    public LiveData<Long> cost(){
        LiveData<Long> cost = new LiveData<Long>() {};
        for(Cart i:allCarts.getValue()){
            //cost.getValue()+= i.getQuantity() * Long.valueOf(cost);
        }
        return cost;
    }

    private static class InsertCartAsyncTask extends AsyncTask<Cart, Void, Void> {

        private CartDao cartDao;

        public InsertCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.insert(carts[0]);
            return null;
        }
    }

    private static class UpdateCartAsyncTask extends AsyncTask<Cart, Void, Void>{

        private CartDao cartDao;

        public UpdateCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.update(carts[0]);
            return null;
        }
    }

    //AsyncTask for delete existing player
    private static class DeleteCartAsyncTask extends AsyncTask<Cart, Void, Void>{

        private CartDao cartDao;

        public DeleteCartAsyncTask (CartDao cartDao){
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.delete(carts[0]);
            return null;
        }
    }
/*
    //AsyncTask for delete all players
    private static class DeleteAllPlayersAsyncTask extends AsyncTask<Void, Void, Void>{

        private PlayerDao playerDao;

        public DeleteAllPlayersAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            playerDao.deleteAllPlayer();
            return null;
        }
    }*/
}
