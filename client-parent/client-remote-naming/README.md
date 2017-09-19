# Client Using Scoped EJB Context

Note that jboss-remote-naming is not recommended for EJB invocations, 
see [Scoped Context](../client-scoped-ejb-context/README.md)
or [Externally Defined Remoting Connections](../client-outbound-connection/README.md) for alternatives.

Using remote-naming for clustered EJBs is not supported.

Trying to connect to a cluster via any other protocol than "http-remoting" will give you exceptions 
like following and clustering functionality will not work:

```
java.io.EOFException: XNIO000812: Connection closed unexpectedly
	at org.xnio.http.HttpUpgrade$HttpUpgradeState$UpgradeResultListener.handleEvent(HttpUpgrade.java:416)
	at org.xnio.http.HttpUpgrade$HttpUpgradeState$UpgradeResultListener.handleEvent(HttpUpgrade.java:400)
	at org.xnio.ChannelListeners.invokeChannelListener(ChannelListeners.java:92)
	at org.xnio.conduits.ReadReadyHandler$ChannelListenerHandler.readReady(ReadReadyHandler.java:66)
	at org.xnio.nio.NioSocketConduit.handleReady(NioSocketConduit.java:89)
	at org.xnio.nio.WorkerThread.run(WorkerThread.java:571)
	at ...asynchronous invocation...(Unknown Source)
	at org.jboss.remoting3.EndpointImpl.doConnect(EndpointImpl.java:294)
	at org.jboss.remoting3.EndpointImpl.connect(EndpointImpl.java:430)
	at org.jboss.ejb.client.remoting.NetworkUtil.connect(NetworkUtil.java:153)
	at org.jboss.ejb.client.remoting.NetworkUtil.connect(NetworkUtil.java:133)
	at org.jboss.ejb.client.remoting.ConnectionPool.getConnection(ConnectionPool.java:78)
	at org.jboss.ejb.client.remoting.RemotingConnectionManager.getConnection(RemotingConnectionManager.java:51)
	at org.jboss.ejb.client.remoting.RemotingConnectionClusterNodeManager.getEJBReceiver(RemotingConnectionClusterNodeManager.java:79)
	at org.jboss.ejb.client.ClusterContext$EJBReceiverAssociationTask.call(ClusterContext.java:512)
	at org.jboss.ejb.client.ClusterContext$EJBReceiverAssociationTask.call(ClusterContext.java:486)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)	
```	

## Prerequisites

* Running EAP domain,
* WAR with sample EJBs deployed.

See the [parent README file](../../README.md) for details.

## Running Standalone Client

Standalone client will open an EJB context, perform 10 invocations of a remote EJB and close context, in a loop. 

```
cd <ejb-client-demo-dir>/client-parent/client-remote-naming/
mvn clean package exec:java
```
       
## Running In-server Client

The client is a servlet that invokes remote EJB once upon every request.

1) Build client WAR application:

    `
    cd <ejb-client-demo-dir>/client-parent/client-remote-naming/
    mvn clean package
    `
2) Deploy the WAR to client-server-group. In JBoss CLI:

    `deploy <ejb-client-demo-dir>/client-parent/client-remote-naming/target/servlet-remote-naming.war --server-groups=client-server-group`
3) Visit <http://localhost:8280/servlet-scoped-ejb-client/CalculatorClientServlet> - servlet should invoke EJB
 deployed on one of the remote servers.
