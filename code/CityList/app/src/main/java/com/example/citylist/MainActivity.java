package com.example.citylist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button add_city_btn;
    Button edit_city_btn;
    Button delete_city_btn;
    int selected_position = -1;
    ListView city_list;
    ArrayAdapter<String> city_adapter;
    ArrayList<String> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add_city_btn = findViewById(R.id.add_city_btn);
        edit_city_btn = findViewById(R.id.edit_city_btn);
        delete_city_btn = findViewById(R.id.delete_city_btn);
        city_list = findViewById(R.id.city_list);

        String []cities = {"Alexandria", "Winnipeg", "Cairo", "Singapore", "Houston", "San Francisco", "Edmonton", "Los Angeles", "San Antonio", "Asyut"};

        data_list = new ArrayList<>();
        data_list.addAll(Arrays.asList(cities));

        city_adapter = new ArrayAdapter<>(this, R.layout.content, data_list);
        city_list.setAdapter(city_adapter);

        city_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_position = position;
                city_list.setItemChecked(position, true);
            }
        });

        add_city_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add City");

                final EditText input = new EditText(MainActivity.this);
                input.setHint("Enter city name");
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = input.getText().toString().trim();
                        if (city.isEmpty()) {
                            Toast.makeText(MainActivity.this, "City name's cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                        data_list.add(city);
                        city_adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.show();
            }
        });

        edit_city_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_position < 0) {
                    Toast.makeText(MainActivity.this, "Select a city to edit it.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit City");

                final EditText input = new EditText(MainActivity.this);
                input.setHint("Enter new city name");
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = input.getText().toString().trim();
                        if (city.isEmpty()) {
                            Toast.makeText(MainActivity.this, "City name's cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                        data_list.set(selected_position, city);
                        city_adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Edited: " + data_list.get(selected_position) + " to ", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.show();
            }
        });
        delete_city_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_position < 0) {
                    Toast.makeText(MainActivity.this, "Select a city to delete it.", Toast.LENGTH_SHORT).show();
                    return;
                }
                data_list.remove(selected_position);
                city_adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted: " + data_list.get(selected_position), Toast.LENGTH_SHORT).show();
            }
        });

    }
}