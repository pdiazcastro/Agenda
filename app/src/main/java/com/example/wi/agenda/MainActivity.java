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

    //INICIAMOS CON UN ARRAYLIST DE TIPO CONTACT.
    ArrayList<Contact> contacts = new ArrayList<Contact>();
    //CREAMOS UNA VARIABLE DE TIPO CONTACT PARA POSTERIORMENTE INSTANCIAR CONTACTOS.
    Contact c;
    //ESTE INTEGER NOS INDICA SI EL TAMAÃ‘O DEL ARRAY HA SIDO MODIFICADO.
    int counterContacts=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Llamamos a todos los graficos del XML para poder usarlos---
        final EditText name = (EditText)findViewById(R.id.txtName);
        final EditText phone = (EditText)findViewById(R.id.txtPhone);
        final Button add = (Button)findViewById(R.id.btnAdd);
        final Button list = (Button)findViewById(R.id.btnList);
        //LLAMADA HASTA AQUI-----------------------------------------

        //-----------editamos el color de fondo de los txt Y Btns.----------------/
        name.setBackgroundColor(Color.BLACK);
        phone.setBackgroundColor(Color.BLACK);
        name.setTextColor(Color.WHITE);
        phone.setTextColor(Color.WHITE);
        add.setBackgroundColor(Color.BLACK);
        list.setBackgroundColor(Color.BLACK);
        add.setTextColor(Color.WHITE);
        list.setTextColor(Color.WHITE);
        //----------------------------hasta aqui----------------------------------

        //--------------- abrimos el onclicklistener de add.--------------------
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //--------IGUALAMOS COUNTER CON LA CANTIDAD ACTUAL DEL ARRAYLIST
                counterContacts = contacts.size();

                //CREAMOS UN OBJETO DE TIPO CONTACTO NUEVO CON LAS VARIABLES NOMBRE Y TELEFONO PARA VERIFICACION
                c = new Contact(name.getText().toString(), phone.getText().toString());

                //llamamos al metodo check que nos dice si el contacto existe o no con ese nombre y ese telefono
                if (c.check(c, contacts) == true) {
                    String msg = "The contact already EXISTS";
                    showToast(msg);
                } else {

                    //evaluamos si las variables estan vacias, si lo estan, no podemos aÃ±adir un contacto igual de lo contrario aÃ±adimos al array
                    if(name.getText().toString().equalsIgnoreCase("") || phone.getText().toString().equalsIgnoreCase(""))
                    {
                        String msg="We need NAME AND PHONE NUMBER to add the contact";
                        showToast(msg);
                    }
                    else
                    {
                        c.addContact(c, contacts);
                    }

                    //---------------hasta aqui-------------

                    //preguntamos si hubo modificacion en el tamaÃ±o del array, si lo hubo significa que nuestro contacto se aÃ±adiÃ³ exitosamente
                    if (contacts.size() > counterContacts) {
                        String msg = "Contact: " + c.getName() + " successfully added";
                        showToast(msg);
                        name.setText("");
                        phone.setText("");
                    }
                    //de lo contrario un mensaje de error de NO AÃ‘ADIDO
                    else {
                        String msg = "Error: " + c.getName() + " was NOT added";
                        showToast(msg);
                    }
                }
            }
        });

        //abrimos el listener de listar
        list.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //evaluamos si el tamaÃ±o del array es igual a 0 ( esto significa que no hay elementos que listar, asi que nos mostrarÃ¡ un mensaje de aviso

                if (contacts.size()==0)
                {
                    String msg ="There aren't any contacts on your list";
                    showToast(msg);
                }
                //de lo contrario se crea un intento con el arraylist serializado y se inicia la actividad esperando un result_ok
                else {
                    // c = new Contact();
                    // c.setArrayListContacts(contacts);

                    //creamos el intento con  Intent intento = new Intent(laclaseenlaqueestamos.this, clasealaquellamamos.class);
                    Intent intent = new Intent(MainActivity.this, Listar.class);
                    //serializamos el objeto y le aÃ±adimos una clave de recogida
                    intent.putExtra("contacts", contacts);
                    //lanzamos la actividad listar con el resultado 1 requestcode
                    startActivityForResult(intent, 1);
                }
            }
        });
    }


    //metodo sobrescrito onactivityresult que me recibem esos 3 parametros el intent contiene el objeto con las modificaciones necesarias.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //evaluamos si el resultado y respuesta son positivas
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            //si lo son convertimos el Intent data en nuestro array y lo desserealizamos con la clave del paquete.
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
