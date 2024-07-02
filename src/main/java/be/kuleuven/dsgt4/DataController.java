package be.kuleuven.dsgt4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class DataController {

    @Autowired
    Firestore db;

    @GetMapping("/readData")
    public List<String> readDataFromFirestore() throws InterruptedException, ExecutionException {
        System.out.println("Reading data from Firestore...");
        try {
            CollectionReference usermessages = db.collection("usermessages");
            Set<String> seenEmails = new HashSet<>();
            List<String> dataToDisplay = usermessages.get().get().getDocuments().stream()
                .map(document -> formatDocument(document))
                .filter(document -> seenEmails.add(document))
                .collect(Collectors.toList());
            dataToDisplay.forEach(System.out::println);
            return dataToDisplay;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch data from Firestore", e);
        }
    }

    private String formatDocument(DocumentSnapshot document) {
        String customer = document.getString("customer");
        String role = document.getString("role");
        return String.format("Customer: %s, Role: %s", customer, role);
    }
    
    @RequestMapping(value = "/addData", method = RequestMethod.POST)
    public String updateRole(
            @RequestParam("customer") String customer,
            @RequestParam("role") String role) throws ExecutionException, InterruptedException {
        CollectionReference usermessages = db.collection("usermessages");

        List<QueryDocumentSnapshot> documentSnapshots = usermessages.whereEqualTo("customer", customer).get().get().getDocuments();
        if (documentSnapshots.isEmpty()) {
            return "No document found for customer: " + customer;
        }
        DocumentSnapshot document = documentSnapshots.get(0);
        // Update the role
        DocumentReference documentReference = usermessages.document(document.getId());
        documentReference.set(Map.of("role", role), SetOptions.merge()).get();
        return "Role updated for customer: " + customer;
    }
}
