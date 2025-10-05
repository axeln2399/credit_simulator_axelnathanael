package com.axelnathanael.cresim.view;

import com.axelnathanael.cresim.helper.MenuCommand;
import com.axelnathanael.cresim.helper.VehicleCondition;
import com.axelnathanael.cresim.helper.VehicleType;
import com.axelnathanael.cresim.model.Sheet;
import com.axelnathanael.cresim.model.LoanModel;
import com.axelnathanael.cresim.service.LoanCalculatorService;
import com.axelnathanael.cresim.service.WebService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.*;

import static com.axelnathanael.cresim.helper.Constant.*;

public class MenuView {

    private static final Scanner SCAN = new Scanner(System.in);
    private static boolean running = true;
    private static boolean backToStart = false;
    private static final Map<String, List<Sheet>> listSheet = new HashMap<>();
    private static final List<LoanModel> listLoan = new ArrayList<>();

    public static void openMenu() throws IOException {
        System.out.println("=== Credit Simulator ===");
        System.out.println("===  Selamat Datang  ===");

        while (running) {
            System.out.print("\nMasukkan perintah awal (show/end): ");
            String input = SCAN.nextLine().trim();

            if (input.equalsIgnoreCase("show")) {
                showMainMenu();
            } else if (input.equalsIgnoreCase("end")) {
                System.out.println("Keluar dari aplikasi...");
                running = false;
            } else {
                System.out.println("Perintah tidak dikenal. Ketik 'show' atau 'end'.");
            }
        }
    }

    private static void showMainMenu() {
        while (!backToStart) {
            MenuCommand.showAll();
            String input = SCAN.nextLine().trim();

            MenuCommand cmd = MenuCommand.fromInput(input);
            if (cmd == null) {
                System.out.println("Perintah tidak dikenal. Menampilkan semua menu...");
                MenuCommand.showAll();
                continue;
            }

            switch (cmd) {
                case NEW -> {
                    System.out.println("\n>> Mulai input data kendaraan baru.");
                    boolean success = inputLoanData();
                    if (!success) {
                        backToStart = false;
                    }
                }
                case LOAD -> {
                    System.out.println(">> Load sheet yang sudah tersimpan.");
                    String data = WebService.loadDataFromApi();
                    if (data != null) {
                        LoanModel loanModel = new LoanModel();
                        boolean isSuccess = calculateData(loanModel);

                        if (!isSuccess) {
                            backToStart = false;
                        } else {
                            backToStart = true;
                        }
                    } else {
                        System.out.println("Mohon coba beberapa saat lagi.");
                        backToStart = false;
                    }
                }
                case EXIT -> {
                    System.out.println("Kembali ke menu awal...");
                    backToStart = true; // keluar dari loop utama, balik ke openMenu()
                }
            }
        }
    }

