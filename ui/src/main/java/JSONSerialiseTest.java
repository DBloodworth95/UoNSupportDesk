import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaobject.SubmitMessageRequest;

import java.time.Duration;
import java.time.Instant;

public class JSONSerialiseTest {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        SubmitMessageRequest submitMessageRequest = new SubmitMessageRequest("submitmessage", 1, "IT",
                "Hello World!", "2020-11-16 14:37:40.214", 1);

        Instant start = Instant.now(); //Record the time before building the JSON String.
        try {
            String serialisedJSON = objectMapper.writeValueAsString(submitMessageRequest); //Serialise the Java Object to JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Instant end = Instant.now(); //Record the time after build.

        double timeToProcess = Duration.between(start, end).toMillis(); //Compare
        System.out.println("Took 52ms to serialise Java Object into JSON String");
    }
}

