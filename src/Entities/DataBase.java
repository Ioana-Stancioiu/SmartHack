package Entities;

import PasswordManager.PasswordManager;

import java.util.HashMap;
import java.util.List;

public class DataBase {
    private List<User> users;
    private HashMap<User, PasswordManager> passwordManager;
}
