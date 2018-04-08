package finalProject;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ConcurrentModificationException;

public class BorderMoves {
    public static void getAnimationH(Border border){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2),border);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        int setByXValue = 285-(int)border.getTranslateX();
        if (setByXValue < 150 )  setByXValue = -130;
        transition.setByX(setByXValue);
        setProperty(border,transition);
    }
    public static void getAnimationOfFadeTransition(Border border){
        FadeTransition transition = new FadeTransition(Duration.seconds(4),border.imageView);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setOnFinished(e->{
            GameRoot.gameRoot.getChildren().remove(border);
            GameRoot.borders.remove(border);
        });

        setProperty(border,transition);
    }
    public static void setProperty(Border border, Transition transition){
        border.translateYProperty().addListener((obs,ovs,newValue)->{
            int offset = newValue.intValue();
            if (offset > 0) transition.play();
        });
    }
    public static void checkOneHitted(Border border){
        Image image = border.imageView.getImage();
        if (image.equals(Border.imageOneHit)){
            try {
                Border.deleteBorder(border);
            }catch (ConcurrentModificationException ex){}
        }
    }
}
