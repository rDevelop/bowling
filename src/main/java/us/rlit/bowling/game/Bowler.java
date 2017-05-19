package us.rlit.bowling.game;

/**
 * Bowler is a name and his frames and everything that makes him an rock star bowler.
 */
public class Bowler {
    private String name;
    private Frame[] frames;
    int turn;
    boolean finished;

    public Bowler(String name) {
        this.name = name;
        this.turn = 1;
        frames = new Frame[11];
        for(int i=0; i<11; i++) {
            frames[i] = new Frame(i);
        }
        finished = false;
    }

    public Frame getFrame(int frameNumber) {
        frameNumber = (frameNumber < 0) ? 0 : frameNumber;
        return frames[frameNumber];
    }

    public String getName() {
        return name;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bowler)) return false;

        Bowler bowler = (Bowler) o;

        return (getName() != null ? getName().equals(bowler.getName()) : bowler.getName() == null) && getName().equals(bowler.getName());
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getName().hashCode();
        return result;
    }
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Bowler: " + name);
        return buffer.toString();
    }
}
