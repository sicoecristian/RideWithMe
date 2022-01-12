package com.app.ridewithme.user;

import static android.provider.Settings.System.getString;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.ridewithme.R;
import com.app.ridewithme.exceptions.UserIsNullException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DAOUser {
    private DatabaseReference databaseReference;

    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://ridewithmedatabase-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User user) throws UserIsNullException {
        if (user != null)
            return databaseReference.push().setValue(user);
        else throw new UserIsNullException();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

//    public boolean checkIfUserExistsByEmail(String email){
//        boolean returnValue=false;
//        Query query = this.databaseReference
//                .child("User")
//                .orderByChild("email")
//                .equalTo(email);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getChildrenCount()>0) {
//
//                }else{
//                    // username not found
//
//                }
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        return returnValue;
//    }


}
