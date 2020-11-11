package io.mediamachine.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.mediamachine.models.Job;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import static java.util.Map.entry;

public class RealAPI implements API {
//    private static String BASE_API_PATH = "https://api.stackrock.io";
    private static String BASE_API_PATH = "http://localhost:9000";
    private static Map<String, String> SERVICES_TO_PATH = Map.ofEntries(
        entry("thumbnail", "/thumbnail"),
        entry("gif_summary", "/summary/gif"),
        entry("mp4_summary", "/summary/mp4"),
        entry("transcode", "/transcode")
    );

    public Job createJob(String jobType, String body) throws FileNotFoundException {
//        System.out.println("-------------------------------------");
//        System.out.println(body);
//        System.out.println("-----------------------------------------");

        try {
            URL url = new URL(BASE_API_PATH + SERVICES_TO_PATH.get(jobType));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            String jsonInputString = body;

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            JsonObject jsonResponse;
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                jsonResponse = new Gson().fromJson(response.toString(), JsonObject.class);
            }

            con.disconnect();
            return new Job(jsonResponse.get("id").getAsString());
        } catch (MalformedURLException e) {
            return null;
        } catch (ProtocolException e) {
            return null;
        } catch (FileNotFoundException e) {
            // 404
            throw e;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getJobStatus(String jobId) {
        try {
            String uri = BASE_API_PATH + "/jobs/" + jobId + "/status";
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);


            JsonObject jsonResponse;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                jsonResponse = new Gson().fromJson(response.toString(), JsonObject.class);
            }

            con.disconnect();
            return jsonResponse.get("status").getAsString();
        } catch(MalformedURLException ex) {
            return "unknown";
        } catch (UnsupportedEncodingException e) {
            return "unknown";
        } catch (ProtocolException e) {
            return "unknown";
        } catch (IOException e) {
            return "unknown";
        }
    }
}
