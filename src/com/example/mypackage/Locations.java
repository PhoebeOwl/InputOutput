package com.example.mypackage;

import java.io.*;
import java.util.*;

// take the functionality our from the main class in terms of
//creating locations and put it in the locations class
public class Locations implements Map<Integer,Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<Integer, Location>();

    public static void main(String[] args) throws IOException {
       // FileWriter locaFile =null;
        //because try block and finally block introduces new scope
        // so we declare locaFile outside the try block
//        try{
//            locaFile = new FileWriter("location.txt");
//            for(Location location:locations.values()){
//                locaFile.write(location.getLocationID()+","+location.getDescription()+"\n");
//                //throw new IOException("test exception thrown while writing");
//                //throwing your own exceptions may be a useful technique while testing
//                //make sure delete it after testing
//            }
//            //locaFile.close();//this may not be executed when the write line has error
//            //when an error in the for loop occurs, it automatically skip this line and goes to the catch block
//        }finally{
//            System.out.println("in finally block");
//            // close itself will also create IOException
//            //so we once again get an exception error
//            //be careful not to cause more exceptions in the try block
//            //to make the code as resilient as possible
//               if(locaFile!=null){
//                   System.out.println("trying to close locafile");
//                   locaFile.close();
//               }
//
//        }
            // try with resources from java 8 version will make this more cleaner
        try(BufferedWriter locaFile=new BufferedWriter(new FileWriter("locations.txt"));
            BufferedWriter dirFile = new BufferedWriter(new FileWriter("directions.txt"))){
            for(Location location : locations.values()){
                locaFile.write(location.getLocationID()+","+location.getDescription()+"\n");
                for (String direction: location.getExits().keySet()){
                    if(!direction.equalsIgnoreCase("Q")){
                        dirFile.write(location.getLocationID()+","+direction+","+location.getExits().get(direction)+"\n");
                    }

                }
            }
        }

    }
    static{
        //we use a static initialization block to make sure
        // our data is only created once
//        Scanner s=null;

        try(Scanner scanner= new Scanner(new BufferedReader(new FileReader("locations_big.txt")))) {

//            s.useDelimiter(",");// information is seperated by comma
            while(scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(",");
                int loc = Integer.parseInt(data[0]);
                String description=data[1];
                System.out.println("Imported loc:" + loc + ":" + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //now read the exits
        try(BufferedReader dirFile=new BufferedReader(new FileReader("directions_big.txt"))) {
            String input;
            while ((input=dirFile.readLine())!=null) {
                String[] data=input.split(",");
                int loc=Integer.parseInt(data[0]);
                String direction= data[1];
                int destination = Integer.parseInt(data[2]);
                System.out.println(loc + ":" + direction + ":" + destination);
                Location location = locations.get(loc);
                location.addExit(direction, destination);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key,value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
