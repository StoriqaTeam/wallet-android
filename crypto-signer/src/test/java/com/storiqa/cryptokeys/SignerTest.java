package com.storiqa.cryptokeys;

import com.storiqa.cryptokeys.Util.TypeConverter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignerTest {

    @Test
    public void sign() {
        String privateKeyHex = "b920a5cb91c601c6bf7e32974e29d7ebbe591d63f67de40f0a2340525960bf8e";
        String messageToSign = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque molestie odio at scelerisque mollis. Morbi vitae faucibus urna. Vivamus hendrerit eros at egestas dictum";
        String validSignedMessage = "aec73341d6bf70ef1b317d7f5461711dfcf94ed048671ecbb820b03a85139d535d86807814c352ce9859c08cbf945bd3f1769f25572d8058d10b142c1ed2831d";
        byte[] privateKeyRaw = TypeConverter.hexStringToByteArray(privateKeyHex);
        PrivateKey key = new PrivateKey(privateKeyRaw);
        Signer signer = new Signer();

        assertEquals(validSignedMessage, signer.sign(messageToSign, key));
    }
}