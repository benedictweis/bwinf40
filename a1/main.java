public class Main {
    public static void main(String[] args) {
        String[] all;
        //If no Parameters are given, the first example is used
        if(args.length==0){
            System.out.println("ERROR: Keine Parameter gefunden");
            new SchiebeParkplatz("./examples/parkplatz0.txt");
            return;
        }
        //If the parameter given is "all", all examples are solved
        //else: the parameters are given to
        if (args[0].equals("all")) {
            all= new String[10];
            for (int i = 0; i < 10; i++) {
                all[i] = "./examples/parkplatz" + String.valueOf(i) + ".txt";
            }
            for (int i = 0; i < all.length; i++) {
                String fileName = all[i];
                new SchiebeParkplatz(fileName);
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                String fileName = args[i];
                new SchiebeParkplatz(fileName);
            }
        }
    }
}