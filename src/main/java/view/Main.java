package view;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        ApplicationView view = new ApplicationView();

        Application.launch(ApplicationView.class, args);
    }
}
