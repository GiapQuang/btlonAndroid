package com.manager.btlonappbanhangonline.home.delivering.received;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.btlonappbanhangonline.model.Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceivedViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    FirebaseFirestore db;
    LiveData<FirebaseUser> user;
    LiveData<List<Delivery>> receivedLiveData;
    String userEmail;
    public ReceivedViewModel(@NonNull Application application) {
        super(application);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = getUser();
        userEmail = user.getValue().getEmail();
        receivedLiveData = getData();
    }

    LiveData<List<Delivery>> getData(){
        MutableLiveData<List<Delivery>> deliveries = new MutableLiveData<>();
        List<Delivery> data = new ArrayList<>();
        db.collection("deliveries")
                .document(userEmail)
                .collection(userEmail)
                .whereEqualTo("isReceived", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.i("error when getting data:", error.toString());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                data.add(dc.getDocument().toObject(Delivery.class));
                                Log.i("when getting data:",dc.getDocument().toObject(Delivery.class).getIsReceived().toString());
                            }
                        }
                        deliveries.postValue(data);
                    }
                });
        return deliveries;
    }

    public LiveData<FirebaseUser> getUser() {
        MutableLiveData<FirebaseUser> userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData.setValue(auth.getCurrentUser());
        return userMutableLiveData;
    }
}
