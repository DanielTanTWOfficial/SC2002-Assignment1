package model;

import java.io.Serial;
import java.io.Serializable;

public class Cinema implements Serializable {
    @Serial
    private static final long serialVersionUID = 123457L;
    private CinemaTypes cinemaType;
    private String cinemaDetails;
    private int numSeats;
    private int numRow;
    private int numCol;
    private int cinemaCode;

    public Cinema(String cinemaName, int numRow, int numCol, String cinemaDetails, int cinemaCode) {
        this.cinemaName = cinemaName;
        this.numCol= numCol;
        this.numRow = numRow;
        this.cinemaDetails=cinemaDetails;
        this.numSeats=numCol*numRow;
        this.cinemaCode=cinemaCode;
    }

    public String getCinemaTypes() {
        return cinemaName;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public String getCinemaDetails() {
        return cinemaDetails;
    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public int getCinemaCode() {
        return cinemaCode;
    }
}


