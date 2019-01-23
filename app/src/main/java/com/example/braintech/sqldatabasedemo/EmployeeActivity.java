package com.example.braintech.sqldatabasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
List<Employee> myList;
SQLiteDatabase sqLiteDatabase;
ListView listView;
EmployeeAdapter employeeAdapter;
Button update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        myList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listViewEmployees);
        update = (Button)findViewById(R.id.buttonUpdateEmployee);
        DisplayEmployeeData();

    }

    public void DisplayEmployeeData()
    {
        sqLiteDatabase = openOrCreateDatabase("Employee",MODE_PRIVATE,null);//build the database connection;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from employees",null);


        System.out.println("Total Count of Cursor------->"+cursor.getCount());
        if (cursor.moveToFirst())
        {
            while(cursor.moveToNext())
            {
                myList.add(new Employee(cursor.getString(0),
                        cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        employeeAdapter = new EmployeeAdapter(this,R.layout.list_layout_employee,myList,sqLiteDatabase);
        listView.setAdapter(employeeAdapter);

    }
}
