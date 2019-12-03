package com.hfad.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemInput;
    private Button btn;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private boolean checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemInput = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_button);
        itemsList = findViewById(R.id.items_list);

        items = Items.readData(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

        btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);


//
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_button:
                String itemEntered = itemInput.getText().toString();
                adapter.add(itemEntered);
                itemInput.setText("");
                Items.writeData(items, this);
                Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Select an action");
        final String[] options = {
                "See Detail",
                "Mark as Complete",
                "In Progress",
                "Delete"
        };
        final View fragmentContainer = findViewById(R.id.detail_frag);

        dialog.setSingleChoiceItems(options, // Items list
                -1, // Index of checked item (-1 = no selection)
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedItem = Arrays.asList(options).get(which);
                        if (selectedItem.equals("Mark as Complete")){
                            itemsList.getChildAt(position).setBackgroundColor(Color.GREEN);
                            dialog.dismiss();
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
                        } else if (selectedItem.equals("In Progress")){
                            itemsList.getChildAt(position).setBackgroundColor(Color.YELLOW);
                            dialog.dismiss();
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
                        } else if (selectedItem.equals("Delete")) {
                            items.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
                        }

                        if (selectedItem.equals("See Detail") && fragmentContainer != null) {
                            DetailListFragment details = new DetailListFragment();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            details.setItemID(which);
                            ft.replace(R.id.detail_frag,details);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack(null);
                            ft.commit();
                            dialog.dismiss();
                        } else if (selectedItem.equals("See Detail") && fragmentContainer == null) {
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
        });
        dialog.create();
        dialog.show();

    }



}

