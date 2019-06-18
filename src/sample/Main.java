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
import java.time.LocalDate;
import java.time.LocalTime;

public class Main extends Application {
    MediaPlayer mplayer = null;
    Slider slider = null;
    Label l = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 現在の日付のみを取得
        LocalDate d1 = LocalDate.now();
        System.out.println(d1);
        // 現在の時刻のみを取得
        LocalTime t1 = LocalTime.now();
        System.out.println(t1);
        int timeHour = t1.getHour();
        System.out.println(timeHour);

        stage.setTitle("JavaFX");
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 320, 240);

        //ボタンの設定
        Button playButton = new Button("音源再生");
        playButton.addEventHandler( MouseEvent.MOUSE_CLICKED ,  e -> buttonClikedA( e ) );
        Button pauseButton = new Button("一時停止");
        pauseButton.addEventHandler( MouseEvent.MOUSE_CLICKED ,  f -> buttonClikedB( f ) );
        //ラベルの設定
        l = new Label("");
        //スライダーの初期化
        slider = new Slider();

        pane.getChildren().add( playButton );
        pane.getChildren().add( pauseButton );
        pane.getChildren().add( slider );
        pane.getChildren().add( l );
        stage.setScene(scene);
        stage.show();
    }

    private void buttonClikedA( MouseEvent e ){
        Media media = new Media(new File("sample.wav").toURI().toString());
        mplayer = new MediaPlayer( media );
        mplayer.play();

        mplayer.setOnReady(() -> {
            //スライダーの最大最小を設定
            slider.setMin(mplayer.getStartTime().toSeconds());
            slider.setMax(mplayer.getStopTime().toSeconds());
            //音声の長さを表示
            l.setText( "再生時間 " + mplayer.getStopTime().toSeconds() + " 秒" );
            //ループ回数を無限に指定
            mplayer.setCycleCount(MediaPlayer.INDEFINITE);
        });

        //再生時間が進んだらスライダーの位置を変更する
        mplayer.currentTimeProperty().addListener((Observable observable) -> slider.setValue( mplayer.getCurrentTime().toSeconds() ));
    }

    private void buttonClikedB( MouseEvent f ){
        mplayer.pause();
    }

}