package com.example.mp5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PullActivity extends AppCompatActivity {
    EditText id;
    ListView lv;
    FirebaseDatabase fbdb;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    ArrayList<String> listofItems;
    String studentName;
    FirebaseUser user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        id=(EditText) findViewById(R.id.id);
        fbdb=FirebaseDatabase.getInstance();
        ref=fbdb.getReference();
        listofItems=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        lv=(ListView) findViewById(R.id.list);

    }
    public void query1Click(View view) {

        if(user!=null) {
            int studID = Integer.parseInt(id.getText().toString());

            DatabaseReference gradeKey = ref.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            query.addListenerForSingleValueEvent(query1EventListener);

        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }

    }
    public void query2Click(View view) {

        if(user!=null) {
            int studID = Integer.parseInt(id.getText().toString());

            DatabaseReference gradeKey = ref.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").startAt(studID);
            query.addListenerForSingleValueEvent(query2EventListener);

        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }

    }
    public void signOut(View view){
        if(user!=null) {
            mAuth.signOut();
            user = null;
            Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PullActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void push(View view){
        if(user!=null) {
            Intent intent = new Intent(PullActivity.this, PushActivity.class);
            startActivity(intent);
        }
    }
    ValueEventListener query1EventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                listofItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    String s=grade.getcourse_name()+", "+grade.getgrade();
                    listofItems.add(s);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PullActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, listofItems);
                lv.setAdapter(adapter);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };
    ValueEventListener query2EventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                listofItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    switch(grade.getstudent_id()){
                        case 123:
                            studentName="Bart";
                            break;
                        case 404:
                            studentName="Ralph";
                            break;
                        case 456:
                            studentName="Milhouse";
                            break;
                        case 888:
                            studentName="Lisa";
                            break;
                    }
                    String s=studentName+", "+grade.getcourse_name()+", "+grade.getgrade();
                    listofItems.add(s);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PullActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, listofItems);
                lv.setAdapter(adapter);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };
}
