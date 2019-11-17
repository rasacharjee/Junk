package com.gamecodeschool.nr;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class user_bookings_fragment extends Fragment {


    private RecyclerView recyclerViewbook;
    private adapterbook  booking;
    private List<book> bookList;
    Query dbbooksferoz;
    Query dbbooksamrit;
    Query dbbooksbaij;
    Query dbbookslud;
    Query dbbooksjammu;
    Query dbbookskatra;
    Query dbbookspathan;
    
    String CrisId;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;


    public user_bookings_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_user_bookings_fragment, container, false);

         CrisId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Toast.makeText(getActivity(),CrisId,Toast.LENGTH_SHORT).show();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("WAIT");
        progressDialog.setMessage("Please wait while we are getting booking record");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        //String crisId=this.getArguments().getString("CrisID");
        dbbooksferoz= FirebaseDatabase.getInstance().getReference("FEROZPUR").orderByChild("uid").equalTo(CrisId);
        dbbooksbaij= FirebaseDatabase.getInstance().getReference("BAIJNATH").orderByChild("uid").equalTo(CrisId);
        dbbooksamrit= FirebaseDatabase.getInstance().getReference("AMRITSAR").orderByChild("uid").equalTo(CrisId);
        dbbookslud= FirebaseDatabase.getInstance().getReference("LUDHIANA").orderByChild("uid").equalTo(CrisId);
        dbbooksjammu= FirebaseDatabase.getInstance().getReference("JAMMU").orderByChild("uid").equalTo(CrisId);
        dbbookskatra= FirebaseDatabase.getInstance().getReference("KATRA").orderByChild("uid").equalTo(CrisId);
        dbbookspathan= FirebaseDatabase.getInstance().getReference("PATHANKOT").orderByChild("uid").equalTo(CrisId);

        bookList=new ArrayList<>();
        recyclerViewbook=view.findViewById(R.id.recyclerViewbook);
        //recyclerViewbook.setHasFixedSize(false);
//        booking.notifyDataSetChanged();

       /* bookList.add(
                new book(
                        "FEROZPUR",
                        "2 November",
                        "2 pm",
                        "3pm",
                        "3 pm" ,
                        "true",
                        "temp")); */
      bookList.clear();
      dbbooksferoz.addListenerForSingleValueEvent(valueEventListener);
      dbbookslud.addListenerForSingleValueEvent(valueEventListener);

        Log.d("LOG","value"+bookList);


        return view;

    }
    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if (dataSnapshot.exists()){
               for (DataSnapshot data:dataSnapshot.getChildren()){
                   book obj=data.getValue(book.class);
                   Log.d("bookdata","data"+obj.getCheckOutdate());
                   bookList.add(obj);
               }
               Collections.reverse(bookList);
               booking=new adapterbook(getActivity(),bookList);
               recyclerViewbook.setLayoutManager(new LinearLayoutManager(getActivity()));
               progressDialog.dismiss();
               recyclerViewbook.setAdapter(booking);
               booking.notifyDataSetChanged();
           }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
