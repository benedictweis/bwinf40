import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Wortsuche{

    char[][] Gitter;
    ArrayList<String> lines;
    String[] parts;
    int zeile;
    int spalte;
    int count;
    int count2;
    int row;
    int coloum;

    public Wortsuche(String worte){
        einlesen(worte);
        zeile = Integer.parseInt(parts[0]);
        spalte = Integer.parseInt(parts[1]);
        Gitter = new char[zeile][spalte];

        for(int y = 0; y < spalte; y++){
            for(int x = 0; x < zeile; x++){
                Gitter[x][y] = '#';
            }
        }
    }

    private void einlesen(String input){
        File file = new File(input);
        lines = new ArrayList<String>();
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        parts = lines.get(0).split(" ");
    }

    private void zeigen(){
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        count = 0;
        count2 = 0;

        for(int o = 0; o < zeile + 1; o++){
            if(count2 == 10){
                count2 = 0;
            }
            System.out.print(count2);
            System.out.print(" ");
            count2++;
        }

        for(int y = 0; y < spalte; y++){
            System.out.println(" ");
            count++;
            if(count == 10){
                count = 0;
            }
            System.out.print(count);
            System.out.print(" ");
            for(int x = 0; x < zeile; x++){
                System.out.print(Gitter[x][y]);
                System.out.print(" ");
            }
        }
    }

    private char randomBuchstaben(){
        Random random = new Random();
        char randomizedCharacter = (char) (random.nextInt(26) + 'a');
        return Character.toUpperCase(randomizedCharacter);
    }

    private void randomAuffüllen(){
        for(int y = 0; y < spalte; y++){
            for(int x = 0; x < zeile; x++){
                if(Gitter[x][y] == '#'){
                    Gitter[x][y] = randomBuchstaben();
                }
            }
        }
    }

    private void vertfuellen(String wort){
        vertEinfg(wort);
    }

    private void vertEinfg(String wort){
        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        row = random.nextInt(zeile);
        coloum = random.nextInt((spalte - bst.length + 1));
        while(passt){
            for(int i = 0; i < bst.length; i++){
                if(Gitter[row][coloum+i] == '#' || Gitter[row][coloum+i] == bst[i]){
                    passt = true;
                }else{
                    passt = false;
                    break;
                }
            }
            break;
        }

        if(passt == true){
            for(int i = 0;i < bst.length; i++){
                Gitter[row][coloum+i] = bst[i];
            }

        } else{
            horiEinfg(wort);
        }
    }

    private void horifuellen(String wort){
        horiEinfg(wort);
    }

    private void horiEinfg(String wort){
        boolean passt = true;
        char bst[] = wort.toCharArray();
        Random random = new Random();

        row = random.nextInt((zeile - bst.length + 1));
        coloum = random.nextInt(spalte);
        while(passt){
            for(int i = 0; i < bst.length; i++){
                if(Gitter[row+i][coloum] == '#' || Gitter[row+i][coloum] == bst[i]){
                    passt = true;
                }else{
                    passt = false;
                    break;
                }
            }
            break;
        }

        if(passt == true){
            for(int i = 0;i < bst.length; i++){
                Gitter[row+i][coloum] = bst[i];
            }

        } else{
            vertEinfg(wort);
        }
    }

    public void level1(){
        Random random = new Random();
        for(int i = 2; i < (Integer.parseInt(lines.get(1)) + 2); i++){
            int zufall = random.nextInt(2);
            if(zufall == 0){
                vertfuellen(lines.get(i));
            } else if(zufall == 1){
                horifuellen(lines.get(i));
            }
        }

        randomAuffüllen();
        zeigen();
    }

    public void level2(){
        Random random = new Random();
        for(int i = 2; i < (Integer.parseInt(lines.get(1)) + 2); i++){
            int zufall2 = random.nextInt(2);
            if(zufall2==0){
                int zufall = random.nextInt(2);
                if(zufall == 0){
                    vertfuellen(lines.get(i));
                } else if(zufall == 1){
                    horifuellen(lines.get(i));
                }
            }else{
                drehen(i);
                int zufall = random.nextInt(2);
                if(zufall == 0){
                    vertfuellen(lines.get(i));
                } else if(zufall == 1){
                    horifuellen(lines.get(i));
                }
            }
        }
        randomAuffüllen();
        zeigen();
    }

    public void drehen(int index){
        String wort=lines.get(index);
        String umgekehrt=new String();
        for(int i=(wort.length()-1);i>=0;i--){
            umgekehrt += wort.charAt(i);
        }
        lines.set(index, umgekehrt);
    }

    public void test(int anz){
        for(int i=0; i<anz;i++){
            level1();
        }
    }
}
