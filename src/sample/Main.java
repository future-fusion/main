package sample;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFX");
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 320, 240);
        Button b = new Button("音源再生");
        b.addEventHandler( MouseEvent.MOUSE_CLICKED ,  e -> buttonCliked( e ) );
        pane.setCenter(b);
        stage.setScene(scene);
        stage.show();
    }

    private void buttonCliked( MouseEvent e ){
        AudioClip media = new AudioClip(new File("sample.wav").toURI().toString());
        media.play();
    }
}