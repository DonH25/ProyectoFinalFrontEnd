package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CancionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre_Canciones;
	private String genero_Musica;
	private String url_Cancion;
	private String artista_Canciones;

	public CancionBean() {
	}

	public String agregarCancion() {
		Canciones canciones = new Canciones();
		canciones.setNombre_Canciones(this.nombre_Canciones);
		canciones.setGenero_Musica(this.genero_Musica);
		canciones.setUrl_Cancion(this.url_Cancion);
		canciones.setArtista_Canciones(this.artista_Canciones);

		int respuesta = 0;
		try {
			respuesta = CancionesJSON.postJSON(canciones);
			if (respuesta == 200) {
				return "canciones?faces-redirect=true";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public ArrayList<Canciones> listarCanciones() {
		try {
			return CancionesJSON.getJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public String getNombre_Canciones() {
		return nombre_Canciones;
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

	public String getUrl_Cancion() {
		return url_Cancion;
	}

	public void setUrl_Cancion(String url_Cancion) {
		this.url_Cancion = url_Cancion;
	}

	public String getArtista_Canciones() {
		return artista_Canciones;
	}

	public void setArtista_Canciones(String artista_Canciones) {
		this.artista_Canciones = artista_Canciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
