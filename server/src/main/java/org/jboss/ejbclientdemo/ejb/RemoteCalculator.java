/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.ejb;

public interface RemoteCalculator {
 
    int add(int a, int b);
 
    int subtract(int a, int b);
}