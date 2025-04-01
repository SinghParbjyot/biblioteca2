package Biblioteca.modelo;

public class Libro {
	private int codigo;
	private String isbn;
	private String titulo;
	private String escritor;
	private int añoPublicacion;
	private double puntuacion;



	public Libro(int codigo, String isbn, String titulo, String escritor, int añoPublicacion, double puntuacion) {
		super();
		this.codigo = codigo;
		this.isbn = isbn;
		this.titulo = titulo;
		this.escritor = escritor;
		this.añoPublicacion = añoPublicacion;
		this.puntuacion = puntuacion;
	}

	public Libro(int codigo) {
		this.codigo = codigo;
	}
	
	public Libro(String isbn, String titulo) {
		this.isbn = isbn;
		this.titulo = titulo;
	}
	
	

	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}



	public String getIsbn() {
		return isbn;
	}



	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getEscritor() {
		return escritor;
	}



	public void setEscritor(String escritor) {
		this.escritor = escritor;
	}



	public int getAñoPublicacion() {
		return añoPublicacion;
	}



	public void setAñoPublicacion(int añoPublicacion) {
		this.añoPublicacion = añoPublicacion;
	}



	public double getPuntuacion() {
		return puntuacion;
	}



	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}



	@Override
	public String toString() {
		return "Libro [codigo=" + codigo + ", isbn=" + isbn + ", titulo=" + titulo + ", escritor=" + escritor
				+ ", añoPublicacion=" + añoPublicacion + ", puntuacion=" + puntuacion + "]";
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
