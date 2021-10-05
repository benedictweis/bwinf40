import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Marktwaage{

    ArrayList<String> lines;
    String[] parts;
    String gewichte;

    public Marktwaage(String input){
        gewichte = input;
        einlesen(gewichte);
    }

    private void einlesen(String input){
        File file = new File(input);
        lines = new ArrayList<String>();
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                parts = (scanner.nextLine()).split(" ");
                lines.add(parts[0]);
                if(parts.length == 2){
                    lines.add(parts[1]);
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        //parts = lines.get(0).split(" ");
    }
}