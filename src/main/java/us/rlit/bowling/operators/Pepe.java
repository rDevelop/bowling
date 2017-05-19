package us.rlit.bowling.operators;

import us.rlit.bowling.game.Bowler;
import us.rlit.bowling.game.Frame;

import java.util.HashMap;
import java.util.Map;

/**
 * Pepe is the gnome behind the scorer's terminal. He knows who is bowling and their turn. He keeps track of frames.
 * He also keeps track of scores.
 * Pepe is always ready. He doesn't care how many bowlers there are or where they come from, he just does his job.
 */
public class Pepe implements Keeper {
    // instance variables that are global to all things Pepe does.
    private Map<String, Bowler> bowlers;
    private Bowler bowler;
    private Bowler firstBowler;
    private int frame;

    public Pepe() {
        bowlers = new HashMap<>();
        frame = 0;
    }

    @Override
    public void setBowler(String name) {
        // set current bowler from bowler map
        if (bowlers.containsKey(name)) {
            bowler = bowlers.get(name);
        } else {
            // bowler wasn't in map, add new bowler
            bowler = new Bowler(name);
            bowlers.put(name, bowler);
            // check if this is the first bowler
            if (firstBowler == null) {
                firstBowler = bowler;
            }
        }
    }

    @Override
    public void setPinsKnockedDown(int pins) {
        // getTurn is 1 for the first pin
        int turn = bowler.getTurn();
        // frame starts at zero for two reasons.
        // 1. On double strike, two frames ago from frame 1, there needs to be a zero frame
        // 2. The first bowler's first turn is always a new frame.
        if (turn == 1 && bowler.equals(firstBowler)) {
            frame++;
        }
        // tenth frame can have 3 turns
        // and has different scoring situations
        if (frame == 10) {
            tenthFramePins(bowler, turn, pins);
        } else if (turn == 1) {
            // set pins for first roll
            bowler.getFrame(frame).setRoll1(pins);
            if (pins == 10) {
                // if the bowler had a strike, the next turn is the first one.
                bowler.setTurn(1);
                setScores();
            } else { // always the second turn, because we do a different method for 10th frame.
                bowler.setTurn(2);
            }
        } else {
            // second turn, set pins and then set turn to 1.
            bowler.getFrame(frame).setRoll2(pins);
            setScores();
            bowler.setTurn(1);
        }
    }


    /**
     * setScores does exactly that after every frame.
     * Pepe scores this frame
     * and up to two previous frame deserving a bonus.
     */
    @Override
    public void setScores() {
        // we always increase the frame after the scores, so the first call to set score is frame zero.
        if (frame == 0) {
            return; // don't score frame zero.
        }
        // get the current frame and two previous frames for bonus scoring.
        Frame currentFrame = bowler.getFrame(frame);
        Frame previousFrame = bowler.getFrame(frame - 1);

        // set scoring values of roll (fouls are -1 but need to score as zero)
        int roll1 = setPinScore(currentFrame.getRoll1());
        int roll2 = setPinScore(currentFrame.getRoll2());
        int roll3 = setPinScore(currentFrame.getRoll3());

        // set the next frame's previousFrame strike or spare flag.
        setBonusFrames(bowler, currentFrame, roll1, roll2);

        // if we had previous strike (previous implies frame > 0)
        if (frame > 1 && currentFrame.hasPreviousStrike()) {
            // if we had two previous strikes in a row. (implies frame > 2)
            if (frame > 2 && previousFrame.hasPreviousStrike()) {
                // set the bonus two frames ago, because we've only rocked it since then (2 strikes)
                setDoubleStrikeBonusScore(bowler, roll1);
            }
            // set the bonus scoring for last frames strike.
            setStrikeBonusScore(bowler, roll1, roll2, roll3);
        } else if (currentFrame.hasPreviousSpare()) {
            // set scoring for a spare
            setSpareScore(bowler, roll1, roll2);
        } else {
            // set scores on on open frame
            // *** the 10th frame might be a strike (not open I guess)
            setOpenFrameScore(bowler, roll1, roll2, roll3);
        }
    }

    /**
     * setPinScore (check for foul value)
     *
     * @param rollValue - raw value of throw (-1 for fouls)
     * @return zero for fouls or the actual number of pins knocked down.
     */
    private int setPinScore(int rollValue) {
        return (rollValue > 0) ? rollValue : 0;
    }

