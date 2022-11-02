package src.controller;

public class CineplexController {
    public static void main(String[] args){

        printCineplexes();
        addCineplexes();

        //delete serialization file
        File dfile = new File("VendorCineplexesInfo.ser");
        try {
            SerializationUtil.deleteFile(dfile);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Creates the cineplexes and cinemas
     */
    public static void addCineplexes(){
        //-----creating cineplexes and cinemas
        Vendor thisVendor = new Vendor("Cathay Cineplexes");

        Cineplex dummyCineplex;
        Cinema dummyCinema;

        dummyCineplex = new Cineplex("Jurong East"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema("Gold Class",10,10,"Gold class is so cool wow ",1002);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema("Silver Class",20,30,"Silver class is cool but not as cool as gold class!!",8927);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema("Bronze Class",40,50,"Bronze class is for noobs",9839);
        dummyCineplex.addNewCinema(dummyCinema);

        dummyCineplex = new Cineplex("Vivocity"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema("Gold Class",10,10,"Gold class is so cool wow ",9896);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema("Platinum Class",5,5,"Platinum class is the best",3456);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema("3D",40,50,"Wow 3D so cool",7897);
        dummyCineplex.addNewCinema(dummyCinema);


        //serialising data
        try {
            SerializationUtil.serialize(thisVendor,"VendorCineplexesInfo.ser");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Displays the cineplexes and selected cinemas
     */
    public static void printCineplexes() {

        //deserialising vendor data
        ArrayList<Object> cineplexesInfo = new ArrayList<>();
        try {
            cineplexesInfo = SerializationUtil.deserialize("VendorCineplexesInfo.ser");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Vendor outputVendor = (Vendor)cineplexesInfo.get(0); //obtain vendor (there is only one vendor)
        outputVendor.printCineplexes();


        int choice;
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to "+ outputVendor.getVendorName() +" cinemas!");
        outputVendor.printCineplexes();

        //prompts user to choose cinepelx
        System.out.println("Choose a cineplex to view their cinemas: ");
        choice=in.nextInt();

        Cineplex cineplexChoice=outputVendor.getCineplex(choice-1);
        cineplexChoice.printCinemas();
    }
}
