# Client Using Externally Defined Remoting Connections

## Prerequisites

* Running EAP domain,
* WAR with sample EJBs deployed.

See the [parent README file](../../README.md) for details.

## Server Setup

1) In JBoss CLI, create security realm for remoting connections from client server to ejb servers
 (the secret value here is base64 encoded password of ApplicationRealm user "ejbuser" which we created previously):
 
    ```
    /host=master/core-service=management/security-realm=ejb-security-realm:add()
    /host=master/core-service=management/security-realm=ejb-security-realm/server-identity=secret:add(value=ZWpidXNlckAx)
    ```
2) Create outbound socket bindings from client server to ejb servers:

    `
    /socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=remote-ejb-1:add(host=localhost, port=8443)
    /socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=remote-ejb-2:add(host=localhost, port=8543)
    `
3) Create remoting outbound connections on client server, using socket bindings created above:

    ```
    /profile=default/subsystem=remoting/remote-outbound-connection=remote-ejb-connection1:add(outbound-socket-binding-ref=remote-ejb-1, security-realm=ejb-security-realm, protocol=https-remoting, username=ejbuser)
    /profile=default/subsystem=remoting/remote-outbound-connection=remote-ejb-connection2:add(outbound-socket-binding-ref=remote-ejb-2, security-realm=ejb-security-realm, protocol=https-remoting, username=ejbuser)
    ```

## Running In-Server Client

The client is a servlet, which invokes remote EJB upon every request.

1) Build client application:

`cd <ejb-client-demo-dir>/client-parent/client-outbound-connection/`
2) Deploy the app. In JBoss CLI:

`deploy <ejb-client-demo-dir>/client-parent/client-outbound-connection/target/servlet-outbound-connection.war --server-groups=client-server-group`
3) Visit <http://localhost:8280/servlet-outbound-connection/CalculatorClientServlet>, remote EJB should be invoked.
