package org.workspace7.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import com.google.common.io.BaseEncoding;
import com.google.common.net.PercentEscaper;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

/**
 * GeneralUtil
 */
public class GeneralUtil {

    public static final String HMAC_SHA1 = "HmacSHA1";
    public static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.toString();
    public static final String AND = "&";
    static final PercentEscaper percentEscaper = new PercentEscaper(".-_", false);

    public static long timestamp() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static String timestampString() {
        return Long.toString(timestamp());
    }

    public static String nonce() {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .filteredBy(CharacterPredicates.ASCII_ALPHA_NUMERALS).build();
        final String nonce = randomStringGenerator.generate(32);
        return nonce;
    }

    public static String quoted(String str) {
        StringBuilder sb = new StringBuilder("\"");
        sb.append(str);
        sb.append("\"");
        return sb.toString();
    }

    public static String urlencode(String str) throws UnsupportedEncodingException {
        return percentEscaper.escape(str);
    }

    public static String base64Encode(String str) throws UnsupportedEncodingException {
        return BaseEncoding.base64().encode(str.getBytes(DEFAULT_CHARSET));
    }

    public static boolean isNullOrBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }
}