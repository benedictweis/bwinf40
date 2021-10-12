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
}
