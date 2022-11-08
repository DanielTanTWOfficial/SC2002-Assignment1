package model;

import java.io.Serial;
import java.io.Serializable;

public class Cinema implements Serializable {
    @Serial
    private static final long serialVersionUID = 123457L;
    public enum CinemaClass {STANDARD, PLATINUM};
    private CinemaClass cinemaClass;
    private int cinemaCode;
    private int numRow;
    private int numCol;
    private int numSeats;

    public Cinema(CinemaClass cinemaClass, int cinemaCode, int numRow, int numCol) {
        this.cinemaClass = cinemaClass;
        this.cinemaCode=cinemaCode;
        this.numCol= numCol;
        this.numRow = numRow;
        this.numSeats=numCol*numRow;
    }

    public CinemaClass getCinemaClass() {
        return cinemaClass;
    }

    public String cinemaTypestoString(CinemaClass cinemaClass){
        switch (cinemaClass){
            case STANDARD: return "Standard DOLBY ATMOS cinema";
            case PLATINUM: return "Platinum Movie Suites";
        }
        return "";
    }

    public int getCinemaCode() {
        return cinemaCode;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }
}


