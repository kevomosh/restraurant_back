package kakuom.com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kakuom.com.restaurant.inputDto.TableInfo;
import kakuom.com.restaurant.models.enums.AreaEnum;
import kakuom.com.restaurant.models.enums.TableNumberEnum;
import kakuom.com.restaurant.models.enums.TableStatusEnum;

import javax.persistence.*;

@Entity
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AreaEnum area;

    @Enumerated(value = EnumType.STRING)
    private TableNumberEnum number;

    @Enumerated(value = EnumType.STRING)
    private TableStatusEnum status;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "res_id")
    private Reservation reservation;

    public Tables() {
    }


    public Tables(TableInfo tableInfo) {
        this.area = AreaEnum.valueOf(tableInfo.getAreaStr());
        this.number = TableNumberEnum.valueOf(tableInfo.getNumberStr());
        this.status = TableStatusEnum.BOOKED;
    }

    public Long getId() {
        return id;
    }


    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }

    public TableNumberEnum getNumber() {
        return number;
    }

    public void setNumber(TableNumberEnum number) {
        this.number = number;
    }

    public TableStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TableStatusEnum status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tables tables = (Tables) o;

        if (!getId().equals(tables.getId())) return false;
        if (getArea() != tables.getArea()) return false;
        return getNumber().equals(tables.getNumber());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
