public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            new Wortsuche("beispiele/worte1.txt").level1();
        }

        if(args[0] == "all"){
            for(int i = 0; i < 5; i++){
                    new Wortsuche("beispiele/worte" + i + ".txt").level1();
                    new Wortsuche("beispiele/worte" + i + ".txt").level2();
                    new Wortsuche("beispiele/worte" + i + ".txt").level3();
            }
        }
        
        if (args[1].equals("1")) {
            new Wortsuche(args[0]).level1();
        } else if (args[1].equals("2")) {
            new Wortsuche(args[0]).level2();
        } else if (args[1].equals("3")) {
            new Wortsuche(args[0]).level3();
        }

    }
}
