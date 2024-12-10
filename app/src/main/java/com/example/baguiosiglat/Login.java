package com.example.baguiosiglat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.*;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText emailField, passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Begin Code

        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        ImageButton loginButton = findViewById(R.id.login_btn);
        TextView registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Register.class)));

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {

            String email = String.valueOf(emailField.getText());
            String password = String.valueOf(passwordField.getText());

            try {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, task -> {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_LONG).show();
                            }
                        });
            }catch (Exception e){
                Toast.makeText(Login.this, "Do not leave empty fields.",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Check if the user is logged in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}