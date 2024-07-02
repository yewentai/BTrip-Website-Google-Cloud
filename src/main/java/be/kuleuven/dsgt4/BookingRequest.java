package be.kuleuven.dsgt4;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("adults")
    private int adults;
    @JsonProperty("children")
    private int children;
    @JsonProperty("totalPrice")
    private double totalPrice;

    public String getEmail() {
        return email;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}