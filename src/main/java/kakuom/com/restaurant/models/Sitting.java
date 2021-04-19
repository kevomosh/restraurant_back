package kakuom.com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kakuom.com.restaurant.inputDto.SittingInfo;
import kakuom.com.restaurant.models.enums.CategoryEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sitting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer capacity;

    private Boolean isClosed;

    @JsonIgnore
    @OneToMany(
            mappedBy = "sitting",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reservation> reservations = new ArrayList<>();

    public Sitting() {
    }

    public Sitting(SittingInfo info) {
       this.createOrUpdate(info);
        this.isClosed = false;
    }

    public void updateSitting(SittingInfo info) {
        this.createOrUpdate(info);
        this.isClosed = info.isClosed();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setSitting(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setSitting(null);
    }

    private void createOrUpdate(SittingInfo info) {
        this.category = CategoryEnum.valueOf(info.getCategoryStr());
        this.date = LocalDate.of(info.getDate().getYear(), info.getDate().getMonth(), info.getDate().getDay());
        this.startTime = LocalTime.of(info.getStartTime().getHour(), info.getStartTime().getMinute());
        this.endTime = LocalTime.of(info.getEndTime().getHour(), info.getEndTime().getMinute());
        this.capacity = info.getCapacity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum sittingCategory) {
        this.category = sittingCategory;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sitting sitting = (Sitting) o;

        if (!getId().equals(sitting.getId())) return false;
        return getDate().equals(sitting.getDate());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getDate().hashCode();
        return result;
    }
}
