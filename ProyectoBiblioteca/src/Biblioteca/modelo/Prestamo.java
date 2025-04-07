package Biblioteca.modelo;

public class Prestamo {
	private Libro libro;
	private Socio socio;
	private String fechaInicio;
	private String fechaFin;
	private String fechaDevolucion;
	private static int contador = 1;
	private int numeroPrestamo;
	public Prestamo(Libro libro, Socio socio, String fechaInicio, String fechaFin,String fechaDevolucion) {
		super();
		this.libro = libro;
		this.socio = socio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaDevolucion = fechaDevolucion;
	}
	public Prestamo(Libro libro ,Socio socio,String fechaDevolucion) {
		this.libro = libro;
		this.socio = socio;
		this.fechaDevolucion = fechaDevolucion;
	}
	public Prestamo(Libro libro ,Socio socio) {
		this.libro = libro;
		this.socio = socio;
	}
	public Prestamo(Libro libro ,Socio socio,int numeroPrestamos) {
		this.libro = libro;
		this.socio = socio;
		this.numeroPrestamo = numeroPrestamos;
	}

	@Override
	public String toString() {
		
		String cadena =  "("+(contador++)+")Prestamo [codigo_libro=" + libro.getCodigo() + ", codigo_socio=" + socio.getCodigo() + ", fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + ", fechaDevolucion=" + fechaDevolucion + "]\n";
		return cadena;
	}
	public String toString2() {
		
		return "("+(contador++)+")"+"Nombre="+socio.getNombre()+",DNI="+socio.getDni()+",Titulo="+libro.getTitulo()+",ISBN="+libro.getIsbn()+",fechaDevolucion="+this.fechaDevolucion+"\n";
	}
	
	public String toString3() {
		
		return "("+(contador++)+")"+"Titulo="+libro.getTitulo()+",ISBN="+libro.getIsbn()+", Numero_Prestamo="+this.numeroPrestamo;
	}
	public String toString4() {
        return "(" + (contador++) + ") Nombre=" + socio.getNombre() + 
               ", DNI=" + socio.getDni() + ", Numero_Prestamo=" + this.numeroPrestamo;
    }
	
	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
