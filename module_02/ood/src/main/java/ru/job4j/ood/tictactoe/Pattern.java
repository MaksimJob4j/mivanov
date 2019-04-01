package ru.job4j.ood.tictactoe;

public class Pattern implements Comparable<Pattern> {

    private final int[] sequence;
    private final int priority;
    private final boolean own;

    public Pattern(int[] sequence, int priority, boolean own) {
        this.sequence = sequence;
        this.priority = priority;
        this.own = own;
    }

    public int[] getSequence() {
        return sequence;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isOwn() {
        return own;
    }

    @Override
    public int compareTo(Pattern pattern) {
        return -(this.priority - pattern.priority);
    }

}
