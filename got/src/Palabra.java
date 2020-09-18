package got;

public class Palabra {
	
	private String palabra;
	private int cantidad;
	
	public Palabra(String palabra) {
		this.palabra = palabra;
		cantidad++;
	}
	
	public void sumarPalabra() {
		cantidad++;
	}
	
	public String getPalabra() {
		return palabra;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public Palabra mayor(Palabra p) {
		if (cantidad>=p.getCantidad())
			return this;
		else return p;
	}

}
