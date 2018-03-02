import java.util.HashSet;

class Station {
    private int stationNumber;
    public HashSet<Station> linkedStations;

    public Station(int n){
        this.stationNumber = n;
        linkedStations = new HashSet<>();
    }

    public boolean addStationLink(Station a){
        return linkedStations.add(a);
    }

    public int getStationNumber(){
        return stationNumber;
    }

    public HashSet<Station> getLinkedStations(){
        return linkedStations;
    }
}
