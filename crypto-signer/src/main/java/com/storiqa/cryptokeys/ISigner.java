package com.storiqa.cryptokeys;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ISigner {

    @Nullable
    String sign(@NotNull String message, @NotNull PrivateKey key);

}
