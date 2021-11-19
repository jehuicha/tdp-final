package com.example.proyectonotas;

public class Note {

    private String titulo;
    private String texto;

    public Note(String titulo, String texto){
        this.titulo = titulo;
        this.texto = texto;
    }

    public Note(){
        this.titulo = "";
        this.texto = "";
    }

    public String getTitulo(){
        return titulo;
    }

    public String getTexto(){
        return texto;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

}
