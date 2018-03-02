import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TrainExample {

    public static void main() throws FileNotFoundException {
        File text = new File("C:\\Users\\steve\\IdeaProjects\\HackerRank\\input#test.txt");
        Scanner in = new Scanner(text);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int cost1 = in.nextInt();
            int cost2 = in.nextInt();
            int cost3 = in.nextInt();
            int v1 = in.nextInt();
            int v2 = in.nextInt();
            int v3 = in.nextInt();
            HashMap<Integer,Station> allStations = new HashMap<>();
            for(int a1 = 0; a1 < n-1; a1++){
                int u = in.nextInt();
                int v = in.nextInt();
                Station stationU;
                Station stationV;
                if (allStations.containsKey(u)){stationU=allStations.get(u);}
                else{
                    stationU = new Station(u);
                    allStations.put(u,stationU);
                }
                if (allStations.containsKey(v)){stationV = allStations.get(v);}
                else{
                    stationV = new Station(v);
                    allStations.put(v,stationV);
                }

                stationU.addStationLink(stationV);
                stationV.addStationLink(stationU);
            }

            //Create new passengers initializing them at a station
            Passenger passenger1 = new Passenger(allStations.get(v1));
            Passenger passenger2 = new Passenger(allStations.get(v2));
            Passenger passenger3 = new Passenger(allStations.get(v3));

            //Set all possible routes for each passenger (GoalStation as Station1),
            // this method sets all the possible Stations for the Passenger to visit that are not out of their way
            passenger1.setAllPossibleRoutes(allStations.get(1));
            passenger2.setAllPossibleRoutes(allStations.get(1));
            passenger3.setAllPossibleRoutes(allStations.get(1));

            ArrayList<Passenger> passengers = new ArrayList(Arrays.asList(passenger1,passenger2,passenger3));
            HashMap<Integer,Integer> costByGroupSize = new HashMap<>();
            costByGroupSize.put(1,cost1);
            costByGroupSize.put(2,cost2);
            costByGroupSize.put(3,cost3);

            System.out.println(Route.getCostOfCheapestTrip(passengers,costByGroupSize));
        }
        in.close();
    }
}
