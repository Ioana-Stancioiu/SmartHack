package Entities;

import java.util.List;

public class Application {
    private static Application instance = null;
    private List<String> sites;

    private Application() {
    }

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
}
