package com.example.baguiosiglat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    private EditText nameField, emailField, passField, phoneField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Begin Program
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        nameField = findViewById(R.id.name_register);
        phoneField = findViewById(R.id.phone_number_register);
        emailField = findViewById(R.id.email_register);
        passField = findViewById(R.id.password_register);

        TextView returnToLogin = findViewById(R.id.return_to_login);
        ImageButton regBtn = findViewById(R.id.register_acc);


        //Go back to the login Screen
        returnToLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));

        //Register
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameField.getText());
                String phoneNumber = String.valueOf(phoneField.getText());
                String email = String.valueOf(emailField.getText());
                String password = String.valueOf(passField.getText());
                Map <String, String> userData = new HashMap<>();
                userData.put("Name", name);
                userData.put("Phone", phoneNumber);


                try {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest profileChange = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build();
                                    user.updateProfile(profileChange);

                                    db.collection("users").document(user.getUid()).set(userData);
                                    startActivity(new Intent(getApplicationContext(), Login.class));

                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(Register.this, "Do not leave empty fields.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}