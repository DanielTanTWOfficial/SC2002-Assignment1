package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Bernice
 * The Cinema class, which implements the Serializable interface
 */
public class Cinema implements Serializable {
    /**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
    private static final long serialVersionUID = 123457L;
    public enum CinemaClass {STANDARD, PLATINUM};
    private CinemaClass cinemaClass;
    private int cinemaCode;
    private int numRow;
    private int numCol;
    private int numSeats;

    /**
     * Constructor to create a new Cinema object
     * @param cinemaClass
     * @param cinemaCode
     * @param numRow
     * @param numCol
     */
    public Cinema(CinemaClass cinemaClass, int cinemaCode, int numRow, int numCol) {
        this.cinemaClass = cinemaClass;
        this.cinemaCode=cinemaCode;
        this.numCol= numCol;
        this.numRow = numRow;
        this.numSeats=numCol*numRow;
    }

    /**
     * @return cinemaClass
     */
    public CinemaClass getCinemaClass() {
        return cinemaClass;
    }

    /**
     * Converts cinemaTypes from enum to an extensive String
     * @param cinemaClass
     * @return cinemaTypes in String type
     */
    public String cinemaTypestoString(CinemaClass cinemaClass){
        switch (cinemaClass){
            case STANDARD: return "Standard DOLBY ATMOS cinema";
            case PLATINUM: return "Platinum Movie Suites";
        }
        return "";
    }

    /**
     * @return cinemaCode
     */
    public int getCinemaCode() {
        return cinemaCode;
    }

    /**
     * @return numSeats
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * @return numRow
     */
    public int getNumRow() {
        return numRow;
    }

    /**
     * @return numCol
     */
    public int getNumCol() {
        return numCol;
    }
}


