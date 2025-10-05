package com.axelnathanael.cresim.service;

import com.axelnathanael.cresim.helper.VehicleCondition;
import com.axelnathanael.cresim.helper.VehicleType;
import com.axelnathanael.cresim.model.LoanModel;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class FileInputService {

    public static void processJsonFile(String fileName) throws IOException {
        String json = Files.readString(Paths.get(fileName)).trim();

        if (!json.contains("\"data\"")) {
            System.out.println("Format file tidak sesuai (tidak ada field 'data').");
            return;
        }

        int startArray = json.indexOf("[");
        int endArray = json.lastIndexOf("]");
        if (startArray == -1 || endArray == -1) {
            System.out.println("Format JSON tidak sesuai.");
            return;
        }

        String dataContent = json.substring(startArray + 1, endArray).trim();

        List<String> objectStrings = splitObjects(dataContent);

        List<LoanModel> loanList = new ArrayList<>();

        for (String obj : objectStrings) {
            LoanModel loan = parseLoanObject(obj);
            if (loan != null) {
                loanList.add(loan);
            }
        }

        if (loanList.isEmpty()) {
            System.out.println("Tidak ada data valid ditemukan di file.");
            return;
        }

        System.out.println("Berhasil memuat " + loanList.size() + " data dari file.");
        LoanCalculatorService service = new LoanCalculatorService();

        for (LoanModel loan : loanList) {
            System.out.println("\nSimulasi untuk: " + loan.getVehicleType() + " " + loan.getCondition());
            List<String> hasil = service.calculateInstallments(loan);
            hasil.forEach(System.out::println);
        }
    }

    private static List<String> splitObjects(String dataContent) {
        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        int start = -1;

        for (int i = 0; i < dataContent.length(); i++) {
            char c = dataContent.charAt(i);
            if (c == '{') {
                if (braceCount == 0) start = i;
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0 && start != -1) {
                    objects.add(dataContent.substring(start + 1, i));
                }
            }
        }
        return objects;
    }

    private static LoanModel parseLoanObject(String obj) {
        String[] pairs = obj.split(",");
        String vehicleType = "";
        String vehicleCondition = "";
        BigDecimal loanAmount = BigDecimal.ZERO;
        BigDecimal downPayment = BigDecimal.ZERO;
        double rate = 0.0;
        int tenure = 0;

        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            if (kv.length < 2) continue;
            String key = kv[0].replace("\"", "").trim();
            String value = kv[1].replace("\"", "").trim();

            switch (key) {
                case "vehicleType" -> vehicleType = value;
                case "vehicleCondition" -> vehicleCondition = value;
                case "loanAmount" -> loanAmount = new BigDecimal(value);
                case "downPayment" -> downPayment = new BigDecimal(value);
                case "rate" -> rate = Double.parseDouble(value);
                case "tenure" -> tenure = Integer.parseInt(value);
            }
        }

        try {
            LoanModel loanModel = new LoanModel(
                    VehicleType.fromString(vehicleType),
                    VehicleCondition.fromString(vehicleCondition),
                    Year.now(),
                    loanAmount,
                    tenure,
                    downPayment
            );
            return loanModel;
        } catch (Exception e) {
            System.out.println("Gagal membaca file.");
            return null;
        }
    }
}
