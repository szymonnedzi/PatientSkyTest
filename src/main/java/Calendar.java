import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(value = "patient_meta")
public class Calendar {

    @JsonIgnore
    UUID calendarID;
    List<Appointment> appointments;
    List<Timeslot> timeslots;
    List<TimeslotType> timeslottypes;

    public UUID getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(UUID calendarID) {
        this.calendarID = calendarID;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public List<TimeslotType> getTimeslottypes() {
        return timeslottypes;
    }

    public void setTimeslottypes(List<TimeslotType> timeslottypes) {
        this.timeslottypes = timeslottypes;
    }
}
