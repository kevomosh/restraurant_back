package kakuom.com.restaurant.inputDto;

import javax.validation.constraints.*;

public class ResInfo {
    @NotBlank(message = "Reservation source is required")
    private String sourceStr;

    @NotBlank(message = "Firstname is required")
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 7, max = 50)
    private String phoneNumber;

    @NotBlank
    private TimeInfo startTime;


    @Max(value = 2, message = "Reservation cant be more than 2 hours")
    @Min(0)
    private long durHr;

    @Max(value = 59)
    @Min(0)
    private long durMin;

    @Min(0)
    private int noOfGuests;

    private String notes;

    public ResInfo(String sourceStr, String firstName, String lastName,
                   String email, String phoneNumber,TimeInfo startTime , long durHr, long durMin, int noOfGuests,
                   String notes) {
        this.sourceStr = sourceStr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.durHr = durHr;
        this.durMin = durMin;
        this.noOfGuests = noOfGuests;
        this.notes = notes;
    }

    public String getSourceStr() {
        return sourceStr;
    }

    public void setSourceStr(String sourceStr) {
        this.sourceStr = sourceStr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public TimeInfo getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeInfo startTime) {
        this.startTime = startTime;
    }

    public long getDurHr() {
        return durHr;
    }

    public void setDurHr(long durHr) {
        this.durHr = durHr;
    }

    public long getDurMin() {
        return durMin;
    }

    public void setDurMin(long durMin) {
        this.durMin = durMin;
    }

    public int getNoOfGuests() {
        return noOfGuests;
    }

    public void setNoOfGuests(int noOfGuests) {
        this.noOfGuests = noOfGuests;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
