import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Marktwaage {

    ArrayList<String> lines;
    HashMap<Integer, Daten> hm;
    String[] parts;
    String gewichte;
    char binarGA[];
    char binarGG[];
    long maxgewicht;

    public Marktwaage(String input) {
        hm=new HashMap<Integer, Daten>();
        einlesen(input);
        try {
            binarGG = new char[lines.size() - 1];
            binarGA = new char[lines.size() - 1];
        } catch (NegativeArraySizeException e) {
            System.out.println("Datei nicht gefunden");
            System.exit(0);
        }
        for (int i = 1; i < lines.size(); i++) {
            maxgewicht += Long.parseLong(lines.get(i));
        }
        berechnen();
    }

    private void einlesen(String input) {
        File file = new File(input);
        lines = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                parts = (scanner.nextLine()).split(" ");
                if (parts.length == 2) {
                    for (int i = 0; i < Integer.parseInt(parts[1]); i++) {
                        lines.add(parts[0]);
                    }
                } else {
                    lines.add(parts[0]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void alleGewichte() {
        String rechts="";
        String links="";

        for (int i = 10; i <= 10000; i += 10) {
            Daten aktuell=hm.get(i);
            if(aktuell.abstand==0){
                System.out.println(i+"g ist möglich");
            }else{
                System.out.println(i+"g ist nicht möglich");
                System.out.println("nächster Abstand: "+aktuell.abstand);
            }

            for(int h=0;h<aktuell.rechts.size();h++){
                rechts+=String.valueOf(aktuell.rechts.get(h)+"   ");
            }

            for(int h=0;h<aktuell.links.size();h++){
                links+=String.valueOf(aktuell.links.get(h)+"   ");
            }

            System.out.println("linke Seite:\t"+links);
            System.out.println("rechte Seite:\t"+rechts);
            System.out.println();
        }
    }

    private void berechnen(){
        long gegengewicht=0;
        long gewichtsadd=0;
        for(int i=0;i<binarGG.length;i++){
            binarGG[i]='0';
            binarGA[i]='0';
        }
        binarGG[0]='1';

        while(true){
            if(binarGG[0]=='#'){
                return;
            }
            for(int i=0;i<binarGA.length;i++){
                binarGA[i]='0';
            }

            gegengewicht=0;
            for(int i=0;i<binarGG.length;i++){
                if(binarGG[i]=='1'){
                    gegengewicht+=Long.parseLong(lines.get(i+1));
                }
            }

            while(true){
                if(binarGA[0]=='#'){
                    break;
                }

                gewichtsadd=0;
                for(int i=0;i<binarGG.length;i++){
                    if(binarGA[i]=='1'){
                        gewichtsadd+=Long.parseLong(lines.get(i+1));
                    }
                }
                
                    long abstand;
                    Daten neu=new Daten();
                    abstand=Math.abs(gegengewicht-(i+gewichtsadd));
                    neu.rechts.clear();
                    neu.links.clear();

                    for(int h=0;h<binarGG.length;h++){
                        if(binarGG[h]=='1'){
                            neu.rechts.add(Integer.parseInt(lines.get(h+1)));
                        }
                        if(binarGA[h]=='1'){
                            neu.links.add(Integer.parseInt(lines.get(h+1)));
                        }
                    }
                    
                    for(int i=10;i<1000;i+=10){
                    if(hm.containsKey(i)){
                        Daten alt=hm.get(i);
                        if(alt.abstand>abstand){
                            neu.abstand=abstand;
                            hm.remove(i);
                            hm.put(i, neu);
                        }
                    }else{
                        neu.abstand=abstand;
                        hm.put(i, neu);
                    }
                }
                binarGA=trinarAddieren(binarGA, binarGG, gewichtsadd, gegengewicht);
            }
            binarGG=binarAddieren(binarGG, gegengewicht);
        }
    }

    private char[] binarAddieren(char[] binar, long davor) {
        boolean aktiv=false;
        long neu = 0;
        for (int i = 0; i < binar.length; i++) {
            if (binar[i] == '0') {
                binar[i] = '1';
                aktiv=true;
                for (int h = (i - 1); h >= 0; h--) {
                    binar[h] = '0';
                }
                break;
            }
        }

        if(!aktiv){
            binar[0]='#';
            return binar;
        }

        for (int j = 0; j < binar.length; j++) {
            if (binar[j] == '1') {
                neu += Long.parseLong(lines.get(j + 1));
            }
        }

        if (neu <= davor) {
            return binarAddieren(binar, davor);
        } else {
            return binar;
        }
    }

    private char[] trinarAddieren(char[] binar, char[] vorlage, long davor, long gegengewicht) {
        long neu = 0;
        boolean aktiv = false;
        int index = 0;

        for (int i = 0; i < vorlage.length; i++) {
            if (vorlage[i] == '1') {
                index = i;
            }
        }

        for (int i = 0; i < index; i++) {
            if (vorlage[i] == '0' && binar[i] == '0') {
                binar[i] = '1';
                for (int h = (i - 1); h >= 0; h--) {
                    binar[h] = '0';
                }
                aktiv = true;
                break;
            }
        }

        for (int j = 0; j < binar.length; j++) {
            if (binar[j] == '1') {
                neu += Long.parseLong(lines.get(j + 1));
            }
        }

        if ((!aktiv)||(neu>=gegengewicht)) {
            binar[0] = '#';
            return binar;
        }

        if (neu <= davor) {
            return trinarAddieren(binar, vorlage, davor, gegengewicht);
        } else {
            return binar;
        }
    }
}
