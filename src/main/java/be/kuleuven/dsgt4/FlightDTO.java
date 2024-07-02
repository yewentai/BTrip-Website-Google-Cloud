package be.kuleuven.dsgt4;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("departure")
    private String departure;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("departureTimeAsString")
    private String departureTime;
    @JsonProperty("lowestPricedSeat")
    private String cheapestSeatCode;
    @JsonProperty("availableSeatCount")
    private int availableSeatCount;
    @JsonProperty("_links")
    private FlightLinks _links;


    public FlightLinks get_links() {
        return _links;
    }

    public void set_links(FlightLinks _links) {
        this._links = _links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getCheapestSeatCode() {
        return cheapestSeatCode;
    }

    public void setCheapestSeatCode(String cheapestSeatCode) {
        this.cheapestSeatCode = cheapestSeatCode;
    }

    public int getAvailableSeatCount() {
        return availableSeatCount;
    }

    public void setAvailableSeatCount(int availableSeatCount) {
        this.availableSeatCount = availableSeatCount;
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
                "id='" + id + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", cheapestSeatCode='" + cheapestSeatCode + '\'' +
                ", availableSeatCount=" + availableSeatCount +
                ", _links=" + _links +
                '}';
    }

    public static class FlightLinks {
        @JsonProperty("self")
        private FullLink self;
        @JsonProperty("flights")
        private FullLink flights;
        @JsonProperty("cheapest")
        private FullLink cheapest;

//        public FlightLinks(FullLink self, FullLink flights, FullLink cheapest) {
//            this.self = self;
//            this.flights = flights;
//            this.cheapest = cheapest;
//        }

        public FullLink getSelf() {
            return self;
        }

        public void setSelf(FullLink self) {
            this.self = self;
        }

        public FullLink getFlights() {
            return flights;
        }

        public void setFlights(FullLink flights) {
            this.flights = flights;
        }

        public FullLink getCheapest() {
            return cheapest;
        }

        public void setCheapest(FullLink cheapest) {
            this.cheapest = cheapest;
        }

        // Getters and setters
    }

    public static class FullLink {
        @JsonProperty("href")
        private String href;
        @JsonProperty("templated")
        private boolean templated;

//        public FullLink(String href) {
//            this.href = href;
//        }

        public boolean isTemplated() {
            return templated;
        }

        public void setTemplated(boolean templated) {
            this.templated = templated;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

    }
}



