package com.miudelar.server.logic.interfaces;

import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/*@author Romina */
public interface InitMgt {
//    void initBaseData() throws NoSuchAlgorithmException, RolWithInvalidDataException, UsuarioWithInvalidDataException, NonexistentEntityException;
    public List<Asignatura_Carrera> getAllPrevias(Asignatura_Carrera asigcar);
}
