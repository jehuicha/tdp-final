package got;

import java.util.LinkedList;

public class Logica {
	
	private GUI gui;
	private Manager manager;
	private LinkedList<Palabra> palabras;
	private int contador;
	
	public Logica(GUI gui) {
		this.gui = gui;
		manager = new Manager(this);
	}
	
	public void cargarDirectorio(String dir) {
		if (!manager.setDirectorio(dir))
			gui.dirInvalid();
	}
	
	public void comenzar() {
		if(manager.cargado()) {
			palabras = new LinkedList<Palabra>();
			contador=0;
			manager.recorrerDirectorio();
			mostrar();
			}
	}
	
	public void addPalabra(String word) {
		if (word!="") {
			contador++;
			int n = palabras.size();
			int i=0;
			boolean match = false;
			
			while((i<n) && !match) {
				if (palabras.get(i).getPalabra().equalsIgnoreCase(word)) {
					palabras.get(i).sumarPalabra();
					match = true;
				}
				else 
					i++;
			}
			if(!match) {
				Palabra p = new Palabra(word);
				palabras.add(p);
			}
		}
	}
	
	public void mostrar() {
		if (!palabras.isEmpty())
		{
			LinkedList<Palabra> listaAuxiliar = new LinkedList<Palabra>();
			Palabra temp;
			int i;
			int n;
						
			for (int extra=0 ; extra<5 && !palabras.isEmpty() ; extra++) {
				
				temp = palabras.get(0);
				n = palabras.size();
				i = 1;
				
				while((i<n)) {
					temp = temp.mayor(palabras.get(i));
					i++;
				}
				
				listaAuxiliar.add(temp);
				palabras.remove(temp);
			}
			
			gui.resultado(listaAuxiliar,contador);
		}
	}
}
