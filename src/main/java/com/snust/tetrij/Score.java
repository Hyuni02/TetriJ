package com.snust.tetrij;

import java.time.LocalDate;

public class Score {
    private String name;
    private int score;
    private LocalDate date;

    public Score(String name, int score, LocalDate date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }
}