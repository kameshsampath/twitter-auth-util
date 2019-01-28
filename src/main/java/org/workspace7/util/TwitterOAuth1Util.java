package org.workspace7.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workspace7.model.OAuth1Param;

/**
 * TwitterOAuth1Util
 */
public class TwitterOAuth1Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterOAuth1Util.class);

    /**
     * 
     * @param reqestURI
     * @param httpMethod
     * @param getParams
     * @param postParams
     * @param oAuth1Param
     * @param consumerSecret
     * @param tokenSecret
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String oauth1HeaderString(URI reqestURI, String httpMethod, Map<String, String> getParams,
            Map<String, String> postParams, OAuth1Param oAuth1Param, String consumerSecret, String tokenSecret)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {

        ParameterStringUtil parameterStringUtil = new ParameterStringUtil(oAuth1Param, getParams, postParams);
        String strParameter = parameterStringUtil.generateParameterString();
        String strSingatureBase = SignatureUtil.buildSignatureBase(httpMethod, reqestURI, strParameter);
        String signingKey = SignatureUtil.signingKey(consumerSecret, tokenSecret);
        String signature = SignatureUtil.generateSignature(signingKey, strSingatureBase);

        // Make a copy of the oAuth1Param and add the generated signature to it
        OAuth1Param copyOAuth1Param = new OAuth1Param(oAuth1Param.getConsumerKey(), oAuth1Param.getNonce(), signature,
                oAuth1Param.getTimestamp(), oAuth1Param.getToken());

        return oauth1HeaderString(copyOAuth1Param.paramsAsMap());
    }

    /**
     * 
     * @param oauthParameters
     * @return
     */
    public static String oauth1HeaderString(Map<String, String> oauthParameters) {

        Map<String, String> quotedoAuthParameters = ParameterStringUtil
                .urlEncodedKeyQuotedValueSortedMap(oauthParameters);

        StringBuilder oauthParamBuilder = new StringBuilder("OAuth ");
        oauthParamBuilder.append(
                quotedoAuthParameters.entrySet().stream().map(Map.Entry::toString).collect(Collectors.joining(", ")));
        LOGGER.debug("Oauth Header {}", oauthParamBuilder.toString());
        return oauthParamBuilder.toString();
    }
}