import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Marktwaage{

    ArrayList<String> lines;
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

    /*public void NilsMethode(){
        for(int i=10;i<=10000;i+=10){
            if(noRekursion(i)){
                System.out.println(i +" g: möglich");
            }else{
                System.out.println(i +" g: nicht möglich");
            }
        }
    }
    
    private boolean noRekursion(int gewicht){
        String binar="1";
        int gegengewicht;
        for(int i=1;i<lines.size();i++){
            binar+="0";
        }
        for(int i=0;i<10000;i++){
            gegengewicht=0;
            char[] tf=binar.toCharArray();
            for(int h=0;h<tf.length;h++){
                if(tf[h]=='1'){
                    gegengewicht+=Integer.parseInt(lines.get(h+1));
                }
            }
            if(gegengewicht==gewicht){
                return true;
            }else{
                binar=binarAddieren(binar);
            }
        }
        return false;
    }

    private String binarAddieren(String binar){
        String ausgabe="";
        char[] ne=binar.toCharArray();
        for(int i=0;i<ne.length;i++){
            if(ne[i]==0){
                ne[i]=1;
                for(int h=i;h>=0;h--){
                    ne[h]=0;
                }
                break;
            }
        }
        for(int i=0;i<ne.length;i++){
            ausgabe+=ne[i];
        }
        return ausgabe;
    }*/
}
