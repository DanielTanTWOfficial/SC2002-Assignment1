package src.model;

public class CinemaBooking {
    private static final long serialVersionUID = 123459L;
    private String cinemaName;

    private Seat[][] seats;

    private int numRow;

    private int numCol;

    private int numSeats;


    public CinemaBooking(Cinema cinema) {
        this.cinemaName = cinema.getCinemaName();
        this.numRow=cinema.getNumRow();
        this.numCol= cinema.getNumCol();

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

    public void checkSeats(){
        for (int i=0;i<numRow;i++){
            for (int j=0;j<numCol;j++){
                System.out.print(seats[i][j].getAssigned()+" ");
            }
            System.out.println();
        }
    }
}