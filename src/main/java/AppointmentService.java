import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentService {
    List<Appointment> findAppointmentsInCalendar(Calendar calendar, Date startOfPeriodToSearch, Date endOfPeriodToSearch) {
        List<Appointment> appointments = new ArrayList<>();
        //Existing appointments in the selected time period.
        for (Appointment appointment : calendar.getAppointments()) {
            if (appointment.getStart().after(startOfPeriodToSearch)
                    && appointment.getEnd().before(endOfPeriodToSearch)) {
                //System.out.println("Appointment: " + appointment.getStart() + " - " + appointment.getEnd());
                appointments.add(appointment);
            }
        }
        return appointments;
    }
}
