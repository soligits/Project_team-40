package Main;

import exceptions.DataException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Loader;

import java.io.File;

public class Main extends Application{
    private static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        initializeLoading();
        System.out.println("This works! java 1.8");
        launch();
    }

    private static void resourcesInitialization() throws DataException {
        File resourcesDirectory = new File(PATH);
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdir())
                throw new DataException("System loading failed.");
    }

    private static void initializeLoading(){
        try {
            resourcesInitialization();
        } catch (DataException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        try {
            Loader.getLoader().loadData();
          /*
            Menu.setScanner(new Scanner(System.in));
            MainMenu mainMenu = new MainMenu();
            mainMenu.show();
            mainMenu.execute();

           */
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view-sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
//    private static void run() {
//        MainMenu mainMenu = new MainMenu();
//        Menu.setScanner(new Scanner(System.in));
//        mainMenu.show();
//        mainMenu.execute();
//    }