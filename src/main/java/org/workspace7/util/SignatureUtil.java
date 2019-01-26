package org.workspace7.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SignatureBaseUtil
 */
public class SignatureUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureUtil.class);
    /**
     * 
     * @param httpMethod
     * @param requestURI
     * @param strParameter
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String buildSignatureBase(String httpMethod, URI requestURI, String strParameter)
            throws UnsupportedEncodingException {
        StringBuilder strSignatureBase = new StringBuilder(httpMethod.toUpperCase());
        strSignatureBase.append(GeneralUtil.AND);
        strSignatureBase.append(GeneralUtil.urlencode(urlWithnoQueryString(requestURI)));
        strSignatureBase.append(GeneralUtil.AND);
        strSignatureBase.append(GeneralUtil.urlencode(strParameter));
        LOGGER.debug("SIG BASE STRING {}",strSignatureBase.toString());
        return strSignatureBase.toString();
    }

    /**
     * 
     * @param consumerSecret
     * @param tokenSecret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String signingKey(String consumerSecret, String tokenSecret) throws UnsupportedEncodingException {
        StringBuilder signingKey = new StringBuilder();
        signingKey.append(GeneralUtil.urlencode(consumerSecret));
        signingKey.append(GeneralUtil.AND);
        signingKey.append(GeneralUtil.urlencode(tokenSecret));
        LOGGER.debug("Signing Key:{}",signingKey.toString());
        return signingKey.toString();
    }

    /**
     * 
     * @param signingKey
     * @param digest
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String generateSignature(String signingKey, String digest)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
               
        SecretKeySpec secretKeySpec = new SecretKeySpec(signingKey.getBytes(GeneralUtil.DEFAULT_CHARSET),
                GeneralUtil.HMAC_SHA1);
        Mac mac = Mac.getInstance(GeneralUtil.HMAC_SHA1);
        mac.init(secretKeySpec);
        byte[] digestBytes = mac.doFinal(digest.getBytes(GeneralUtil.DEFAULT_CHARSET));
        // convert to hex string and pass it to the encoder
        String strHex = BaseEncoding.base16().lowerCase().encode(digestBytes);
        LOGGER.debug("Signature Hex:{}",strHex.toUpperCase());
        String strHashDigest = BaseEncoding.base64().encode(digestBytes);
        LOGGER.debug("Signature Digest:{}",strHashDigest);
        return strHashDigest;
    }
    /**
     * 
     * @param requestURI
     * @return
     */
    private static final String urlWithnoQueryString(URI requestURI) {
        StringBuilder url = new StringBuilder(requestURI.getScheme());
        url.append("://");
        url.append(requestURI.getHost());
        url.append(requestURI.getPath());
        return url.toString();
    }
}