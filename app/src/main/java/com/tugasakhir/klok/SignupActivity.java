package com.tugasakhir.klok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Rizal Abiyyu on 12/7/2016.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignup;
    private EditText eEmail;
    private EditText ePassword;
    private TextView linkLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        linkLogin = (TextView) findViewById(R.id.linkLogin);

        btnSignup.setOnClickListener(this);
        linkLogin.setOnClickListener(this);
    }

    private void registerUser(){
        String sEmail = eEmail.getText().toString().trim();
        String sPassword = ePassword.getText().toString().trim();

        if(TextUtils.isEmpty(sEmail)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sPassword)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG ).show();
            return;
        }
        //if validation success

        progressDialog.setMessage("Creating User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), FirstUserDataActivity.class));
                        }else{
                            Toast.makeText(SignupActivity.this,"Registration Error "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btnSignup){
            registerUser();
        }
        if(view == linkLogin){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
