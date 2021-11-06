package Entities;

import CryptoFunctions.HashFunction;

public class User {
    private String username;
    private String password;
    Application application = Application.getInstance();


    public User(final String username, final String password) {
        this.username = username;
        this.password = HashFunction.hashString(password);
    }

    public void loggin() {
        
    }

    public String getUsername() {
        return username;
    }
}
