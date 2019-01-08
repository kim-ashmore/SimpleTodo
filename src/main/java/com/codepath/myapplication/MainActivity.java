package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

//import java.io.IOException;
import org.apache.commons.io.*;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listViewItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listViewItems = (ListView) findViewById(R.id.listViewItems);
        listViewItems.setAdapter(itemsAdapter);

        //data in list (mock data)
        //items.add("First item");
        //items.add("Second item");

        //invoke listener
        setupListViewListener();
    }


    public void onAddItem(View v) {
        //resolve edit text
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);// gets refrence from edit to text
        String itemText = etNewItem.getText().toString();
        //add to items adapter directly
        itemsAdapter.add(itemText);
        //clear text field
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();

    }

    private void setupListViewListener() {
        Log.i("MainActivity", "setting up list4ener on list view");
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //only invoked on long click
                Log.i("MainActivity", "Item removed from list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }

        });
    }


    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }
        //read and write to file functions
        private void readItems()
        {
            try {
                //initialize array
                items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            } catch (IOException e) {
                Log.e("MainActivity", "Error reading file ", e);
                items = new ArrayList<>();
            }
        }

        private void writeItems()
        {
            try {
                FileUtils.writeLines(getDataFile(), items);
            } catch (IOException e) {
                Log.e("MainActivity", "Error writing file ", e);
            }

        }
    }
