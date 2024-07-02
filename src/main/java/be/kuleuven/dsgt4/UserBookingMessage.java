package be.kuleuven.dsgt4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBookingMessage {
    @JsonProperty("email")
    private String email;
    @JsonProperty("flight")
    private FlightDTO flight;
    @JsonProperty("flightMessage")
    private FlightBookingMessage flightMessage;
    @JsonProperty("hotelMessage")
    private HotelBookingMessage hotelMessage;
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("time")
    private LocalDateTime time;

    public UserBookingMessage(){}
    public UserBookingMessage(UUID id, LocalDateTime time, String email, FlightDTO flight, FlightBookingMessage flightMessage, HotelBookingMessage hotelMessage) {
        this.id = id;
        this.time = time;
        this.email = email;
        this.flight = flight;
        this.flightMessage = flightMessage;
        this.hotelMessage = hotelMessage;
    }

    public HotelBookingMessage getHotelMessage() {
        return hotelMessage;
    }

    public void setHotelMessage(HotelBookingMessage hotelMessage) {
        this.hotelMessage = hotelMessage;
    }

    public FlightBookingMessage getFlightMessage() {
        return flightMessage;
    }

    public void setFlightMessage(FlightBookingMessage flightMessage) {
        this.flightMessage = flightMessage;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }

    public Map<String, Object> toDoc(){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("id", this.id.toString());
        data.put("time", this.time.format(DateTimeFormatter.ISO_DATE_TIME));
        data.put("email", this.email);
        try {
            data.put("flight", objectMapper.writeValueAsString(this.flight));
            data.put("flightOrder", objectMapper.writeValueAsString(this.flightMessage));
            data.put("hotelOrder", objectMapper.writeValueAsString(this.hotelMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static UserBookingMessage fromDoc(Map<String, Object> doc) {
        ObjectMapper mapper = new ObjectMapper();
        
        UUID id = UUID.fromString((String) doc.get("id"));
        LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse((String) doc.get("time")));
        String email = (String) doc.get("email");
        
        try {
            FlightDTO flight = mapper.readValue((String) doc.get("flight"), FlightDTO.class);
            FlightBookingMessage flightMessage = mapper.readValue((String) doc.get("flightOrder"), FlightBookingMessage.class);
            HotelBookingMessage hotelMessage = mapper.readValue((String) doc.get("hotelOrder"), HotelBookingMessage.class);
            return new UserBookingMessage(id, time, email, flight, flightMessage, hotelMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Map<String, Object> updateBookingMessage(FlightBookingMessage flightMessage, HotelBookingMessage hotelMessage) {
        ObjectMapper mapper = new ObjectMapper();
        // Create a map of the fields you want to update
        Map<String, Object> updates = new HashMap<>();
        try {
            updates.put("flightOrder", mapper.writeValueAsString(flightMessage));
            updates.put("hotelOrder", mapper.writeValueAsString(hotelMessage)); // Using server timestamp
            return updates;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
