package com.axelnathanael.cresim.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.axelnathanael.cresim.helper.Constant.MOCKY_URL;

public class WebService {

    public static String loadDataFromApi() {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(MOCKY_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            // 🔴 Handle Error Response
            if (status != HttpURLConnection.HTTP_OK) {
                System.out.println("Gagal memuat data. HTTP Status: " + status);
                if (status == HttpURLConnection.HTTP_NOT_FOUND) {
                    System.out.println("Data tidak ditemukan (Error 404).");
                }
                return null;
            }

            // ✅ Read response jika OK
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                System.out.println("\n=== Data dari Mock API ===");
                System.out.println(response);
                System.out.println("==========================");

                return response.toString();
            }

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memuat data");
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
