package edu.wpi.smartcoach.util;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class FitbitApi extends DefaultApi10a {

    public static final String API_BASE_URL = "https://api.fitbit.com";
    public static final String AUTH_BASE_URL = "https://www.fitbit.com/oauth";

    private static final String AUTHORIZE_URL = "https://api.fitbit.com/oauth/authorize?oauth_token=%s&display=touch";
    private static final String REQUEST_TOKEN_RESOURCE = "https://api.fitbit.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "https://api.fitbit.com/oauth/access_token";

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_RESOURCE;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_RESOURCE;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}
