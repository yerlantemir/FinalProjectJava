package finalProject;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class mainWindow extends Application{

    public static ToggleButton jumpingSoundOff = new ToggleButton("OFF");
    public static ToggleButton jumpingSoundOn = new ToggleButton("ON");
    public static Stage stage;
    public static GameRoot gm = new GameRoot();
    MediaPlayer mediaPlayer;
    public static Scene scene;
    MediaPlayer mediaPlayer2;
    Media musicFile2 = new Media("file:///C:/Users/comatose/Desktop/jump_07.wav");
    Button buttonMenu;
    public  boolean jump = true;
    Button play = new Button("");
    public mainWindow(){

    }

    public void start(Stage ps){

        this.stage = ps;
        GridPane pane = new GridPane() ;
        play.setOnAction(e->{
            stage.setScene(gm.scene);
            gm.timer.start();
            jumpingSoundOn.setSelected(true);
        });
        gm.btnRestart.setOnAction(e->{
            gm.restart();
        });
        pane.add(play,0,0);
        play.setMinSize( 108,36);
        play.setTranslateY(123);
        play.setTranslateX(55);

        Button btnScores = new Button("");
        pane.add(btnScores,0,1);
        btnScores.setTranslateY(146);
        btnScores.setTranslateX(88.5);
        btnScores.setMinSize(110,33);

        Button btnOptions = new Button();
        btnOptions.setTranslateY(227.8);
        btnOptions.setTranslateX(175);
        btnOptions.setMinSize(115,34);

        pane.add(btnOptions,0,2);

        ImageView doodler = new ImageView();
        pane.add(doodler,0,3);

        animationOfJumping(doodler);


        this.scene = new Scene(pane,330,460);
        scene.getStylesheets().add(getClass().getResource("style.css").toString());



        GridPane paneOptions = new GridPane();
        GetSceneOptions(paneOptions,stage);
        Scene sceneOptions = new Scene(paneOptions,330,460);

        btnOptions.setOnAction(e->stage.setScene(sceneOptions));
        sceneOptions.getStylesheets().add(getClass().getResource("styleOptions.css").toString());

        btnScores.setOnAction(e->{
            try {
                stage.setScene(getScoresScene());
            }catch (FileNotFoundException ex){}
        });

        stage.setScene(scene);
        stage.setTitle("DoodleJump");
        stage.show();
    }
    public void animationOfJumping(ImageView doodler){


        Image image = new Image("file:C:/Users/comatose/Desktop/doodle2.gif");
        doodler.setImage(image);
        doodler.setFitWidth(50);
        doodler.setFitHeight(60);
        doodler.setTranslateY(80);
        doodler.setTranslateX(68);

        TranslateTransition doodleTransitionDown1 = new TranslateTransition();
        doodleTransitionDown1.setNode(doodler);
        doodleTransitionDown1.setDuration(Duration.millis(200));
        doodleTransitionDown1.setByY(57);

        TranslateTransition doodleTransitionDown2 = new TranslateTransition() ;
        doodleTransitionDown2.setNode(doodler);
        doodleTransitionDown2.setDuration(Duration.millis(100));
        doodleTransitionDown2.setByY(57);
        doodleTransitionDown2.setOnFinished(e-> { try {
            if (jumpingSoundOff.isSelected()){
                mediaPlayer2 = new MediaPlayer(musicFile2);
                mediaPlayer2.setVolume(0.3);
                mediaPlayer2.play();
            }
            else if(jumpingSoundOn.isSelected())
                mediaPlayer2.stop();

        } catch (NullPointerException ex){} });

        TranslateTransition doodleTransitionUp1 = new TranslateTransition();
        doodleTransitionUp1.setDuration(Duration.millis(100));
        doodleTransitionUp1.setNode(doodler);
        doodleTransitionUp1.setByY(-57);

        TranslateTransition doodleTransitionUp2 = new TranslateTransition();
        doodleTransitionUp2.setDuration(Duration.millis(200));
        doodleTransitionUp2.setNode(doodler);
        doodleTransitionUp2.setByY(-57);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.millis(120));

        SequentialTransition sq = new SequentialTransition(doodleTransitionDown1,doodleTransitionDown2,doodleTransitionUp1,doodleTransitionUp2,pauseTransition);
        sq.setCycleCount(Animation.INDEFINITE);
        sq.play();
    }
    public void GetSceneOptions(GridPane paneOptions,Stage window){

        int indexOfRow = 0;
        String arrayOfNames[] = {"Music","OFF","ON","Jumping sound","OFF","ON","Monsters","OFF","ON","Character","Simple","Police"};
        int indexOfArray = 0;
        music();
        for (int index = 0 ; index < 4 ; index++){

            VBox box = new VBox(10);
            Label nameOfToggleGroup = new Label(arrayOfNames[indexOfArray]);

            nameOfToggleGroup.setId("labelers");
            nameOfToggleGroup.setTranslateY(17);
            if(indexOfArray == 0){
                nameOfToggleGroup.setTranslateX(17);
            }
            else if (indexOfArray == 9){
                nameOfToggleGroup.setTranslateX(17);
            }
            indexOfArray++;
            box.getChildren().add(nameOfToggleGroup);

            ToggleGroup tg = new ToggleGroup();
            HBox boxHor = new HBox();
            HBox boxOfLabel = new HBox(7.5);

            for (int index2 = 0 ; index2 < 2 ; index2++){

                ToggleButton tgb = new ToggleButton(arrayOfNames[indexOfArray]);
                indexOfArray++;
                tgb.setId("Buttons");
                tgb.setToggleGroup(tg);
                tgb.setSelected(true);
                mediaPlayer.setVolume(0.05);
                if (indexOfArray == 11 || indexOfArray == 12)
                    tgb.setMinSize(48,30);
                else if (indexOfArray == 3){
                    tgb.setOnAction(e->{
                        if (tgb.isSelected())
                            mediaPlayer.play();
                    });
                }
                else if(indexOfArray == 2){
                    tgb.setOnAction(e->{
                        if(tgb.isSelected())
                            mediaPlayer.stop();
                    });
                }
                else if(indexOfArray ==5){
                    jumpingSoundOn = tgb;
                    jumpingSoundOn.setOnAction(e->jump = true);
                }
                else if(indexOfArray ==6){
                    jumpingSoundOff = tgb;
                    jumpingSoundOff.setOnAction(e->jump = false);
                }
                else
                    tgb.setMinSize(32,30);
                boxHor.getChildren().add(tgb);
            }
            tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

                public void changed(ObservableValue<? extends Toggle> observable,
                                    final Toggle oldValue, final Toggle newValue) {
                    if ((newValue == null)) {
                        Platform.runLater(new Runnable() {

                            public void run() {
                                tg.selectToggle(oldValue);
                            }
                        });
                    }
                }
            });
            if (index == 0){
                box.setTranslateX(35);
                box.setTranslateY(77);
                box.setRotate(3);
            }
            else if (index == 1){
                box.setTranslateX(180);
                box.setTranslateY(45);
                box.setRotate(-5);
            }
            else if (index == 2){
                box.setTranslateX(55);
                box.setTranslateY(40);
                box.setRotate(-2);
            }
            else{
                box.setTranslateX(170);
                box.setTranslateY(30);
                box.setRotate(-4);
            }

            box.getChildren().add(boxOfLabel);
            boxOfLabel.setTranslateY(27);


            box.getChildren().add(boxHor);
            paneOptions.add(box,0,indexOfRow);
            indexOfRow++;
        }
        buttonMenu = new Button("");
        buttonMenu.setMinSize(90,30);
        paneOptions.add(buttonMenu,0,5);
        buttonMenu.setTranslateX(31);
        buttonMenu.setTranslateY(24.5);
        buttonMenu.setRotate(5);
        buttonMenu.setOnAction(e->window.setScene(scene));

    }
    public void music(){
        Media musicFile = new Media("file:///C:/Users/comatose/Downloads/Boardwalk-Arcade.mp3");
        mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(1000);
    }
    public Scene getScoresScene()throws FileNotFoundException{
        Group root = new Group();
        Scene scoresScene = new Scene(root,Constants.gameWidth,Constants.gameHeight);
        gm.scores = new Scores();
        root.getChildren().add(gm.scores);
        gm.scores.setTranslateX(100);
        gm.scores.setTranslateY(150);
        buttonMenu = new Button("menu");
        buttonMenu.setOnAction(e->stage.setScene(scene));
        root.getChildren().add(buttonMenu);
        buttonMenu.setTranslateX(130);
        buttonMenu.setTranslateY(350);
        return scoresScene;
    }

    public static void main(String[] args) { Application.launch(args); }
}
