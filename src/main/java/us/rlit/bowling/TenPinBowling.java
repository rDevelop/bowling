package us.rlit.bowling;

import us.rlit.bowling.alley.Lane;
import us.rlit.bowling.alley.PennyLane;

import java.util.List;

/**
 * TenPinBowling allows any number of bowlers to compete in a bowling game and have the scores printed.
 *
 * From pba.com
 * In an open frame, a game simply gets credit for the number of pins knocked down.
 * In the case of a spare, a slash mark is recorded in a small square
 * in the upper right-hand corner of that frame on the score sheet,
 * and no score is entered until the first ball of the next frame is rolled.
 * Then credit is given for 10 plus the number of pins knocked down with that next ball.
 * For example, a player rolls a spare in the first frame;
 * with the first ball of the second frame, the player knocks down seven pins.
 * The first frame, then, gets 17 points.
 * If two of the remaining three pins get knocked down, 9 pins are added,
 * for a total of 26 in the second frame.
 * If a game gets a strike, it is recorded with an X in the small square,
 * the score being 10 plus the total number of pins knocked down in the next two rolls.
 * Thus, the game who rolls three strikes in a row in the first three frames
 * gets credit for 30 points in the first frame.
 */
public class TenPinBowling {
    public static void main(String[] args) {
        String file = "bowling-game.txt";
        new TenPinBowling().bowl(file);
    }

    public Lane bowl(String bowlingSheet) {
        Lane laneSeven = new PennyLane();
        List<String> gameSheet = laneSeven.getBowlingSheet(bowlingSheet);
        gameSheet.forEach( s -> {
            String[] bowlingFrame = s.split(" ");
            String bowler = bowlingFrame[0];
            String rawPins = bowlingFrame[1];
            Integer pins;
            if("F".equalsIgnoreCase(rawPins)) {
                pins = -1; // -1 is a foul
            } else {
                pins = Integer.valueOf(rawPins); // number of pins knocked down
            }
            laneSeven.setBowler(bowler);
            laneSeven.setPinsKnockedDown(pins);
        });
        System.out.println(laneSeven.getScoreBoard());
        return laneSeven;
    }
}
