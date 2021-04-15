package vott.auth;

import java.util.HashMap;
import java.util.Map;

public enum GrantType {
    IMPLICIT {
        @Override
        protected Map<String, Object> formParams() {
            Map<String, Object> formParams = new HashMap<>();

            formParams.put("grant_type", "password");
            formParams.put("userName", "");
            formParams.put("password", "");
            formParams.put("resource", "");
            formParams.put("client_id", "");

            return formParams;
        }
    },
    CLIENT_CREDENTIALS {
        @Override
        protected Map<String, Object> formParams() {
            Map<String, Object> formParams = new HashMap<>();

            formParams.put("grant_type", "client_credentials");
            formParams.put("scope", "");
            formParams.put("client_secret", "");
            formParams.put("client_id", "");

            return formParams;
        }
    };

    protected abstract Map<String, Object> formParams();
}
