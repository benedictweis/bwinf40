public class main{
    public static void main(String[]args){
        String[] largs;
        if(args[0].equals("all")){
            largs=new String[10];
            for(int i=0;i<10;i++){
                largs[i]="./examples/parkplatz"+String.valueOf(i)+".txt";
            }
        } else{
            largs=args;
        }
        for(int i=0;i<largs.length;i++){
            String fileName=largs[i];
            new SchiebeParkplatz(fileName);
        }
    }
}