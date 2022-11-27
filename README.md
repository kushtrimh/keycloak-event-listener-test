# keycloak-event-listener-test

Uses _Keycloak 16.1.1_.
Tries to simulate a process where once a resource is created by one of the admins, all other admins are notified by email.

## Start Keycloak

You can either install Keycloak _16.1.1_ locally or run it via `docker`.

```shell
docker run -d -p 8180:8080 -p 8787:8787 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e DEBUG=true -e DEBUG_PORT='*:8787' -v /user/keycloak-test/providers:/opt/jboss/keycloak/standalone/deployments/ --name keycloak-sandbox jboss/keycloak:16.1.1
```

## Deploying

Create the `jar` file by using `mvn clean package`, and put the `jar` file at your mounted directory for the providers (e.g. _/user/keycloak-test/providers_ in the case of this example).

## Setup

Create a group called `admins`, add the created event listener, and configure the SMTP settings. That's all :).
