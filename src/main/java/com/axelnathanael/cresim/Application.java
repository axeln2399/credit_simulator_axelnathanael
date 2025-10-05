package com.axelnathanael.cresim;

import com.axelnathanael.cresim.service.FileInputService;
import com.axelnathanael.cresim.view.MenuView;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                String fileName = args[0];
                System.out.println("=== Mode File Input ===");
                FileInputService.processJsonFile(fileName);
            } else {
                MenuView.openMenu();
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }
}
