package com.example.mp5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PushActivity extends AppCompatActivity {
    int radioID,dbID=123;
    EditText cI,cN,g;
    FirebaseDatabase fbdb;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    FirebaseUser user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        cI=findViewById(R.id.courseId);
        cN=findViewById(R.id.courseName);
        g=findViewById(R.id.grade);
        fbdb=FirebaseDatabase.getInstance();
        ref=fbdb.getReference("simpsons/grades");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void push_grade(View view) {
        if(user!=null) {
            Grade grade = new Grade(Integer.parseInt(cI.getText().toString()),
                    cN.getText().toString(),g.getText().toString(),dbID);
            String id = ref.push().getKey();
            ref.child(id).setValue(grade);
            Toast.makeText(getApplicationContext(),"Grade Added",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PushActivity.this, PullActivity.class);
            startActivity(intent);
        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PushActivity.this, PullActivity.class);
            startActivity(intent);
        }
    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.bart){dbID = 123;}
        if(radioID==R.id.ralph){dbID = 404;}
        if(radioID==R.id.milhouse){dbID = 456;}
        if(radioID==R.id.lisa){dbID = 888;}
    }
}
