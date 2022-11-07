package controller;

public class CineplexController {
    public static void cineplexController(String[] args){

        addCathayCineplexes();
        printCineplexes();

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
    public static void addCathayCineplexes(){
        //-----creating cineplexes and cinemas
        Vendor thisVendor = new Vendor("Cathay Cineplexes");

        Cineplex dummyCineplex;
        Cinema dummyCinema;

        dummyCineplex = new Cineplex("Cineleisure Orchard"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",101);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",102);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",103);
        dummyCineplex.addNewCinema(dummyCinema);

        dummyCineplex = new Cineplex("West Mall"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",201);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",202);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",203);
        dummyCineplex.addNewCinema(dummyCinema);

        dummyCineplex = new Cineplex("JEM"); //creating new cineplex
        thisVendor.addNewCineplex(dummyCineplex);
        //ading cinemas to cineplex
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",301);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.STANDARD,11,22,"DOLBY ATMOS: Feel every dimension.",302);
        dummyCineplex.addNewCinema(dummyCinema);
        dummyCinema = new Cinema(CinemaTypes.PLATINUM,10,20,"The most luxurious cinematic destination, only in JEM mall. Platinum Movie Suites: Cinemas with its own exclusive lounge area, leather recliner seats, and wider legroom.",303);
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

        int choice=-1;


        int back=0;
        while (back==0){
            Scanner in = new Scanner(System.in);
            System.out.println("Welcome to "+ outputVendor.getVendorName() +" cinemas!");
            outputVendor.printCineplexes();

            //prompts user to choose cineplex
            System.out.println("Choose a cineplex to view their cinemas: ");
            choice=in.nextInt();

            Cineplex cineplexChoice=outputVendor.getCineplex(choice-1);
            cineplexChoice.printCinemas();
            System.out.println("Enter 0 to return to Cineplex list. Enter 1 to exit.");
            back=in.nextInt();
        }


    }
}