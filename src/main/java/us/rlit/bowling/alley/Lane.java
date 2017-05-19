package us.rlit.bowling.alley;

import us.rlit.bowling.game.Bowler;

import java.util.List;
import java.util.Map;

/**
 * Lane is the old lady that knows without the bowlers coming to bowl, there would be no bowling.
 */
public interface Lane {
    List<String> getBowlingSheet(String bowlingSheet);
    void setBowler(String name);
    void setPinsKnockedDown(int pins);
    Map<String, Bowler> getBowlers();
    String getFramesAsString();
    String getScoreBoard();
}
