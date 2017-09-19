# Client Using Scoped EJB Context

## Prerequisites

* Running EAP domain,
* WAR with sample EJBs deployed.

See the [parent README file](../../README.md) for details.

## Running Standalone Client

Standalone client will open an EJB context, perform 10 invocations of a remote EJB and close context, in a loop. 

```
cd <ejb-client-demo-dir>/client-parent/client-scoped-ejb-context/
mvn clean package exec:java
```

## Running In-server Client

The client is a servlet that invokes remote EJB once upon every request.

1) Build client WAR:

    ```
    cd <ejb-client-demo-dir>/client-parent/client-scoped-ejb-context/
    mvn clean package
    ```
2) Deploy the WAR to client-server-group. In JBoss CLI:

    `deploy <ejb-client-demo-dir>/client-parent/client-scoped-ejb-context/target/servlet-scoped-ejb-client.war --server-groups=client-server-group`
3) Visit <http://localhost:8280/servlet-scoped-ejb-client/CalculatorClientServlet> - servlet should invoke EJB
 deployed on one of the remote servers.
