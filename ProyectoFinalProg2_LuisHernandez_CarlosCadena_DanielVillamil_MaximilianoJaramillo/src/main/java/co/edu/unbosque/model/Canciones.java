package co.edu.unbosque.model;

public class Canciones {
	private Integer id;
	private String nombre_Canciones;
	private String genero_Musica;
	private byte[] archivo_MP3; // Cambio de String a byte[] para almacenar el archivo MP3

	public String getNombre_Canciones() {
		return nombre_Canciones;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre_Canciones(String nombre_Canciones) {
		this.nombre_Canciones = nombre_Canciones;
	}

	public String getGenero_Musica() {
		return genero_Musica;
	}

	public void setGenero_Musica(String genero_Musica) {
		this.genero_Musica = genero_Musica;
	}

	public byte[] getArchivo_MP3() {
		return archivo_MP3;
	}

	public void setArchivo_MP3(byte[] archivo_MP3) {
		this.archivo_MP3 = archivo_MP3;
	}
}
