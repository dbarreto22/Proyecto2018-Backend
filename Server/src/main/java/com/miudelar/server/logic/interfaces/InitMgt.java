package com.miudelar.server.logic.interfaces;

import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import java.security.NoSuchAlgorithmException;

/*@author Romina */
public interface InitMgt {
    void initBaseData() throws NoSuchAlgorithmException, RolWithInvalidDataException, UsuarioWithInvalidDataException, NonexistentEntityException;
}
