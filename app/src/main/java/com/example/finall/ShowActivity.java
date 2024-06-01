package com.example.finall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        listView = findViewById(R.id.listView);
        itemList = new ArrayList<>();

        String phpScriptUrl = "http://192.168.56.2/php-androids/fetch_data.php";

        new FetchDataAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, phpScriptUrl);

        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(5);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String itemDetails = itemList.get(position);
                    Intent intent = new Intent(ShowActivity.this, ShowItemDetailsActivity.class);
                    intent.putExtra("itemDetails", itemDetails);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private class FetchDataAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                connection.disconnect();

                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("JSON Response", result); // Log the JSON response

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    displayItems(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("FetchDataAsyncTask", "JSON result is null");
            }
        }
    }

    private void displayItems(JSONArray jsonArray) {
        itemList.clear(); // Clear the existing data

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);

                String postType = item.optString("post_type");
                String itemName = item.optString("item_name");
                String itemDescription = item.optString("item_description");
                String dateLostFound = item.optString("date_lost_found");
                String contactInfo = item.optString("contact_info");
                String userName = item.optString("user_name"); // Get the user name

                // Customize the display format based on your needs
                String displayText =
                        "Type: " + postType +
                                "\nName: " + userName +
                                "\nItem Name: " + itemName +
                                "\nDescription: " + itemDescription +
                                "\nDate: " + dateLostFound +
                                "\nContact: " + contactInfo;
                itemList.add(displayText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Use ArrayAdapter to display the items in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);
    }

}
