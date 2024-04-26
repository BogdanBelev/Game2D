package com.example.game2d;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView bg1, bg2, bgCheck, player, enemy1, shot, shotCheck, playersLifeImg1, playersLifeImg2, playersLifeImg3;
    @FXML
    private Label labelPause, labelLose, score;
    private final int BG_HEIGHT = 758; //длина окна
    private ParallelTransition parallelTransition; //параллельная анимация для того, чтобы фоны двигались вместе - параллельно
    private TranslateTransition enemyTransition, shotTransition;
    private int playersLife = 3;
    private int scoreCount = 0;

    public static boolean right = false;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean down = false;
    public static boolean isPause = false;
    public static boolean isLose = false;
    public static boolean isShot = false;
    private int playerSpeed = 5;


    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if(right && player.getLayoutX() < 390f){
                player.setLayoutX(player.getLayoutX() + playerSpeed); //меняем передвижение по Х отнимая от текущего положения скорость самолета
                shot.setLayoutX(shot.getLayoutX() + playerSpeed);
                shotCheck.setLayoutX(shotCheck.getLayoutX() + playerSpeed);
            }
            if(left && player.getLayoutX() > 8f){
                player.setLayoutX(player.getLayoutX() - playerSpeed);
                shot.setLayoutX(shot.getLayoutX() - playerSpeed);
                shotCheck.setLayoutX(shotCheck.getLayoutX() - playerSpeed);
            }
            if(down && player.getLayoutY() < 590f){
                player.setLayoutY(player.getLayoutY() + playerSpeed);
                shot.setLayoutY(shot.getLayoutY() + playerSpeed);
                shotCheck.setLayoutY(shotCheck.getLayoutY() + playerSpeed);
            }
            if(up && player.getLayoutY() > 5f){
                player.setLayoutY(player.getLayoutY() - playerSpeed);
                shot.setLayoutY(shot.getLayoutY() - playerSpeed);
                shotCheck.setLayoutY(shotCheck.getLayoutY() - playerSpeed);
            }

            if(isShot && shot.getBoundsInParent().intersects(shotCheck.getBoundsInParent()) && !isLose && !isPause){ //выстрел
                shot.setVisible(true);
            }else if (!isShot && shot.getBoundsInParent().intersects(shotCheck.getBoundsInParent()) && !isLose){
                shot.setVisible(false);
            }

            if(isShot && shot.getBoundsInParent().intersects(enemy1.getBoundsInParent()) && !isLose && !enemy1.getBoundsInParent().intersects(bgCheck.getBoundsInParent()) && enemy1.isVisible()){ //убийство врага
                enemy1.setVisible(false);
                scoreCount+=10;                                 //добавление счета
                score.setText(String.valueOf(scoreCount));
            }

            if(isPause && !labelPause.isVisible()){         //пауза
                playerSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
                shotTransition.pause();
                labelPause.setVisible(true);
            }else if (!isPause && labelPause.isVisible()){
                labelPause.setVisible(false);
                playerSpeed = 5;
                parallelTransition.play();
                enemyTransition.play();
                shotTransition.play();
            }

            if(isLose && !labelLose.isVisible()){       //проигрыш
                playerSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
                shotTransition.pause();
                labelLose.setVisible(true);
            }else if(!isLose && labelLose.isVisible()){  //начало новой игры
                player.setLayoutX(202);
                player.setLayoutY(585);
                shot.setLayoutX(234);
                shot.setLayoutY(438);
                shotCheck.setLayoutX(234);
                shotCheck.setLayoutY(438);
                labelLose.setVisible(false);
                playerSpeed = 5;
                parallelTransition.play();
                enemyTransition.play();
                shotTransition.play();
                playersLife = 3;
                playersLifeImg1.setVisible(true);
                playersLifeImg2.setVisible(true);
                playersLifeImg3.setVisible(true);
                score.setText(String.valueOf(0));
                scoreCount = 0;
            }

            else if(player.getBoundsInParent().intersects(enemy1.getBoundsInParent()) && enemy1.isVisible() && playersLife > 0){ //проверяем соприкосновение игрока с врагом
                enemy1.setVisible(false);
                playersLife--;
            }else if (playersLife == 2) {
                playersLifeImg3.setVisible(false);
            }else if (playersLife == 1) {
                playersLifeImg2.setVisible(false);
            }else if (playersLife == 0) {
                playersLifeImg1.setVisible(false);
            }
            if(enemy1.getBoundsInParent().intersects(bgCheck.getBoundsInParent()) && !enemy1.isVisible()){  //появление врага за окном игры
                enemy1.setVisible(true);
            }
            if(player.getBoundsInParent().intersects(enemy1.getBoundsInParent()) && playersLife <= 0){
                enemy1.setVisible(false);
                isLose = true;
            }
        }
    };


    @FXML
    void initialize() {
        TranslateTransition bgTrans1 = new TranslateTransition(Duration.millis(7000), bg1); //движение фона со скоростью 7000 миллисек
        bgTrans1.setFromX(0); //движение начинается от позиции самого фона
        bgTrans1.setToY(BG_HEIGHT); //движение на 598 пикселей вниз
        bgTrans1.setInterpolator(Interpolator.LINEAR); //скорость движения не меняется

        TranslateTransition bgTrans2 = new TranslateTransition(Duration.millis(7000), bg2);
        bgTrans2.setFromX(0); //движение начинается от позиции самого фона
        bgTrans2.setToY(BG_HEIGHT); //движение на 598 пикселей вниз
        bgTrans2.setInterpolator(Interpolator.LINEAR); //скорость движения не меняется

        enemyTransition = new TranslateTransition(Duration.millis(3000), enemy1);
        enemyTransition.setFromX(0);
        enemyTransition.setToY(BG_HEIGHT*2);
        enemyTransition.setInterpolator(Interpolator.LINEAR);

        parallelTransition = new ParallelTransition(bgTrans1, bgTrans2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        enemyTransition.setCycleCount(Animation.INDEFINITE);
        enemyTransition.play();

        shotTransition = new TranslateTransition(Duration.millis(150), shot);
        shotTransition.setFromX(0);
        shotTransition.setToY(BG_HEIGHT*-1.25);
        shotTransition.setInterpolator(Interpolator.LINEAR);
        shotTransition.setCycleCount(Animation.INDEFINITE);
        shotTransition.play();

        timer.start();
    }

}
