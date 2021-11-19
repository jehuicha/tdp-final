package com.example.proyectonotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectonotas.utilidades.Utilidades;

public class addNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    public void AgregarNota(View view){
        EditText campoTitulo = (EditText) findViewById(R.id.editTitulo);
        EditText campoTexto = (EditText) findViewById(R.id.editTexto);

        if(campoTitulo.getText().toString().isEmpty())
            showMessage("Complete el campo Titulo");
        else if (existeTitulo(campoTitulo.getText().toString()))
            showMessage("El titulo ya existe, ingrese uno nuevo.");
        else {

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"bd_notas", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Utilidades.CAMPO_TITULO, campoTitulo.getText().toString());
            values.put(Utilidades.CAMPO_TEXTO, campoTexto.getText().toString());

            Long idres = db.insert(Utilidades.TABLA_NOTAS,null,values);

            showMessage("Registro = "+idres);

            db.close();
            this.finish();
        }
    }

    private boolean existeTitulo(String title){
        boolean existe =false;
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"bd_notas", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String [] prueba = new String[1];
        prueba[0] = title;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_NOTAS+" WHERE "+ Utilidades.CAMPO_TITULO+"=?",prueba);

        if(cursor.getCount()!=0)
            existe = true;

        cursor.close();
        db.close();

        return existe;
    }

    private void showMessage(String s){
        Toast.makeText(getApplicationContext(),s,
                Toast.LENGTH_LONG).show();
    }
}