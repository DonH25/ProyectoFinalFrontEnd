package co.edu.unbosque.model;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
		String json = "";
		for (int i = 0; i < inp.length; i++) {
			json += (char) inp[i];
		}
		ArrayList<Canciones> lista = new ArrayList<Canciones>();
		lista = parsingCanciones(json);
		http.disconnect();
		return lista;
	}

	public static ArrayList<Canciones> parsingCanciones(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Canciones> lista = new ArrayList<Canciones>();
		JSONArray canciones = (JSONArray) jsonParser.parse(json);
		Iterator i = canciones.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			Canciones cancion = new Canciones();
			cancion.setNombre_Canciones((innerObj.get("nombre_Canciones").toString()));
			cancion.setGenero_Musica((innerObj.get("genero_Musica").toString()));
			cancion.setURL_Cancion(((innerObj.get("url_Cancion").toString())));
			lista.add(cancion);
		}
		return lista;
	}

	public static int postJSON(Canciones canciones) throws IOException {
		url = new URL(sitio + "canciones/guardar");

		HttpURLConnection http;
		http = (HttpURLConnection) url.openConnection();
		try {
			http.setRequestMethod("POST");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		http.setDoOutput(true);
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Content-Type", "application/json");
		String data = "{" + "\"nombre_Canciones\":\"" + canciones.getNombre_Canciones() + "\",\"url_Cancion\": \""
				+ canciones.getURL_Cancion() + "\",\"genero_Musica\": \"" + canciones.getGenero_Musica() + "\"}";
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);
		int respuesta = http.getResponseCode();
		http.disconnect();
		return respuesta;
	}
}
