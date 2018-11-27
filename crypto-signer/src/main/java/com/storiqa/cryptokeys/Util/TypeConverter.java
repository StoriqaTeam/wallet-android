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


import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

public class TypeConverter {

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    public static BigInteger StringHexToBigInteger(final String input) {
        if (input == null) {
            return BigInteger.ZERO;
        }

        final String hexa = input.startsWith("0x") ? input.substring(2) : input;
        try {
            return new BigInteger(hexa, 16);
        } catch (final NumberFormatException ex) {
            return BigInteger.ZERO;
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String x) {
        if (x.startsWith("0x")) {
            x = x.substring(2);
        }
        if (x.length() % 2 != 0) x = "0" + x;
        return Hex.decode(x);
    }

    public static String jsonStringToString(final String jsonString) {
        if (jsonString.startsWith("\"") && jsonString.endsWith("\""))
            return jsonString.substring(1, jsonString.length() - 1);
        if (jsonString.startsWith("\"")) return jsonString.substring(1);
        if (jsonString.endsWith("\"")) return jsonString.substring(0, jsonString.length() - 1);
        return jsonString;
    }

    public static String toJsonHex(final byte[] x) {
        return "0x" + Hex.toHexString(x);
    }

    public static String toJsonHex(final String x) {
        if (x.startsWith("0x")) return x;
        return "0x" + x;
    }

    public static String toJsonHex(final long n) {
        return "0x" + Long.toHexString(n);
    }

    public static String toJsonHex(final BigInteger n) {
        return "0x" + n.toString(16);
    }

    public static String fromHexToDecimal(final String input) {
        final String hex = input.startsWith("0x") ? input.substring(2) : input;
        return String.valueOf(new BigInteger(hex, 16));
    }


    private static boolean isEmptyString(final Object obj) {
        return obj instanceof String && ((String) obj).length() == 0;
    }

}
