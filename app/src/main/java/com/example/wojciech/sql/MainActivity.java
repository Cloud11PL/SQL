package com.example.wojciech.sql;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("Studenci",MODE_PRIVATE,null);
        /*
        Trzeba dać dropa, żeby przy testowaniu wartości się nadopisywały.
         */
        String dropSQl = "DROP TABLE IF EXISTS STUDENCI";
        database.execSQL(dropSQl);

        String sqlDB = "CREATE TABLE IF NOT EXISTS STUDENCI (id PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, surname VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM STUDENCI";
        Cursor cursor = database.rawQuery(sqlCount,null);
        cursor.moveToFirst();
        int liczba = cursor.getInt(0);
        cursor.close();

        if(liczba == 0){
            String sqlStudent = "INSERT INTO STUDENCI VALUES (?,?)";
            SQLiteStatement statement = database.compileStatement(sqlStudent);

            //statement.bindLong(1,1);
            statement.bindString(1,"Sharon");
            statement.bindString(2,"Den Adel");

            statement.executeInsert();

            //statement.bindLong(1,2);
            statement.bindString(1,"Tarja");
            statement.bindString(2,"Turnen");

            statement.executeInsert();

        }
    }

    public void onClick(View view) {

        ArrayList<String> results = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT id, name, surname FROM STUDENCI", null);

        if(c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                String surname = c.getString(c.getColumnIndex("surname"));
                results.add("ID: " + id + ", Name: " + name + ", Surname: " + surname);
            } while(c.moveToNext());
        }

        ListView list = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,results);
        list.setAdapter(adapter);
        c.close();
    }

    public void next(View view){
        Intent next = new Intent(getApplicationContext(), Second.class);
        startActivity(next);
    }

    public void xD(){
        System.out.println("XD");
    }

}
