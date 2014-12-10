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

    //iniciamos con un array de tipo contact y una variable de tipo contact para recibir y enviar nuestro array y objetos. entre las activities
    ArrayList<Contact> contacts = new ArrayList<Contact>();
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        //creamos un intent que recibe al intent que esta volando sin coger.
        Intent intent = getIntent();

        //intent.getSerializableExtra("contacts");

        //lo dessearizamos y como es un array enviado desde la activity main lo convertimos en un array y lo guardamos en nuestro array creado arriba.
        contacts = (ArrayList<Contact>)intent.getSerializableExtra("contacts");

        //creamos un adaptador de array a listview para que el array nos lo convierta en un listview.
        setListAdapter(new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contacts));



    }

    //sobrescribimos el metodo onlistitemclick para que nos coja la posicion y el objeto que queremos modificar en la 3 actividad.
    public void onListItemClick(ListView parent, View v, int position, long id){
        /*Toast.makeText(this, "Toy toca " + contacts.get(position),
                Toast.LENGTH_SHORT).show();*/
        //decimos que nuestro contacto creado arriba del to-do es igual al objeto identificado en la posicion definida como parametro (esta es la posicion que el usuario clicka
        contact = contacts.get(position);

        //creamos un intent que me llama a la 3 actividad Intent intent = new Intent(actividadactual.this, actividadalaquequierollamar.class);
        Intent intent = new Intent(Listar.this, Activity2.class);

        //serializo en este caso un objeto contact que es el seleccionado por el usuario en la lista.
        intent.putExtra("contact", contact);

        //inicio la actividad 3 esperando un resultado y le envio el intento con el objeto serializado
        startActivityForResult(intent, 1);
    }

    //sobrescribo el metodo onactivityresult para poder recibir el objeto enviado anteriormente.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //si nuestro codigo de respuesta y resultado son ok (positivos) REALIZAMOS OPERACIONES
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            //creo un contacto nuevo de tipo contact para recibir el intent y deserealizarlo con la clave del paquete
            Contact newContact = (Contact)data.getSerializableExtra("contact");

            //recorro mi array contacts para buscar un contacto...
            for(int i=0; i<contacts.size(); i++)
            {
                //... como no he modificado el contacto enviado a la activity2 en el array, lo busco y digo que si el contacto que enviÃ© es igual a alguno que tengo en el array...
                if (contacts.get(i).getName().toString().equalsIgnoreCase(contact.getName().toString()) && contacts.get(i).getPhone().toString().equalsIgnoreCase(contact.getPhone().toString()) )
                {
                    //contacto.set index, nuevocontacto --> es decir, modifico el contacto entero encontrado en la posicion i
                    contacts.set(i, newContact);
                    //un mensaje de toast para ver que objeto obtuve antes (el del intent data) RECIBIDO DE LA ACTIVITY2
                    String msg = "GOT Object "+newContact.getName().toString()+"-->"+newContact.getPhone().toString();

                    //muestro el mensaje
                    showToast(msg);

                    //creo un intento nuevo donde voy a mandar el arraylist, esta vez con el objeto modificado ya.
                    Intent intent = new Intent(Listar.this, MainActivity.class);

                    //serializo el array con la clave contacts(verde)
                    intent.putExtra("contacts", contacts);

                    //digo que el result es ok y envio el intent
                    setResult(RESULT_OK, intent);

                    //por ultimo finalizo la activity actual.
                    finish();
                }
            }

        }
        //si nuestros objetos no se enviaron correctamente entre una activity y otra aparece un mensaje de error.
        else
        {
            String msg="Error, your contact could not be modified";
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
