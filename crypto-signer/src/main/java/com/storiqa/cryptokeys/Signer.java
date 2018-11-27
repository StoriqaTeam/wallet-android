package com.storiqa.cryptokeys;

import com.storiqa.cryptokeys.Util.ECKey;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signer implements ISigner {

    @Override
    public String sign(@NotNull String message, @NotNull PrivateKey key) {

        ECKey.ECDSASignature signature = null;
        try {
            signature = key.getKey()
                    .doSign(MessageDigest.getInstance("SHA-256")
                            .digest(message.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (null == signature)
            return null;

        String signedMessage = signature.toHex();

        return signedMessage.substring(2, signedMessage.length() - 2);
    }
}
