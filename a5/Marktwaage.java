import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Marktwaage{

    ArrayList<String> lines;
    String[] parts;
    String gewichte;
    char binarGA[];
    char binarGG[];
    boolean datei=true;
    long maxgewicht;

    public Marktwaage(String input){
        einlesen(input);
        try{
            binarGG=new char[lines.size()-1];
            binarGA=new char[lines.size()-1];
        }catch(NegativeArraySizeException e){
            System.out.println("Datei nicht gefunden");
            datei=false;
        }
        if(datei){
            for(int i=1;i<lines.size();i++){
                maxgewicht+=Long.parseLong(lines.get(i));
            }
        }
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
            scanner.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void alleGewichte(){
        if(!datei){
            System.out.println("keine Gewichte vorhanden");
            return;
        }

        for(int i=10;i<=10000;i+=10){
            long abstand=wiege(i);
            if(abstand==0){
                String links="";
                String rechts="";

                System.out.println(i +" g: \tmöglich");
                for(int j=0;j<binarGA.length;j++){
                    if(binarGA[j]=='1'){
                        links+=(lines.get(j+1)+"g, ");
                    }
                }
                System.out.println("linke Seite: \t"+links);
                for(int j=0;j<binarGG.length;j++){
                    if(binarGG[j]=='1'){
                        rechts+=(lines.get(j+1)+"g, ");
                    }
                }
                System.out.println("rechte Seite: \t"+rechts);
                System.out.println();
                System.out.println();
            }else{
                System.out.println(i +" g: \tnicht möglich");
                System.out.println("niedrigester Abstand:\t"+abstand+"g");
                System.out.println();
                System.out.println();
            }
        }
    }

    private int potenzieren(int basis, int expo){
        int ergebnis=basis;
        for(int i=1;i<expo;i++){
            ergebnis=ergebnis*basis;
        }
        return ergebnis;
    }

    private long wiege(int input){
        long gewicht=Long.valueOf(input);
        long abstand=10000;

        binarGG[0]='1';
        binarGA[0]='0';
        for(int i=1;i<binarGG.length;i++){
            binarGG[i]='0';
            binarGA[i]='0';
        }

        long gegengewicht=0;
        for(int i=0;i<(potenzieren(2,binarGG.length));i++){
            gegengewicht=0;
            for(int j=0;j<binarGG.length;j++){
                if(binarGG[j]=='1'){
                    gegengewicht+=Long.parseLong(lines.get(j+1));
                }
            }

            if(abstand>Math.abs((gewicht-gegengewicht))){
                abstand=Math.abs((gewicht-gegengewicht));
            }

            if(gegengewicht==gewicht){
                return 0;
            }else if(gegengewicht>gewicht){
                break;
            }
            if(gegengewicht==maxgewicht){
                break;
            }
            binarGG=binarAddieren(binarGG,gegengewicht);
        }

        while(true){
            gegengewicht=0;
            for(int j=0;j<binarGG.length;j++){
                if(binarGG[j]=='1'){
                    gegengewicht+=Long.parseLong(lines.get(j+1));
                }
            }

            binarGA[0]='1';
            for(int x=1;x<(binarGA.length);x++){
                binarGA[x]='0';
            }

            while(true){
                if(binarGA[0]=='#'){
                    break;
                }
                long gewichtsadd=0;

                for(int j=0;j<binarGA.length;j++){
                    if(binarGA[j]=='1'){
                        gewichtsadd+=Long.parseLong(lines.get(j+1));
                    }
                }

                if(gewichtsadd>=gegengewicht){
                    break;
                }

                if((gegengewicht==(gewicht+gewichtsadd))){
                    return 0;
                }else{
                    binarGA=trinarAddieren(binarGA,binarGG,gewichtsadd);
                }
            }
            if(gegengewicht>=maxgewicht){
                break;
            }
            binarGG=binarAddieren(binarGG,gegengewicht);
        }
        return abstand;
    }

    private char[] binarAddieren(char[] binar,long davor){
        long neu=0;
        for(int i=0;i<binar.length;i++){
            if(binar[i]=='0'){
                binar[i]='1';
                for(int h=(i-1);h>=0;h--){
                    binar[h]='0';
                }
                break;
            }
        }

        for(int j=0;j<binar.length;j++){
            if(binar[j]=='1'){
                neu+=Long.parseLong(lines.get(j+1));
            }
        }

        if(neu==davor){
            return binarAddieren(binar,davor);
        }else{
            return binar;
        }
    }

    private char[] trinarAddieren(char[] binar, char[] vorlage, long davor){
        long neu=0;
        boolean aktiv=false;
        int index=0;

        for(int i=0;i<vorlage.length;i++){
            if(vorlage[i]=='1'){
                index=i;
            }
        }

        for(int i=0;i<index;i++){
            if(vorlage[i]=='0'&&binar[i]=='0'){
                binar[i]='1';
                for(int h=(i-1);h>=0;h--){
                    binar[h]='0';
                }
                aktiv=true;
                break;
            }
        }
        if(!aktiv){
            binar[0]='#';
            return binar;
        }

        for(int j=0;j<binar.length;j++){
            if(binar[j]=='1'){
                neu+=Long.parseLong(lines.get(j+1));
            }
        }

        if(neu==davor){
            return trinarAddieren(binar,vorlage,davor);
        }else{
            return binar;
        }
    }
}
