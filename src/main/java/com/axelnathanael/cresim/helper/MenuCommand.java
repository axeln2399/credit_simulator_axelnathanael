package com.axelnathanael.cresim.helper;

public enum MenuCommand {

    NEW("new", "Menginput data baru"),
    LOAD("load", "Memuat data kredit yang sudah disimpan"),
    EXIT("exit", "Keluar dari aplikasi");

    private final String command;
    private final String description;

    MenuCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static MenuCommand fromInput(String input) {
        for (MenuCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        return null;
    }

    public static void showAll() {
        System.out.println("\n=== Daftar Perintah ===");
        for (MenuCommand cmd : values()) {
            System.out.printf("- %s : %s%n", cmd.getCommand(), cmd.getDescription());
        }
        System.out.println("\nMasukan perintah yang diinginkan : ");
    }
}
