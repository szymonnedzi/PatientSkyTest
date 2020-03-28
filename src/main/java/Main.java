import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        TypeReference<List<Appointment>> typeReference = new TypeReference<List<Appointment>>(){};
        Path currentDir = Paths.get("");
        Path targetDir = Paths.get(currentDir + "src/main/resources/CalendarJsons/Danny boy appointments.json");
        File file = new File(String.valueOf(targetDir));
        List<Appointment> appointments = objectMapper.readValue(file, typeReference);
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }


    }
}
