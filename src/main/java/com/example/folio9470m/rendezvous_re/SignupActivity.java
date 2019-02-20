package com.example.folio9470m.rendezvous_re;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);
    }

    public void registerClick(View v) {
        EditText emailField = findViewById(R.id.registrationEmail);
        String email = emailField.getText().toString();
        EditText passwordField = findViewById(R.id.registrationPassword);
        String password = passwordField.getText().toString();
        EditText nameField = findViewById(R.id.registrationName);
        final String name = nameField.getText().toString();
        if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SIGNUP", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("SIGNUP", "User profile updated.");
                                                }
                                                Toast.makeText(getApplicationContext(),
                                                        "Signup successful",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("SIGNUP", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_LONG).show();
                            }

                            // ...
                        }
                    });
        }
    }
}
