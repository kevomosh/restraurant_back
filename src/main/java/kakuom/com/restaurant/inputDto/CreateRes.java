package kakuom.com.restaurant.inputDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CreateRes {
    @NotNull(message = "reservation details required")
    private ResInfo resInfo;

    @Min(0)
    private long sittingId;

    @NotNull(message = "Table information required")
    private Set<TableInfo> tableInfoSet;

    private Long reservationId;

    public CreateRes(ResInfo reservation,
                     Set<TableInfo> tableInfoSet, Long sittingId,
                     long reservationId) {

        this.resInfo = reservation;
        this.tableInfoSet = tableInfoSet;
        this.sittingId = sittingId;
        this.reservationId = reservationId;
    }


    public ResInfo getResInfo() {
        return resInfo;
    }

    public void setResInfo(ResInfo resInfo) {
        this.resInfo = resInfo;
    }

    public long getSittingId() {
        return sittingId;
    }

    public void setSittingId(long sittingId) {
        this.sittingId = sittingId;
    }

    public Set<TableInfo> getResTableInfoSet() {
        return tableInfoSet;
    }

    public void setResTableInfoSet(Set<TableInfo> tableInfoSet) {
        this.tableInfoSet = tableInfoSet;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
