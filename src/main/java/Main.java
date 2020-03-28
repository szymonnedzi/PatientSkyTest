import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("C:\\Users\\Darek\\Desktop\\PatientSkyTest\\src\\main\\resources\\CalendarJsons\\Danny boy.json");
        Calendar calendar = objectMapper.readValue(file, Calendar.class);
    }
}
