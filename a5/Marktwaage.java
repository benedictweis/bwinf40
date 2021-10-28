import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Marktwaage{

    ArrayList<String> lines;
    ArrayList<String> uebrigeGewichte;
    ArrayList<String> speicher;
    String[] parts;
    String gewichte;
    char binarGA[];
    char binarGG[];

    public Marktwaage(String input){
        einlesen(input);
        binarGG=new char[lines.size()-1];
        binarGA=new char[lines.size()-1];
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
        for(int i=10;i<=10000;i+=10){
            int abstand=wiege(i);
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

    private int wiege(int gewicht){
        int abstand=10000;
        int maxgewicht=0;
        for(int i=1;i<lines.size();i++){
            maxgewicht+=Integer.parseInt(lines.get(i));
        }

        binarGG[0]='1';
        for(int i=1;i<binarGG.length;i++){
            binarGG[i]='0';
        }

        binarGA[0]='0';
        for(int x=1;x<(binarGA.length);x++){
            binarGA[x]='0';
        }

        int gegengewicht=0;
        for(int i=0;i<(potenzieren(2,binarGG.length));i++){
            gegengewicht=0;
            for(int j=0;j<binarGG.length;j++){
                if(binarGG[j]=='1'){
                    gegengewicht+=Integer.parseInt(lines.get(j+1));
                }
            }

            if(gegengewicht==gewicht){
                return 0;
            }else if(gegengewicht>gewicht){
                break;
            }
            binarGG=binarAddieren(binarGG);
            if(abstand>Math.abs((gewicht-gegengewicht))){
                abstand=Math.abs((gewicht-gegengewicht));
            }
        }

        while(true){
            gegengewicht=0;
            for(int j=0;j<binarGG.length;j++){
                if(binarGG[j]=='1'){
                    gegengewicht+=Integer.parseInt(lines.get(j+1));
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
                int gewichtsadd=0;

                for(int j=0;j<binarGA.length;j++){
                    if(binarGA[j]=='1'){
                        gewichtsadd+=Integer.parseInt(lines.get(j+1));
                    }
                }

                if(gewichtsadd>=gegengewicht){
                    break;
                }

                if((gegengewicht==(gewicht+gewichtsadd))){
                    return 0;
                }else{
                    binarGA=trinarAddieren(binarGA,binarGG);
                }
            }
            if(gegengewicht==maxgewicht){
                break;
            }
            binarGG=binarAddieren(binarGG);
        }
        return abstand;
    }

    private char[] binarAddieren(char[] binar){
        for(int i=0;i<binar.length;i++){
            if(binar[i]=='0'){
                binar[i]='1';
                for(int h=(i-1);h>=0;h--){
                    binar[h]='0';
                }
                break;
            }
        }
        return binar;
    }

    private char[] trinarAddieren(char[] binar, char[] vorlage){
        for(int i=0;i<vorlage.length;i++){
            if(vorlage[i]=='0'&&binar[i]=='0'){
                binar[i]='1';
                for(int h=(i-1);h>=0;h--){
                    binar[h]='0';
                }
                return binar;
            }
        }
        binar[0]='#';
        return binar;
    }

    public void maxHoffnung(){
        for(int i = 10; i <= 10000; i += 10){
            if(istMöglich(i) == true){
                System.out.println(i + " g ist möglich");
            } else{
                System.out.println(i + " g ist nicht möglich");
            }
        }
    }

    public boolean istMöglich(int gewicht){

        for(int i = lines.size() - 1; i > 0; i--){
            uebrigeGewichte = (ArrayList) lines.clone();
            int iWert = Integer.parseInt(lines.get(i));
            int gewichtrechts = iWert;
            uebrigeGewichte.remove(i);
            speicher = (ArrayList) uebrigeGewichte.clone();
            int gewichterechtsSpeicher = gewichtrechts;

            if(gewichtrechts == gewicht){
                return true;
            }

            for(int p = 1; p < uebrigeGewichte.size() - 1; p++){
                uebrigeGewichte = (ArrayList) speicher.clone();
                gewichtrechts = gewichterechtsSpeicher;
                int pWert = Integer.parseInt(uebrigeGewichte.get(p));
                gewichtrechts += pWert;
                uebrigeGewichte.remove(p);
                System.out.println(uebrigeGewichte);

                if(gewichtrechts == gewicht){
                    return true;
                }

                for(int j = 1 ; j < lines.size() - 2; j++){
                    int jWert = Integer.parseInt(uebrigeGewichte.get(1));
                    gewichtrechts += jWert;
                    uebrigeGewichte.remove(1);
                    System.out.println(uebrigeGewichte);
                    System.out.println(gewichtrechts);

                    if(gewichtrechts == gewicht){
                        return true;
                    }

                    int llllll = uebrigeGewichte.size() - 1;

                    for(int k = llllll; k > 0; k--){
                        int kWert = Integer.parseInt(uebrigeGewichte.get(k));
                        int gewichtlinks = kWert;
                        uebrigeGewichte.remove(k);
                        System.out.println(uebrigeGewichte);
                        System.out.println(gewichtlinks);

                        if((gewichtrechts - gewichtlinks) == gewicht){
                            return true;
                        }

                        int kkkkkk = uebrigeGewichte.size() - 1;

                        for(int l = kkkkkk; l > 0; l--){
                            int lWert = Integer.parseInt(uebrigeGewichte.get(l));
                            gewichtlinks += lWert;
                            uebrigeGewichte.remove(l);

                            System.out.println(uebrigeGewichte);
                            System.out.println(gewichtlinks);

                            if((gewichtrechts - gewichtlinks) == gewicht){
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public void test(){
        uebrigeGewichte = (ArrayList) lines;
    }
}
