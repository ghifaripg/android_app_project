package com.example.finall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button button1, button2;
    String userID; // Declare a variable to hold user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve user ID from intent extras
        userID = getIntent().getStringExtra("user_id");

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MainActivity2.class);
                intent.putExtra("user_id", userID); // Pass user ID to MainActivity2
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(Home.this, ShowActivity.class);
                intents.putExtra("user_id", userID); // Pass user ID to ShowActivity
                startActivity(intents);
            }
        });
    }
}
