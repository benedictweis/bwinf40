package a4;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test void testRandomStuff(){
        int[] dice = new int[]{1,2};
        Player test = new Player(dice, 0);
        for (int index = 0; index < 10; index++) {
            System.out.println(test.rollDice());
        }
    }

    @Test void testPullingPieceOut(){
        int[] dice = new int[]{6};
        Player test = new Player(dice, 0);
        for (int index = 0; index < 3; index++) {
            System.out.println(test.takeTurn());
        }
        for (int piece : test.pieces){
            System.out.print(piece+" ");
        }
    }

    @Test void testTenTurns(){
        int[] dice = new int[]{2,4,6};
        Player test = new Player(dice, 0);
        for (int index = 0; index < 10; index++) {
            System.out.println(test.takeTurn());
        }
        for (int piece : test.pieces){
            System.out.print(piece+" ");
        }
    }
}