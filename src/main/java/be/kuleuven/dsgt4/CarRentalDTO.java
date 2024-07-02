package be.kuleuven.dsgt4;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarRentalDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
    @JsonProperty("basePrice")
    private double basePrice;
    @JsonProperty("carCount")
    private int carCount;
    @JsonProperty("_links")
    private HotelLinks _links;

    // Getters and setters
    // ...


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }

    public HotelLinks get_links() {
        return _links;
    }

    public void set_links(HotelLinks _links) {
        this._links = _links;
    }

    public static class HotelLinks {
        @JsonProperty("self")
        private FullLink self;
        @JsonProperty("carRentals")
        private FullLink carRentals;
        @JsonProperty("cheapest")
        private FullLink cheapest;

        // Getters and setters
        // ...

        public FullLink getSelf() {
            return self;
        }

        public void setSelf(FullLink self) {
            this.self = self;
        }

        public FullLink getCarRentals() {
            return carRentals;
        }

        public void setCarRentals(FullLink carRentals) {
            this.carRentals = carRentals;
        }

        public FullLink getCheapest() {
            return cheapest;
        }

        public void setCheapest(FullLink cheapest) {
            this.cheapest = cheapest;
        }
    }

    public static class FullLink {
        @JsonProperty("href")
        private String href;
        @JsonProperty("templated")
        private boolean templated;

        // Getters and setters
        // ...

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public boolean isTemplated() {
            return templated;
        }

        public void setTemplated(boolean templated) {
            this.templated = templated;
        }
    }
}