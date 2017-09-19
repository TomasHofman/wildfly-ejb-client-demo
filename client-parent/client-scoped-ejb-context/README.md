# Client Using Scoped EJB Context

## Prerequisites

* Running EAP domain,
* WAR with sample EJBs deployed.

See the [parent README file](../../README.md) for details.

## Running Standalone Client

Standalone client will open an EJB context, perform 10 invocations of a remote EJB and close context, in a loop. 

`
cd <ejb-client-demo-dir>/client-parent/client-scoped-ejb-context/
mvn clean package exec:java
`

Sample output:

`
Creating context
lookupname=ejb:/sample-ejb//CalculatorBean!org.jboss.ejbclientdemo.ejb.RemoteCalculator
Sep 18, 2017 2:41:17 PM org.xnio.Xnio <clinit>
INFO: XNIO version 3.4.6.Final-redhat-1
Sep 18, 2017 2:41:17 PM org.xnio.nio.NioXnio <clinit>
INFO: XNIO NIO Implementation Version 3.4.6.Final-redhat-1
Sep 18, 2017 2:41:17 PM org.jboss.remoting3.EndpointImpl <clinit>
INFO: JBoss Remoting version 4.0.23.Final-redhat-1
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.remoting.VersionReceiver handleMessage
INFO: EJBCLIENT000017: Received server version 2 and marshalling strategies [river]
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.remoting.RemotingConnectionEJBReceiver associate
INFO: EJBCLIENT000013: Successful version handshake completed for receiver context EJBReceiverContext{clientContext=org.jboss.ejb.client.EJBClientContext@10a414b3, receiver=Remoting connection EJB receiver [connection=org.jboss.ejb.client.remoting.ConnectionPool$PooledConnection@6e14719e,channel=jboss.ejb,nodename=master:ejb-server1]} on channel Channel ID a82d7e24 (outbound) of Remoting connection 1a41a61f to localhost/127.0.0.1:8443 of endpoint "config-based-ejb-client-endpoint" <5d560c52>
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.EJBClient <clinit>
INFO: JBoss EJB Client version 2.1.8.Final-redhat-1
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.remoting.VersionReceiver handleMessage
INFO: EJBCLIENT000017: Received server version 2 and marshalling strategies [river]
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.remoting.RemotingConnectionEJBReceiver associate
INFO: EJBCLIENT000013: Successful version handshake completed for receiver context EJBReceiverContext{clientContext=org.jboss.ejb.client.EJBClientContext@10a414b3, receiver=Remoting connection EJB receiver [connection=org.jboss.ejb.client.remoting.ConnectionPool$PooledConnection@7469c068,channel=jboss.ejb,nodename=master:ejb-server2]} on channel Channel ID c924b06a (outbound) of Remoting connection 4a772b1e to /127.0.0.1:8543 of endpoint "config-based-ejb-client-endpoint" <5d560c52>
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Adding 1 + 1 = 2
Closing context
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.remoting.ChannelAssociation$ResponseReceiver handleEnd
INFO: EJBCLIENT000016: Channel Channel ID a82d7e24 (outbound) of Remoting connection 1a41a61f to localhost/127.0.0.1:8443 of endpoint "config-based-ejb-client-endpoint" <5d560c52> can no longer process messages
Sep 18, 2017 2:41:18 PM org.jboss.ejb.client.remoting.ChannelAssociation$ResponseReceiver handleEnd
INFO: EJBCLIENT000016: Channel Channel ID c924b06a (outbound) of Remoting connection 4a772b1e to /127.0.0.1:8543 of endpoint "config-based-ejb-client-endpoint" <5d560c52> can no longer process messages
iteration=0
`
       
## Running In-server Client

The client is a servlet that invokes remote EJB once upon every request.

1) Build client WAR:

    `
    cd <ejb-client-demo-dir>/client-parent/client-scoped-ejb-context/
    mvn clean package
    `
2) Deploy the WAR to client-server-group. In JBoss CLI:

    `deploy <ejb-client-demo-dir>/client-parent/client-scoped-ejb-context/target/servlet-scoped-ejb-client.war --server-groups=client-server-group`
3) Visit <http://localhost:8280/servlet-scoped-ejb-client/CalculatorClientServlet> - servlet should invoke EJB
 deployed on one of the remote servers.
