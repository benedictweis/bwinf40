public class ParallelAuto {
    protected String parallel[];

    public ParallelAuto(String[] array) {
        parallel = array;
    }

    protected boolean[] noArrayBoundaries(int parallelIndex, int distance) {
        //Checks if an operatrion on the Array would exeed the Array-Boundaries
        boolean moveLR[] = { true, true };
        if (parallelIndex - distance < 0) {
            moveLR[0] = false;
        }
        if (parallelIndex + distance >= parallel.length) {
            moveLR[1] = false;
        }
        return moveLR;
    }

    protected boolean[] noCarBoundaries(int parallelIndex, int distance) {
        // Basicly the same as "noArrayBoundaries" but also includes other cars in its judgement
        int limit[] = calcFreeSpace(parallelIndex);
        boolean moveLR[] = { false, false };
        if (limit[1] >= distance) {
            moveLR[1] = true;
        }
        if (limit[0] >= distance) {
            moveLR[0] = true;
        }
        return moveLR;
    }

    protected int[] calcFreeSpace(int parallelIndex) {
        //Returns in an array the space a car has to its left and right
        //Index 0 of array is for Left, index 1 for right
        int[] freeSpace = { -1, -1 };
        int secondChar = calcSecondChar(parallelIndex);
        if (parallelIndex < 0 || parallelIndex >= parallel.length) {
            return freeSpace;
        }
        String parallelName = parallel[parallelIndex];
        for (int R = parallelIndex; R < parallel.length; R++) {
            if ((R == parallel.length - 1) && (parallel[R] == null)) {
                freeSpace[1] = R - parallelIndex;
                break;
            } else if ((parallel[R] != null) && (parallel[R] != parallelName)) {
                freeSpace[1] = R - parallelIndex - 1;
                break;
            }
            freeSpace[1] = 0;
        }
        for (int L = parallelIndex; L >= 0; L--) {
            if ((L == 0) && (parallel[L] == null)) {
                freeSpace[0] = parallelIndex;
                break;
            } else if ((parallel[L] != null) && (parallel[L] != parallelName)) {
                freeSpace[0] = parallelIndex - L - 1;
                break;
            }
            freeSpace[0] = 0;
        }
        if ((secondChar == 1) && (freeSpace[1] != 0)) {
            freeSpace[1]--;
        } else if ((secondChar == 0) && (freeSpace[0] != 0)) {
            freeSpace[0]--;
        }
        return freeSpace;
    }

    protected int calcSecondChar(int parallelIndex) {
        //Returns the second char of a parallel car.
        //Return 0 -> left
        //Return 1 -> right
        boolean[] limit = noArrayBoundaries(parallelIndex, 1);
        if (limit[1] == false) {
            return 0;
        } else if (limit[0] == false) {
            return 1;
        } else {
            if (parallel[parallelIndex] == parallel[parallelIndex + 1]) {
                return 1;
            }
            return 0;
        }
    }
}