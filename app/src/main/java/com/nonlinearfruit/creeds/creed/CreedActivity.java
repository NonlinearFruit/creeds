package com.nonlinearfruit.creeds.creed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nonlinearfruit.creeds.R;
import com.nonlinearfruit.creeds.confessionofpeter.ConfessionOfPeterDatabase;

public class CreedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        int contentId = intent.getIntExtra("JsonFileId",1); // TODO: Defaulting to 1 could be _bad_

        setTitle(title);
        setContentView(R.layout.activity_paragraph);

        ((TextView) findViewById(R.id.activity_paragraph_paragraph))
                .setText(new CreedDatabase().getCreed(contentId));
    }
}