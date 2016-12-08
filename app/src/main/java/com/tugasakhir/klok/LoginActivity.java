package com.tugasakhir.klok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private EditText eEmail;
    private EditText ePassword;
    private TextView linkSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
//            startActivity(new Intent(this, HomeActivity.class));
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        linkSignup = (TextView) findViewById(R.id.linkSignup);

        btnLogin.setOnClickListener(this);
        linkSignup.setOnClickListener(this);

    }

    private void userLogin(){
        String email = eEmail.getText().toString().trim();
        String password = ePassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validation success

        progressDialog.setMessage("Waiting Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), FirstUserDataActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin){
            userLogin();
        }
        if(view == linkSignup){
            finish();
            startActivity(new Intent(this, SignupActivity.class));
        }
    }

}
