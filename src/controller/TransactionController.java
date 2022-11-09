package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.SerializationUtil;
import model.Ticket;
import model.Transaction;

public class TransactionController {
	
	public static ArrayList<Object> readTransactionFile() {
		ArrayList<Object> transactionList = new ArrayList<>();
        try {
        	transactionList = SerializationUtil.deserialize("transactions.ser");
            return transactionList;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("transactions.ser not found");
		}
        return new ArrayList<Object>();
	}
	
	public static void saveTransactionFile(Transaction transaction) {
		try {
			SerializationUtil.serialize(transaction, "transactions.ser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
