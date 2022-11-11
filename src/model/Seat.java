package model;

import java.io.Serializable;

public class Seat implements Serializable {
    /**
	 * Automatically generated serialVerisonUID value to verify that the sender 
	 * and receiver of a serialized object have loaded classes for that object that
	 * are compatible with respect to serialization during deserialization.
	 */
    private static final long serialVersionUID = 123450L;
    /**
     * The assigned attribute tracks if seat has been assigned
     * true = taken, false = vacant
     */
    private boolean assigned; // true - taken, false - vacant
    // optional feature: seatType for couple seats, XL seats, etc...

    /**
     * Constructor to create a new Seat object
     */
    public Seat(){
        assigned = false;
    }

    /**
     * 
     * @return assigned
     */
    public boolean getAssigned() {
        return assigned;
    }

    /**
     * Assigns seat if it is available
     * @return true if seat has been successfully assigned, false if seat has been occupied
     */
    public void assignSeat(){
        if (assigned){
            System.out.println("Seat has been taken!");
        }
        else{
            assigned = true;
            System.out.println("Seat assigned successfully!");
        }
    }

    /**
     * Unassigns seat
     */
    public void unAssign() {
        assigned = false;
    }
}
