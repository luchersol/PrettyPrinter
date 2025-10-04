package com.prettyprinter;

public class ProgressBar {
    private final int total;
    private final int barLength;
    private final char fillChar;
    private final char emptyChar;

    public ProgressBar(int total, int barLength) {
        this(total, barLength, '=', ' ');
    }

    public ProgressBar(int total, int barLength, char fillChar, char emptyChar) {
        this.total = total;
        this.barLength = barLength;
        this.fillChar = fillChar;
        this.emptyChar = emptyChar;
    }

    public void update(int current) {
        double percent = (double) current / total;
        int filled = (int) (percent * barLength);

        StringBuilder bar = new StringBuilder();
        bar.append('\r');
        bar.append('[');
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? fillChar : emptyChar);
        }
        bar.append(']');
        bar.append(String.format(" %3d%%", (int) (percent * 100)));

        System.out.print(bar);
        System.out.flush();
        if (current >= total) {
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProgressBar progressBar1 = new ProgressBar(100, 40);
        ProgressBar progressBar2 = new ProgressBar(100, 40, '/', '\\');

        for (int i = 0; i <= 100; i++) {
            progressBar1.update(i);
            Thread.sleep(30);
        }

        for (int i = 0; i <= 100; i++) {
            progressBar2.update(i);
            Thread.sleep(30);
        }
    }
}
