/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.ejbclientdemo.ejb.RemoteCalculator;

/**
 * Abstract servlet connecting to a remote EJB.
 *
 * Subclasses should implement {@link #createContext()} ()} and {@link #getLookupName()}
 * and override {@link #closeContext(InitialContext)} if needed.
 *
 * @author rmartinc
 * @author Tomas Hofman
 */
public abstract class AbstractCalculatorClientServlet extends HttpServlet implements RemoteEJBClient {

    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        InitialContext context = null;

        try (PrintWriter out = response.getWriter()) {
            int a = request.getParameter("op1") != null ? Integer.parseInt(request.getParameter("op1")) : 0;
            int b = request.getParameter("op2") != null ? Integer.parseInt(request.getParameter("op2")) : 0;

            context = createContext();
            final String lookupName = getLookupName();

            System.err.println("lookupname=" + lookupName);
            RemoteCalculator statelessRemoteCalculator = (RemoteCalculator) context.lookup(lookupName);
            int sum = statelessRemoteCalculator.add(a, b);
            System.err.println("Adding " + a + " + " + b + " = " + sum);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>StatelessClientServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>StatelessClientServlet at " + request.getContextPath() + "</h1>");
            out.println("<p>Adding " + a + " + " + b + " = " + sum + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                closeContext(context);
            } catch (NamingException e) {
                logger.log(Level.SEVERE, "Couldn't close context", e);
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
