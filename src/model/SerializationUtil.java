package src.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author d188878
 * A class to implement serialize and deserialize method implementation
 */
public class SerializationUtil {
	/**
	 * deserialize method to read objects from a file
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Object> deserialize(String filename) throws IOException, ClassNotFoundException {
		ArrayList<Object> objects = new ArrayList<>();
		FileInputStream f_in = new FileInputStream(filename);
		ObjectInputStream obj_in = new ObjectInputStream(f_in);
		boolean cont = true;
		// keep looping and reading objects in the stream until no more objects found
		while(cont) {
			try {
				Object readObj = obj_in.readObject();
				if(readObj != null) {
					// append new object to the ArrayList if it exists
					objects.add(readObj);
				}
				else {
					cont = false;
				}
			} catch(EOFException e) {
				break; // break if obj_in.readObject() errors out due to end-of-file reached
			}
		}
		obj_in.close();
		return objects;
	}
	
	/**
	 * serialize method to write objects to a file
	 * @param obj
	 * @param filename
	 * @throws IOException
	 */
	public static void serialize(Object obj, String filename) throws IOException {
		boolean empty = false;
		// check if an object already exists in the file, and if so, call FileOutputStream with append = true
		try {
			FileInputStream f_in = new FileInputStream(filename);
			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			Object test_obj = obj_in.readObject();
			obj_in.close();
		} catch(ClassNotFoundException | FileNotFoundException e) {
			empty = true;
		}
		// if file already has some objects data
		if(empty != true) {
			FileOutputStream f_out = new FileOutputStream(filename, true);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out) {
				protected void writeStreamHeader() throws IOException {
	                reset();
	            }
			};
			obj_out.writeObject(obj);
			f_out.close();
		}
		// if file is empty
		else {
			FileOutputStream f_out = new FileOutputStream(filename);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			obj_out.writeObject(obj);
			f_out.close();
		}
	}
	
}