    private static Boolean inputLoanData() {
        System.out.println("\n=== Input Data Pinjaman Baru (ketik 'exit' untuk batal) ===");

        VehicleType vehicleType = null;
        while (vehicleType == null) {
            System.out.print("Jenis Kendaraan (Mobil/Motor): ");
            String input = SCAN.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return false;
            try {
                vehicleType = VehicleType.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("\nData yang dimasukan tidak sesuai.\nKembali ke menu awal.");
                return false;
            }
        }

        VehicleCondition condition = null;
        while (condition == null) {
            System.out.print("Kondisi (Baru/Bekas): ");
            String input = SCAN.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return false;
            try {
                condition = VehicleCondition.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("\nData yang dimasukan tidak sesuai.\nKembali ke menu awal.");
                return false;
            }
        }

        Year year = null;
        while (year == null) {
            System.out.print("Tahun Kendaraan (YYYY): ");
            String input = SCAN.nextLine().trim();
            if (input.equalsIgnoreCase("exit"))
                return false;
            try {
                year = Year.of(Integer.parseInt(input));
                if (VehicleCondition.BARU.equals(condition) && year.isBefore(Year.now().minusYears(1L))) {
                    System.out.println("\nTipe kendaraan baru tidak bisa memilih kendaraan yang keluaran > 1 tahun.\nKembali ke menu awal.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("\nInput tahun tidak valid.\nKembali ke menu awal.");
                return false;
            }
        }

        BigDecimal totalLoan = null;
        while (totalLoan == null) {
            System.out.print("Jumlah Pinjaman (<= 1 miliar): ");
            String input = SCAN.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return false;
            try {
                totalLoan = new BigDecimal(input);
                if (ONE_BILLION.compareTo(totalLoan) < 0) {
                    System.out.println("\nJumlah pinjaman melebihi batas ketentuan.\nKembali ke menu awal.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("\nInput jumlah pinjaman tidak valid.\nKembali ke menu awal.");
                return false;
            }
        }

        int tenure = 0;
        while (tenure < 1 || tenure > 6) {
            System.out.print("Tenor Pinjaman (1-6 tahun): ");
            String input = SCAN.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return false;
            try {
                tenure = Integer.parseInt(input);
                if (tenure < 1 || tenure > 6) {
                    System.out.println("\nTenor harus 1-6 tahun.\nKembali ke menu awal.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("\nInput tenor tidak valid.\nKembali ke menu awal.");
                return false;
            }
        }

        BigDecimal dp = null;
        while (dp == null) {
            System.out.print("Jumlah DP: ");
            String input = SCAN.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) return false;
            try {
                dp = new BigDecimal(input);
                if (dp.compareTo(condition.getMinDpPercentage().multiply(totalLoan)) < 0) {
                    System.out.println("\nJumlah DP tidak sesuai Ketentuan.\nKembali ke menu awal.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("\nInput DP tidak valid.\nKembali ke menu awal.");
                return false;
            }
        }

        // Build Models
        LoanModel loanModel = new LoanModel(vehicleType, condition, year, totalLoan, tenure, dp);

        System.out.println("\n>> Data berhasil dimasukkan!");

        boolean isSuccess = calculateData(loanModel);

        if (!isSuccess) {
            return false;
        }

        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
        SCAN.nextLine();
        return false;
    }

    public static boolean calculateData(LoanModel loanModel) {
        LoanCalculatorService service = new LoanCalculatorService();
        List<String> listLoan = service.calculateInstallments(loanModel);

        System.out.println("Simulasi cicilan untuk " + loanModel.getVehicleType() + " " + loanModel.getCondition() +
                " dengan pinjaman Rp " + loanModel.getTotalLoanAmount() + " tenor " + loanModel.getLoanTenureYears() + " tahun.");

        listLoan.forEach(System.out::println);
        System.out.println("Apakah anda ingin menyimpan data ini (ya/tidak)? : ");
        String saveOrNo = SCAN.nextLine().trim();
        if (saveOrNo.equalsIgnoreCase("exit")) return false;
        if (YES_NO.stream().noneMatch(saveOrNo::equalsIgnoreCase)) {
            System.out.println("Input perintah tidak valid.\nKembali ke menu awal.");
            return false;
        }

        if (saveOrNo.equalsIgnoreCase("ya")) {
            if (listSheet.isEmpty()) {
                System.out.println("\nTidak ada sheet yang tersimpan. Simpan ke sheet baru.");
                System.out.print("\nMasukan nama sheet baru : ");
                String sheetName = SCAN.nextLine().trim();

                Sheet sheetNew = new Sheet(sheetName, loanModel, listLoan);
                listSheet.computeIfAbsent(sheetName, k -> new ArrayList<>()).add(sheetNew);

                System.out.println("\nSheet baru berhasil dibuat dan data disimpan!");
            } else {
                System.out.println("\nTerdapat sheet yang tersimpan:");
                listSheet.keySet().forEach(System.out::println);

                System.out.print("\nSimpan data ke sheet: ");
                String selectedSheet = SCAN.nextLine().trim();

                Sheet sheetNew = new Sheet(selectedSheet, loanModel, listLoan);
                listSheet.computeIfAbsent(selectedSheet, k -> new ArrayList<>()).add(sheetNew);

                System.out.println("\nData berhasil disimpan ke sheet " + selectedSheet);
            }
            return false;
        }
        return true;
    }
}
