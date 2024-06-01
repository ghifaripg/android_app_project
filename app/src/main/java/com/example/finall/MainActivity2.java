package com.example.finall;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    Button btnback1, btnsubmit, datePickerBtn;
    EditText dateEditText;
    Calendar myCalendar = Calendar.getInstance();
    String userID; // Declare user ID variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Retrieve user ID from the intent
        userID = getIntent().getStringExtra("user_id");

        btnback1 = findViewById(R.id.btnback1);
        btnsubmit = findViewById(R.id.btnsubmit);
        datePickerBtn = findViewById(R.id.datePickerBtn);
        dateEditText = findViewById(R.id.date);

        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Home.class);
                startActivity(intent);
            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postType = getSelectedRadioButtonText();
                String itemName = ((EditText) findViewById(R.id.title)).getText().toString();
                String itemDescription = ((EditText) findViewById(R.id.description)).getText().toString();
                String selectedDate = dateEditText.getText().toString();
                String contactInfo = ((EditText) findViewById(R.id.Contact)).getText().toString();
                submitData(postType, itemName, itemDescription, selectedDate, contactInfo, userID);
            }
        });
    }

    private void showDatePickerDialog() {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity2.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private String getSelectedRadioButtonText() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            return radioButton.getText().toString();
        } else {
            return "";
        }
    }

    private void submitData(String postType, String itemName, String itemDescription, String selectedDate, String contactInfo, String userID) {
        // Check if userID is null or empty
        if (userID == null || userID.isEmpty()) {
            // Handle the case where userID is null or empty
            Toast.makeText(MainActivity2.this, "User ID is null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.56.2/php-androids/insert_data.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("result");

                            if (result.equals("Success")) {
                                Toast.makeText(MainActivity2.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity2.this, "Error: " + jsonResponse.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("postType", postType);
                params.put("item_name", itemName);
                params.put("item_description", itemDescription);
                params.put("date_lost_found", selectedDate);
                params.put("contact_info", contactInfo);
                params.put("user_id", userID); // Add user ID to the request
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
