/*
 * 	Copyright (c) 2017. Toshi Inc
 *
 * 	This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.storiqa.cryptokeys.Util;


import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public final class ECKeyPairGenerator {

    public static final String ALGORITHM = "EC";
    public static final String CURVE_NAME = "secp256k1";

    private static final String algorithmAssertionMsg =
            "Assumed JRE supports EC key pair generation";

    private static final String keySpecAssertionMsg =
            "Assumed correct key spec statically";

    private static final ECGenParameterSpec SECP256K1_CURVE
            = new ECGenParameterSpec(CURVE_NAME);

    private ECKeyPairGenerator() {
    }

    private static class Holder {
        private static final KeyPairGenerator INSTANCE;

        static {
            try {
                INSTANCE = KeyPairGenerator.getInstance(ALGORITHM);
                INSTANCE.initialize(SECP256K1_CURVE);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(algorithmAssertionMsg, ex);
            } catch (InvalidAlgorithmParameterException ex) {
                throw new RuntimeException(keySpecAssertionMsg, ex);
            }
        }
    }

    public static KeyPair generateKeyPair() {
        return Holder.INSTANCE.generateKeyPair();
    }

    public static KeyPairGenerator getInstance(final String provider, final SecureRandom random) throws NoSuchProviderException {
        try {
            final KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM, provider);
            gen.initialize(SECP256K1_CURVE, random);
            return gen;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(algorithmAssertionMsg, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            throw new RuntimeException(keySpecAssertionMsg, ex);
        }
    }

    public static KeyPairGenerator getInstance(final Provider provider, final SecureRandom random) {
        try {
            final KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM, provider);
            gen.initialize(SECP256K1_CURVE, random);
            return gen;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(algorithmAssertionMsg, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            throw new RuntimeException(keySpecAssertionMsg, ex);
        }
    }
}
