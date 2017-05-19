package us.rlit.bowling;

import org.junit.Test;
import us.rlit.bowling.game.Bowler;

import java.util.Map;

/**
 * GameTest test multiple scenarios of scores
 */
public class GameTest {


    @Test
    public void john() {

        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/john.txt").getBowlers();
        assert(bowlers.get("John").getFrame(10).getScore() == 151);

    }

    @Test
    public void jeff() {

        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/jeff.txt").getBowlers();
        assert(bowlers.get("Jeff").getFrame(10).getScore() == 167);

    }

    @Test
    public void ted() {

        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/ted.txt").getBowlers();
        assert(bowlers.get("Ted").getFrame(10).getScore() == 151);

    }

    @Test
    public void allStrikes() {

        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/all-strikes.txt").getBowlers();
        assert(bowlers.get("Carl").getFrame(10).getScore() == 300);

    }

    @Test
    public void no10thStrike() {
        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/no-strike-10th.txt").getBowlers();
        assert(bowlers.get("Fox").getFrame(10).getScore() == 91);

    }

    @Test
    public void threeBowlers() {
        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/three-bowlers.txt").getBowlers();
        assert(bowlers.get("John").getFrame(10).getScore() == 151);
        assert(bowlers.get("Jeff").getFrame(10).getScore() == 167);
        assert(bowlers.get("Rob").getFrame(10).getScore() == 300);


    }

    @Test
    public void bowlingGame() {

        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/bowling-game.txt").getBowlers();
        assert(bowlers.get("John").getFrame(10).getScore() == 151);
        assert(bowlers.get("Jeff").getFrame(10).getScore() == 167);
    }

    @Test
    public void alotOfFouls() {
        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/fouls.txt").getBowlers();
        assert(bowlers.get("Fox").getFrame(10).getScore() == 91);


    }

    @Test
    public void lowRoller10thSpare() {
        Map<String,Bowler> bowlers = new TenPinBowling().bowl("src/test/resources/low-roller-spare-in-10th.txt").getBowlers();
        assert(bowlers.get("Pete").getFrame(10).getScore() == 70);
    }


    @Test
    public void printFrames() {
        String frames = new TenPinBowling().bowl("src/test/resources/all-strikes.txt").getFramesAsString();
        System.out.println(frames);
        assert(frames.split("\n")[0].equals("Bowler: Carl"));
        assert(frames.split("\n")[1].contains("Frame: 1"));
        assert(frames.split("\n")[5].contains("150"));
        assert(frames.split("\n")[10].contains("bonusScore: 30"));
    }


}
