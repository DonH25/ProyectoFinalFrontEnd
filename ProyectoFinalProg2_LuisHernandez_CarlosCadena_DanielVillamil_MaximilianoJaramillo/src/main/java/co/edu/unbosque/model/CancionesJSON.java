package co.edu.unbosque.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.primefaces.model.file.UploadedFile;

public class CancionesJSON {

	private static URL url;
	private static String sitio = "http://localhost:8088/";

	public static ArrayList<Canciones> getJSON() throws IOException, ParseException {
		url = new URL(sitio + "canciones/listar");
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept", "application/json");
		InputStream respuesta = http.getInputStream();
		byte[] inp = respuesta.readAllBytes();
		String json = new String(inp, StandardCharsets.UTF_8);
		ArrayList<Canciones> lista = parsingCanciones(json);
		http.disconnect();
		return lista;
	}

	public static ArrayList<Canciones> parsingCanciones(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Canciones> lista = new ArrayList<>();
		JSONArray canciones = (JSONArray) jsonParser.parse(json);
		Iterator i = canciones.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			Canciones cancion = new Canciones();

			cancion.setNombre_Canciones((String) innerObj.get("nombre_Canciones"));
			cancion.setGenero_Musica((String) innerObj.get("genero_Musica"));
			cancion.setArchivo_MP3((byte[]) innerObj.get("archivo_MP3"));
			lista.add(cancion);
		}
		return lista;
	}

	import java.io.*;
	import java.net.*;
	import java.nio.charset.StandardCharsets;
	import java.util.Base64;
	import org.json.JSONObject;

	public static int postJSON(Emisoras emisora) throws IOException {
	    URL url = new URL(sitio + "emisoras/guardar");

	    HttpURLConnection http = (HttpURLConnection) url.openConnection();
	    try {
	        http.setRequestMethod("POST");
	    } catch (ProtocolException e) {
	        e.printStackTrace();
	    }
	    http.setDoOutput(true);
	    http.setRequestProperty("Accept", "application/json");
	    http.setRequestProperty("Content-Type", "application/json");

	    // Prepare JSON object for the emisora data
	    JSONObject jsonEmisora = new JSONObject();
	    jsonEmisora.put("nombre_Emisora", emisora.getNombre_Emisora());
	    jsonEmisora.put("tipo_Emisora", emisora.getTipo_Emisora());
	    jsonEmisora.put("tipo_Musica", emisora.getTipo_Musica());

	    // Check if archivo_MP3 is available and encode it to Base64
	    if (emisora.getArchivo_MP3() != null) {
	        String base64MP3 = Base64.getEncoder().encodeToString(emisora.getArchivo_MP3());
	        jsonEmisora.put("archivo_MP3", base64MP3);
	    } else {
	        jsonEmisora.put("archivo_MP3", null); // Handle if MP3 file is null
	    }

	    // Write the JSON data to the output stream of the HTTP request
	    OutputStream outputStream = http.getOutputStream();
	    outputStream.write(jsonEmisora.toString().getBytes(StandardCharsets.UTF_8));
	    outputStream.flush();

	    int respuesta = http.getResponseCode();
	    http.disconnect();
	    return respuesta;
	}

}
