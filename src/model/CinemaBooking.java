package model;

public class CinemaBooking {
    private static final long serialVersionUID = 123459L;
    private CinemaTypes cinemaName;

    private Seat[][] seats;

    private int numRow;

    private int numCol;

    private int numSeats;

    private int cinemaCode;


    public CinemaBooking(Cinema cinema) {
        this.cinemaName = cinema.getCinemaName();
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

        System.out.println("Cinema "+cinemaName+" ready for booking!");
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void assignSeat(int r, int c){
        seats[r][c].assignSeat();
    }

    public void printSeats(){
        System.out.println("----- Screen -----");

        for (int i=0;i<numRow;i++){
            System.out.print(String.format("%-5d",i+1));
            for (int j=0;j<numCol;j++){
                String symbol="o"; //unassigned seats
                if (seats[i][j].getAssigned()==1) symbol="x"; //assigned seats
                System.out.print(symbol+" ");
            }
            System.out.println();
        }
    }
}
