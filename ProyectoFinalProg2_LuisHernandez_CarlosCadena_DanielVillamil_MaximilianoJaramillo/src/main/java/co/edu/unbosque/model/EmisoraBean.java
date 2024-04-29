package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EmisoraBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String tipoEmisora;
	private String tipoMusica;

	public EmisoraBean() {
		// Constructor vacío
	}

	public String agregarEmisora() {
		Emisoras emisora = new Emisoras();
		emisora.setNombre_Emisora(this.nombre);
		emisora.setTipo_Emisora(this.tipoEmisora);
		emisora.setTipo_Musica(this.tipoMusica);

		int respuesta = 0;
		try {
			respuesta = TestJSON.postJSON(emisora);
			if (respuesta == 200) {
				return "registroAgregado"; // Nombre de la página de éxito
			} else {
				return "error"; // Nombre de la página de error
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // En caso de excepción, mostrar página de error
		}
	}

	public ArrayList<Emisoras> listarEmisoras() {
		try {
			return TestJSON.getJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>(); // Devolver lista vacía en caso de error
		}
	}

	// Getters y setters para los atributos

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoEmisora() {
		return tipoEmisora;
	}

	public void setTipoEmisora(String tipoEmisora) {
		this.tipoEmisora = tipoEmisora;
	}

	public String getTipoMusica() {
		return tipoMusica;
	}

	public void setTipoMusica(String tipoMusica) {
		this.tipoMusica = tipoMusica;
	}
}
