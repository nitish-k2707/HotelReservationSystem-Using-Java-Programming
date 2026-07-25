package HotelReservationSystem;
import java.util.*;
import java.io.*;

public class Hotel {

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public Hotel() {

        rooms.add(new Room(101,"Standard",2000));
        rooms.add(new Room(102,"Standard",2000));

        rooms.add(new Room(201,"Deluxe",3500));
        rooms.add(new Room(202,"Deluxe",3500));

        rooms.add(new Room(301,"Suite",5000));

        loadBookings();
    }

    public void searchRooms() {

        System.out.println("\nAvailable Rooms");

        for(Room r : rooms) {

            if(r.isAvailable()) {

                System.out.println("Room : " + r.getRoomNumber()
                        +" | "+r.getCategory()
                        +" | ₹"+r.getPrice());

            }

        }

    }

    public void bookRoom(Scanner sc) {

        System.out.print("Enter Customer Name : ");
        String name=sc.nextLine();

        System.out.print("Enter Room Number : ");
        int number=sc.nextInt();
        sc.nextLine();

        for(Room r:rooms){

            if(r.getRoomNumber()==number && r.isAvailable()){

                System.out.println("Payment Amount : ₹"+r.getPrice());

                System.out.print("Proceed Payment (yes/no): ");
                String payment=sc.nextLine();

                if(payment.equalsIgnoreCase("yes")){

                    r.setAvailable(false);

                    Reservation res=new Reservation(name,r);

                    reservations.add(res);

                    saveBooking(res);

                    System.out.println("Booking Successful.");

                    return;

                }

                else{

                    System.out.println("Payment Failed.");

                    return;

                }

            }

        }

        System.out.println("Room not available.");

    }

    public void cancelReservation(Scanner sc){

        System.out.print("Enter Customer Name : ");
        String name=sc.nextLine();

        Iterator<Reservation> it=reservations.iterator();

        while(it.hasNext()){

            Reservation r=it.next();

            if(r.getCustomerName().equalsIgnoreCase(name)){

                r.getRoom().setAvailable(true);

                it.remove();

                rewriteFile();

                System.out.println("Reservation Cancelled.");

                return;

            }

        }

        System.out.println("Reservation Not Found.");

    }

    public void viewBookings(){

        System.out.println("\nBooking Details");

        for(Reservation r:reservations){

            System.out.println("Customer : "+r.getCustomerName());

            System.out.println("Room : "+r.getRoom().getRoomNumber());

            System.out.println("Category : "+r.getRoom().getCategory());

            System.out.println("----------------------");

        }

    }

    private void saveBooking(Reservation r){

        try{

            FileWriter fw=new FileWriter("bookings.txt",true);

            fw.write(r.getCustomerName()+","
                    +r.getRoom().getRoomNumber()+","
                    +r.getRoom().getCategory()+"\n");

            fw.close();

        }

        catch(Exception e){

            System.out.println(e);

        }

    }

    private void rewriteFile(){

        try{

            FileWriter fw=new FileWriter("bookings.txt");

            for(Reservation r:reservations){

                fw.write(r.getCustomerName()+","
                        +r.getRoom().getRoomNumber()+","
                        +r.getRoom().getCategory()+"\n");

            }

            fw.close();

        }

        catch(Exception e){

            System.out.println(e);

        }

    }

    private void loadBookings(){

        try{

            File file=new File("bookings.txt");

            if(!file.exists()) return;

            Scanner sc=new Scanner(file);

            while(sc.hasNextLine()){

                String line=sc.nextLine();

                String[] data=line.split(",");

                String name=data[0];

                int room=Integer.parseInt(data[1]);

                for(Room r:rooms){

                    if(r.getRoomNumber()==room){

                        r.setAvailable(false);

                        reservations.add(new Reservation(name,r));

                    }

                }

            }

            sc.close();

        }

        catch(Exception e){

            System.out.println(e);

        }

    }

}
