package com.example.fianlprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.fianlprojectapp.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

        ActivitySignupBinding binding;
        Databaselogin databaselogin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivitySignupBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            databaselogin = new Databaselogin(this);

            binding.signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = binding.signupemail.getText().toString();
                    String password = binding.signuppassword.getText().toString();
                    String confirmPassword = binding.confirmpassword.getText().toString();

                    if(email.equals("")||password.equals("")||confirmPassword.equals(""))
                        Toast.makeText(Signup.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    else{
                        if(password.equals(confirmPassword)){
                            Boolean checkUserEmail = databaselogin.checkEmail(email);

                            if(checkUserEmail == false){
                                Boolean insert = databaselogin.insertData(email, password);

                                if(insert == true){
                                    Toast.makeText(Signup.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Signup.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Signup.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Signup.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                }
            });

        }
    }
