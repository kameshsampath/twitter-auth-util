package org.workspace7.util;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workspace7.model.OAuth1Param;

/**
 * ParameterStringUtil
 */
public class ParameterStringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterStringUtil.class);

    public OAuth1Param oauthParams;
    public Map<String, String> getParams;
    public Map<String, String> postParams;

    /**
     * 
     * @param oauthParams
     * @param getParams
     * @param postParams
     */
    public ParameterStringUtil(OAuth1Param oauthParams, Map<String, String> getParams, Map<String, String> postParams) {
        this.getParams = getParams;
        this.oauthParams = oauthParams;
        this.postParams = postParams;
    }

    /**
     * 
     * @return
     */
    public String generateParameterString() {
        LinkedHashMap<String, String> allParamsMap = new LinkedHashMap<>();
        allParamsMap.putAll(oauthParams.paramsAsMap());
        allParamsMap.putAll(getParams);
        allParamsMap.putAll(postParams);

        Map<String, String> sortedParams = urlEncodedKeyValueSortedMap(allParamsMap);

        String strParameterString = sortedParams.entrySet().stream().map(Map.Entry::toString)
                .collect(Collectors.joining(GeneralUtil.AND));
        LOGGER.debug("Parameter String:{}", strParameterString);

        return strParameterString;
    }

    /**
     * 
     * @param unsortedMap
     * @return
     */
    public static Map<String, String> asSortedMap(Map<String, String> unsortedMap) {
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> {
            if (e.getValue() != null) {
                sortedMap.put(e.getKey(), e.getValue());
            }
        });

        return sortedMap;
    }

    /**
     * 
     * @param unsortedMap
     * @return
     */
    public static Map<String, String> urlEncodedKeyValueSortedMap(Map<String, String> unsortedMap) {
        LinkedHashMap<String, String> urlEncodedKeyMap = new LinkedHashMap<>();

        unsortedMap.forEach((k, v) -> {
            try {
                String encodedKey = GeneralUtil.urlencode(k);
                String encodedValue = GeneralUtil.urlencode(v);
                LOGGER.debug("UnEncoded Key {} Encoded Key {} ", k, encodedKey);
                LOGGER.debug("UnEncoded Value {} Encoded Value {} ", v, encodedValue);
                urlEncodedKeyMap.put(encodedKey, encodedValue);
            } catch (UnsupportedEncodingException e) {
                // this is FATAL
            }
        });

        return asSortedMap(urlEncodedKeyMap);
    }

    /**
     * 
     * @param unsortedMap
     * @return
     */
    public static Map<String, String> urlEncodedKeyQuotedValueSortedMap(Map<String, String> unsortedMap) {
        LinkedHashMap<String, String> urlEncodedKeyMap = new LinkedHashMap<>();

        unsortedMap.forEach((k, v) -> {
            try {
                String encodedKey = GeneralUtil.urlencode(k);
                String encodedValue = GeneralUtil.urlencode(v);
                LOGGER.debug("UnEncoded Key {} Encoded Key {} ", k, encodedKey);
                LOGGER.debug("UnEncoded Value {} Encoded Value {} ", v, GeneralUtil.quoted(encodedValue));
                urlEncodedKeyMap.put(encodedKey, GeneralUtil.quoted(encodedValue));
            } catch (UnsupportedEncodingException e) {
                // this is FATAL
            }
        });

        return asSortedMap(urlEncodedKeyMap);
    }

}