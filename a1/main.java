import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String solution[]=new String[alphabet.length];
    String straight[];
    String parallel[];
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
                straight=new String[i+1];
                parallel=new String[i+1];
                for(int j=0;j<straight.length;j++){
                    straight[j]=alphabet[j];
                }
            }
        }
        /* Filling array with the sideways-cars */
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
    }
    public void AutosAusparken()
    {   
        for(int i=0;i<straight.length;i++){
            if(straight[i]==null){
                break;
            }
            solution[i]=straight[i]+": ";
            moveOut(i);
            resetparallel();
        }
        printResult();
    }
    public int moveRight(int index){
        /* Returncodes: -1-> error, 0->other car is blocking, 1->done */
        try{
            String parallelName = parallel[index];
            if(parallelName==null){
                return -1;
            }
            if((index - 1)<0){
            }
            else if(parallel[index-1]==parallelName){
                if(parallel[index+1]==null){
                    parallel[index+1]=parallelName;
                    parallel[index-1]=null;
                    return 1;
                }
                else if(parallel[index+1]!=null&&parallel[index+1]!=parallelName){
                    if(moveRight(index+1)== -1) {
                        return -1;
                    }
                    parallel[index+1] = parallelName;
                    parallel[index-1] = null;
                    return 1;
                }
            }
            if((index + 2)>parallel.length){
            }
            else if(parallel[index+1]==parallelName){
                if(parallel[index+2]==null){
                    parallel[index + 2]=parallelName;
                    parallel[index]=null;
                    return 1;
                }
                else if(parallel[index+2]!=null&&parallel[index+2]!=parallelName){
                    if(moveRight(index+2)== -1) {
                        return -1;
                    }
                    parallel[index + 2]=parallelName;
                    parallel[index]=null;
                    return 1;
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
            String parallelName=parallel[index];
            if(parallelName==null){
                return -1;
            }
            if((index - 2)<0){      
            }
            else if(parallel[index - 1]==parallelName){
                if(parallel[index-2]==null){
                    parallel[index - 2]=parallelName;
                    parallel[index]=null;
                    return 1;
                }
                else if(parallel[index-2]!=null&&parallel[index-2]!=parallelName){
                    if(moveLeft(index-2)== -1) {
                        return -1;
                    }
                        parallel[index-2] = parallelName;
                        parallel[index] = null;
                        return 1;
                }
            }
            if((index + 1)>parallel.length){
            }
            else if(parallel[index + 1]==parallelName){
                if(parallel[index-1]==null){
                    parallel[index - 1]=parallelName;
                    parallel[index + 1]=null;
                    return 1;
                }
                else if(parallel[index-1]!=null&&parallel[index-1]!=parallelName){
                    if(moveLeft(index-1)== -1) {
                        return -1;
                    }
                    parallel[index-1] = parallelName;
                    parallel[index+1] = null;
                    return 1;
                }
            }
            return -1;
        }
        catch(Exception e){
            return -1;
        }
    }
    public void resetparallel(){
        for(int i=0;i<parallel.length;i++){
            parallel[i]=null;
        }
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
    }
    public void printResult(){
        for(int x=0;x<straight.length;x++){
            if(solution[x]==null){
                    break;
                }
                else{
                    System.out.println(solution[x]);
                }
        }
    }
    public void moveOut(int index){
        String parallelName=parallel[index];
        try{
            if(parallel[index]==null){
            }
            else{
                if((index - 1)<0){
                    return;
                }
                else if(parallel[index - 1]==parallelName){
                    if((index-2)<0){
                    }
                    else if(parallel[index-2]==null){
                        moveLeft(index);
                        solution[index]+=parallelName+" 1 left";
                        return;
                    }
                    if((index+2)>parallel.length){
                    }
                    else if(parallel[index+1]==null&&parallel[index+2]==null){
                        moveRight(index);
                        moveRight(index);
                        solution[index]+=parallelName+" 2 right";
                        return;
                    }
                }
                if((index + 1)>=parallel.length){
                }
                else if(parallel[index + 1]==parallelName){
                    if((index+2)>parallel.length){
                    }
                    else if(parallel[index+2]==null){
                        moveRight(index);
                        solution[index]+=parallelName+" 1 right";
                        return;
                    }
                    if((index-2)<0){
                    }
                    else if(parallel[index-1]==null&&parallel[index-2]==null){
                        moveLeft(index);
                        moveLeft(index);
                        solution[index]+=parallelName+" 2 left";
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
