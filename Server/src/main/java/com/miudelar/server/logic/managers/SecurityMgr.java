package com.miudelar.server.logic.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.miudelar.server.logic.interfaces.SecurityMgt;
import javax.jws.WebService;

@WebService(endpointInterface = "com.miudelar.server.logic.interfaces.SecurityMgt")
public class SecurityMgr implements SecurityMgt {

    String SALT = "InYourFace";

    @Override
    public boolean verifyThePassword(String inputPass, String storedPass) throws NoSuchAlgorithmException {
         String SALT2 = "InYourFace";
        boolean isAuthenticated = false;
        String hashedPassword = generateHash(inputPass + SALT2);
        if (hashedPassword.equals(storedPass)) {
            isAuthenticated = true;
        }
        return isAuthenticated;
    }

    @Override
    public String generatePassword(String inputPass) throws NoSuchAlgorithmException {
        return generateHash(inputPass + SALT);
    }

    private static String generateHash(String input) throws NoSuchAlgorithmException {
        StringBuilder hash = new StringBuilder();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = sha.digest(input.getBytes());
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < hashedBytes.length; ++idx) {
            byte b = hashedBytes[idx];
            hash.append(digits[(b & 0xf0) >> 4]);
            hash.append(digits[b & 0x0f]);
        }
        return hash.toString();
    }
}
