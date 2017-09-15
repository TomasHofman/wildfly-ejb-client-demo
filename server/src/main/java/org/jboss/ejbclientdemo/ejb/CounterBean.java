/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.ejbclientdemo.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful
@Remote(RemoteCounter.class)
public class CounterBean implements RemoteCounter {
 
    private int count = 0;
 
    @Override
    public void increment() {
        this.count++;
    }
 
    @Override
    public void decrement() {
        this.count--;
    }
 
    @Override
    public int getCount() {
        return this.count;
    }
}
