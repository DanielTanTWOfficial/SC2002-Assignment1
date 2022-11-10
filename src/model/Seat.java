package model;

import java.io.Serializable;

public class Seat implements Serializable {
    private static final long serialVersionUID = 123450L;
    private boolean assigned; // true - taken, false - vacant
    // optional feature: seatType for couple seats, XL seats, etc...

    /**
     * Constructor to create a new Seat object
     */
    public Seat(){
        assigned = false;
    }

    /**
     * Assigns seat if it is available
     * @return true if seat has been successfully assigned, false if seat has been occupied
     */
    public boolean assignSeat(){
        if (assigned){
            System.out.println("Seat has been taken!");
            return false;
        }
        else{
            assigned = true;
            System.out.println("Seat assigned successfully!");
            return true;
        }
    }

    /**
     * Unassigns seat
     */
    public void unAssign() {
        assigned = false;
    }

    /**
     * @return whether seat is assigned
     */
    public boolean getAssigned() {
        return assigned;
    }
}
