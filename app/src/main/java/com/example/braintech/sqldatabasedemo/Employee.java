package com.example.braintech.sqldatabasedemo;

public class Employee {
    public  String User;
    public String Phone;
    public String Address;
    public Employee(String User,String Phone,String Address)
    {
        this.User = User;
        this.Phone = Phone;
        this.Address = Address;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
