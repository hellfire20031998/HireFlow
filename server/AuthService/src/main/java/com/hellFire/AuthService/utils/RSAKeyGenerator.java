package com.hellFire.AuthService.utils;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAKeyGenerator {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        write("private_key.pem", "PRIVATE KEY", pair.getPrivate().getEncoded());
        write("public_key.pem", "PUBLIC KEY", pair.getPublic().getEncoded());
    }

    private static void write(String file, String type, byte[] key) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(("-----BEGIN " + type + "-----\n").getBytes());
            fos.write(Base64.getEncoder().encode(key));
            fos.write(("\n-----END " + type + "-----").getBytes());
        }
    }
}
