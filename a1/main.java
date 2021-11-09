import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main{
    public static void main(String[]args){
        String[] largs;
        if(args[0].equals("all")){
            largs=new String[10];
            for(int i=0;i<10;i++){
                largs[i]=String.valueOf(i);
            }
        } else{
            largs=args;
        }
        for(int i=0;i<largs.length;i++){
            String fileName="./examples/parkplatz"+largs[i]+".txt";
            File file=new File(largs[i]);
            if(file==null){
            }else {
                new SchiebeParkplatz(fileName);   
            }
        }
    }
}