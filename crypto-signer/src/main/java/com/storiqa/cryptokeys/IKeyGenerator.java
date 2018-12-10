package com.storiqa.cryptokeys;

import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKey;

public interface IKeyGenerator {

    @Nullable
    PrivateKey generatePrivateKey();

    @Nullable
    SecretKey generateSecretKey();

}
