import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main{
    public static void main(String[]args){
        String fileName="./examples/parkplatz"+args[0]+".txt";
        for(;;){
            File file=new File(fileName);
            if(file==null){
            }else {
                new SchiebeParkplatz(fileName);   
            }
        }
    }
}