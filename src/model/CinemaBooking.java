package model;

import java.io.Serializable;

import model.Cinema.CinemaClass;

/**
 * 
 * @author Bernice
 * The CinemaBooking class, which implements the Serializable interface
 *
 */
public class CinemaBooking implements Serializable {
    /**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
    private static final long serialVersionUID = 123459L;
    private CinemaClass cinemaClass;

    private Seat[][] seats;

    private int numRow;

    private int numCol;

    private int numSeats;

    private int cinemaCode;

    /**
     * Constructor to create a new CinemaBooking object
     * @param cinema
    */
    public CinemaBooking(Cinema cinema) {
        this.cinemaClass = cinema.getCinemaClass();
        this.numRow=cinema.getNumRow();
        this.numCol= cinema.getNumCol();
        this.cinemaCode= cinema.getCinemaCode();

        //initialise seats array
        seats= new Seat[numRow][numCol];
        for (int i=0;i<numRow;i++) {
            for (int j = 0; j < numCol; j++) {
                seats[i][j] = new Seat();
            }
        }

        System.out.println("Cinema "+cinemaCode+" ready for booking!");
    }

    /**
     * 
     * @return seats
     */
    public Seat[][] getSeats() {
        return this.seats;
    }

    /**
     * 
     * @return numRow
     */
    public int getNumRows() {
        return this.numRow;
    }

    /**
     * 
     * @return numCol
     */
    public int getNumCols() {
        return this.numCol;
    }

    /**
     * 
     * @return cinemaClass
     */
    public CinemaClass getCinemaClass() {
        return cinemaClass;
    }

    /**
     * 
     * @return numSeats
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Assigns seat with given row and column number
     * @param r (row number)
     * @param c (column number)
     */
    public void assignSeat(int r, int c){
        seats[r][c].assignSeat();
    }

    /**
     * Prints layout of seats and their availabilities
     */
    public void printSeats(){
        System.out.println("----- Screen -----");

        for (int i=0;i<numRow;i++){
            System.out.print(String.format("%-5d",i+1));
            for (int j=0;j<numCol;j++){
                String symbol="o"; //unassigned seats
                if (seats[i][j].getAssigned()==true) symbol="x"; //assigned seats
                System.out.print(symbol+" ");
            }
            System.out.println();
        }
    }
}
