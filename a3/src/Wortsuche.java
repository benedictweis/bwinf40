import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Wortsuche {

    char[][] Gitter;
    ArrayList<String> lines, original;
    String[] parts;
    int zeile;
    int spalte;
    int count;
    int count2;
    int row;
    int coloum;
    String worte;
    String level = "";

    public Wortsuche(String worte) {
        einlesen(worte);
        this.worte = worte;
        original = (ArrayList) lines.clone();
        zeile = Integer.parseInt(parts[0]);
        spalte = Integer.parseInt(parts[1]);
        Gitter = new char[zeile][spalte];

        reset();
    }

    private void einlesen(String input) {
        File file = new File(input);
        lines = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        parts = lines.get(0).split(" ");
    }

    private void zeigen() {
        System.out.println("\n\n\nWortsuche \"" + worte + "\" mit dem Level " + level);

        count = 0;
        count2 = 0;

        for (int o = 0; o < zeile + 1; o++) {
            if (count2 == 10) {
                count2 = 0;
            }
            System.out.print(count2);
            System.out.print(" ");
            count2++;
        }

        for (int y = 0; y < spalte; y++) {
            System.out.println(" ");
            count++;
            if (count == 10) {
                count = 0;
            }
            System.out.print(count);
            System.out.print(" ");
            for (int x = 0; x < zeile; x++) {
                System.out.print(Gitter[x][y]);
                System.out.print(" ");
            }
        }
    }

    private char randomBuchstaben() {
        Random random = new Random();
        char randomizedCharacter = (char) (random.nextInt(26) + 'a');
        return Character.toUpperCase(randomizedCharacter);
    }

    private void randomAuffüllen() {
        for (int y = 0; y < spalte; y++) {
            for (int x = 0; x < zeile; x++) {
                if (Gitter[x][y] == '#') {
                    Gitter[x][y] = randomBuchstaben();
                }
            }
        }
    }

    private boolean vertEinfg(String wort) {
        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        row = random.nextInt(zeile);
        coloum = random.nextInt((spalte - bst.length + 1));
        while (passt) {
            for (int i = 0; i < bst.length; i++) {
                if (Gitter[row][coloum + i] == '#' || Gitter[row][coloum + i] == bst[i]) {
                    passt = true;
                } else {
                    passt = false;
                    break;
                }
            }
            break;
        }

        if (passt == true) {
            for (int i = 0; i < bst.length; i++) {
                Gitter[row][coloum + i] = bst[i];
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean vertEinfgEinf(String wort) {
        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        row = random.nextInt(zeile);
        coloum = random.nextInt((spalte - bst.length + 1));
        while (passt) {
            for (int i = 0; i < bst.length; i++) {
                if (Gitter[row][coloum + i] == '#' || Gitter[row][coloum + i] == bst[i]) {
                    passt = true;
                } else {
                    passt = false;
                    break;
                }
            }
            break;
        }

        if (passt == true) {
            for (int i = 0; i < bst.length; i++) {
                Gitter[row][coloum + i] = bst[i];
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean horiEinfgEinf(String wort) {

        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        row = random.nextInt((zeile - bst.length + 1));
        coloum = random.nextInt(spalte);
        while (passt) {
            for (int i = 0; i < bst.length; i++) {
                if (Gitter[row + i][coloum] == '#' || Gitter[row + i][coloum] == bst[i]) {
                    passt = true;
                } else {
                    passt = false;
                    break;
                }
            }
            break;
        }

        if (passt == true) {
            for (int i = 0; i < bst.length; i++) {
                Gitter[row + i][coloum] = bst[i];
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean horiEinfg(String wort) {
        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        row = random.nextInt((zeile - bst.length + 1));
        coloum = random.nextInt(spalte);
        while (passt) {
            for (int i = 0; i < bst.length; i++) {
                if (Gitter[row + i][coloum] == '#' || Gitter[row + i][coloum] == bst[i]) {
                    passt = true;
                } else {
                    passt = false;
                    break;
                }
            }
            break;
        }

        if (passt == true) {
            for (int i = 0; i < bst.length; i++) {
                Gitter[row + i][coloum] = bst[i];
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean diagonalEinfg(String wort) {
        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        if (random.nextInt(1) == 0) {
            row = random.nextInt((zeile - bst.length + 1));
            coloum = random.nextInt((spalte - bst.length + 1));
            while (passt) {
                for (int i = 0; i < bst.length; i++) {
                    if (Gitter[row + i][coloum + i] == '#' || Gitter[row + i][coloum + i] == bst[i]) {
                        passt = true;
                    } else {
                        passt = false;
                        break;
                    }
                }
                break;
            }

            if (passt == true) {
                for (int i = 0; i < bst.length; i++) {
                    Gitter[row + i][coloum + i] = bst[i];
                }
                return true;
            } else {
                return false;
            }
        } else {
            row = 10 - (random.nextInt(zeile - bst.length + 1));
            coloum = random.nextInt(spalte - bst.length + 1);
            while (passt) {
                for (int i = 0; i < bst.length; i++) {
                    if (Gitter[row - i][coloum + i] == '#' || Gitter[row - i][coloum + i] == bst[i]) {
                        passt = true;
                    } else {
                        passt = false;
                        break;
                    }
                }
                break;
            }

            if (passt == true) {
                for (int i = 0; i < bst.length; i++) {
                    Gitter[row - i][coloum + i] = bst[i];
                }
                return true;
            } else {
                return false;
            }

        }
    }

    private void sortieren() {
        for (int j = 2; j < (Integer.parseInt(lines.get(1)) + 2); j++) {
            int stelle = -1;
            int laenge = 0;
            for (int i = j; i < (Integer.parseInt(lines.get(1)) + 2); i++) {
                if (lines.get(i).length() > laenge) {
                    stelle = i;
                    laenge = lines.get(i).length();
                }
            }
            tauschen(j, stelle);
        }
    }

    private void tauschen(int stelle1, int stelle2) {
        String speicher = lines.get(stelle1);
        lines.set(stelle1, lines.get(stelle2));
        lines.set(stelle2, speicher);
    }

    public void level1() {
        Random random = new Random();
        reset();

        for (int i = 2; i < (Integer.parseInt(lines.get(1)) + 2); i++) {
            for (int j = 0; j < 50; j++) {
                int zufall = random.nextInt(2);
                if (zufall == 0) {
                    if (vertEinfgEinf(lines.get(i)) == true) {
                        break;
                    }
                } else {
                    if (horiEinfgEinf(lines.get(i)) == true) {
                        break;
                    }
                }
            }
        }

        level = "1";
        randomAuffüllen();
        zeigen();
    }

    public void level2() {
        reset();
        Random random = new Random();

        for (int i = 2; i < (Integer.parseInt(lines.get(1)) + 2); i++) {
            for (int j = 0; j < 50; j++) {
                int zufall2 = random.nextInt(2);
                if (zufall2 == 0) {
                    int zufall = random.nextInt(2);
                    if (zufall == 0) {
                        if (vertEinfgEinf(lines.get(i)) == true) {
                            break;
                        }
                    } else if (zufall == 1) {
                        if (horiEinfgEinf(lines.get(i)) == true) {
                            break;
                        }
                    }
                } else {
                    drehen(i);
                    int zufall = random.nextInt(2);
                    if (zufall == 0) {
                        if (vertEinfgEinf(lines.get(i)) == true) {
                            break;
                        }
                    } else if (zufall == 1) {
                        if (horiEinfgEinf(lines.get(i)) == true) {
                            break;
                        }
                    }
                }
            }
        }

        level = "2";
        randomAuffüllen();
        zeigen();
    }

    public void level3() {
        reset();
        Random random = new Random();

        for (int i = 2; i < (Integer.parseInt(lines.get(1)) + 2); i++) {
            for (int j = 0; j < 50; j++) {
                int zufall2 = random.nextInt(2);
                if (zufall2 == 0) {
                    int zufall = random.nextInt(3);
                    if (zufall == 0) {
                        if (vertEinfg(lines.get(i)) == true) {
                            break;
                        }
                    } else if (zufall == 1) {
                        if (horiEinfg(lines.get(i)) == true) {
                            break;
                        }
                    } else {
                        if (diagonalEinfg(lines.get(i)) == true) {
                            break;
                        }
                    }
                } else {
                    drehen(i);
                    int zufall = random.nextInt(3);
                    if (zufall == 0) {
                        if (vertEinfg(lines.get(i)) == true) {
                            break;
                        }
                    } else if (zufall == 1) {
                        if (horiEinfg(lines.get(i)) == true) {
                            break;
                        }
                    } else {
                        if (diagonalEinfg(lines.get(i)) == true) {
                            break;
                        }
                    }
                }
            }
        }

        level = "3";
        randomAuffüllen();
        zeigen();
    }

    private void reset() {
        for (int y = 0; y < spalte; y++) {
            for (int x = 0; x < zeile; x++) {
                Gitter[x][y] = '#';
            }
        }
        lines = (ArrayList<String>) original.clone();
        sortieren();
    }

    private void drehen(int index) {
        String wort = lines.get(index);
        String umgekehrt = new String();
        for (int i = (wort.length() - 1); i >= 0; i--) {
            umgekehrt += wort.charAt(i);
        }
        lines.set(index, umgekehrt);
    }

    public void test1(int anz) {
        for (int i = 0; i < anz; i++) {
            level1();
        }
    }

    public void test2(int anz) {
        for (int i = 0; i < anz; i++) {
            level2();
        }
    }

    public void test3(int anz) {
        for (int i = 0; i < anz; i++) {
            level3();
        }
    }
}