package com.storiqa.cryptokeys;

import com.storiqa.cryptokeys.Util.TypeConverter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrivateKeyTest {

    @Test
    public void getPublicKey() {
        String privateKeyHex = "b920a5cb91c601c6bf7e32974e29d7ebbe591d63f67de40f0a2340525960bf8e";
        String validPublicKey = "04902c233712a54d20edabd9b80ebb75700819db8d0edad74a25e061c925fbeb27a4cc2090dd6b74fdb916bc7c503e18680da81828ecddd5939020cc855ada18da";
        byte[] privateKeyRaw = TypeConverter.hexStringToByteArray(privateKeyHex);
        PrivateKey key = new PrivateKey(privateKeyRaw);

        assertEquals(validPublicKey, key.getPublicKey().getHex());
    }
}