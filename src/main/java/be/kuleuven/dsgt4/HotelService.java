package be.kuleuven.dsgt4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Service
public class HotelService {

    private final WebClient webClient;
    private final String apiKey = "Iw8zeveVyaPNWonPNaU0213uw3g6Ei";
    private final String hotelHost = "94.110.88.19";

    // Constructor injection of the WebClient.Builder
    public HotelService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    public Flux<HotelDTO> getAllHotels(String location) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(hotelHost)
                        .port(8087)
                        .path("/rest/hotels/")
                        .queryParam("api", apiKey)
                        .queryParam("location", location)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(response -> {
                    try {
                        return extractHotelDetails(response);
                    } catch (JsonProcessingException e) {
                        return Flux.error(e);
                    }
                });
    }

    public Mono<HotelDTO> getAvailableHotel(String location, int numOfPeople) {
        return getAllHotels(location)
                .filter(hotel -> hotel.getRoomCount() >= numOfPeople)
                .next();
    }

    public Mono<RoomDTO> getCheapestRoom(String id, String startDate, String endDate) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(hotelHost)
                        .port(8087)
                        .path("/rest/hotels/{id}/cheapest/{start}/{end}")
                        .queryParam("api", apiKey)
                        .build(id, startDate, endDate))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        return extractRoomDetails(response);
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                });
    }

    public Mono<HotelBookingMessage> reserveCheapestRoom(RoomDTO room)  {
        String link = room.getReserveLink();
        URI uri = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("api", apiKey)
                .build()
                .toUri();
        return this.webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(HotelBookingMessage.class);
    }

    public Mono<HotelBookingMessage> bookByLink(String bookingLink) {
        URI uri = UriComponentsBuilder.fromHttpUrl(bookingLink)
                .queryParam("api", apiKey)
                .build()
                .toUri();
        return this.webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(HotelBookingMessage.class);
    }



    private Flux<HotelDTO> extractHotelDetails(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode hotelListJSON = jsonNode.path("_embedded").path("hotelList");
        if (hotelListJSON.isMissingNode()) {
            return Flux.empty();  // Return an empty Flux if no hotels are found
        }

        Flux<HotelDTO> hotels = Flux.fromIterable(objectMapper.convertValue(hotelListJSON, new TypeReference<List<HotelDTO>>(){}));
        return hotels;
    }

    private Mono<RoomDTO> extractRoomDetails(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode roomJSON = jsonNode.path("room");
        if (roomJSON.isMissingNode()) {
            return Mono.empty();  // Return an empty Flux if no hotels are found
        }
        Mono<RoomDTO> room = Mono.just(objectMapper.convertValue(roomJSON, new TypeReference<RoomDTO>(){}));
        return room;
    }
}