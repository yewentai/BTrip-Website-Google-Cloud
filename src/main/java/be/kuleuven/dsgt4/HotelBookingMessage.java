package be.kuleuven.dsgt4;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelBookingMessage {
    @JsonProperty("room")
    private RoomDTO room;
    @JsonProperty("orderID")
    private String orderID;
    @JsonProperty("finalPrice")
    private double finalPrice;
    @JsonAlias({"status","bookingStatus"})
    private String status;
    @JsonProperty("bookingLink")
    private BookingLink bookingLink;

    public static class BookingLink {
        @JsonProperty("rel")
        private String rel;
        @JsonProperty("href")
        private String href;

        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    public BookingLink getBookingLink() {
        return bookingLink;
    }

    public void setBookingLink(BookingLink bookingLink) {
        this.bookingLink = bookingLink;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
