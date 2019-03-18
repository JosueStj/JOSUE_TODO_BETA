package com.example.josue_todo_beta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        //items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        //mock data
       // items.add("Bella");
        setupListViewListener();
    }
    public void onAddItem(View view){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText
                (getApplicationContext(),"Item added to list",Toast.LENGTH_LONG)
                .show();
    }
    private void setupListViewListener(){
        Log.i("Main Activity","Setting up Listener on List View");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick
                    (AdapterView<?> parent, View view, int position, long id) {
                Log.i("Main Activity","Item remove from List"+position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(),"todo.txt");
    }
    private void readItems(){
        try {
            items = new ArrayList<>
                    (FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity","Error reading file",e);
            items = new ArrayList<>();
        }
    }
    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("Main Activity","Error writing file",e);
        }
    }
}
