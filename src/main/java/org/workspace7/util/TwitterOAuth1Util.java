package org.workspace7.util;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TwitterOAuth1Util
 */
public class TwitterOAuth1Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterOAuth1Util.class);

    /**
     * 
     * @param oauthParameters
     * @return
     */
    public static String oauth1HeaderString(Map<String, String> oauthParameters) {
        
        Map<String,String> quotedoAuthParameters = ParameterStringUtil
        .urlEncodedKeyQuotedValueSortedMap(oauthParameters);

        StringBuilder oauthParamBuilder = new StringBuilder("OAuth ");
        oauthParamBuilder
                .append(quotedoAuthParameters.entrySet().stream().map(Map.Entry::toString).collect(Collectors.joining(", ")));
        LOGGER.debug("Oauth Header {}", oauthParamBuilder.toString());
        return oauthParamBuilder.toString();
    }
}