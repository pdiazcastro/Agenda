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

import com.example.wi.agenda.R;

public class Activity2 extends Activity {


    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);


        contact = (Contact)getIntent().getSerializableExtra("contact");


        Button btnOK = (Button)findViewById(R.id.btnOK);
        final EditText txtNameEdited = (EditText)findViewById(R.id.txtNameEdited);
        final EditText txtPhoneEdited = (EditText)findViewById(R.id.txtPhoneEdited);


        txtNameEdited.setText(contact.getName().toString());
        txtPhoneEdited.setText(contact.getPhone().toString());

        btnOK.setTextColor(Color.WHITE);
        btnOK.setBackgroundColor(Color.BLACK);
        txtNameEdited.setTextColor(Color.WHITE);
        txtNameEdited.setBackgroundColor(Color.BLACK);
        txtPhoneEdited.setTextColor(Color.WHITE);
        txtPhoneEdited.setBackgroundColor(Color.BLACK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNameEdited.getText().toString().equalsIgnoreCase("") || txtPhoneEdited.getText().toString().equalsIgnoreCase(""))
                {
                    String msg="Necesitamos alguna información para editar el contacto";
                    showToast(msg);
                }
                else
                {
                    contact= new Contact(txtNameEdited.getText().toString(), txtPhoneEdited.getText().toString());

                    Intent intent = new Intent(Activity2.this, Listar.class);

                    intent.putExtra("contact", contact);

                    String msg="Modificado :"+"Nombre::: "+contact.getName().toString()+" Teléfono::: "+contact.getPhone().toString();
                    showToast(msg);

                    setResult(RESULT_OK, intent);

                    finish();

                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity2, menu);
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
