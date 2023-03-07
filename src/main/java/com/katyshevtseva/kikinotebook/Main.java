package com.katyshevtseva.kikinotebook;

import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.kikinotebook.view.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.katyshevtseva.kikinotebook.view.utils.ViewConstants.NotebookDialogInfo.MAIN;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowBuilder.openDialog(MAIN, new MainController());
    }
}