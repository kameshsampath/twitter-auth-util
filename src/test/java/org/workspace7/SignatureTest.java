package org.workspace7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.junit.Test;
import org.workspace7.model.OAuth1Param;
import org.workspace7.util.ParameterStringUtil;
import org.workspace7.util.SignatureUtil;
import org.workspace7.util.TwitterOAuth1Util;

/**
 * Test data used from
 * https://developer.twitter.com/en/docs/basics/authentication/guides/creating-a-signature.html
 */
public class SignatureTest {

    final String consumerKey = "xvz1evFS4wEEPTGEFPHBog";
    final String consumerSecret = "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw";
    final String token = "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb";
    final String tokenSecret = "LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE";
    final String url = "https://api.twitter.com/1.1/statuses/update.json";

    private String buildSignature() throws Exception {
        URI uri = new URI(url);

        LinkedHashMap<String, String> getParams = new LinkedHashMap<>();
        getParams.put("include_entities", "true");
        LinkedHashMap<String, String> postParams = new LinkedHashMap<>();
        postParams.put("status", "Hello Ladies + Gentlemen, a signed OAuth request!");

        OAuth1Param oAuth1Param = new OAuth1Param(consumerKey, "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg", "",
                "1318622958", token);

        ParameterStringUtil parameterStringUtil = new ParameterStringUtil(oAuth1Param, getParams, postParams);
        String strParameter = parameterStringUtil.generateParameterString();
        String strSingatureBase = SignatureUtil.buildSignatureBase("post", uri, strParameter);
        String signingKey = SignatureUtil.signingKey(consumerSecret, tokenSecret);

        String signature = SignatureUtil.generateSignature(signingKey, strSingatureBase);

        return signature;
    }

    @Test
    public void testSignature() throws Exception {
        assertEquals("hCtSmYh+iHYCEqBWrE7C7hYmtUk=", buildSignature());
    }

    @Test
    public void testOauthHeaderString() throws Exception {
        LinkedHashMap<String, String> getParams = new LinkedHashMap<>();
        getParams.put("include_entities", "true");
        LinkedHashMap<String, String> postParams = new LinkedHashMap<>();
        postParams.put("status", "Hello Ladies + Gentlemen, a signed OAuth request!");

        OAuth1Param oAuth1Param = new OAuth1Param(consumerKey, "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg", "",
                "1318622958", token);
        String signature = buildSignature();
        assertEquals("hCtSmYh+iHYCEqBWrE7C7hYmtUk=", signature);
        oAuth1Param.setSignature(signature);
        String oauthParameterHeader = TwitterOAuth1Util.oauth1HeaderString(oAuth1Param.paramsAsMap());

        String expected = "OAuth oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\", "+
        "oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", oauth_signature=\"hCtSmYh%2BiHYCEqBWrE7C7hYmtUk%3D\","+
        " oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1318622958\", "+
        "oauth_token=\"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb\", oauth_version=\"1.0\"";

        assertEquals(expected,oauthParameterHeader);

    }
}
