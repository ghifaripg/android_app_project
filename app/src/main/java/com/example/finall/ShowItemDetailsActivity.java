package com.example.finall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowItemDetailsActivity extends AppCompatActivity {

    private TextView reportedByTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_details);

        Intent intent = getIntent();
        String itemDetails = intent.getStringExtra("itemDetails");

        reportedByTextView = findViewById(R.id.reportedByTextView);
        reportedByTextView.setText(itemDetails);
    }
}