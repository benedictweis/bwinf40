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

    public Marktwaage(){
        this("gewichtsstuecke0.txt");
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
    }

    public boolean istMöglich(int gewicht){
        int startgewicht = gewicht;

        for(int i = 1; i < lines.size(); i++){
            if(startgewicht == Integer.parseInt(lines.get(i))){
                return true;
            }
        }

        for(int i = lines.size() - 1; i > 0; i--){
            if((gewicht - Integer.parseInt(lines.get(i))) >= 0){
                gewicht -= Integer.parseInt(lines.get(i));
                if(gewicht == 0){
                    return true;
                }
            }
        }

        for(int j = lines.size() - 1; j > 0; j--){
            int jWert = Integer.parseInt(lines.get(j));
            for(int i = lines.size() - 1; i > 0; i--){
                int iWert = Integer.parseInt(lines.get(i));
                if(jWert - iWert >= startgewicht){
                    jWert -= iWert;
                    if(jWert == startgewicht){
                        return true;
                    }
                }
            }
        } 
        
        for(int i = lines.size() - 1; i > 0; i--){
            int iWert = Integer.parseInt(lines.get(i));
            System.out.println("i: " + iWert);
            for(int j = 1; j < lines.size(); j++){
                int jWert = Integer.parseInt(lines.get(j));
                System.out.println("j: " + jWert);
                for(int k = lines.size() - 1; k > 0; k--){
                    int kWert = Integer.parseInt(lines.get(k));
                    System.out.println("k: " + kWert);
                    
                    iWert += jWert;
                    System.out.println(iWert);
                    if(iWert - kWert >= startgewicht){
                        System.out.println(iWert + " - " + kWert);
                        iWert -= kWert;
                        if(iWert == startgewicht){
                            return true;
                        }
                    }
                }
            }
        }
        
        //ungeloeste.add(startgewicht);
        return false;
    }

    public void NilsMethode(){
        for(int i=10;i<=10000;i+=10){
            if(noRekursion(i)){
                System.out.println(i +" g: möglich");
            }else{
                System.out.println(i +" g: nicht möglich");
            }
        }
    }

    public int potenzieren(int basis, int expo){
        int ergebnis=basis;
        for(int i=1;i<expo;i++){
            ergebnis*=ergebnis;
        }
        return ergebnis;
    }

    public boolean noRekursion(int gewicht){
        String binar="1";
        int gegengewicht;
        int gewichtsadd;
        for(int i=1;i<lines.size();i++){
            binar+="0";
        }
        for(int i=0;i<3;i++){
            for(int k=0;k<(lines.size()-1);k++){
                gegengewicht=0;
                gewichtsadd=0;
                char[] tf=binar.toCharArray();
                for(int h=0;h<(tf.length-1);h++){
                    if(tf[h]=='1'){
                        gegengewicht+=Integer.parseInt(lines.get(h+1));
                    }
                }
                for(int h=0;h<(tf.length-1);h++){
                    if(tf[h]=='2'){
                        gewichtsadd+=Integer.parseInt(lines.get(h+1));
                    }
                }
                if(gegengewicht==(gewicht-gewichtsadd)){
                    return true;
                }else{
                    binar=binarAddieren(binar,i);
                }
            }
        }
        return false;
    }

    public String binarAddieren(String binar, int durchgang){
        String ausgabe="";
        char[] ne=binar.toCharArray();

        if(durchgang==0){
            for(int i=0;i<ne.length;i++){
                if(ne[i]=='0'){
                    ne[i]='1';
                    for(int h=(i-1);h>=0;h--){
                        ne[h]='0';
                    }
                    break;
                }
            }
            for(int i=0;i<ne.length;i++){
                ausgabe+=ne[i];
            }
            return ausgabe;
        } else if(durchgang==1){
            for(int i=0;i<ne.length;i++){
                if(ne[i]=='1'){
                    ne[i]='2';
                    for(int h=(i-1);h>=0;h--){
                        ne[h]='1';
                    }
                    break;
                }
            }
            for(int i=0;i<ne.length;i++){
                ausgabe+=ne[i];
            }
            return ausgabe;
        }else{
            for(int i=(ne.length-1);i>=0;i--){
                if(ne[i]=='2'){
                    ne[i]='1';
                    for(int h=(i+1);h<ne.length;h++){
                        ne[h]='0';
                    }
                    break;
                }
            }
            for(int i=0;i<ne.length;i++){
                ausgabe+=ne[i];
            }
            return ausgabe;
        }
    }
}
