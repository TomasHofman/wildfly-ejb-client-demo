/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.ejb;

public interface RemoteCounter {
 
    void increment();
 
    void decrement();
 
    int getCount();
}
