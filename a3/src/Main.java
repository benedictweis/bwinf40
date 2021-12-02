public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            new Wortsuche("beispiele/worte1.txt").level1();
            return;
        }

        if(args[0].equalsIgnoreCase("all")){
            for(int i = 1; i < 6; i++){
                    new Wortsuche("beispiele/worte" + i + ".txt").level1();
                    new Wortsuche("beispiele/worte" + i + ".txt").level2();
                    new Wortsuche("beispiele/worte" + i + ".txt").level3();
            }
            return;
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
