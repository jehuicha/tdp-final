package com.example.proyectonotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectonotas.utilidades.Utilidades;

public class editItemActivity extends AppCompatActivity {

    private ConexionSQLiteHelper conn;
    private EditText campoTitulo;
    private EditText campoTexto;
    private String[] nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        campoTitulo = findViewById(R.id.editTitulo);
        campoTexto = findViewById(R.id.editTexto);

        Intent intent = getIntent();
        nota = new String[1];
        nota[0] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        conn = new ConexionSQLiteHelper(this,"bd_notas", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_NOTAS+" WHERE "+ Utilidades.CAMPO_TITULO+"=?",nota);

        while(cursor.moveToNext()){
            campoTitulo.setText(cursor.getString(0), TextView.BufferType.EDITABLE);
            campoTexto.setText(cursor.getString(1),TextView.BufferType.EDITABLE);
        }
        cursor.close();
        db.close();
    }

    public void deleteNota(View v){
        SQLiteDatabase db = conn.getWritableDatabase();
        db.delete(Utilidades.TABLA_NOTAS,Utilidades.CAMPO_TITULO+"=?",nota);
        db.close();
        this.finish();
    }

    public void updateNota(View v){
        if(campoTitulo.getText().toString().isEmpty())
            showMessage("Complete el campo Titulo");
        else {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"bd_notas", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Utilidades.CAMPO_TITULO, campoTitulo.getText().toString());
            values.put(Utilidades.CAMPO_TEXTO,campoTexto.getText().toString());

            int idres = db.update(Utilidades.TABLA_NOTAS,values,Utilidades.CAMPO_TITULO+"=?",nota);

            db.close();
            this.finish();
        }
    }

    private void showMessage(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}