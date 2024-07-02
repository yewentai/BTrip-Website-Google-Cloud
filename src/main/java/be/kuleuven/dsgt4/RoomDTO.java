package be.kuleuven.dsgt4;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDTO {
    @JsonProperty("pricePerNight")
    private double pricePerNight;

    @JsonProperty("roomCode")
    private String roomCode;

    @JsonProperty("links")
    private List<RoomLink> links;

    // Constructors, getters, and setters

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public List<RoomLink> getLinks() {
        return links;
    }

    public void setLinks(List<RoomLink> links) {
        this.links = links;
    }

    public static class RoomLink {
        @JsonProperty("rel")
        private String rel;

        @JsonProperty("href")
        private String href;

        @JsonProperty("templated")
        private boolean templated;

        // Constructors, getters, and setters

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

        public boolean isTemplated() {
            return templated;
        }

        public void setTemplated(boolean templated) {
            this.templated = templated;
        }
    }

    public String getReserveLink() {
        if (links != null) {
            for (RoomLink link : links) {
                if ("reserve".equals(link.getRel())) {
                    return link.getHref();
                }
            }
        }
        return null;
    }
}
