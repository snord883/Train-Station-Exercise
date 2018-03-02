import java.util.ArrayList;

class Passenger {
    private Station intialStationNumber;
    private ArrayList<Route> allPossibleRoutes;

    public Passenger(Station st){
        intialStationNumber =st;
    }

    public Station getIntialStationNumber(){
        return this.intialStationNumber;
    }
    public void setAllPossibleRoutes(Station goalStation){
        this.allPossibleRoutes = Route.allRoutesFromAtoB(getIntialStationNumber(),goalStation);
    }

    public ArrayList<Route> getAllPossibleRoutes(){
        return this.allPossibleRoutes;
    }
}
