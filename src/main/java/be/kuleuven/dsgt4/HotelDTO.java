package be.kuleuven.dsgt4;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
    @JsonProperty("basePrice")
    private double basePrice;
    @JsonProperty("roomCount")
    private int roomCount;
    @JsonProperty("_links")
    private HotelLinks _links;

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

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public HotelLinks get_links() {
        return _links;
    }

    public void set_links(HotelLinks _links) {
        this._links = _links;
    }
// Getters and setters
    // ...

    public static class HotelLinks {
        @JsonProperty("self")
        private FullLink self;
        @JsonProperty("hotels")
        private FullLink hotels;
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

        public FullLink getHotels() {
            return hotels;
        }

        public void setHotels(FullLink hotels) {
            this.hotels = hotels;
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