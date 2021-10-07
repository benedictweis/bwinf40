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
                if(parts.length == 2){
                    for(int i = 0; i < Integer.parseInt(parts[1]); i++){
                        lines.add(parts[0]);   
                    }
                } else{
                    lines.add(parts[0]);
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void wiegen(){
        for(int i = 10; i <= 10000; i += 10){
            if(istMöglich(i) == true){
                System.out.println(i +" g: möglich");
            } else{
                System.out.println(i +" g: nicht möglich");
            }
        }
    }

    public boolean istMöglich(int gewicht){
        for(int i = 1; i < lines.size() - 1; i++){
            if(gewicht == Integer.parseInt(lines.get(i))){
                return true;
            }
        }
        
        for(int i = lines.size(); i < 1; i--){
            if((gewicht - Integer.parseInt(lines.get(i))) > 0){
                gewicht -= Integer.parseInt(lines.get(i));
            }
        }
        
        return false;
    }
}