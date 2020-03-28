import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(value = "patient_meta")
public class Calendar {

    List<Appointment> appointments;
    List<Timeslot> timeslots;
    List<TimeslotType> timeslottypes;

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
