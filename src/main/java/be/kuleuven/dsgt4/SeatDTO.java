package be.kuleuven.dsgt4;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SeatDTO {
    @JsonProperty("seatCode")
    private String seatCode;
    @JsonProperty("price")
    private double price;
    @JsonProperty("booked")
    private boolean booked;
    @JsonProperty("reserved")
    private boolean reserved;
    @JsonProperty("_links")
    private SeatLinks _links;

//    public SeatDTO(String seatCode, double price, boolean booked, boolean reserved, SeatLinks _links) {
//        this.seatCode = seatCode;
//        this.price = price;
//        this.booked = booked;
//        this.reserved = reserved;
//        this._links = _links;
//    }

    @Override
    public String toString() {
        return "SeatDTO{" +
                "seatCode='" + seatCode + '\'' +
                ", price=" + price +
                ", booked=" + booked +
                ", reserved=" + reserved +
                ", _links=" + _links.toString() +
                '}';
    }

    public static class SeatLinks {
        @JsonProperty("self")
        private SeatLink self;
        @JsonProperty("flight")
        private SeatLink flight;
        @JsonProperty("reserve")
        private SeatLink reserve;


        @Override
        public String toString() {
            return "SeatLinks{" +
                    "self=" + self.toString() +
                    ", flight=" + flight.toString() +
                    ", reserve=" + reserve.toString() +
                    '}';
        }

        public SeatLink getSelf() {
            return self;
        }

        public void setSelf(SeatLink self) {
            this.self = self;
        }

        public SeatLink getFlight() {
            return flight;
        }

        public void setFlight(SeatLink flight) {
            this.flight = flight;
        }

        public SeatLink getReserve() {
            return reserve;
        }

        public void setReserve(SeatLink reserve) {
            this.reserve = reserve;
        }
    }

    public static class SeatLink {
        @JsonProperty("href")
        private String href;

        @Override
        public String toString() {
            return "SeatLink{" +
                    "href='" + href + '\'' +
                    '}';
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

//        public SeatLink(String href) {
//            this.href = href;
//        }
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public SeatLinks get_links() {
        return _links;
    }

    public void set_links(SeatLinks _links) {
        this._links = _links;
    }
}
