/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.scopedejbclient;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejbclientdemo.common.AbstractStandaloneClient;
import org.jboss.ejbclientdemo.common.RemoteEJBClient;
import org.jboss.ejbclientdemo.ejb.RemoteCalculator;

/**
 *
 * @author rmartinc
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
        System.err.println("Creating context");
        Properties props = RemoteEJBClient.loadPropertiesFromResource("ejb-client.properties");
        return new InitialContext(props);
    }

    @Override
    public void closeContext(InitialContext context) throws NamingException {
        if (context != null) {
            ((Context) context.lookup("ejb:")).close();
            context.close();
        }
    }

    @Override
    public String getLookupName() {
        return "ejb:/sample-ejb//CalculatorBean!org.jboss.ejbclientdemo.ejb.RemoteCalculator";
    }

}
