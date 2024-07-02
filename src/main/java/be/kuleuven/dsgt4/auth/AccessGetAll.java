package be.kuleuven.dsgt4.auth;

import be.kuleuven.dsgt4.User;
import be.kuleuven.dsgt4.UserBookingMessage;
import be.kuleuven.dsgt4.UserMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class AccessGetAll {

    @Autowired
    private Firestore db;

    @GetMapping("/api/getAllCustomers")
    public List<User> getAllCustomers() throws InterruptedException, ExecutionException {
        List<User> customers = new ArrayList<>();

        // Retrieve all documents from the "usermessages" collection
        List<QueryDocumentSnapshot> documents = db.collection("usermessages").get().get().getDocuments();

        // Extract unique customers and their roles from the documents
        for (QueryDocumentSnapshot document : documents) {
            UserMessage userMessage = UserMessage.fromDoc(document.getData());
            String email = userMessage.getCustomer();
            String role = userMessage.getRole();

            // Check if the customer already exists in the list
            boolean isExistingCustomer = customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));

            if (!isExistingCustomer) {
                User customer = new User(email, role);
                customers.add(customer);
            }
        }

        return customers;
    }

    @GetMapping("/api/getAllOrders")
    public List<UserBookingMessage> getAllOrders() throws InterruptedException, ExecutionException {
        List<UserBookingMessage> orders = new ArrayList<>();

        // Retrieve all documents from the "userbookingmessage" collection
        List<QueryDocumentSnapshot> documents = db.collection("userbookingmessage").get().get().getDocuments();

        // Extract orders from the documents
        for (QueryDocumentSnapshot document : documents) {
            Map<String, Object> data = document.getData();
            UserBookingMessage order = UserBookingMessage.fromDoc(data);
            orders.add(order);
        }

        return orders;
    }
}
