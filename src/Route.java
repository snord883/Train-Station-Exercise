import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Route {
    private HashSet<Station> alreadyVisitedStations;
    private Station endStation;


    public Route(){
        alreadyVisitedStations = new HashSet<>();
    }

    public boolean addStationToRoute(Station st){
        if(!this.hasThisStationBeenVisited(st)) {endStation = st;}
        return alreadyVisitedStations.add(st);
    }

    public boolean hasThisStationBeenVisited(Station st){
        return alreadyVisitedStations.contains(st);
    }

    public void copyRoute(Route old){
        for (Station st:old.getAlreadyVisitedStations()){
            this.alreadyVisitedStations.add(st);
        }
    }

    public HashSet<Station> getAlreadyVisitedStations(){return this.alreadyVisitedStations;}

    public Station getEndStation(){
        return endStation;
    }

    public static ArrayList<Route> allRoutesFromAtoB(Station stationA, Station stationB){
        //All routes that end at StationB will be moved from the activeRoutes to the completedRoutes
        ArrayList<Route> completedRoutes = new ArrayList<>();

        ArrayList<Route> activeRoutes = new ArrayList<>();
        Route initialRoute = new Route();
        initialRoute.addStationToRoute(stationA);
        activeRoutes.add(initialRoute);

        while (!activeRoutes.isEmpty()){
            //If next active Route has an end Station equal to the goal station (B) remove from activeRoutes to completed Routes
            if (activeRoutes.get(0).getEndStation()==stationB){
                completedRoutes.add(activeRoutes.get(0));
            }
            else {
                for (Station nextStation : activeRoutes.get(0).getEndStation().getLinkedStations()) {
                    //If this station has already been visited ignore from adding to the activeRoutes
                    if (activeRoutes.get(0).hasThisStationBeenVisited(nextStation)) {}
                    //Else make a new route and copy over the old route and extend to the new station. Adds the new extended route to the activeRoute
                    else {
                        Route extendRoute = new Route();
                        extendRoute.copyRoute(activeRoutes.get(0));
                        extendRoute.addStationToRoute(nextStation);
                        activeRoutes.add(extendRoute);
                    }
                }
            }
            activeRoutes.remove(0);
        }

        return completedRoutes;

    }

    public static int getCostOfCheapestTrip(ArrayList<Passenger> passengers, HashMap<Integer,Integer> costByGroupSize) {
        int minTotalCost = Integer.MAX_VALUE;
        for (Route passenger1Route:passengers.get(0).getAllPossibleRoutes()){
            for (Route passenger2Route:passengers.get(1).getAllPossibleRoutes()){
                for (Route passenger3Route:passengers.get(2).getAllPossibleRoutes()) {
                    int doubleOverlap = Route.numberOfStationOverlap(passenger1Route,passenger2Route);
                    doubleOverlap += Route.numberOfStationOverlap(passenger1Route,passenger3Route);
                    doubleOverlap += Route.numberOfStationOverlap(passenger2Route,passenger3Route);

                    int tripleOverlap = Route.numberOfStationOverlap(passenger1Route,passenger2Route,passenger3Route);
                    int individualRoutes = passenger1Route.getAlreadyVisitedStations().size()-1;
                    individualRoutes+=passenger2Route.getAlreadyVisitedStations().size()-1;
                    individualRoutes+=passenger3Route.getAlreadyVisitedStations().size()-1;

                    int noOverlap = individualRoutes - 2*doubleOverlap+3*tripleOverlap;
                    int onlyDoubleOverlap = doubleOverlap - 3*tripleOverlap;

                    int currentRouteCost =  noOverlap*costByGroupSize.get(1) + onlyDoubleOverlap*costByGroupSize.get(2)+tripleOverlap*costByGroupSize.get(3);

                    minTotalCost = currentRouteCost<minTotalCost ? currentRouteCost : minTotalCost;

                }
            }

        }
        return minTotalCost;
    }

    public static int numberOfStationOverlap(Route routeA, Route routeB){
        Route tempRoute = new Route();
        tempRoute.copyRoute(routeA);
        HashSet<Station> stationsOnRoute = tempRoute.getAlreadyVisitedStations();
        stationsOnRoute.retainAll(routeB.getAlreadyVisitedStations());
        return stationsOnRoute.size() - 1;
    }

    public static int numberOfStationOverlap(Route routeA, Route routeB, Route routeC){
        Route tempRoute = new Route();
        tempRoute.copyRoute(routeA);
        HashSet<Station> stationsOnRoute = tempRoute.getAlreadyVisitedStations();
        stationsOnRoute.retainAll(routeB.getAlreadyVisitedStations());
        stationsOnRoute.retainAll(routeC.getAlreadyVisitedStations());
        return stationsOnRoute.size() - 1;
    }
}
