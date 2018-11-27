package com.storiqa.cryptokeys;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class KeyGeneratorTest {

    @Test
    public void generatePrivateKey() {
        KeyGenerator keyGenerator = new KeyGenerator();
        for (int i = 0; i < 100; i++) {
            assertNotNull(keyGenerator.generatePrivateKey());
        }
    }
}