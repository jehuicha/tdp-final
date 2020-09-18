package got;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


import java.io.FileInputStream;

public class Manager {
	
	private Logica logica;
	private File directorio;
	private String path;
	private boolean cargo;
	
	public Manager(Logica logica) {
		this.logica = logica;
		cargo = false;
	}
	
	public boolean setDirectorio(String path) {	
		cargo = false;
		directorio = new File(path);
		if (directorio.exists())
			if (directorio.isDirectory()) {
				cargo = true;
				this.path = path;
			}
		return cargo;
	}
	
	public boolean cargado() {
		return cargo;
	}
	
	public void recorrerDirectorio() {
		String [] archivos = directorio.list();
		String auxiliar;
		for(int i = 0 ; i< archivos.length ; i++) {
			if(archivos[i].endsWith(".txt")) {
				auxiliar= path+"\\"+archivos[i];
				recorrerArchivo(auxiliar);
			}
		}
	}
	
	private void recorrerArchivo(String path) {
		BufferedReader br = null;
        
        try {

            String sCurrentLine;
            String temp = "";

            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));

            while ((sCurrentLine = br.readLine()) != null) {
            	
            	for(int i = 0; i < sCurrentLine.length(); i++){
            		char letra = sCurrentLine.charAt(i);
            		
            		if(!esLetra(letra)) {
            			temp = temp.toLowerCase();
            			logica.addPalabra(temp);
            			temp = "";
            		}
            		else
            			temp = temp+letra;
            	}
            	if (temp!="") {
            		temp = temp.toLowerCase();
            		logica.addPalabra(temp);
            		temp = "";
            	}
            }
        } catch (IOException e) { // Esto es por si ocurre un error
            e.printStackTrace();
        } finally { // Esto es para que, haya ocurrido error o no
            try {
                if (br != null)br.close(); // Cierre el archivo
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	private boolean esLetra(char c) {
		if(((c>='A') && (c<='Z')) || ((c>='a') && (c<='z')) || 
				(c>='á') || (c=='é') || (c=='í') || (c=='ó') || (c=='ú') || (c=='ñ') || 
				(c>='Á') || (c=='É') || (c=='Í') || (c=='Ó') || (c=='Ú')|| (c=='Ñ'))
			return true;
		else return false;
	}
	
}
