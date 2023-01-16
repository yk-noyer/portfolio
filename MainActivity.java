package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper helper = new DatabaseHelper(this);
        EditText txtIsbn = findViewById(R.id.txtIsbn);
        EditText txtTitle = findViewById(R.id.txtTitle);
        EditText txtPrice = findViewById(R.id.txtPrice);


        //REGISTRATON押した際の挙動
        ((Button) findViewById(R.id.btnSave)).setOnClickListener(view -> {
            try (SQLiteDatabase db = helper.getWritableDatabase()) {
                ContentValues cv = new ContentValues();
                cv.put("isbn", txtIsbn.getText().toString());
                cv.put("title", txtTitle.getText().toString());
                cv.put("price", txtPrice.getText().toString());
                db.insertWithOnConflict("books", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                Toast.makeText(this, "Registration Success.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //DELETE押した際の挙動
        ((Button) findViewById(R.id.btnDelete)).setOnClickListener(view -> {
            try (SQLiteDatabase db = helper.getWritableDatabase()) {
                String[] params = {txtIsbn.getText().toString()};
                db.delete("books", "isbn=?", params);
                Toast.makeText(this, "Deletion Success.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //SEARCH押した際の挙動
        ((Button) findViewById(R.id.btnSearch)).setOnClickListener(view -> {
            String[] cols = {"isbn", "title", "price"};
            String[] params = {txtIsbn.getText().toString()};
            try (SQLiteDatabase db = helper.getWritableDatabase();
                 Cursor cs = db.query("books", cols, "isbn=?",
                         params, null, null, null, null)) {
                if (cs.moveToFirst()) {
                    txtTitle.setText(cs.getString(1));
                    txtPrice.setText(cs.getString(2));
                } else {
                    Toast.makeText(this, "No data.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //データベースへ接続した際の表示
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            Toast.makeText(this, "Connected.",
                    Toast.LENGTH_SHORT).show();

            }
        }
    }