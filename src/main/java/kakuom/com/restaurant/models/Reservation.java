package kakuom.com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kakuom.com.restaurant.inputDto.CreateRes;
import kakuom.com.restaurant.inputDto.ResInfo;
import kakuom.com.restaurant.inputDto.TableInfo;
import kakuom.com.restaurant.models.enums.ResStatusEnum;
import kakuom.com.restaurant.models.enums.SourceEnum;
import kakuom.com.restaurant.models.enums.TableStatusEnum;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SourceEnum source;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer noOfGuests;

    private String notes;

    private OffsetDateTime actualStartTime;

    private OffsetDateTime actualEndTime;

    @Enumerated(value = EnumType.STRING)
    private ResStatusEnum status;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "sitting_id")
    private Sitting sitting;

    @OneToMany(
            mappedBy = "reservation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Tables> tables = new ArrayList<>();

    public Reservation() {
    }

    public Reservation(ResInfo info){
        createOrUpdateReservation(info);
    }

    public void updateReservation(CreateRes createRes) {
        createOrUpdateReservation(createRes.getResInfo());
//        var newTableNumberList = createRes.getResTableInfoSet()
//                .stream().map(t -> t.getNumberStr())
//                .collect(Collectors.toList());
//
//       var partion=  tables.stream()
//                .collect(Collectors.partitioningBy(t -> !newTableNumberList.contains(t.getNumber())));

   //     System.out.println(tables);
       // tables.stream()
        tables.clear();
//        for (Tables table: tables) {
//            removeTable(table);
//        }
    }

    private void createOrUpdateReservation(ResInfo info){
        this.source = SourceEnum.valueOf(info.getSourceStr());
        this.firstName = info.getFirstName();
        this.lastName = info.getLastName();
        this.email = info.getEmail();
        this.phoneNumber = info.getPhoneNumber();
        this.startTime = LocalTime.of(info.getStartTime().getHour(),
                info.getStartTime().getMinute());
        this.endTime =  this.startTime.plusHours(info.getDurHr()).plusMinutes(info.getDurMin());
        this.noOfGuests = info.getNoOfGuests();
        this.notes = info.getNotes();
        this.status = ResStatusEnum.PENDING;
    }

    public void addTables(Set<TableInfo> tableInfoSet){
        for (TableInfo tableInfo : tableInfoSet) {
            var table = new Tables(tableInfo);
            tables.add(table);
            table.setReservation(this);
        }
    }

    public void removeTable(Tables table){
        tables.remove(table);
        table.setReservation(null);
       // this.tables.clear();
    }

    public void confirm(){
        if (getStatus() != ResStatusEnum.CONFIRMED) {
            setStatus(ResStatusEnum.CONFIRMED);
            for (Tables table: getResTables()){
                table.setStatus(TableStatusEnum.CONFIRMED_BOOKING);
            }
        }
    }

    public void cancel(){
        if (getStatus() != ResStatusEnum.CANCELLED) {
            setStatus(ResStatusEnum.CANCELLED);
            this.tables.clear();
        }
    }

    public void handleSeated(){
        var currentStatus = getStatus();
        if (currentStatus != ResStatusEnum.SEATED) {
            setStatus(ResStatusEnum.SEATED);
            setActualStartTime(OffsetDateTime.now());
            for (Tables table: getResTables()){
                table.setStatus(TableStatusEnum.OCCUPIED);
            }
        }
    }

    public void handleCompleted(){
        if (getStatus() != ResStatusEnum.COMPLETED) {
            setStatus(ResStatusEnum.COMPLETED);
            setActualEndTime(OffsetDateTime.now());
            for (Tables table: getResTables()){
                table.setStatus(TableStatusEnum.AVAILABLE);
            }
        }
    }

    public Long getId() {
        return id;
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

    public Sitting getSitting() {
        return sitting;
    }

    public void setSitting(Sitting sitting) {
        this.sitting = sitting;
    }

    public Integer getNoOfGuests() {
        return noOfGuests;
    }

    public void setNoOfGuests(Integer noOfGuests) {
        this.noOfGuests = noOfGuests;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<Tables> getResTables() {
        return tables;
    }

    public void setResTables(List<Tables> tables) {
        this.tables = tables;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ResStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ResStatusEnum status) {
        this.status = status;
    }

    public OffsetDateTime getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(OffsetDateTime actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public OffsetDateTime getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(OffsetDateTime actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}
