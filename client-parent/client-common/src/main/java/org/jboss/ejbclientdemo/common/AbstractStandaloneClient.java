package org.jboss.ejbclientdemo.common;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejbclientdemo.ejb.RemoteCalculator;

/**
 * @author Tomas Hofman (thofman@redhat.com)
 */
public abstract class AbstractStandaloneClient implements RemoteEJBClient {

    public abstract InitialContext createContext() throws NamingException;

    public abstract String getLookupName();

    public void callEjbInLoop(int a, int b) throws Exception {
        long iterationCounter = 0;
        InitialContext context;
        while (true) {
            context = null;
            try {
                System.err.println("iteration=" + iterationCounter);

                context = createContext();

                final String lookupName = getLookupName();
                System.err.println("lookupname=" + lookupName);
                RemoteCalculator statelessRemoteCalculator = (RemoteCalculator) context.lookup(lookupName);

                // invoke the same instance several times
                for (int j = 0; j < 10; j++) {
                    int sum = statelessRemoteCalculator.add(a, b);
                    System.err.println("Adding " + a + " + " + b + " = " + sum);
                }
            } catch(Exception e) {
                e.printStackTrace(System.err);
            } finally {
                System.err.println("Closing context");
                closeContext(context);
            }
        }
    }

}
