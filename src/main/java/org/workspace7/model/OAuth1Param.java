package org.workspace7.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.workspace7.util.GeneralUtil;

/**
 * OAuth1Param
 */
public class OAuth1Param {

    public static final String KEY_OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String KEY_OAUTH_NONCE = "oauth_nonce";
    public static final String KEY_OAUTH_SIGNATURE = "oauth_signature";
    public static final String KEY_OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String KEY_OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String KEY_OAUTH_TOKEN = "oauth_token";
    public static final String KEY_OAUTH_VERSION = "oauth_version";

    // Default VALUES
    public static final String OAUTH_VERSION = "1.0";
    public static final String SIGNATURE_METHOD = "HMAC-SHA1";

    private String consumerKey;
    private String nonce;
    private String signature;
    private String timestamp;
    private String token;

    public OAuth1Param() {

    }

    public OAuth1Param(String consumerKey, String nonce, String signature, String timestamp, String token) {
        this.consumerKey = consumerKey;
        this.nonce = nonce;
        this.signature = signature;
        this.timestamp = timestamp;
        this.token = token;
    }

    /**
     * @return the consumerKey
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * @return the nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param nonce the nonce to set
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the signatureMethod
     */
    public String getSignatureMethod() {
        return SIGNATURE_METHOD;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the oauthVersion
     */
    public String getOauthVersion() {
        return OAUTH_VERSION;
    }

    /**
     * Return the paramters as map
     * 
     * @return
     */
    public Map<String, String> paramsAsMap() {

        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(KEY_OAUTH_CONSUMER_KEY, getConsumerKey());
        paramMap.put(KEY_OAUTH_NONCE, getNonce());
        if (!GeneralUtil.isNullOrBlank(getSignature())) {
            paramMap.put(KEY_OAUTH_SIGNATURE, getSignature());
        }
        paramMap.put(KEY_OAUTH_SIGNATURE_METHOD, getSignatureMethod());
        paramMap.put(KEY_OAUTH_TIMESTAMP, getTimestamp());
        paramMap.put(KEY_OAUTH_TOKEN, getToken());
        paramMap.put(KEY_OAUTH_VERSION, getOauthVersion());

        return paramMap;
    }

}