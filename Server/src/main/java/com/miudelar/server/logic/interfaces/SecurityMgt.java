package com.miudelar.server.logic.interfaces;

import java.security.NoSuchAlgorithmException;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/* @author Romina*/
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface SecurityMgt {

    @WebMethod
    public boolean verifyThePassword(String inputPass, String storedPass) throws NoSuchAlgorithmException;

    @WebMethod
    public String generatePassword(String inputPass) throws NoSuchAlgorithmException;
}
