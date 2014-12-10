package com.example.wi.agenda;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable{

    private ArrayList<Contact> contacts;
    private String name;
    private String phone;

    public Contact()
    {

    }

    public Contact(String name, String phone)
    {
        this.name=name;
        this.phone=phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString()
    {
        return "Name: "+this.getName()+"\n"+
                "Phone Number: "+this.getPhone();
    }

    public void addContact(Contact c, ArrayList<Contact> contacts)
    {
        contacts.add(c);
    }

    public boolean check(Contact c, ArrayList<Contact> contacts)
    {
        boolean check=false;

        for (int i=0; i<contacts.size(); i++)
        {
            if (contacts.get(i).getName().toString().equalsIgnoreCase(c.getName().toString()) &&
                    contacts.get(i).getPhone().toString().equalsIgnoreCase(c.getPhone().toString()))
            {
                check=true;
            }
        }
        return check;
    }

    public void setArrayListContacts(ArrayList<Contact> contacts)
    {
        this.contacts=contacts;
    }

    public ArrayList<Contact> getArrayListContacts()
    {
        return this.contacts;
    }
}
