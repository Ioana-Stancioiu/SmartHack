package Entities;

import CryptoFunctions.HashFunction;

public class User {
    private String username;
    private String password;

    public User(final String username, final String password) {
        this.username = username;
        this.password = HashFunction.hashString(password);
    }

}
