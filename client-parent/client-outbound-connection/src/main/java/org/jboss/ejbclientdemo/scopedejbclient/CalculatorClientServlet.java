/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.scopedejbclient;

import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import org.jboss.ejbclientdemo.common.AbstractCalculatorClientServlet;
import org.jboss.ejbclientdemo.common.RemoteEJBClient;

/**
 *
 * @author rmartinc
 */
@WebServlet(name = "CalculatorClientServlet", urlPatterns = {"/CalculatorClientServlet"})
public class CalculatorClientServlet extends AbstractCalculatorClientServlet {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public InitialContext createContext() throws NamingException {
        System.err.println("Creating context");
        Properties props = new Properties();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return new InitialContext(null);
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
