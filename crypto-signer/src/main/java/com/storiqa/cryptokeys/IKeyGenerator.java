package com.storiqa.cryptokeys;

import org.jetbrains.annotations.Nullable;

public interface IKeyGenerator {

    @Nullable
    PrivateKey generatePrivateKey();

}
