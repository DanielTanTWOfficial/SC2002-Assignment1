package src.model;

public class Seat {
    private static final long serialVersionUID = 123450L;
    private int assigned; //1 - taken, 0 - vacant

    public Seat(){
        assigned=0;
    }

    public int getAssigned() {
        return assigned;
    }

    public void assignSeat(){
        if (assigned==1){
            System.out.println("Seat has been taken!");
        }
        else{
            assigned=1;
        }
    }
}
