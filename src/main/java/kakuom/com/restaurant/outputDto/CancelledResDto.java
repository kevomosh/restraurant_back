package kakuom.com.restaurant.outputDto;

import kakuom.com.restaurant.models.enums.SourceEnum;

import java.time.LocalTime;

public class CancelledResDto {
    private SourceEnum source;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalTime startTime;

    public CancelledResDto(SourceEnum source, String firstName, String lastName, String email, String phoneNumber, LocalTime startTime) {
        this.source = source;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
    }

    public SourceEnum getSource() {
        return source;
    }

    public void setSource(SourceEnum source) {
        this.source = source;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
