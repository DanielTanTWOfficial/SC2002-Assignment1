package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.SerializationUtil;
import model.Transaction;
/**
 * 
 * @author WeiJie
 * Controller class for actions related to transactions
 */
public class TransactionController {
	
	/**
	 * This method reads transaction.ser file and returns an arrayList of Transaction object
	 * @return 
	 */
	public static ArrayList<Object> readTransactionFile() {
		ArrayList<Object> transactionList = new ArrayList<>();
        try {
        	transactionList = SerializationUtil.deserialize("database/transactions.ser");
            return transactionList;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("transactions.ser not found");
		}
        return new ArrayList<Object>();
	}
	
	/**
	 * This method takes in a Transaction object, calls SerializationUtil to serialize and store the object into transactions.ser
	 * @param transaction
	 */
	public static void saveTransactionFile(Transaction transaction) {
		try {
			SerializationUtil.serialize(transaction, "database/transactions.ser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
