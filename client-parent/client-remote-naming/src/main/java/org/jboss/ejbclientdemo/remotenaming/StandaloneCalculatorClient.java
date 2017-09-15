/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.remotenaming;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejbclientdemo.common.AbstractStandaloneClient;
import org.jboss.ejbclientdemo.common.RemoteEJBClient;
import org.jboss.ejbclientdemo.ejb.RemoteCalculator;

/**
 * Standalone client that connects to a remote EJB via remote-naming library - this is a deprecated way.
 *
 * @author Tomas Hofman
 */
public class StandaloneCalculatorClient extends AbstractStandaloneClient {

    public static void main(String... args) throws Exception {
        int a = args.length > 0 ? Integer.parseInt(args[0]) : 0;
        int b = args.length > 1 ? Integer.parseInt(args[1]) : 0;
        StandaloneCalculatorClient calculatorClient = new StandaloneCalculatorClient();
        calculatorClient.callEjbInLoop(a, b);
    }

    @Override
    public InitialContext createContext() throws NamingException {
        Properties props = RemoteEJBClient.loadPropertiesFromResource("remote-naming.properties");
        return new InitialContext(props);
    }

    @Override
    public String getLookupName() {
        return "sample-ejb/CalculatorBean!org.jboss.ejbclientdemo.ejb.RemoteCalculator";
    }

}
