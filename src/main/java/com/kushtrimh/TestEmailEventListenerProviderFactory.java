package com.kushtrimh;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * @author Kushtrim Hajrizi
 */
public class TestEmailEventListenerProviderFactory implements EventListenerProviderFactory {

    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new TestEmailEventListenerProvider(keycloakSession);
    }

    public void init(Config.Scope scope) {

    }

    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    public void close() {

    }

    public String getId() {
        return "test-email-event-listener";
    }
}
