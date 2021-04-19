package kakuom.com.restaurant.outputDto;

import kakuom.com.restaurant.models.Reservation;
import kakuom.com.restaurant.models.enums.AreaEnum;
import kakuom.com.restaurant.models.enums.TableNumberEnum;
import kakuom.com.restaurant.models.enums.TableStatusEnum;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResWithFreeTable {
    private List<Reservation> reservations;
    private List<CancelledResDto> cancelled;
    private Map<AreaEnum, List<TableDto>> freeMappedTables;
    private List<TableDto>freeTables;

    public ResWithFreeTable(List<Reservation> reservations,List<CancelledResDto> cancelled ) {
        this.reservations = reservations;
//       this.freeMappedTables = computeMappedFreeTables();
        this.freeTables = computeFreeTables();
        this.cancelled = cancelled;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Map<AreaEnum, List<TableDto>> getFreeMappedTables() {
        return freeMappedTables;
    }

    public void setFreeMappedTables(Map<AreaEnum, List<TableDto>> freeMappedTables) {
        this.freeMappedTables = freeMappedTables;
    }

    public List<TableDto> getFreeTables() {
        return freeTables;
    }

    public void setFreeTables(List<TableDto> freeTables) {
        this.freeTables = freeTables;
    }

    public List<CancelledResDto> getCancelled() {
        return cancelled;
    }

    public void setCancelled(List<CancelledResDto> cancelled) {
        this.cancelled = cancelled;
    }

    private List<TableDto> computeFreeTables(){
        var tablesInRes = this.reservations
                .stream()
                .flatMap(reservation -> reservation
                        .getResTables()
                        .stream()
                        .filter(t -> t.getStatus() != TableStatusEnum.AVAILABLE)
                        .map(table -> table.getNumber())
                )
                .collect(Collectors.toList());

        return EnumSet.allOf(TableNumberEnum.class)
                .stream()
                .filter(table -> !tablesInRes.contains(table))
                .map(TableDto::new)
                .collect(Collectors.toList());
    }

    private Map<AreaEnum, List<TableDto>> computeMappedFreeTables() {
        var tablesInRes = this.reservations
                .stream()
                .flatMap(reservation -> reservation
                        .getResTables()
                        .stream()
                        .filter(t -> t.getStatus() != TableStatusEnum.AVAILABLE)
                        .map(table -> table.getNumber())
                )
                .collect(Collectors.toList());

        return EnumSet.allOf(TableNumberEnum.class)
                .stream()
                .filter(table -> !tablesInRes.contains(table))
                .map(TableDto::new)
                .collect(Collectors.groupingBy(TableDto::getArea));
    }
}
