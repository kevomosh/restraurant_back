package kakuom.com.restaurant.services;

import kakuom.com.restaurant.models.Tables;
import kakuom.com.restaurant.models.enums.AreaEnum;
import kakuom.com.restaurant.models.enums.TableNumberEnum;
import kakuom.com.restaurant.repositories.ReservationRepository;
import kakuom.com.restaurant.repositories.TableRepository;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TableService {
    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public TableService(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public Map<AreaEnum, List<Tables>> getAllInSitting(Long sittingId){
        var resIdList = reservationRepository.getIdsInSitting(sittingId);
        return tableRepository.getAllInSitting(resIdList)
                .stream()
                .collect(Collectors.groupingBy(Tables::getArea));
    }

    public List<TableNumberEnum> getFreeInSitting(Long sittingId){
        var allTables = EnumSet.allOf(TableNumberEnum.class);
        var resIdList = reservationRepository.getIdsInSitting(sittingId);
        var bookedTables =  tableRepository.getAllInSitting(resIdList)
                .stream()
                .map(t -> t.getNumber())
                .collect(Collectors.toList());

        return allTables.stream()
                .filter(t -> !bookedTables.contains(t))
                .collect(Collectors.toList());
    }
}
