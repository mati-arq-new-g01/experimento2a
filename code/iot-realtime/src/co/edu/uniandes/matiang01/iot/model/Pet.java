package co.edu.uniandes.matiang01.iot.model;

public class Pet {
	private String palabra;
	private String animal;
	private String cuenta;

	public Pet() {
	}

	public Pet(String palabra, String animal, String cuenta) {
		this.palabra = palabra ;
		this.animal = animal ;
		this.cuenta = cuenta;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

}
