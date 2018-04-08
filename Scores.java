package finalProject;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Scores extends ScrollPane{
    Label label = new Label();
    HashMap<String,Integer>scoreMap = new HashMap<>();
    String array[] = new String[2];
    VBox content = new VBox(2);

    public Scores()throws FileNotFoundException{
        setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setMaxHeight(200);
        setPannable(true);
        setContent(content);
        scoreButtonAction();
    }
    public void addScoreAction(String name,int score)throws FileNotFoundException{
        readAlreadyExistScores();
        writeScore(name,score);
        content.getChildren().clear();
        sortMap(scoreMap);
        writeToScrollPane();
    }
    public void scoreButtonAction()throws FileNotFoundException{
        readAlreadyExistScores();
        sortMap(scoreMap);
        writeToScrollPane();
    }

    public void readAlreadyExistScores()throws FileNotFoundException{
        Scanner in = new Scanner(new FileInputStream("C:\\Users\\comatose\\IdeaProjects\\2ndSem\\src\\finalProject\\scores.txt"));
        while (in.hasNextLine()){
            array = in.nextLine().split(" ");
            scoreMap.put(array[0],Integer.parseInt(array[1]));
        }
        in.close();
    }

    public void writeToScrollPane(){
        Iterator it = scoreMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            content.getChildren().addAll(new Label(pair.getKey() + "   "  + pair.getValue()));
            it.remove();
        }
    }
    public void writeScore(String name,int score)throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(new FileOutputStream("C:\\Users\\comatose\\IdeaProjects\\2ndSem\\src\\finalProject\\scores.txt"));
        Iterator it = scoreMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            writer.println(pair.getKey() + " " + pair.getValue());
            it.remove();
        }
        writer.println(name + " " +score);
        writer.close();
    }

    public void sortMap(HashMap<String,Integer> map){
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
    }}