/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

/**
 *
 * @author rmoreno
 */
public interface SecurityMgt {
    
    public String getSecret();
    
    public String createAndSignToken(String username, String password);
    
//    public 
}
