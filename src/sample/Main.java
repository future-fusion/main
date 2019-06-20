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

import static java.lang.Thread.*;

public class Main extends Application {
    MediaPlayer mplayer = null;
    Slider slider = null;
    Label label = null;
    String PATH = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 現在の日付のみを取得
        LocalDate d1 = LocalDate.now();
        System.out.println(d1);

        stage.setTitle("JavaFX");
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 320, 240);

        //ボタンの設定
        Button playButton = new Button("音源再生");
        playButton.addEventHandler( MouseEvent.MOUSE_CLICKED ,  e -> buttonClikedA( e ) );
        Button pauseButton = new Button("一時停止");
        pauseButton.addEventHandler( MouseEvent.MOUSE_CLICKED ,  f -> buttonClikedB( f ) );
        //ラベルの設定
        label = new Label("");
        //スライダーの初期化
        slider = new Slider();

        pane.getChildren().add( playButton );
        pane.getChildren().add( pauseButton );
        pane.getChildren().add( slider );
        pane.getChildren().add( label );
        stage.setScene(scene);
        stage.show();
    }

    private void buttonClikedA( MouseEvent e ){
        // 現在の時刻のみを取得
        LocalTime t1 = LocalTime.now();
        int timeHour = t1.getHour();

        //音源のPATHを取得
        PATH = "audio/sample" + new String(String.valueOf(timeHour)) + ".wav";
        Media mediaA = new Media(new File(PATH).toURI().toString());

        mplayer = new MediaPlayer(mediaA);
        mplayer.play();
        mplayer.setOnReady(() -> {
            //スライダーの最大最小を設定
            slider.setMin(mplayer.getStartTime().toSeconds());
            slider.setMax(mplayer.getStopTime().toSeconds());
            //音源のPATHを設定
            label.setText(PATH);
            //ループ回数を無限に指定
            mplayer.setCycleCount(MediaPlayer.INDEFINITE);
        });
        mplayer.setOnRepeat(() -> {
            LocalTime t2 = LocalTime.now();
            int timeMinute = t2.getMinute();
            if(timeMinute == 59){
                mplayer.stop();
            }
        });
        mplayer.setOnStopped(() -> {
            // 現在の時刻のみを取得
            LocalTime t2 = LocalTime.now();
            int timeHourA = t2.getHour() + 1;
            //音源のPATHを取得
            PATH = "audio/sample" + new String(String.valueOf(timeHourA)) + ".wav";
            Media mediaB = new Media(new File(PATH).toURI().toString());

            mplayer = new MediaPlayer(mediaB);
            mplayer.play();
            mplayer.setOnReady(() -> {
                //スライダーの最大最小を設定
                slider.setMin(mplayer.getStartTime().toSeconds());
                slider.setMax(mplayer.getStopTime().toSeconds());
                //音源のPATHを設定
                label.setText(PATH);
                //ループ回数を無限に指定
                mplayer.setCycleCount(MediaPlayer.INDEFINITE);
            });
        });
        //再生時間が進んだらスライダーの位置を変更する
        mplayer.currentTimeProperty().addListener((Observable observable) -> slider.setValue(mplayer.getCurrentTime().toSeconds()));
    }

    private void buttonClikedB( MouseEvent f ){
        mplayer.pause();
    }

}