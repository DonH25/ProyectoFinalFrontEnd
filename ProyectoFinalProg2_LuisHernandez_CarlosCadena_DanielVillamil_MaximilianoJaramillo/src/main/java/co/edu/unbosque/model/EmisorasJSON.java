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

public class EmisorasJSON {

	private static URL url;
	private static String sitio = "http://localhost:8088/";

	public static ArrayList<Emisoras> getJSON() throws IOException, ParseException {
		url = new URL(sitio + "emisoras/listar");
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept", "application/json");
		InputStream respuesta = http.getInputStream();
		byte[] inp = respuesta.readAllBytes();
		String json = "";
		for (int i = 0; i < inp.length; i++) {
			json += (char) inp[i];
		}
		ArrayList<Emisoras> lista = new ArrayList<Emisoras>();
		lista = parsingEmisoras(json);
		http.disconnect();
		return lista;
	}

	public static ArrayList<Emisoras> parsingEmisoras(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Emisoras> lista = new ArrayList<Emisoras>();
		JSONArray emisoras = (JSONArray) jsonParser.parse(json);
		Iterator i = emisoras.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			Emisoras emisora = new Emisoras();
			emisora.setNombre_Emisora((innerObj.get("nombre_Emisora").toString()));
			emisora.setTipo_Emisora((innerObj.get("tipo_Emisora").toString()));
			emisora.setTipo_Musica(((innerObj.get("tipo_Musica").toString())));
			lista.add(emisora);
		}
		return lista;
	}

	public static int postJSON(Emisoras emisora) throws IOException {
		url = new URL(sitio + "emisoras/guardar");

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
		String data = "{" + "\"nombre_Emisora\":\"" + emisora.getNombre_Emisora() + "\",\"tipo_Emisora\": \""
				+ emisora.getTipo_Emisora() + "\",\"tipo_Musica\": \"" + emisora.getTipo_Musica() + "\"}";
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);
		int respuesta = http.getResponseCode();
		http.disconnect();
		return respuesta;
	}
}
