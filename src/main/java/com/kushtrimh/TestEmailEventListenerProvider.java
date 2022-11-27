package com.kushtrimh;

import org.jboss.logging.Logger;
import org.keycloak.email.DefaultEmailSenderProvider;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kushtrim Hajrizi
 */
public class TestEmailEventListenerProvider implements EventListenerProvider {

    private static final Logger logger = Logger.getLogger(TestEmailEventListenerProvider.class);

    private final KeycloakSession session;
    private final EmailSenderProvider emailSender;

    public TestEmailEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.emailSender = new DefaultEmailSenderProvider(session);
    }

    public void onEvent(Event event) {
    }

    public void onEvent(AdminEvent adminEvent, boolean b) {
        var operationType = adminEvent.getOperationType();
        var resourceType = adminEvent.getResourceType();

        RealmModel realm = session.getContext().getRealm();
        var authenticatedUser = session.users().getUserById(
                realm, adminEvent.getAuthDetails().getUserId());
        GroupModel adminsGroup = KeycloakModelUtils.findGroupByPath(realm, "/admins");
        List<UserModel> admins = session.users()
                .getGroupMembersStream(realm, adminsGroup)
                .collect(Collectors.toList());

        var emailBody = String.format("<html><body><p>Admin %s performed action: %s %s</p></body></html>",
                authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName() + "/" + authenticatedUser.getId(),
                operationType,
                resourceType);

        for (UserModel admin : admins) {
            try {
                emailSender.send(
                        realm.getSmtpConfig(),
                        admin,
                        "Admin audit",
                        "",
                        emailBody);
            } catch (EmailException e) {
                logger.error("Could not send email to admins", e);
            }
        }
    }

    public void close() {
    }
}
