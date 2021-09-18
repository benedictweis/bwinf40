import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String solution[]=new String[alphabet.length];
    String nf[];
    String qf[];
    public main()
    {
        /* Reading File */
        File file = new File("parkplatz0.txt");
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println(lines);
        /* Getting each char, line by line  */
        String firstln[]=lines.get(0).split(" ");
        String thirdln[]=lines.get(2).split(" ");
        /* Filling array with the normal-cars */
        for(int i=0;i<alphabet.length;i++){
            if(alphabet[i].equalsIgnoreCase(firstln[1])){
                nf=new String[i+1];
                qf=new String[i+1];
                for(int j=0;j<nf.length;j++){
                    nf[j]=alphabet[j];
                }
            }
        }
        /* Filling array with the sideways-cars */
        for(int i=2;i<lines.size();i++){
            String qfln[]=lines.get(i).split(" ");
            qf[Integer.parseInt(qfln[1])]=qfln[0];
            qf[Integer.parseInt(qfln[1])+1]=qfln[0];
        }
    }
    public void AutosAusparken()
    {   
        for(int i=0;i<nf.length;i++){
            if(nf[i]==null){
                break;
            }
            solution[i]=nf[i]+": ";
            moveOut(i);
            resetQF();
        }
        printResult();
    }
    public int moveRight(int index){
        /* Returncodes: -1-> error, 0->other car is blocking, 1->done */
        try{
            String carName = qf[index];
            if(carName==null){
                return -1;
            }
            if((index - 1)<0){
            }
            else if(qf[index-1]==carName){
                if(qf[index+1]==null){
                    qf[index+1]=carName;
                    qf[index-1]=null;
                    return 1;
                }
                else if(qf[index+1]!=null&&qf[index+1]!=carName){
                    return 0;
                }
            }
            if((index + 2)>qf.length){
            }
            else if(qf[index+1]==carName){
                if(qf[index+2]==null){
                    qf[index + 2]=carName;
                    qf[index]=null;
                    return 1;
                }
                else if(qf[index+2]!=null&&qf[index+2]!=carName){
                    return 0;
                }
            }
            return -1;
        }
        catch (Exception e){
            return -1;
        }
    }
    public int moveLeft(int index){
        /* Returncodes: -1-> error, 0->other car is blocking, 1->done */
        try{
            String carName=qf[index];
            if(carName==null){
                return -1;
            }
            if((index - 2)<0){      
            }
            else if(qf[index - 1]==carName){
                if(qf[index-2]==null){
                    qf[index - 2]=carName;
                    qf[index]=null;
                    return 1;
                }
                else if(qf[index-2]!=null&&qf[index-2]!=carName){
                    return 0;
                }
            }
            if((index + 1)>qf.length){
            }
            else if(qf[index + 1]==carName){
                if(qf[index-1]==null){
                    qf[index - 1]=carName;
                    qf[index + 1]=null;
                    return 1;
                }
                else if(qf[index-1]!=null&&qf[index-1]!=carName){
                    return 0;
                }
            }
            return -1;
        }
        catch(Exception e){
            return -1;
        }
    }
    public void resetQF(){
        for(int i=0;i<qf.length;i++){
            qf[i]=null;
        }
        for(int i=2;i<lines.size();i++){
            String qfln[]=lines.get(i).split(" ");
            qf[Integer.parseInt(qfln[1])]=qfln[0];
            qf[Integer.parseInt(qfln[1])+1]=qfln[0];
        }
    }
    public void printResult(){
        for(int x=0;x<nf.length;x++){
            if(solution[x]==null){
                    break;
                }
                else{
                    System.out.println(solution[x]);
                }
        }
    }
    public void moveOut(int index){
        String qfName=qf[index];
        try{
            if(qf[index]==null){
            }
            else{
                if((index - 1)<0){
                    return;
                }
                else if(qf[index - 1]==qfName){
                    if((index-2)<0){
                    }
                    else if(qf[index-2]==null){
                        moveLeft(index);
                        solution[index]+=qfName+" 1 left";
                        return;
                    }
                    if((index+2)>qf.length){
                    }
                    else if(qf[index+1]==null&&qf[index+2]==null){
                        moveRight(index);
                        moveRight(index);
                        solution[index]+=qfName+" 2 right";
                        return;
                    }
                }
                if((index + 1)>=qf.length){
                }
                else if(qf[index + 1]==qfName){
                    if((index+2)>qf.length){
                    }
                    else if(qf[index+2]==null){
                        moveRight(index);
                        solution[index]+=qfName+" 1 right";
                        return;
                    }
                    if((index-2)<0){
                    }
                    else if(qf[index-1]==null&&qf[index-2]==null){
                        moveLeft(index);
                        moveLeft(index);
                        solution[index]+=qfName+" 2 left";
                        return;
                    }
                }
            }
        }
        catch(Exception e){
            return;
        }
    }
}
