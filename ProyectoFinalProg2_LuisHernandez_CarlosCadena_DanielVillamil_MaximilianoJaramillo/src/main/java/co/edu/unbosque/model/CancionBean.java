package co.edu.unbosque.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@ManagedBean
@SessionScoped
public class CancionBean {
	private String nombre_Cancion;
	private String genero_Musica;
	private byte[] archivo_MP3;
	private String mediaUrl; // Variable para almacenar la URL del archivo cargado

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();

		if (uploadedFile != null) {
			try (InputStream input = uploadedFile.getInputStream()) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096]; // Tamaño del buffer

				int bytesRead;
				while ((bytesRead = input.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}

				archivo_MP3 = output.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
				// Manejar o registrar la excepción apropiadamente
			}
		}
	}

	public String agregarCancion() {
		Canciones cancion = new Canciones();
		cancion.setNombre_Canciones(this.nombre_Cancion);
		cancion.setGenero_Musica(this.genero_Musica);
		cancion.setArchivo_MP3(this.archivo_MP3);

		int respuesta = 0;
		try {
			respuesta = CancionesJSON.postJSON(cancion);
			if (respuesta == 200) {
				return "playlist?faces-redirect=true";
			} else {
				return "error?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public ArrayList<Canciones> listarCanciones() {
		try {
			return CancionesJSON.getJSON(); // Suponiendo que existe un servicio para obtener la lista de canciones
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getNombre_Cancion() {
		return nombre_Cancion;
	}

	public void setNombre_Cancion(String nombre_Cancion) {
		this.nombre_Cancion = nombre_Cancion;
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
