/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.scopedejbclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.ejbclientdemo.common.AbstractCalculatorClientServlet;
import org.jboss.ejbclientdemo.common.RemoteEJBClient;
import org.jboss.ejbclientdemo.ejb.RemoteCalculator;

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
