package us.rlit.bowling.alley;

import us.rlit.bowling.game.Bowler;
import us.rlit.bowling.game.Frame;
import us.rlit.bowling.loader.FileLoader;
import us.rlit.bowling.operators.Pepe;

import java.util.List;
import java.util.Map;

/**
 * PennyLane is the seating area that watches the bowlers and lets Pepe know when to act.
 * She finds new bowlers and tells them to keep bowling or sit back down. She reports the
 * scores to Pepe as they happen.
 */
public class PennyLane implements Lane {
    /**
     * PennyLane is going to put Pepe to work for every single throw she see's in game.
     */
    private Pepe pepe;

    public PennyLane() {
        this.pepe = new Pepe();
    }

    @Override
    public Map<String, Bowler> getBowlers() {
        return pepe.getBowlers();
    }

    @Override
    public List<String> getBowlingSheet(String bowlingSheet) {
        return new FileLoader().readFileAsString(bowlingSheet);
    }

    @Override
    public void setBowler(String name) {
        pepe.setBowler(name);
    }

    @Override
    public void setPinsKnockedDown(int pins) {
        pepe.setPinsKnockedDown(pins);
    }

    @Override
    public String getFramesAsString() {
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, Bowler> entry : getBowlers().entrySet()) {
            Bowler b = getBowlers().get(entry.getKey());
            buffer.append("Bowler: " + b.getName() + "\n");
            for (int i = 1; i < 11; i++) {
                buffer.append(" - frame: " + i + " score: " + b.getFrame(i) + "\n");
            }
        }
        return buffer.toString();
    }

    @Override
    public String getScoreBoard() {
        StringBuilder buffer = new StringBuilder("Frame\t\t");
        for (int i = 1; i <= 10; i++) {
            buffer.append(i + "\t\t");
        }
        buffer.append("\n");
        for (Map.Entry<String, Bowler> entry : getBowlers().entrySet()) {
            buffer.append(getBowlers().get(entry.getKey()).getName() + "\n");
            buffer.append(getScoreBoardPinfalls(entry));
            buffer.append("\n");
            buffer.append(getScoreBoardScores(entry));
            buffer.append("\n");
        }
        return buffer.toString();
    }

    private String getScoreBoardScores(Map.Entry entry) {
        StringBuilder buffer = new StringBuilder("Score\t\t");
        for (int i = 1; i <= 10; i++) {
            int score = getBowlers().get(entry.getKey()).getFrame(i).getScore();
            String frameScore = score + "\t";
            if( score >= 100) {
                frameScore += "\t";
            } else {
                frameScore += " \t";
            }
            buffer.append(frameScore);

        }
        return buffer.toString();
    }

    private String getScoreBoardPinfalls(Map.Entry entry) {
        StringBuilder buffer = new StringBuilder("Pinfalls\t");
        for (int i = 1; i <= 10; i++) {
            Frame frame = getBowlers().get(entry.getKey()).getFrame(i);
            if(i == 10 && frame.getRoll1() == 10) {
                buffer.append(frame.getRoll1() + "\t" + frame.getRoll2() + "\t");
                if(frame.getRoll2() + frame.getRoll3() == 10) {
                    buffer.append("/");
                } else {
                    buffer.append(frame.getRoll3());
                }

            } else if (frame.getRoll1() == 10) {
                buffer.append("\tX\t");
            } else if(frame.getRoll1() + frame.getRoll2() == 10) {
                buffer.append(frame.getRoll1() + "\t/\t");
            } else {
                buffer.append(frame.getRoll1() + "\t" + frame.getRoll2() + "\t");
            }
        }
        return buffer.toString();
    }
}
