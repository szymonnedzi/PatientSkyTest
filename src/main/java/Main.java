import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws IOException {
        CalendarService calendarService = new CalendarService();
        List<Calendar> calendars = calendarService.getAllCalendars();

        for (Calendar calendar : calendars) {
            System.out.println(calendar.getCalendarID());
        }
    }


    //findAvailableTime(array<Uuid> calendarIds, Integer duration, Iso8601TimeInterval periodToSearch [,Uuid timeSlotType]);
}
