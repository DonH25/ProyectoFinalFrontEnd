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
	}

	public String agregarEmisora() {
		Emisoras emisora = new Emisoras();
		emisora.setNombre_Emisora(this.nombre);
		emisora.setTipo_Emisora(this.tipoEmisora);
		emisora.setTipo_Musica(this.tipoMusica);

		int respuesta = 0;
		try {
			respuesta = EmisorasJSON.postJSON(emisora);
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

	public ArrayList<Emisoras> listarEmisoras() {
		try {
			return EmisorasJSON.getJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

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
