package finalProject;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ConcurrentModificationException;


public class Character extends Pane{
    public Point2D velocity = new Point2D(0,0);
    Image image = new Image("file:C:\\Users\\comatose\\Desktop/doodle.png");
    ImageView imageView;
    public static Media musicFile2 = new Media("file:///C:/Users/comatose//Desktop/jump_07.wav");
    public static MediaPlayer mediaPlayer2 = new MediaPlayer(musicFile2);
    public static boolean jumpControl = false;
    public Character() {
        velocity = new Point2D(0,0);
        imageView = new ImageView(image);
        imageView.setFitWidth(Constants.characterwidth);
        imageView.setFitHeight(Constants.characterHeight);
        setTranslateX(100);
        setTranslateY(300);
        getChildren().add(imageView);
        mediaPlayer2.setVolume(0);
    }

    public void moveY(int value){
        boolean moveDown = value>0;
        for (int i = 0 ; i < Math.abs(value) ; i ++){
            try{for (Border border : GameRoot.borders) {
                if (this.getScaleX() == 1.0) {
                    if (intersects(border) && border.getTranslateY() - this.getTranslateY() > 53 && this.getTranslateX() + 21 <= border.getTranslateX() + 55 && value >= 3) {
                        jump();
                        BorderMoves.checkOneHitted(border);
                    }

                }
                else if (this.getScaleX() == -1.0)
                    if (value>=3&&intersects(border) && border.getTranslateY()-this.getTranslateY()>53 && this.getTranslateX() + 29 >= border.getTranslateX() ){
                        jump();
                        BorderMoves.checkOneHitted(border);
                    }
            }}catch (ConcurrentModificationException ex){}
        }
        this.setTranslateY(getTranslateY() + (moveDown?6:-5.5));
    }
    public void moveX(int value){
        boolean movingRight = value>0;
        for (int index = 0 ; index < Math.abs(value) ; index++){
            this.setTranslateX(this.getTranslateX() + (movingRight? 1.3:-1.3));
        }
        if (movingRight && this.getTranslateX() > 340)
            this.setTranslateX(0);
        else if(!movingRight && this.getTranslateX() <-10)
            this.setTranslateX(330);
    }

    public boolean intersects(Pane pane){
        return this.getBoundsInParent().intersects(pane.getBoundsInParent());
    }

    public void jump()
    {
        jumpControl = true;
        TranslateTransition transition = new TranslateTransition(Duration.millis(35),this);
        transition.setByY(-2);
        transition.play();
        velocity = new Point2D(0,-50);
    }

    public  static void jumpingSound(){
        mediaPlayer2 = new MediaPlayer(musicFile2);
        mediaPlayer2.setVolume(0.3);
        mediaPlayer2.play();
    }

}
