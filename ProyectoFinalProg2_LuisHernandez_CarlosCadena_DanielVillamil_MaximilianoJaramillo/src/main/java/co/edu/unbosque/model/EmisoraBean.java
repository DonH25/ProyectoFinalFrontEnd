package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EmisoraBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre_Emisora;
	private String tipo_Musica;
	private String tipo_Emisora;

	public EmisoraBean() {
	}

	public String agregarEmisora() {
		Emisoras emisora = new Emisoras();
		emisora.setNombre_Emisora(this.nombre_Emisora);
		emisora.setTipo_Emisora(this.tipo_Emisora);
		emisora.setTipo_Musica(this.tipo_Musica);

		int respuesta = 0;
		try {
			respuesta = EmisorasJSON.postJSON(emisora);
			if (respuesta == 200) {
				return "reproductor?faces-redirect=true";
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
		return nombre_Emisora;
	}

	public void setNombre(String nombre_Emisora) {
		this.nombre_Emisora = nombre_Emisora;
	}

	public String getTipoEmisora() {
		return tipo_Emisora;
	}

	public void setTipoEmisora(String tipo_Emisora) {
		this.tipo_Emisora = tipo_Emisora;
	}

	public String getTipoMusica() {
		return tipo_Musica;
	}

	public void setTipoMusica(String tipo_Musica) {
		this.tipo_Musica = tipo_Musica;
	}
}
