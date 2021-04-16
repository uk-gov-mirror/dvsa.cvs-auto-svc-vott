package vott.auth;

import vott.config.ClientCredentialsGrantProperties;
import vott.config.VottConfiguration;
import vott.config.ImplicitGrantProperties;

import java.util.HashMap;
import java.util.Map;

public enum GrantType {
    IMPLICIT {
        @Override
        protected Map<String, Object> formParams() {
            Map<String, Object> formParams = new HashMap<>();

            ImplicitGrantProperties properties = VottConfiguration.local()
                .getOAuthProperties()
                .getImplicit();

            formParams.put("grant_type", properties.getGrantType());
            formParams.put("userName", properties.getUsername());
            formParams.put("password", properties.getPassword());
            formParams.put("resource", properties.getResource());
            formParams.put("client_id", properties.getClientId());

            return formParams;
        }
    },
    CLIENT_CREDENTIALS {
        @Override
        protected Map<String, Object> formParams() {
            Map<String, Object> formParams = new HashMap<>();

            ClientCredentialsGrantProperties properties = VottConfiguration.local()
                .getOAuthProperties()
                .getClientCredentials();

            formParams.put("grant_type", properties.getGrantType());
            formParams.put("scope", properties.getScope());
            formParams.put("client_secret", properties.getClientSecret());
            formParams.put("client_id", properties.getClientId());

            return formParams;
        }
    };

    protected abstract Map<String, Object> formParams();
}
