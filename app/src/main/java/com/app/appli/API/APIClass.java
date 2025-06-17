package com.app.appli.API;
import com.owlike.genson.Genson;
import com.owlike.genson.GenericType;
import java.io.*;
import java.net.*;
import java.util.List;

public class APIClass {

    private static final String BASE_URL = getString(R.strings.API_URL);
    private static final Genson genson = new Genson();

    // GET all
    public static List<Appartement> getAll() throws IOException {
        URL url = new URL(BASE_URL+"/appartements");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStream in = new BufferedInputStream(conn.getInputStream());
        String json = new Scanner(in).useDelimiter("\\A").next();
        in.close();
        conn.disconnect();

        return genson.deserialize(json, new GenericType<List<Appartement>>() {});
    }

    // GET by ID
    public static Appartement getById(String id) throws IOException {
        URL url = new URL(BASE_URL + "/appartement/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStream in = new BufferedInputStream(conn.getInputStream());
        String json = new Scanner(in).useDelimiter("\\A").next();
        in.close();
        conn.disconnect();

        return genson.deserialize(json, Appartement.class);
    }

    // POST (create)
    public static Appartement create(Appartement appartement) throws IOException {
        URL url = new URL(BASE_URL+"/appartement");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonToSend = genson.serialize(appartement);
        OutputStream os = conn.getOutputStream();
        os.write(jsonToSend.getBytes());
        os.flush();
        os.close();

        InputStream in = new BufferedInputStream(conn.getInputStream());
        String jsonResponse = new Scanner(in).useDelimiter("\\A").next();
        in.close();
        conn.disconnect();

        return genson.deserialize(jsonResponse, Appartement.class);
    }

    // PUT (update)
    public static Appartement update(String id, Appartement appartement) throws IOException {
        URL url = new URL(BASE_URL + "/appartement/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonToSend = genson.serialize(appartement);
        OutputStream os = conn.getOutputStream();
        os.write(jsonToSend.getBytes());
        os.flush();
        os.close();

        InputStream in = new BufferedInputStream(conn.getInputStream());
        String jsonResponse = new Scanner(in).useDelimiter("\\A").next();
        in.close();
        conn.disconnect();

        return genson.deserialize(jsonResponse, Appartement.class);
    }

    // DELETE
    public static boolean delete(String id) throws IOException {
        URL url = new URL(BASE_URL + "/appartement/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        conn.disconnect();

        return responseCode == 200 || responseCode == 204;
    }
}
