package com.example.proyectonotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.proyectonotas.utilidades.Utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    ListView listViewNotas;
    ArrayList<Note> listaNotas;
    ArrayList<String> listaInformacion;

    public static final String EXTRA_MESSAGE = "clave_message_titulo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_notas", null, 1);
        listViewNotas = findViewById(R.id.listViewNotas);
        listViewNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editar(listaNotas.get(position).getTitulo());
            }
        });
        consultarListaNotas();
    }

    public void editar(String name){
        Intent intent = new Intent(this, editItemActivity.class);
        intent.putExtra(EXTRA_MESSAGE,name);
        startActivity(intent);
    }

    public void addNote(android.view.View view){
        Intent intent = new Intent(this, addNoteActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Intent intent = new Intent(this, ActivityAbout.class);///////////////////////////////////////////////////////////////////////////////////
        //startActivity(intent);

        //System.out.println("Se selecciono un item");
        return super.onOptionsItemSelected(item);
    }

    private void consultarListaNotas(){
        SQLiteDatabase db = conn.getReadableDatabase();
        Note nota = null;
        listaNotas = new ArrayList<Note>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_NOTAS,null);

        while(cursor.moveToNext()){
            nota = new Note();
            nota.setTitulo(cursor.getString(0));
            nota.setTexto(cursor.getString(1));
            listaNotas.add(nota);
        }
        cursor.close();
        obtenerListaInformacion();
        db.close();
    }

    private void obtenerListaInformacion() {
        listaInformacion = new ArrayList<String>();

        for(int i=0;i<listaNotas.size();i++){
            listaInformacion.add(listaNotas.get(i).getTitulo());
        }

        miArrayAdapter adaptador = new miArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listaInformacion);
        listViewNotas.setAdapter(adaptador);
    }
    protected void onStart(){
        super.onStart();
        consultarListaNotas();
    }
}