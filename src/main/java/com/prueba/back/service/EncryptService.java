package com.prueba.back.service;

public interface EncryptService {

    String encryptPassword(String password);

    boolean verifyPassword(String originalPassword,String hashPassword);
}
