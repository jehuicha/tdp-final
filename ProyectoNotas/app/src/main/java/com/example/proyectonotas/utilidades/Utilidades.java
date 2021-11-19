package com.example.proyectonotas.utilidades;

public class Utilidades {
    public static final String TABLA_NOTAS = "notas";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_TEXTO = "texto";

    public final static String CREAR_TABLA_USUARIO = "CREATE TABLE "+TABLA_NOTAS+" ( "+
            CAMPO_TITULO+" TEXT, "+CAMPO_TEXTO+" TEXT)";
}
