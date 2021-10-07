import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Marktwaage{

    ArrayList<String> lines;
    ArrayList<Integer> ungeloeste;
    String[] parts;
    String gewichte;

    public Marktwaage(String input){
        einlesen(input);
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
        ungeloeste = new ArrayList<Integer>();

        for(int i = 10; i <= 10000; i += 10){
            if(istMöglich(i) == true){
                System.out.println(i +" g: möglich");
            } else{
                System.out.println(i +" g: nicht möglich");
            }
        }

        ungeloesteLoesen();
    }

    public boolean istMöglich(int gewicht){
        int startGewicht = gewicht;

        for(int i = 1; i < lines.size(); i++){
            if(gewicht == Integer.parseInt(lines.get(i))){
                return true;
            }
        }

        for(int i = lines.size() - 1; i > 0; i--){
            if((gewicht - Integer.parseInt(lines.get(i))) >= 0){
                //System.out.println(gewicht + ", " + lines.get(i));
                gewicht -= Integer.parseInt(lines.get(i));

                if(gewicht == 0){
                    return true;
                }
            }
            //System.out.println("ende " + i + ": " + lines.get(i));
        }

        ungeloeste.add(startGewicht);
        return false;
    }

    public void ungeloesteLoesen(){
        for(int i = lines.size() - 1; i > 0; i--){
            for(int j = lines.size() - 1; j > 0; j--){
                if((i - j) >= 0){
                    i -= j;
                }
            }
        }
    }

    public boolean Rekursionftw(int gewicht){
        String binar="1";
        for(int i=1;i<lines.size();i+=2){
            binar+="0";
        }
        //if(

        return false;
    }
}
