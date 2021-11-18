import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Marktwaage {

    ArrayList<String> lines;
    ArrayList<Long> alleErgebnisseKeys;
    HashMap<Integer, Daten> zehnerSchritte;
    HashMap<Long, Daten> alleErgebnis;
    String[] parts;
    String gewichte;
    char binarGA[];
    char binarGG[];
    long groesstesGewicht;

    public Marktwaage(String input) {
        zehnerSchritte = new HashMap<Integer, Daten>();
        alleErgebnis = new HashMap<Long, Daten>();
        alleErgebnisseKeys = new ArrayList<Long>();
        einlesen(input);
        try {
            binarGG = new char[lines.size() - 1];
            binarGA = new char[lines.size() - 1];
            groesstesGewicht =Long.parseLong(lines.get(lines.size()-1)+1);
        } catch (NegativeArraySizeException e) {
            System.out.println("Datei nicht gefunden");
            System.exit(0);
        }
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

    public void ausgabe() {
        for (int i = 10; i <= 10000; i += 10) {
            String rechts = "";
            String links = "";

            Daten aktuell = zehnerSchritte.get(i);
            if (aktuell.gewicht == i) {
                System.out.println(i + "g ist moeglich");
                for (int h = 0; h < aktuell.rechts.length; h++) {
                    if (aktuell.rechts[h] == '1') {
                        rechts += (lines.get(h + 1) + "    ");
                    }
                }

                for (int h = 0; h < aktuell.links.length; h++) {
                    if (aktuell.links[h] == '1') {
                        links += (lines.get(h + 1) + "    ");
                    }
                }

                System.out.println("linke Seite:\t" + links);
                System.out.println("rechte Seite:\t" + rechts);
                System.out.println();

            } else {
                System.out.println(i + "g ist nicht moeglich");
                System.out.println("nächster Abstand: " + Math.abs((aktuell.gewicht - i)));
            }
            System.out.println();
        }
    }

    public void alleberechnen() {
        long gegengewicht = 0;
        long gewichtsadd = 0;
        for (int i = 0; i < binarGG.length; i++) {
            binarGG[i] = '0';
            binarGA[i] = '0';
        }
        binarGG[0] = '1';

        while (true) {
            if (binarGG[0] == '#') {
                return;
            }
            for (int i = 0; i < binarGA.length; i++) {
                binarGA[i] = '0';
            }
            gewichtsadd = 0;

            gegengewicht = 0;
            for (int i = 0; i < binarGG.length; i++) {
                if (binarGG[i] == '1') {
                    gegengewicht += Long.parseLong(lines.get(i + 1));
                }
            }

            while (true) {
                long gewicht = gegengewicht - gewichtsadd;
                if (gewicht > 10000) {
                    if ((gewicht < groesstesGewicht)) {
                        alleErgebnis.remove(groesstesGewicht);
                        alleErgebnisseKeys.remove(groesstesGewicht);
                        Daten neu = new Daten(gewicht);
                        if (!alleErgebnis.containsKey(gewicht)) {
                            alleErgebnis.put(gewicht, neu);
                            alleErgebnisseKeys.add(gewicht);
                        }
                    }
                } else {
                    Daten neu = new Daten(gewicht);
                    if ((gewicht % 10 == 0) && (gewicht <= 10000)) {
                        neu.links = binarGA.clone();
                        neu.rechts = binarGG.clone();
                    }
                    if (!alleErgebnis.containsKey(gewicht)) {
                        alleErgebnis.put(gewicht, neu);
                        alleErgebnisseKeys.add(gewicht);
                    }
                }
                if (gewicht < groesstesGewicht && gewicht > 10000) {
                    groesstesGewicht = gewicht;
                }

                long neuGA = Long.valueOf(gewichtsadd);
                while (neuGA <= gewichtsadd) {
                    binarGA = trinarAddieren(binarGA, binarGG);
                    neuGA = 0;
                    if (binarGA[0] == '#') {
                        break;
                    }
                    for (int i = 0; i < binarGG.length; i++) {
                        if (binarGA[i] == '1') {
                            neuGA += Long.parseLong(lines.get(i + 1));
                        }
                    }
                }
                if (binarGA[0] == '#') {
                    break;
                }
                gewichtsadd = Long.valueOf(neuGA);
            }
            long neuGG = Long.valueOf(gegengewicht);
            while (neuGG <= gegengewicht) {
                binarGG = binarAddieren(binarGG);
                neuGG = 0;
                if (binarGG[0] == '#') {
                    break;
                }
                for (int i = 0; i < binarGG.length; i++) {
                    if (binarGG[i] == '1') {
                        neuGG += Long.parseLong(lines.get(i + 1));
                    }
                }
            }
            if (binarGG[0] == '#') {
                break;
            }
            gegengewicht = Long.valueOf(neuGG);
        }
    }

    public void umrechnen() {
        Collections.sort(alleErgebnisseKeys);
        for (int i = 10; i <= 10000; i += 10) {
            if (alleErgebnis.containsKey(Long.valueOf(i))) {
                zehnerSchritte.put(i, alleErgebnis.get(Long.valueOf(i)));
            } else {
                int index = 0;
                for (int h = 0; h < alleErgebnisseKeys.size() - 1; h++) {
                    if (i < alleErgebnisseKeys.get(h)) {
                        break;
                    }
                    index = h;
                }
                long abstandK = (i - alleErgebnisseKeys.get(index));
                long abstandG = (alleErgebnisseKeys.get(index + 1) - i);

                if (abstandK <= abstandG) {
                    zehnerSchritte.put(i, alleErgebnis.get(Long.valueOf(alleErgebnisseKeys.get(index))));
                } else {
                    zehnerSchritte.put(i, alleErgebnis.get(Long.valueOf(alleErgebnisseKeys.get(index + 1))));
                }
            }
        }
    }

    private char[] binarAddieren(char[] binar) {
        boolean aktiv = false;
        for (int i = 0; i < binar.length; i++) {
            if (binar[i] == '0') {
                binar[i] = '1';
                aktiv = true;
                for (int h = (i - 1); h >= 0; h--) {
                    binar[h] = '0';
                }
                break;
            }
        }

        if (!aktiv) {
            binar[0] = '#';
        }
        return binar;
    }

    private char[] trinarAddieren(char[] binar, char[] vorlage) {
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

        if (!aktiv) {
            binar[0] = '#';
        }
        return binar;
    }
}
