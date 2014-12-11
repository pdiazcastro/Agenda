package com.example.wi.agenda;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wi.agenda.R;

import java.util.ArrayList;

public class Listar extends ListActivity {

    ArrayList<Contact> contacts = new ArrayList<Contact>();
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        Intent intent = getIntent();

        contacts = (ArrayList<Contact>)intent.getSerializableExtra("contacts");

        setListAdapter(new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contacts));



    }

    public void onListItemClick(ListView parent, View v, int position, long id){

        contact = contacts.get(position);

        Intent intent = new Intent(Listar.this, Activity2.class);

        intent.putExtra("contact", contact);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            Contact newContact = (Contact)data.getSerializableExtra("contact");

            for(int i=0; i<contacts.size(); i++)
            {
                if (contacts.get(i).getName().toString().equalsIgnoreCase(contact.getName().toString()) && contacts.get(i).getPhone().toString().equalsIgnoreCase(contact.getPhone().toString()) )
                {
                    contacts.set(i, newContact);
                    String msg = "GOT Object "+newContact.getName().toString()+"-->"+newContact.getPhone().toString();

                    showToast(msg);

                    Intent intent = new Intent(Listar.this, MainActivity.class);

                    intent.putExtra("contacts", contacts);

                    setResult(RESULT_OK, intent);

                    finish();
                }
            }

        }

        else
        {
            String msg="Error, el contacto no puede ser modificado";
            showToast(msg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showToast(String msg)
    {
        Context contexto = getApplicationContext();
        int duracion = Toast.LENGTH_SHORT;
        Toast tostada = Toast.makeText(contexto, msg, duracion);
        tostada.show();
    }
}
