package model;

import java.io.Serializable;
import java.time.LocalDate;

public class StatisticWord implements Serializable {
    private String word;
    private LocalDate date;

    public StatisticWord(String word, LocalDate date) {
        this.word = word;
        this.date = date;
    }

    public String getWord() {
        return word;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
}
