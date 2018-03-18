package com.android.siggy_siggy.newprojectassignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = findViewById(R.id.editText);

        Button btn = findViewById(R.id.button);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               saveURLRssFeed();
                Intent returnIntent = getIntent();
                setResult(RESULT_OK, returnIntent);
                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveURLRssFeed() {
        String editTextValue = editText.getText().toString();
    }

public String getRssFeedFromUser(Context context){
        SharedPreferences prefs = context.getSharedPreferences("RSSPrefs", MODE_PRIVATE);
        return prefs.getString("PrefsForRSS", "http://www.aweber.com/blog/feed/");
    }

    // backbutton will send u back to first activity / listview
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}