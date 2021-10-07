import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String finalSolution[];
    String straight[];
    String parallel[];
    String fileName = "parkplatz1.txt";
    /**
     *  fills the straight- & parallel-car arrays with the content of the .txt file   
     **/
    public main(int fileIndex)
    {   
        fileName="parkplatz"+fileIndex+".txt";
        String visualizeStraight = "";
        String visualizeParallel = "";
        /* Reading File */
        File file = new File(fileName);
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        /* Getting each char, line by line  */
        String firstln[]=lines.get(0).split(" ");
        String thirdln[]=lines.get(2).split(" ");
        /* Filling array with the straight-cars */
        for(int i=0;i<alphabet.length;i++){
            if(alphabet[i].equalsIgnoreCase(firstln[1])){
                straight=new String[i+1];
                parallel=new String[i+1];
                for(int j=0;j<straight.length;j++){
                    straight[j]=alphabet[j];
                }
            }
        }
        /* Filling array with the parallel-cars */
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
        }

        for(int i = 0; i<straight.length; i++){
            visualizeStraight += straight[i]+" ";
        }

        for(int i = 0; i<parallel.length; i++){
            if(parallel[i]!=null){
                visualizeParallel += parallel[i]+" ";
            } else {
                visualizeParallel += "# ";
            }
        }
        finalSolution=new String[straight.length];
        // Visualization of the two arrays for better understanding of the solution
        System.out.println("Searching solution of: \""+fileName+"\" ☺\n");
        System.out.println("straight cars: \t"+visualizeStraight);
        System.out.println("parallel cars: \t"+visualizeParallel);
    }

    /**
     *  goes through all straight cars and calls the "findPath" function on them
     **/
    public void AutosAusparken()
    {   
        System.out.println("\n\n"+"Solution: \n");
        for(int straightIndex=0;straightIndex<straight.length;straightIndex++){
            findPath(straightIndex);
            resetCars();
            System.out.println(finalSolution[straightIndex]);
        }
    }

    /**
     *  moves the selected parallel car one or two positions to the right
     *  index: location of the car
     *  distance: how many positions the car shall be moved
     *  parallelIndex: to which car the operations should be assosiated with
     **/
    public boolean moveDirection(int parallelIndex, int distance, int straightIndex, int direction){
        /* Returncodes: false-> error, true->done */
        // 0 1 0 1 überschreibt R
        int occupiedDistanceToCar = 0;
        int secondChar=calcSecondChar(parallelIndex);
        int freePath=calcFreeSpace(parallelIndex)[direction];
        try{
            String parallelName = parallel[parallelIndex]; 
            if((canMove(parallelIndex, distance, direction))&&
            (secondChar==0)&&   //second char is to the left
            (distance==1)&&
            (freePath>=distance)){      //car shall be moved one position
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                return true;
            }
            else if((canMove(parallelIndex, distance, direction))&&
            (secondChar==0)&&   //second char is to the left
            (distance==1)&&
            (freePath<distance)){     //car shall be moved one position
                if(!moveDirection(parallelIndex+1, distance, straightIndex, direction)) {
                    return false;
                }
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                return true;
            }
            if((canMove(parallelIndex, distance, direction))&&
            (secondChar==1)&&  //second char is to the right
            (distance==1)&&
            (freePath>=distance)){
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                return true;
            }
            else if((canMove(parallelIndex, distance, direction))&&
            (secondChar==1)&&  //second char is to the right
            (distance==1)&&
            (freePath<distance)){
                if(!moveDirection(parallelIndex+2, distance, straightIndex, direction)) {
                    return false;
                }
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                return true;
            }
            if((canMove(parallelIndex, distance, direction))&&
            (secondChar==0)&&  //second char is to the left
            (distance==2)&&
            (freePath<distance)){   //car shall be moved two positions 
                //freeSpace machen
                int newSpace=calcFreeSpace(parallelIndex+secondChar+distance)[direction];
                //0 or 1 or 2
                occupiedDistanceToCar=occupiedDistanceToCar(parallelIndex, 1, 0);
                if((occupiedDistanceToCar != -1)&&
                (!moveDirection(parallelIndex+(1+occupiedDistanceToCar), distance-occupiedDistanceToCar, straightIndex, direction))) {
                    return false;
                }
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                return true;
            }
            else if((canMove(parallelIndex, distance, direction))&&
            (secondChar==1)&& //second char is to the right
            (distance==2)&&
            (freePath<distance)){
                occupiedDistanceToCar=occupiedDistanceToCar(parallelIndex, 1, 1);
                if((occupiedDistanceToCar!= -1)&&
                (!moveDirection(parallelIndex+(2+occupiedDistanceToCar), distance-occupiedDistanceToCar, straightIndex, direction))){
                    return false;
                }
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                return true;
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  resets the parallel cars to their start position
     **/
    public void resetCars(){
        for(int i=0;i<parallel.length;i++){ //overwriting the array with "null"
            parallel[i]=null;
        }
        for(int i=2;i<lines.size();i++){    //refilling it with the data from the .txt file
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
    }

    public void calcSolution(int parallelIndex, int distance, int straightIndex, int direction){
        String[] directions= {"left","right"};
        if(direction==0){
            parallelIndex-=distance;
        }
        else {
            parallelIndex+=distance;
        }
        if(finalSolution[straightIndex]==null){
            finalSolution[straightIndex]=straight[straightIndex]+": "+parallel[parallelIndex]+" "+distance+" "+directions[direction];
        }
        else if(finalSolution[straightIndex]!=null){
            finalSolution[straightIndex]+=", "+parallel[parallelIndex]+" "+distance+" "+directions[direction];
        }
    }

    public void calcMovement(int parallelIndex, int distance, int direction){
        String parallelName=parallel[parallelIndex];
        int secondChar=calcSecondChar(parallelIndex);
        if((distance==1)&&
        (direction==1)){
            parallel[parallelIndex+(1+secondChar)]=parallelName;
            parallel[parallelIndex-(1-secondChar)]=null;
        }
        else if((distance==2)&&
        (direction==1)){
            parallel[parallelIndex+(2+secondChar)]=parallelName;
            parallel[parallelIndex+(1+secondChar)]=parallelName;
            parallel[parallelIndex+secondChar]=null;
            parallel[parallelIndex-(1-secondChar)]=null;
        }
        else if((distance==1)&&
        (direction==0)){
            parallel[parallelIndex-(2-secondChar)]=parallelName;
            parallel[parallelIndex+(secondChar)]=null;
        }
        else if((distance==2)&&
        (direction==0)){
            parallel[parallelIndex-(3-secondChar)]=parallelName;
            parallel[parallelIndex-(2-secondChar)]=parallelName;
            parallel[parallelIndex-(1-secondChar)]=null;
            parallel[parallelIndex+secondChar]=null;
        }
    }

    public int occupiedDistanceToCar(int parallelIndex,int direction, int secondChar){
        //TODO kann man zsm fassen mit dir0=-1
        if((direction==1)&&
        ((parallelIndex+(2+secondChar))<=parallel.length)){
            if(parallel[parallelIndex+(2+secondChar)]==null){
                return -1;
            }
            if(parallel[parallelIndex+(1+secondChar)]!=null){
                return 0;
            }
            return 1;
        }
        else if((direction==0)&&
        ((parallelIndex-(2+secondChar))>=0)){
            if(parallel[parallelIndex-(2+secondChar)]==null){
                return -1;
            }
            if(parallel[parallelIndex-(1+secondChar)]!=null){
                return 0;
            }
            return 1;
        }
        return 0;
    }

    public int calcParallelLimit(int parallelIndex, int limit){
        /* Returncodes: -1*limit -> outside left from array; limit -> outside right from array; 0 -> inside of array */
        if(parallelIndex-limit<0){
            return -limit;
        }
        else if(parallelIndex+limit>=parallel.length){
            return limit;
        }
        return 0;
    }

    public int calcSecondChar(int parallelIndex){
        //Returncodes: 0-> secondChar is left; 1-> secondChar is right
        int limit=calcParallelLimit(parallelIndex, 1);
        if(limit==1){   //
            return 0;
        }
        else if(limit== -1){    //
            return 1;
        }
        else{
            if(parallel[parallelIndex]==parallel[parallelIndex+1]){
                return 1;
            }
            return 0;
        }
    }

    public boolean canMove(int parallelIndex, int distance, int direction){
        int limit=calcParallelLimit(parallelIndex, distance);
        if((limit != distance && direction==1)||(limit != -distance && direction==0)){
            return true;
        }
        return false;
    }

    public int[] calcFreeSpace(int parallelIndex){
        int[] freeSpace={-1, -1};
        int secondChar=calcSecondChar(parallelIndex);
        if(parallelIndex<0||parallelIndex>=parallel.length){
            return freeSpace;
        }
        String parallelName=parallel[parallelIndex];
        for(int R=parallelIndex;R<parallel.length;R++){
            if((R==parallel.length-1)&&(parallel[R]==null)){
                freeSpace[1]=R-parallelIndex;
                break;
            }
            else if((parallel[R]!=null)&&(parallel[R]!=parallelName)){
                freeSpace[1]=R-parallelIndex-1;
                break;
            }
            freeSpace[1]=0;
        }
        for(int L=parallelIndex;L>=0;L--){
            if((L==0)&&(parallel[L]==null)){
                freeSpace[0]=parallelIndex;
                break;
            }
            else if((parallel[L]!=null)&&(parallel[L]!=parallelName)){
                freeSpace[0]=parallelIndex-L-1;
                break;
            }
            freeSpace[0]=0;
        }
        if((secondChar==1)&&(freeSpace[1]!=0)){
            freeSpace[1]--;
        }
        else if((secondChar==0)&&(freeSpace[0]!=0)){
            freeSpace[0]--;
        }
        return freeSpace;
    }
    
    /**
     *  chooses the optimal way to move out the selected car
     *  index: location of the straight car the shall be moved out
     **/
    public boolean findPath(int straightIndex){
        //TODO p4: B verschiebt r 2 mal und kein q soll mD 1 2 1 1 funct aber
        String parallelName=parallel[straightIndex];
        int secondChar=calcSecondChar(straightIndex);
        int[] freeSpace=calcFreeSpace(straightIndex);
        int movingDirection=freeSpace[1]>freeSpace[0] ? 1 : 0;
        //calcMoveDir
        if(secondChar==1&&freeSpace[1]>0){
            movingDirection=1;
        }
        else if(secondChar==0&&freeSpace[0]>0){
            movingDirection=0;
        }
        else if(freeSpace[1]==0&&freeSpace[0]==0){
            //hier auf autos nebendran gucken
            int rightSpace=calcFreeSpace(straightIndex+secondChar+1)[1];
            int leftSpace=calcFreeSpace(straightIndex-1-(1-secondChar))[0];
            if(rightSpace!= -1&&rightSpace!=0){
                movingDirection=1;
            }
            else if(leftSpace!= -1&&leftSpace!=0){
                movingDirection=0;
            }
            //hier überprüfen ob eins -1 -1 hat und welches platz hat, 0 x oder x 0
        }
        else if(freeSpace[1]==freeSpace[0]){
            movingDirection=secondChar;
        }
        try{
            if(parallel[straightIndex]==null){  //exit path is empty
                finalSolution[straightIndex]=straight[straightIndex]+": ";
            }
            else{
                if((movingDirection==1)){ //second char is to the left and free space to the left side
                    if(!moveDirection(straightIndex, 2-secondChar, straightIndex, movingDirection)){
                        return false;
                    }
                    return true;
                }
                else if((movingDirection==0)){
                    if(!moveDirection(straightIndex, 1+secondChar, straightIndex, movingDirection)){
                        return false;
                    }
                    return true;
                }
                //difference dir1/0 ist distance
                // einfach ein movingDir berechnen und dann verschiebenlassen
            }
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}