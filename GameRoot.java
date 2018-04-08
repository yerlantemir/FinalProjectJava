package finalProject;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Random;

public class GameRoot extends Pane{

    private HashMap<KeyCode,Boolean> keys = new HashMap();

    public static Pane gameRoot = new Pane();
    public static ArrayList<Border>borders = new ArrayList<>();
    public static Character doodle = new Character();
    public static Button btnRestart = new Button("Replay");
    public static int score = 0;
    public static ArrayList<Monster>monsters = new ArrayList<>();
    public static Scores scores ;

    public  Scene scene = new Scene(this);
    public Label scoreLabel = new Label("Score: " + score);

    mainWindow mw = new mainWindow();
    Random rand = new Random();
    AnimationTimer timer ;
    TextField nicknameField = new TextField();
    Button btnSave = new Button("Save");
    boolean started = false;

    public GameRoot(){
        startApp();
    }

    public void restart(){
        gameRoot.getChildren().removeAll(borders);
        borders.clear();
        gameRoot.getChildren().removeAll(doodle,scoreLabel,btnRestart);
        this.getChildren().remove(gameRoot);
        gameRoot = new Pane();
        doodle = new Character();
        score = 0;
        startApp();
    }

    public void createContent(){
        GameRoot.gameRoot.getChildren().addAll(nicknameField,btnSave);
        nicknameField.setTranslateY(820);
        nicknameField.setTranslateX(100);
        nicknameField.setFocusTraversable(false);
        btnSave.setTranslateY(900);
        btnSave.setTranslateX(200);
        btnSave.setOnAction(e->{
            try {
                scores = new Scores();
            }catch (FileNotFoundException ex){}
            try {
                String array[] = this.scoreLabel.getText().split(" ");
                scores.addScoreAction(nicknameField.getText(),Integer.parseInt(array[1]));
            }catch (FileNotFoundException ex){}
            mainWindow.stage.setScene(mainWindow.scene);
            mainWindow.gm.restart();
            nicknameField.setText("");
        });

        gameRoot.setPrefSize(Constants.gameWidth,Constants.gameHeight);
        createBorders();
        doodle = new Character();
        gameRoot.getChildren().add(doodle);
        this.getChildren().add(gameRoot);
    }

    public void startApp(){
        createContent();
        scoreLabel.setTranslateX(10);
        scoreLabel.setTranslateY(10);
        gameRoot.getChildren().add(scoreLabel);

        keys.put(KeyCode.RIGHT,false);
        keys.put(KeyCode.LEFT,false);
        scene.setOnKeyPressed(e->keys.put(e.getCode(),true));
        scene.setOnKeyReleased(e->keys.put(e.getCode(),false));

        scene.getStylesheets().add(getClass().getResource("styleOfGame.css").toString());
        scene.setOnMouseClicked(e->{
            doodle.jump();
        });
        gameRoot.getChildren().add(btnRestart);
        btnRestart.setTranslateY(900);
        btnRestart.setTranslateX(100);
        timer = new AnimationTimer() {
            public void handle(long now) {
                update();
            }
        };
    }

    public void update(){
        started = true;
        doodle.moveY((int)doodle.velocity.getY());
        if (doodle.velocity.getY() < Constants.maxDownVelocity)
            doodle.velocity = doodle.velocity.add(0,Constants.velocityDown);
        if(isPressed(KeyCode.LEFT) ){
            doodle.setScaleX(1);
            doodle.moveX(-Constants.moveRightLeftVelocity);
        }
        if(isPressed(KeyCode.RIGHT)){
            doodle.setScaleX(-1);
            doodle.moveX(Constants.moveRightLeftVelocity);
        }
        if(Character.jumpControl == true  && mw.jump && started){
            Character.mediaPlayer2.setVolume(0.3);
            Character.jumpingSound();
        }

        Character.jumpControl = false;
        scrollScreen();
        try{
            checkForDeleteBorder();
        }
        catch (ConcurrentModificationException exx){}

        scoreLabel.setText("Score: " + score);
    }
    public void scrollScreen(){
        if (doodle.getTranslateY() < Constants.gameHeight/2.3){
            for (Border border:borders){
                border.setTranslateY(border.getTranslateY()+(Constants.gameHeight/2.3-doodle.getTranslateY()));
            }
            doodle.setTranslateY(Constants.gameHeight/2.3);
            score += Constants.scoreUp;
        }
        else if (doodle.getTranslateY() > Constants.gameOverIndex){
            doodle.translateYProperty().addListener((obs,ovs,newValue)->{
                int offset = newValue.intValue();
                if (offset > Constants.gameOverIndex && offset < Constants.gameOverWindowY){
                    gameRoot.setLayoutY(-(offset-Constants.gameOverIndex));
                }
                else if(offset > 1000)
                    timer.stop();
            });
        }
    }

    public static void main(String[] args){Application.launch(args);}


    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,true);
    }

    public void checkForDeleteBorder(){
        for (Border border:borders){
            if (border.getTranslateY() > Constants.gameHeight-10){
                Border.deleteBorder(border);
            }
        }
    }
    public void createBorders(){
        double y =0;
        double x =0;
        Border bord = new Border(Border.BorderType.GREEN,100,400);
        String platforms = Constants.constantPlatforms;
        for (int i = 0; i < platforms.length();i++){
            double lastCoordinateY = borders.get(borders.size()-1).getTranslateY();
            if (i > 100 && i < 160){
                y = (lastCoordinateY - 130);
                x = Math.random()*270;
            }
            else{
                y = -(Math.random()*(Math.random()*80)-lastCoordinateY+40);
                x = Math.random()*270;
            }
            char borderType = platforms.charAt(i);

            switch (borderType){
                case '0':
                    Border borderGreen = new Border(Border.BorderType.GREEN,x,y);
                    break;
                case '1':
                    Border borderBlue = new Border(Border.BorderType.BLUE,x,y);
                    BorderMoves.getAnimationH(borderBlue);
                    break;
                case '2':
                    Border borderYellow = new Border(Border.BorderType.WHITE,x,y);
                    BorderMoves.getAnimationOfFadeTransition(borderYellow);
                    break;
                case '3':
                    Border borderOneHit = new Border(Border.BorderType.ONEHITTED,x,y);
                    break;
            }
        }
    }




}
