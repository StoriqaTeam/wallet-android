package com.storiqa.cryptokeys;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class KeyGenerator implements IKeyGenerator {

    private static final int ITERATIONS = 1024;
    private static final int KEY_LENGTH = 256;

    private static String password = "storiqa";
    private static String salt = "android" + new Random().nextDouble(); //+float (0..1000)

    @Override
    public PrivateKey generatePrivateKey() {

        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        byte[] privateKey = hashPassword(passwordChars, saltBytes);

        return privateKey == null ? null : new PrivateKey(privateKey);
    }

    private byte[] hashPassword(final char[] password, final byte[] salt) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);

            return key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SecretKey generateSecretKey() {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);

            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }
}
