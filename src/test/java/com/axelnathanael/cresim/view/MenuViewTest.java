package com.axelnathanael.cresim.view;

import com.axelnathanael.cresim.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class MenuViewTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testRunWithFileInput() throws Exception {
        String[] args = {"credit_simulator file_inputs.txt"}; // pastikan file_inputs.txt ada di root project
        Application.main(args);

        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("=== Mode File Input ==="));
        Assertions.assertTrue(output.contains("Berhasil memuat"));
    }

    @Test
    void testInvalidVehicleTypeTypo() throws IOException {
        String simulatedInput = String.join("\n",
                "show",
                "new",
                "mobiil", // salah tulis
                "exit",
                "end"
        ) + "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        MenuView.openMenu();

        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("Data yang dimasukan tidak sesuai"));
        Assertions.assertTrue(output.contains("Kembali ke menu awal"));
    }
}
