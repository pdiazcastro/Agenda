package com.example.wi.agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<Contact> contacts = new ArrayList<Contact>();

    Contact c;

    int counterContacts=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText name = (EditText)findViewById(R.id.txtName);
        final EditText phone = (EditText)findViewById(R.id.txtPhone);
        final Button add = (Button)findViewById(R.id.btnAdd);
        final Button list = (Button)findViewById(R.id.btnList);

        name.setBackgroundColor(Color.BLACK);
        phone.setBackgroundColor(Color.BLACK);
        name.setTextColor(Color.WHITE);
        phone.setTextColor(Color.WHITE);
        add.setBackgroundColor(Color.BLACK);
        list.setBackgroundColor(Color.BLACK);
        add.setTextColor(Color.WHITE);
        list.setTextColor(Color.WHITE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counterContacts = contacts.size();

                c = new Contact(name.getText().toString(), phone.getText().toString());

                if (c.check(c, contacts) == true) {
                    String msg = "El contacto ya existe";
                    showToast(msg);
                } else {

                    if(name.getText().toString().equalsIgnoreCase("") || phone.getText().toString().equalsIgnoreCase(""))
                    {
                        String msg="Necesitamos NOMBRE y NÚMERO DE TELÉFONO para añadir el contacto";
                        showToast(msg);
                    }
                    else
                    {
                        c.addContact(c, contacts);
                    }


                    if (contacts.size() > counterContacts) {
                        String msg = "Contacto: " + c.getName() + " añadido correctamente";
                        showToast(msg);
                        name.setText("");
                        phone.setText("");
                    }

                    else {
                        String msg = "Error: " + c.getName() + " NO ha sido añadido";
                        showToast(msg);
                    }
                }
            }
        });


        list.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (contacts.size()==0)
                {
                    String msg ="No tienes contactos en tu lista";
                    showToast(msg);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Listar.class);

                    intent.putExtra("contacts", contacts);

                    startActivityForResult(intent, 1);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            contacts = (ArrayList<Contact>)data.getSerializableExtra("contacts");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
