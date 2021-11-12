import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
//CODE REVIEW: java.util.Arrays is never used and can be removed
import java.util.Arrays;

public class SchiebeParkplatz {
    ParallelAuto parallelAuto;
    String alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines = new ArrayList<String>();
    String finalSolution[], straight[], parallel[];
    String selectSolution[] = new String[2];
    int iterations[] = { 0, 0 };
    String visualizeStraight = "";
    String visualizeParallel = "";

    /**
     * fills the straight- & parallel-car arrays with the content of the .txt file
     * and doing some visualization stuff to make the solution more human readable
     **/
    public SchiebeParkplatz(String filePath) {
        // Reading File
        try {
            File file = new File(filePath);
            // CODE REVIEW: Resource leak: 'scanner' is never closed
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Invalid filename");
            return;
        }
        // Filling array with the straight-cars
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].equalsIgnoreCase(lines.get(0).split(" ")[1])) {
                straight = new String[i + 1];
                parallel = new String[i + 1];
                for (int j = 0; j < straight.length; j++) {
                    straight[j] = alphabet[j];
                    visualizeStraight += alphabet[j] + " ";
                }
            }
        }
        // Filling array with the parallel-cars
        for (int i = 2; i < lines.size(); i++) {
            String parallelln[] = lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1]) + 1] = parallelln[0];
            parallel[Integer.parseInt(parallelln[1])] = parallelln[0];
        }
        for (int i = 0; i < parallel.length; i++) {
            if (parallel[i] != null) {
                visualizeParallel += parallel[i] + " ";
            } else {
                visualizeParallel += "# ";
            }
        }
        // copying the parallel cars to the other method
        parallelAuto = new ParallelAuto(parallel);
        finalSolution = new String[straight.length];
        // Visualization of the two arrays for better understanding of the solution
        visualization(filePath, visualizeStraight, visualizeParallel);
        if (canSolve() == 0) {
            System.out.println(
                    "\nNote:\nBased on the composition of the parallel cars, no Solution for the straight cars exist");
        } else {
            autosAusparken();
        }
    }

    protected void autosAusparken() {
        if (canSolve() == 0.5) {
            System.out.println(
                    "\nNote:\nBased on the composition of the parallel cars, not all of the straight cars have a Solution");
            System.out.println("\n\n" + "Solution: \n");
        } else if (canSolve() == 1) {
            System.out.println("\n\n" + "Solution: \n");
        }
        for (int straightIndex = 0; straightIndex < straight.length; straightIndex++) {
            if (selectShorterPath(straightIndex)) {
                System.out.println(finalSolution[straightIndex]);
            } else {
                System.out.println(straight[straightIndex] + ": No solution for this car");
            }
            resetParallelCars();
        }
    }

    /**
     * moves the selected parallel car one or two positions to the right index:
     * location of the car distance: how many positions the car shall be moved
     * parallelIndex: to which car the operations should be associated with
     **/
    private boolean moveParallelCar(int parallelIndex, int distance, int straightIndex, int direction) {
        int secondChar = parallelAuto.calcSecondChar(parallelIndex);
        int freePath = parallelAuto.calcFreeSpace(parallelIndex)[direction];
        boolean noArrayBoundaries = parallelAuto.noArrayBoundaries(parallelIndex, distance)[direction];
        try {
            if ((noArrayBoundaries) && (freePath >= distance)) {
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                iterations[direction] += distance;
                return true;
            } else if ((noArrayBoundaries) && (freePath < distance)) {
                int newParallelIndex = (parallelIndex - (2 - secondChar) - freePath) * (1 - direction)
                        + (parallelIndex + secondChar + 1 + freePath) * direction;
                int newDistance = 1;
                if (distance == 2) {
                    newDistance = 2;
                    newDistance -= freePath;
                }
                if (!moveParallelCar(newParallelIndex, newDistance, straightIndex, direction)) {
                    return false;
                }
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                iterations[direction] += distance;
                return true;
            }
            return false;
        } catch (Exception e) {
            iterations[direction] = 0;
            return false;
        }
    }

    /**
     * resets the parallel cars to their original position
     **/
    private void resetParallelCars() {
        for (int i = 0; i < parallel.length; i++) { // overwriting the array with "null"
            parallel[i] = null;
        }
        for (int i = 2; i < lines.size(); i++) { // refilling it with the data from the .txt file
            String parallelln[] = lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])] = parallelln[0];
            parallel[Integer.parseInt(parallelln[1]) + 1] = parallelln[0];
        }
    }

    // CODE REVIEW: isn't this more like "printSolution"?
    private void calcSolution(int parallelIndex, int distance, int straightIndex, int direction) {
        String[] directions = { "left", "right" };
        if (direction == 0) {
            parallelIndex -= distance;
        } else {
            parallelIndex += distance;
        }
        if (selectSolution[direction] == null) {
            selectSolution[direction] = straight[straightIndex] + ": " + parallel[parallelIndex] + " " + distance + " "
                    + directions[direction];
        } else if (selectSolution[direction] != null) {
            selectSolution[direction] += ", " + parallel[parallelIndex] + " " + distance + " " + directions[direction];
        }
    }

    private void calcMovement(int parallelIndex, int distance, int direction) {
        String parallelName = parallel[parallelIndex];
        int secondChar = parallelAuto.calcSecondChar(parallelIndex);
        // CODE REVIEW: please add a comment here, I have no idea what the part below
        // this is supposed to do
        parallel[parallelIndex + ((2 + secondChar) * direction - (3 - secondChar) * (1 - direction)) * (distance - 1)
                + ((1 + secondChar) * direction - (2 - secondChar) * (1 - direction)) * (2 - distance)] = parallelName;
        parallel[parallelIndex + ((1 + secondChar) * direction - (2 - secondChar) * (1 - direction)) * (distance - 1)
                + ((1 + secondChar) * direction - (2 - secondChar) * (1 - direction)) * (2 - distance)] = parallelName;
        parallel[parallelIndex + (secondChar * direction - (1 - secondChar) * (1 - direction)) * (distance - 1)
                - ((1 - secondChar) * direction + secondChar * (1 - direction)) * (2 - distance)] = null;
        parallel[parallelIndex - ((1 - secondChar) * direction + secondChar * (1 - direction)) * (distance - 1)
                - ((1 - secondChar) * direction + secondChar * (1 - direction)) * (2 - distance)] = null;

        // TODO: remove commented unnecessary code before finalizing th project
        /*
         * if(distance==1){
         * parallel[parallelIndex+(1+secondChar)*direction-(2-secondChar)*(1-direction)]
         * =parallelName;
         * parallel[parallelIndex-(1-secondChar)*direction+secondChar*(1-direction)]=
         * null; } else if(distance==2){
         * parallel[parallelIndex+(2+secondChar)*direction-(3-secondChar)*(1-direction)]
         * =parallelName;
         * parallel[parallelIndex+(1+secondChar)*direction-(2-secondChar)*(1-direction)]
         * =parallelName;
         * parallel[parallelIndex+secondChar*direction-(1-secondChar)*(1-direction)]=
         * null;
         * parallel[parallelIndex-(1-secondChar)*direction+secondChar*(1-direction)]=
         * null; }
         */
    }

    private double canSolve() {
        int j = 0;
        for (int i = 0; i < parallel.length; i++) {
            if (parallel[i] == null) {
                j++;
            }
        }
        if (j == 0) { // nicht lösbar
            return 0;
        } else if (j == 1) { // nur die hälfte lösbar
            return 0.5;
        } else if (j >= 2) { // sollte lösbar sein
            return 1;
        }
        return -1;
    }

    private void visualization(String filePath, String visualizeStraight, String visualizeParallel) {
        String spacer = "";
        for (int i = 0; i < 60; i++) {
            spacer += "*";
        }
        System.out.println("\n" + spacer + "\n");
        System.out.println("Searching solution of: \"" + filePath + "\" ☺\n");
        System.out.println("straight cars: \t" + visualizeStraight);
        System.out.println("parallel cars: \t" + visualizeParallel);
    }

    private boolean selectShorterPath(int straightIndex) {
        iterations[1] = 0;
        iterations[0] = 0;
        selectSolution[0] = null;
        selectSolution[1] = null;
        int secondChar = parallelAuto.calcSecondChar(straightIndex);
        if (parallel[straightIndex] == null) { // exit path is empty
            finalSolution[straightIndex] = straight[straightIndex] + ": ";
            return true;
        } else {
            for (int i = 1; i >= 0; i--) {// for-loop to get both directions
                moveParallelCar(straightIndex, (1 + secondChar) * (1 - i) + (2 - secondChar) * i, straightIndex, i);
                resetParallelCars();
            }
            // CODE REVIEW: please add a comment here, I once again have no idea what the
            // part below this is supposed to do
            if (selectSolution[1] == null && selectSolution[0] == null) {
                return false;
            } else if (iterations[0] == 0 || selectSolution[0] == null) {
                finalSolution[straightIndex] = selectSolution[1];
            } else if (iterations[1] == 0 || selectSolution[1] == null) {
                finalSolution[straightIndex] = selectSolution[0];
            } else if (iterations[1] <= iterations[0]) {
                finalSolution[straightIndex] = selectSolution[1];
            } else if (iterations[1] > iterations[0]) {
                finalSolution[straightIndex] = selectSolution[0];
            }
            return true;
        }
    }
}