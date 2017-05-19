package us.rlit.bowling.operators;

import java.util.Map;

/**
 * A game is a legendary scorer that knows the things he must do to keep track of the score
 */
public interface Keeper {
    void setBowler(String name);
    void setPinsKnockedDown(int pins);
    void setScores();
    Map getBowlers();
}
