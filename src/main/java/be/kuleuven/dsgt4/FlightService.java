package be.kuleuven.dsgt4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;


@Service
public class FlightService {

    private final WebClient webClient;
    private final String apiKey = "Iw8zeveVyaPNWonPNaU0213uw3g6Ei";
    private final String flightHost = "94.110.88.19";

    // Constructor injection of the WebClient.Builder
    public FlightService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    public Mono<List<FlightDTO>> getAllFlights(String departure, String destination, String startDate, String endDate ) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(flightHost)
                        .port(8088)
                        .path("/rest/flights")
                        .queryParam("api", apiKey)
                        .queryParam("origin", departure)
                        .queryParam("destination", destination)
                        .queryParam("startDate", startDate )
                        .queryParam("endDate",  endDate )
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        return Mono.just(extractFlightDetails(response));
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                });
    }

    public Mono<FlightDTO> getAvailableFlight(String departure, String destination, String startDate, String endDate, int numOfTickets ) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(flightHost)
                        .port(8088)
                        .path("/rest/flights")
                        .queryParam("api", "Iw8zeveVyaPNWonPNaU0213uw3g6Ei")
                        .queryParam("origin", departure)
                        .queryParam("destination", destination)
                        .queryParam("startDate", startDate )
                        .queryParam("endDate",  endDate )
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        return Mono.just(extractFlightDetails(response));
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                }).flatMap(flightList -> {
                    if (flightList.isEmpty()) {
                        return Mono.empty();
                    }

                    for (FlightDTO flight : flightList) {
                        if (flight.getAvailableSeatCount() >= numOfTickets) {
                            return Mono.just(flight);
                        }
                    }
                    return Mono.empty();
                });
    }

    public Mono<SeatDTO> getAvailableCheapestSeat(FlightDTO flight) {
        String link = flight.get_links().getCheapest().getHref();

        URI uri = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("api", apiKey)
                .build()
                .toUri();
        return this.webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SeatDTO.class);
    }

    public Mono<FlightBookingMessage> reserveSeat(SeatDTO seat) {
        String link = seat.get_links().getReserve().getHref();
        URI uri = UriComponentsBuilder.fromHttpUrl(link)
                .queryParam("api", apiKey)
                .build()
                .toUri();
        return this.webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(FlightBookingMessage.class);
    }

    public Mono<FlightBookingMessage> bookByLink(String bookingLink) {
        URI uri = UriComponentsBuilder.fromHttpUrl(bookingLink)
                .queryParam("api", apiKey)
                .build()
                .toUri();
        return this.webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(FlightBookingMessage.class);
    }



    private List<FlightDTO> extractFlightDetails(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        jsonNode = objectMapper.readTree(jsonString);

        JsonNode flightListJSON = jsonNode.get("_embedded").get("flightList");
        List<FlightDTO> flights = objectMapper.convertValue(flightListJSON, new TypeReference<List<FlightDTO>>(){});
        return flights;
    }



}
