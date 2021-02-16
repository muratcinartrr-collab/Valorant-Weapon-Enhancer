package ValorantWeaponEnhancer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ValorantWeaponEnhancerApplication extends Application {

	private static final static String TITLE = " VALORANT Weapon Enhancer";
	private static final static String ICON_PATH = "valorant.png";

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass()
                .getResource("ValorantWeaponEnhancerUI.fxml"));

            primaryStage.setTitle(TITLE);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

            primaryStage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}