package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import model.Vendor;
import model.SerializationUtil;

public class VendorController {
    public static ArrayList<Object> readVendorsFile() {
        ArrayList<Object> vendors = new ArrayList<>();
        try {
			vendors = SerializationUtil.deserialize("cineplexes.ser");
            return vendors;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        return new ArrayList<Object>();
    }

    public static void addVendor(Vendor vendor) {
        File dfile = new File("vendors.ser");
    	try {
			SerializationUtil.deleteFile(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
            SerializationUtil.serialize(vendor, "vendors.ser");
            System.out.println("Vendor" + vendor.getVendorName() + " added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Vendor adding unsuccessful!");
    	}

    }
}