import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeslotService {
    List<Timeslot> findAllTimeslotsInCalendar(Calendar calendar, Date startOfSearchPeriod, Date endOfSearchPeriod) {
        List<Timeslot> allTimeslots = calendar.getTimeslots();
        List<Timeslot> foundTimeslots = new ArrayList<>();
        for (Timeslot timeslot : allTimeslots) {
            if (timeslot.getStart().after(startOfSearchPeriod)
                    && timeslot.getEnd().before(endOfSearchPeriod)) {
                //System.out.println("Timeslot:" + timeslot.getStart() + " - " + timeslot.getEnd());
                foundTimeslots.add(timeslot);
            }
        }
        return foundTimeslots;
    }


    long calculateDuration(Timeslot timeslot) {
        LocalDateTime startDate = timeslot.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = timeslot.getEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return ChronoUnit.MINUTES.between(startDate, endDate);
    }
}
