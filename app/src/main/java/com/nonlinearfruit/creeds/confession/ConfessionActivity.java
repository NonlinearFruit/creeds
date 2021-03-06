package com.nonlinearfruit.creeds.confession;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.nonlinearfruit.creeds.R;
import com.nonlinearfruit.creeds.confession.models.Chapter;
import com.nonlinearfruit.creeds.main.Database;
import com.nonlinearfruit.creeds.main.models.Document;

import java.util.ArrayList;
import java.util.List;

public class ConfessionActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        setTitle(title);
        setContentView(R.layout.activity_list);

        adapter = setupAdapter(title);
        setupListView(adapter);
        setupSearchView();
    }

    private ChapterAdapter setupAdapter(String title) {
        Intent intent = getIntent();
        int jsonFile = intent.getIntExtra("JsonFileId",1); // TODO: Defaulting to 1 could be _bad_
        Database db = new Database(jsonFile);
        List<Chapter> chapters = new ArrayList<>();

        try{
            chapters = db.<List<Chapter>>getDocument(this, List.class, Chapter.class).Data;
        } catch (Exception exception) {
            Log.e("CREEDS", "Loading json failed for "+title, exception); // TODO: handle gracefully
        }

        return new ChapterAdapter(chapters, this, (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
    }

    private void setupListView(ArrayAdapter adapter) {
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(false);
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
        } else {
            adapter.getFilter().filter(newText);
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}