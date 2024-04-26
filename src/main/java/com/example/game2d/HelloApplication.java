package com.example.game2d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Timer;

/*
* scene - окно игры
* */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 469, 758);
        stage.initStyle(StageStyle.UNDECORATED); //делает окно без границ и без кнопок, окно нельзя растягивать
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT){
                HelloController.left = true;
            }
            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT){
                HelloController.right = true;
            }
            if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP){
                HelloController.up = true;
            }
            if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN){
                HelloController.down = true;
            }
            if(e.getCode() == KeyCode.ENTER && HelloController.isLose){
                HelloController.isLose = false;
            }
            if(e.getCode() == KeyCode.SPACE && !HelloController.isShot && !HelloController.isLose){
                HelloController.isShot = true;
            }
        });
        scene.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT){
                HelloController.left = false;
            }
            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT){
                HelloController.right = false;
            }
            if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP){
                HelloController.up = false;
            }
            if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN){
                HelloController.down = false;
            }
            if(e.getCode() == KeyCode.ESCAPE && !HelloController.isLose){
                HelloController.isPause = !HelloController.isPause;
            }
            if(e.getCode() == KeyCode.SPACE && HelloController.isShot){
                HelloController.isShot = false;
            }


//            myTimerTaskForEnemies enemiesTask = new myTimerTaskForEnemies();
//            Timer enemiesTimer = new Timer();
//            enemiesTimer.schedule(enemiesTask, 3000, 2000);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException exc) {
//                enemiesTimer.cancel();
//            }

        });


    }

    public static void main(String[] args) {
        launch();

    }
}