    private void setStrikeBonusScore(Bowler currentBowler, int roll1, int roll2, int roll3) {
        // get the current frame and two previous frames for bonus scoring.
        Frame currentFrame = currentBowler.getFrame(frame);
        Frame previousFrame = currentBowler.getFrame(frame - 1);
        Frame twoFramesAgo = currentBowler.getFrame(frame - 2);
        int bonusScore;
        // Have a strike last frame;
        // Situations
        // 1. Tenth frame, special scoring
        // 2. This frame has 2 rolls, so we make the bonus both rolls.
        // 3. This frame is a strike. This case we have 2 consecutive strikes which will be handled next turn in previous code block.
        if (frame == 10 && roll1 == 10) {
            // we have a strike in the 10th (consecutive)
            // bonus is strike bonus + first roll
            bonusScore = 10 + roll1;
            previousFrame.setBonusScore(bonusScore);
            previousFrame.setScore(twoFramesAgo.getScore() + bonusScore + roll2);
            currentFrame.setBonusScore(10 + roll2 + roll3);
            currentFrame.setScore(previousFrame.getScore() + roll1 + roll2 + roll3);

        } else if (currentFrame.getTurns() == 2) {
            // current frame has open frame so bonus comes from both rolls
            bonusScore = roll1 + roll2;
            if (previousFrame.getScore() == 0) {
                // if previous frame score is zero then add the bonus roll amount of 10
                bonusScore += 10;
                previousFrame.setBonusScore(bonusScore);
                previousFrame.setScore(twoFramesAgo.getScore() + bonusScore);
            } else {
                previousFrame.setBonusScore(bonusScore);
                previousFrame.setScore(previousFrame.getScore() + bonusScore);
            }
            currentFrame.setScore(previousFrame.getScore() + roll1 + roll2 + roll3);

        } else {
            // another strike bonus equals last bonus and this bonus
            bonusScore = previousFrame.getBonusScore() + roll1;
            previousFrame.setBonusScore(bonusScore);
            previousFrame.setScore(twoFramesAgo.getScore() + bonusScore);
        }
    }

    private void setDoubleStrikeBonusScore(Bowler currentBowler, int roll1) {
        // get the current frame and two previous frames for bonus scoring.
        Frame previousFrame = currentBowler.getFrame(frame - 1);
        Frame twoFramesAgo = currentBowler.getFrame(frame - 2);
        int bonusScore;
        // Have a strike two frames ago.
        // Situations
        // 1. Two strikes ago, the next frame was a strike (1 turn) and we needed to add bonus + last strike plus this first roll.
        // 2. Two strikes ago, the next frame had to rolls, so we add bonus + both roles in the next frame;
        if (previousFrame.getTurns() == 2) {
            bonusScore = previousFrame.getRoll1() + previousFrame.getRoll2();
            twoFramesAgo.setBonusScore(bonusScore);
            twoFramesAgo.setScore(twoFramesAgo.getScore() + bonusScore);
        } else {
            bonusScore = twoFramesAgo.getBonusScore() + roll1;
            twoFramesAgo.setBonusScore(bonusScore);
            twoFramesAgo.setScore(twoFramesAgo.getScore() + bonusScore);
        }
    }

    private void setSpareScore(Bowler currentBowler, int roll1, int roll2) {
        // get the current frame and two previous frames for bonus scoring.
        Frame currentFrame = currentBowler.getFrame(frame);
        Frame previousFrame = currentBowler.getFrame(frame - 1);
        // Have a spare last frame;
        // last frames bonus is this frames first roll
        // add bonus to previous score
        previousFrame.setBonusScore(roll1);
        previousFrame.setScore(previousFrame.getScore() + roll1);

        // Situations
        // 1. 2 turns this frame, bonus = current score tacks on bonus + second roll
        if (currentFrame.getTurns() == 2) {
            currentFrame.setScore(previousFrame.getScore() + roll1 + roll2);
        }
    }

    private void setOpenFrameScore(Bowler currentBowler, int roll1, int roll2, int roll3) {
        // get the current frame and two previous frames for bonus scoring.
        Frame currentFrame = currentBowler.getFrame(frame);
        Frame previousFrame = currentBowler.getFrame(frame - 1);
        if (frame == 10 && roll1 == 10) {
            int bonusScore = roll2 + roll3;
            currentFrame.setBonusScore(bonusScore);
            currentFrame.setScore(previousFrame.getScore() + roll1 + roll2 + roll3);
        } else {
            currentFrame.setScore(previousFrame.getScore() + roll1 + roll2);
        }
    }

    private void setBonusFrames(Bowler currentBowler, Frame currentFrame, int roll1, int roll2) {
        if (frame == 10) {
            return;
        }
        // if this frame is a strike or spare, set score to previous score and next frame previousStrike/spare
        if (roll1 == 10) {
            currentBowler.getFrame(frame + 1).setPreviousStrike();
        } else if (currentFrame.getTurns() > 1 && (roll1 + roll2 == 10)) {
            currentBowler.getFrame(frame + 1).setPreviousSpare();
        }
    }

    /**
     * Private method for setting pins in the tenth frame.
     *
     * @param turnNumber      of currentBowler
     * @param pinsKnockedDown by currentBowler
     */
    private void tenthFramePins(Bowler currentBowler, int turnNumber, int pinsKnockedDown) {
        // turn number 1: set roll1 and nextTurn = 2
        // turn 2: set roll2 if it's it a strike or spare we'll have a 3rd frame
        if (turnNumber == 1) {
            currentBowler.getFrame(frame).setRoll1(pinsKnockedDown);
            currentBowler.setTurn(2);
        } else if (turnNumber == 2) {
            currentBowler.getFrame(frame).setRoll2(pinsKnockedDown);
            Frame tenthFrame = currentBowler.getFrame(frame);
            if (tenthFrame.getRoll1() == 10 || (tenthFrame.getRoll1() + tenthFrame.getRoll2() == 10)) {
                currentBowler.setTurn(3);
            } else {
                // if there isn't a third roll, set the scores - we're finished.
                setScores();
            }
        } else {
            // it's the third roll, so set roll3 and set the scores - we're finished.
            currentBowler.getFrame(frame).setRoll3(pinsKnockedDown);
            setScores();
        }
    }

    /**
     * @return the map of bowlers
     */
    @Override
    public Map<String, Bowler> getBowlers() {
        return bowlers;
    }
}
