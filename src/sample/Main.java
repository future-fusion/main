package sample;

import java.io.File;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {
    MediaPlayer mplayer = null;
    Slider slider = null;
    Label l = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFX");
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 320, 240);

        //ボタンの設定
        Button b = new Button("音源再生");
        b.addEventHandler( MouseEvent.MOUSE_CLICKED ,  e -> buttonCliked( e ) );
        l = new Label("");
        //スライダーの初期化
        slider = new Slider();

        pane.getChildren().add( b );
        pane.getChildren().add( slider );
        pane.getChildren().add( l );
        stage.setScene(scene);
        stage.show();
    }

    private void buttonCliked( MouseEvent e ){
        Media media = new Media(new File("sample.wav").toURI().toString());
        mplayer = new MediaPlayer( media );
        mplayer.play();
        //スライダーの最大最小を設定
        mplayer.setOnReady(() -> {
            slider.setMin(mplayer.getStartTime().toSeconds());
            slider.setMax(mplayer.getStopTime().toSeconds());
            //音声の長さを表示
            l.setText( "再生時間 " + mplayer.getStopTime().toSeconds() + " 秒" );
        });

        //再生時間が進んだらスライダーの位置を変更する
        mplayer.currentTimeProperty().addListener((Observable observable) -> slider.setValue( mplayer.getCurrentTime().toSeconds() ));
    }

}