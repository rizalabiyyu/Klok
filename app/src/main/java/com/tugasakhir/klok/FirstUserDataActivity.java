package com.tugasakhir.klok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstUserDataActivity extends AppCompatActivity implements View.OnClickListener{


    private FirebaseAuth firebaseAuth;
    private TextView userEmail;
    private Button btnLogout;
    private DatabaseReference databaseReference;

    private EditText editTextName, editTextAddress;
    private Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinformation);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userEmail = (TextView) findViewById(R.id.userEmail);

        userEmail.setText("Welcome "+user.getEmail());
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation() {
        String name = editTextName.getText().toString().trim();
        String add = editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, add);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == buttonSave){
            saveUserInformation();
        }
    }
}
