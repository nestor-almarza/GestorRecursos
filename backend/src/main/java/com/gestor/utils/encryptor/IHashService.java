package com.gestor.utils.encryptor;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IHashService {
    String generateHash(String plainText)
            throws NoSuchAlgorithmException, InvalidKeySpecException;

    // CREATE HASH
    // generate hash
    public String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
    // generate Salt
    public byte[] getSalt() throws NoSuchAlgorithmException;
    // converts to Hex format
    public String toHexFormat(byte[] array);

    // VERIFY HASH
    // check password
    public boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;
    // from Hex format
    byte[] fromHexFormat(String hex);
}
