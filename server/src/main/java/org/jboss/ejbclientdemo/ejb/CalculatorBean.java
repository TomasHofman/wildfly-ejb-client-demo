/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.ejb;

import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Clustered;

@Stateless
@Remote(RemoteCalculator.class)
@Clustered
public class CalculatorBean implements RemoteCalculator {

    private Logger logger = Logger.getLogger(getClass().getName());
 
    @Override
    public int add(int a, int b) {
        logger.info("Bean invoked.");
        //try {Thread.sleep(20000L);} catch(Exception e) {};
        return a + b;
    }
 
    @Override
    public int subtract(int a, int b) {
        return a - b;
    }
}
