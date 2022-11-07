package model;

import java.io.Serializable;

public class Seat implements Serializable{
    private static final long serialVersionUID = 123450L;
    private boolean assigned; // true - taken, false - vacant
    // optional feature: seatType for couple seats, XL seats, etc...

    public Seat(){
        assigned = false;
    }

    public boolean getAssigned() {
        return assigned;
    }

    public void assignSeat(){
        if (assigned){
            System.out.println("Seat has been taken!");
        }
        else{
            assigned = true;
            System.out.println("Seat assigned successfully!");
        }
    }

    public void unAssign() {
        assigned = false;
    }
}
