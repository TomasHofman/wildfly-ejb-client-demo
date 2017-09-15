/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.remotenaming;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import org.jboss.ejbclientdemo.common.AbstractCalculatorClientServlet;
import org.jboss.ejbclientdemo.common.RemoteEJBClient;

/**
 * Servlet that connects to remote EJB via remote-naming library - this is a deprecated way.
 *
 * @author Tomas Hofman
 */
@WebServlet(name = "CalculatorClientServlet", urlPatterns = {"/CalculatorClientServlet"})
public class CalculatorClientServlet extends AbstractCalculatorClientServlet {

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
