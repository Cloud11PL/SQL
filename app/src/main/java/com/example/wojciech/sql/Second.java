package com.example.wojciech.sql;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Second extends AppCompatActivity {

    EditText nameEdit;
    EditText surnameEdit;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nameEdit = findViewById(R.id.editName);
        surnameEdit = findViewById(R.id.editSurname);

        database = openOrCreateDatabase("Studenci",MODE_PRIVATE,null);
        String sqlCount = "SELECT count(*) FROM STUDENCI";
        Cursor cursor = database.rawQuery(sqlCount,null);
        cursor.moveToFirst();
        int liczba = cursor.getInt(0);
        cursor.close();

    }

    public void submit(View view){

        if(nameEdit.getText().toString() == null || surnameEdit.getText().toString() == null) {
            Toast.makeText(getApplicationContext(),"Please provide complete data",Toast.LENGTH_SHORT).show();
        } else {
            String sqlStudent = "INSERT INTO STUDENCI VALUES (?,?,?)";
            SQLiteStatement statement = database.compileStatement(sqlStudent);

            statement.bindString(2,nameEdit.getText().toString());
            statement.bindString(3,surnameEdit.getText().toString());

            statement.executeInsert();
            Toast.makeText(getApplicationContext(),"Person " + nameEdit.getText().toString() + " " + surnameEdit.getText().toString() + " was added to the database.",Toast.LENGTH_SHORT).show();
        }
    }
}
