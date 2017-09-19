# JBoss EJB Client Demo

This project demonstrates various ways of calling remote EJBs using both, standalone client applications
and applications deployed on JBoss EAP / Wildfly application servers.

The project contains following sub-projects:

| Subproject | Description |
| --- | --- | 
| server | Jar containing sample EJBs to be called by clients. |
| client-parent/client-outbound-connection | In-server client that uses externally defined remoting connections. |
| client-parent/client-scoped-ejb-context  | In-server and standalone clients using jboss-ejb-client library alone. |
| client-parent/client-remote-naming       | In-server and standalone clients leveraging jboss-remote-naming library (not recommended for calling EJBs). |
| client-parent/client-common              | Common client code. |   

## Setup Overview

The demo is using three-node EAP domain. Two nodes are connected into cluster, running EJB's from the `server` 
sub-project, and the third node is where the in-server client applications are to be deployed.

Where possible, the clients leverage the clustering functionality, meaning that clients alternate between the two 
clustered servers to spread the load, and in case that one of the servers fails, the client is still able to connect to 
the other one.
    
## Domain Setup

Start with clean EAP 7.0 install.

1) Copy `configs/ssl/` into `EAP_HOME/domain/configuration/`.
2) Add an `ApplicationRealm` user:

    `EAP_HOME/bin/add-user.sh -a -u ejbuser -p ejbuser@1`
3) Run EAP domain:

    `EAP_HOME/bin/domain.sh`
4) Connect with JBoss CLI and run `install-domain.cli` script:

    `EAP_HOME/bin/jboss-cli.sh -c --file=configs/install-domain.cli`
5) Build archives with sample EJBs and client apps:

    `
    cd ejb-client-demo/
    mvn clean install
    `
6) Deploy sample sample EJBs. In JBoss CLI:
 
    `deploy <path-to-ejb-client-demo>/server/target/sample-ejb.jar --server-groups=ejb-server-group`
    
7) Follow directions in one of the client projects:
    * [Client using scoped EJB context](client-parent/client-scoped-ejb-context/README.md)
    * [Client using externally defined remoting connections](client-parent/client-outbound-connection/README.md)
    * [Client using jboss-remote-naming library](client-parent/client-remote-naming/README.md) (not recommended)

## Domain Setup Using Provided Configuration Files

Alternatively, instead of creating EAP domain using CLI script as described in previous section, it is possible to use prepared
configuration files from `configs/` directory.

Replace step 4) from above section with following (with EAP turned off):

1) Copy `configs/domain-https.xml` and `configs/host.xml` into `EAP_HOME/domain/configuration/`.
2) Run EAP domain:

    `EAP_HOME/bin/domain.sh -c domain-https.xml`