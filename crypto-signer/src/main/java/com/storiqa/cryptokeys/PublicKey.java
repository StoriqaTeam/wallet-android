package com.storiqa.cryptokeys;

import com.storiqa.cryptokeys.Util.TypeConverter;

public class PublicKey {

    private byte[] raw;
    private String hex;

    public PublicKey(byte[] pubKey) {
        raw = pubKey;
        hex = TypeConverter.byteArrayToHexString(raw);
    }

    public byte[] getRaw() {
        return raw;
    }

    public String getHex() {
        return hex;
    }
}
