package com.example.braintech.sqldatabasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    Context context;
    int listLayoutRes;
    List<Employee> employeeList;
    SQLiteDatabase sqLiteDatabase;

    public EmployeeAdapter(Context context , int listLayoutRes, List<Employee> employeeList, SQLiteDatabase sqLiteDatabase) {
        super(context, listLayoutRes, employeeList);
        this.context = context;
        this.listLayoutRes = listLayoutRes;
        this.employeeList = employeeList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(listLayoutRes, null);

        //getting employee of the specified position
        Employee employee = employeeList.get(position);


        //getting views
        TextView textViewUserName = view.findViewById(R.id.textViewusername);
        TextView textViewMobile = view.findViewById(R.id.textViewmobilenumber);
        TextView textViewAdd = view.findViewById(R.id.textViewaddress);


        //adding data to views
        textViewUserName.setText(employee.getUser());
        textViewMobile.setText(employee.getPhone());
        textViewAdd.setText(employee.getAddress());

        return view;
    }
    private void updateEmployee(final Employee employee) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_update_data, null);
        builder.setView(view);


        final EditText editTextUserupdate = view.findViewById(R.id.editTextUsernameupdate);
        final EditText editTextMobileupdate = view.findViewById(R.id.editTextMobileupdate);
        final EditText editTextAddressupdate = view.findViewById(R.id.editTextAddressupdate);


        editTextUserupdate.setText(employee.getUser());
        editTextMobileupdate.setText(employee.getPhone());
        editTextAddressupdate.setText(employee.getAddress());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editTextUserupdate.getText().toString();
                String phone = editTextMobileupdate.getText().toString();
                String address = editTextAddressupdate.getText().toString();

                if (user.isEmpty()) {
                    editTextUserupdate.setError("Enter UserName");
                    editTextUserupdate.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    editTextMobileupdate.setError("Enter Phone");
                    editTextMobileupdate.requestFocus();
                    return;
                }

                if (address.isEmpty()) {
                    editTextAddressupdate.setError("Enter Address");
                    editTextAddressupdate.requestFocus();
                    return;
                }

                String sql =  "UPDATE employees \n" +
                        "SET user = ?, \n" +
                        "phone = ?, \n" +
                        "address = ? \n" +
                        "WHERE phone = ?;\n";

                sqLiteDatabase.execSQL(sql, new String[]{user, phone, address});
                Toast.makeText(context, "Employee Updated", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();

                dialog.dismiss();
            }
        });


    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = sqLiteDatabase.rawQuery("SELECT * FROM employees", null);
        if (cursorEmployees.moveToFirst()) {
            employeeList.clear();
            while(cursorEmployees.moveToNext())
            {
                employeeList.add(new Employee(cursorEmployees.getString(0),
                        cursorEmployees.getString(1),cursorEmployees.getString(2)));
            }
        }
        cursorEmployees.close();
        notifyDataSetChanged();
    }
}
