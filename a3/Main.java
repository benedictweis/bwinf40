public class Main {
    public static void main(String[] args) {
        try{
            if(args[1].equals("1")){
                new Wortsuche(args[0]).level1();
            } else if(args[1].equals("2")){
                new Wortsuche(args[0]).level2();
            } else if(args[1].equals("3")){
                new Wortsuche(args[0]).level3();
                        }
        } catch(Exception e){
            System.out.println("Falsche Eingabe");
        }

        
    }
}
