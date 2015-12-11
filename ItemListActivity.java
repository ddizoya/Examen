package com.example.ddizoya.examen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;



public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks {

    private boolean mTwoPane;
    //Creamos una constante de tipo int que valide el valor enviado por el startActivityForResult()
    private static final int OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
           Le decimos que, de forma obligatoria, si no es nulo y está apaisado, que cargue una sola activity con el fragment de listas
           y sus detalles.
           Si no es así, es decir, no es nulo pero no está apaisado, se verá en modo default, teniendo que depender de dos activities y dos fragments.
           La condición booleana la puedes encontrar en res/values/condicion.xml.

         */

        if (findViewById(R.id.item_detail_container) != null && getResources().getBoolean(R.bool.apaisado) == true) {

            mTwoPane = true;

            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }


    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {

            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {

            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);

            //Empleamos el método startActivityForResult(), y le pasamos el intent y la constante que creamos previamente como atributo (valor int 1)
            startActivityForResult(detailIntent, OK);
        }
    }

    //Una vez abrimos el detalle de alguien, y le damos al botón de Borrar que hay allí, se cerrará la 2ª activity y saldrá la toast.
    //Este método siempre retorna el dato cuando la activity a la que se llama se 'muere'. Es importante que se cierre.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OK){
            Toast.makeText(this,  "Activity cerrada", Toast.LENGTH_LONG).show();
            }
        }



    }


