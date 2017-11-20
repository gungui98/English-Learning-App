import com.loader.user.UserManager;
import com.loader.word.WordManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        new WordManager();
        new UserManager();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/SplashFXML.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
