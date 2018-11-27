package com.storiqa.cryptokeys;

import com.storiqa.cryptokeys.Util.ECKey;
import com.storiqa.cryptokeys.Util.TypeConverter;

public class PrivateKey {

    private byte[] raw;
    private String hex;
    private ECKey key;
    private PublicKey publicKey;

    PrivateKey(byte[] keyRaw) {
        key = ECKey.fromPrivate(keyRaw);
        raw = key.getPrivKeyBytes();
        publicKey = new PublicKey(key.getPubKey());
        hex = TypeConverter.byteArrayToHexString(raw);
    }

    ECKey getKey() {
        return key;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public byte[] getRaw() {
        return raw;
    }

    public String getHex() {
        return hex;
    }
}
