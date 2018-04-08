package finalProject;
import javafx.scene.layout.Pane;
import java.util.ConcurrentModificationException;
public class Border extends Pane{
    Image imageGreen = new Image("file:C:\\Users\\comatose\\Desktop/platform.png");
    Image imageBlue = new Image("file:C:\\Users\\comatose\\Desktop/bluePlatform.png");
    public static Image imageOneHit = new Image("file:C:\\Users\\comatose\\Desktop/Glass.png");
    Image imageWhite = new Image("file:C:\\Users\\comatose\\Desktop/Glass2.png");
    public static Image imageRed = new Image("file:C:\\Users\\comatose\\Desktop/red.png");
    ImageView imageView = new ImageView();
    ImageView imageView2 = new ImageView();

    public enum BorderType{
        GREEN("g"),BLUE("b"),WHITE("w"),ONEHITTED("oh");
        private final String name;
        BorderType(String s) {name = s;};


    }
    public Border(BorderType borderType,double x , double y){
        this.setTranslateX(x);
        this.setTranslateY(y);
        switch (borderType){
            case GREEN:
                imageView = new ImageView(imageGreen);
                break;
            case BLUE:
                imageView = new ImageView(imageBlue);
                break;
            case WHITE:
                imageView = new ImageView(imageWhite);
                imageView2 = new ImageView(imageRed);
                imageView2.setFitWidth(Constants.borderWidth);
                imageView2.setFitHeight(Constants.borderHeight);
                getChildren().add(imageView2);
                break;
            case ONEHITTED:
                imageView = new ImageView(imageOneHit);
        }
        imageView.setFitWidth(Constants.borderWidth);
        imageView.setFitHeight(Constants.borderHeight);
        getChildren().add(imageView);
        GameRoot.borders.add(this);
        GameRoot.gameRoot.getChildren().add(this);
    }
    public static void deleteBorder(Border border){
            GameRoot.borders.remove(border);
            GameRoot.gameRoot.getChildren().remove(border);
    }


}
