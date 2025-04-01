package Biblioteca.modelo;

public class Prestamo {
	private Libro libro;
	private Socio socio;
	private String fechaInicio;
	private String fechaFin;
	private String fechaDevolucion;
	
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
	public static String obtenerFechaDevolucion(String fechaInicio) {
        // Suponemos que el formato de entrada es "YYYY-MM-DD"
        String[] partes = fechaInicio.split("-");
        int anio = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        // Sumar 14 días manualmente
        dia += 14;

        // Días máximos en cada mes (sin considerar bisiestos en febrero)
        int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Ajustar febrero en años bisiestos
        if (mes == 2 && (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0))) {
            diasPorMes[2] = 29;
        }

        // Si el día supera el máximo del mes, ajustar
        if (dia > diasPorMes[mes]) {
            dia -= diasPorMes[mes];
            mes++;
            if (mes > 12) { // Si el mes pasa diciembre, avanzar un año
                mes = 1;
                anio++;
            }
        }

        // Formatear la fecha con ceros a la izquierda si es necesario
        String nuevoMes = (mes < 10) ? "0" + mes : String.valueOf(mes);
        String nuevoDia = (dia < 10) ? "0" + dia : String.valueOf(dia);

        return anio + "-" + nuevoMes + "-" + nuevoDia;
    }

	@Override
	public String toString() {
		int contador = 0;
		return "("+contador+++")Prestamo [codigo_libro=" + libro.getCodigo() + ", codigo_socio=" + socio.getCodigo() + ", fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + ", fechaDevolucion=" + fechaDevolucion + "]\n";
	}
	public String toString2() {
		int contador = 0;
		return "("+contador+++")"+"Nombre="+socio.getNombre()+",DNI="+socio.getDni()+",Titulo="+libro.getTitulo()+",ISBN="+libro.getIsbn()+",fechaDevolucion="+this.fechaDevolucion+"\n";
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
