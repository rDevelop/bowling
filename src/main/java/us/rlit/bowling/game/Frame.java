package us.rlit.bowling.game;


/**
 * Frame class is one frame
 */
public class Frame {
    private int number;
    private int turns;
    private int roll1;
    private int roll2;
    private int roll3;
    private int score; // current score
    private int bonusScore; // bonus score in frame.
    private boolean previousSpare;
    private boolean previousStrike;
    private boolean[] fouls = {false, false, false};

    public Frame(int frameNumber) {
        this.number = frameNumber;
        roll1 = 0;
        roll2 = 0;
        roll3 = 0;
        score = 0;
        bonusScore = 0;
        previousSpare = false;
        previousStrike = false;
    }

    public int getRoll1() {
        return roll1;
    }

    public void setRoll1(int roll) {
        if(roll == -1) {
            fouls[0] = true;
            roll = 0;
        }
        this.roll1 = roll;
        turns ++;
    }

    public int getRoll2() {
        return roll2;
    }

    public void setRoll2(int roll) {
        if(roll == -1) {
            fouls[1] = true;
            roll = 0;
        }
        this.roll2 = roll;
        turns ++;
    }

    public int getRoll3() {
        return roll3;
    }

    public void setRoll3(int roll) {
        if(roll == -1) {
            fouls[2] = true;
            roll = 0;
        }
        this.roll3 = roll;
        turns ++;
    }

    public boolean isFoul(int roll) {
        return fouls[roll-1];
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public int getBonusScore() {
        return bonusScore;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }

    public int getTurns() {
        return turns;
    }

    public boolean hasPreviousSpare() {
        return previousSpare;
    }

    public void setPreviousSpare() {
        this.previousSpare = true;
    }

    public boolean hasPreviousStrike() {
        return previousStrike;
    }

    public void setPreviousStrike() {
        this.previousStrike = true;
    }

    @Override
    public String toString(){
        String r1 = (roll1 == 0 && isFoul(1) ? "F" : String.valueOf(roll1));
        String r2 = (roll2 == 0 && isFoul(2) ? "F" : String.valueOf(roll2));
        String r3 = (roll3 == 0 && isFoul(3) ? "F" : String.valueOf(roll3));
        StringBuilder buffer = new StringBuilder("Frame: " + number + " [");
        buffer.append("[previousSpare: " +  previousSpare + " ]");
        buffer.append("[previousStrike: " +  previousStrike + " ]");
        buffer.append("[turns: " +  turns + " ]");
        buffer.append(",[roll1: " +  r1 + " ]");
        buffer.append(",[roll2: " +  r2 + " ]");
        buffer.append(",[roll3: " +  r3 + " ]");
        buffer.append(",[bonusScore: " +  bonusScore + " ]");
        buffer.append(" --> [score: " +  score + " ]");
        buffer.append("]");
        return buffer.toString();
    }
}
