package org.jboss.ejbclientdemo.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Interface to be implemented by servlets and standalone client classes.
 *
 * This is refactored just for the sake of reducing code repetition in this demo project.
 * 
 * @author Tomas Hofman (thofman@redhat.com)
 */
public interface RemoteEJBClient {
    
    /**
     * Creates and configures an {@link InitialContext}.
     *
     * @return configured InitialContext
     */
    InitialContext createContext() throws NamingException;

    /**
     * Returns a lookup name of a target bean.
     *
     * @return remote EJB lookup name
     */
    String getLookupName();

    /**
     * Closes InitialContext.
     *
     * @param context context to close
     */
    default void closeContext(InitialContext context) throws NamingException {
        if (context != null) {
            context.close();
        }
    }

    static Properties loadPropertiesFromResource(String name) {
        Properties props = new Properties();
        try (InputStream stream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(name)) {
            props.load(stream);
        } catch (IOException ex) {
            throw new IllegalStateException(name + " not found on classpath");
        }
        return props;
    }

}
