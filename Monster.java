package finalProject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

//пружина
public class Monster extends Pane{
    Image bigBlue = new Image("file:C:\\Users\\comatose\\Desktop/bolwoi.gif");
    Image littleBlue = new Image("file:C:\\Users\\comatose\\Desktop/bolwoi.gif");
    Image longM = new Image("file:C:\\Users\\comatose\\Desktop/bolwoi.gif");
    Image greenM = new Image("file:C:\\Users\\comatose\\Desktop/bolwoi.gif");
    Image redM = new Image("file:C:\\Users\\comatose\\Desktop/bolwoi.gif");
    ImageView monster = new ImageView();
    public enum MonsterType{
        bigBlue,littleBlue,longM,greenM,redM
    }
    public Monster(MonsterType monsterType,int x,int y){

        this.setTranslateX(x);
        this.setTranslateY(y);

        switch (monsterType){
            case bigBlue:
                monster = new ImageView(bigBlue);
                break;
            case littleBlue:
                monster = new ImageView(littleBlue);
                break;
            case longM:
                monster = new ImageView(longM);
                break;
            case redM:
                monster = new ImageView(redM);
                break;
            case greenM:
                monster = new ImageView(greenM);
                break;
        }
        monster.setFitHeight(40);
        monster.setFitWidth(50);
        GameRoot.gameRoot.getChildren().add(monster);
        GameRoot.monsters.add(this);

    }
}
