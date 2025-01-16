package com.katyshevtseva.kikinotebook;

import com.katyshevtseva.fx.windowbuilder.WindowBuilder;
import com.katyshevtseva.kikinotebook.view.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.MAIN_DIALOG;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowBuilder.openDialog(MAIN_DIALOG, new MainController());
    }
}