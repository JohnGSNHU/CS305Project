package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SslServerApplication.class, args);
    }
}

@RestController
class ServerController {

    @RequestMapping("/hash")
    public String myHash() throws NoSuchAlgorithmException {

        // Create a unique data string containing your name.
        String data = "John Grzeskowiak - SNHU CS305";

        // Generate the SHA-256 checksum.
        MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
        byte[] byteArr = msgDigest.digest(
                data.getBytes(StandardCharsets.UTF_8));

        String checksum = toHexString(byteArr);

        // Verify the checksum by recalculating it.
        MessageDigest verifyDigest = MessageDigest.getInstance("SHA-256");
        byte[] verifyBytes = verifyDigest.digest(
                data.getBytes(StandardCharsets.UTF_8));

        String verifyChecksum = toHexString(verifyBytes);

        String result;
        if (checksum.equals(verifyChecksum)) {
            result = "SUCCESS - Checksums match";
        } else {
            result = "FAILED - Checksums do not match";
        }

        // Return the data, checksum, and verification result.
        return "<html><body>"
                + "<h2>SHA-256 Checksum Verification</h2>"
                + "<p>Name: John Grzeskowiak</p>"
                + "<p>Data: " + data + "</p>"
                + "<p>Checksum: " + checksum + "</p>"
                + "<p>Verification: " + result + "</p>"
                + "</body></html>";
    }

    // Convert the hash bytes to a hexadecimal string.
    private String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder();

        for (byte b : bytes) {
            hex.append(String.format("%02x", b & 0xff));
        }

        return hex.toString();
    }
}