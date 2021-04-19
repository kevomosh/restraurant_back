package kakuom.com.restaurant.repositories;

import kakuom.com.restaurant.models.Reservation;
import kakuom.com.restaurant.models.enums.ResStatusEnum;
import kakuom.com.restaurant.outputDto.CancelledResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT DISTINCT r FROM Reservation r JOIN FETCH " +
            "r.tables WHERE  r.sitting.id = :sitId ORDER BY r.startTime")
    List<Reservation> getAllBySittingId(@Param("sitId") Long sittingIt);

//    @Query(value = "SELECT r FROM Reservation r WHERE " +
//            "r.sitting.id = :sitId AND r.status = :status")
//    List<Reservation> getCancelledBySittingId(@Param("sitId") Long sittingIt,
//                                               @Param("status")ResStatusEnum resStatusEnum);

    @Query(value = "SELECT new kakuom.com.restaurant.outputDto.CancelledResDto " +
            "(r.source, r.firstName, r.lastName, r.email, r.phoneNumber, r.startTime) " +
            "FROM Reservation r WHERE r.sitting.id = :sitId AND r.status = :status")
    List<CancelledResDto> getCancelledBySittingId(@Param("sitId") Long sittingIt,
                                                  @Param("status") ResStatusEnum resStatusEnum);

    @Query(value = "SELECT DISTINCT r FROM Reservation r JOIN FETCH r.tables" +
            " WHERE r.sitting.date = :date ORDER BY r.startTime")
    List<Reservation> getAllByDate(@Param("date") LocalDate date);

    @Query(value = "SELECT r FROM Reservation r JOIN FETCH r.tables WHERE r.id = :reservationId")
    Optional<Reservation> getById(@Param("reservationId") Long reservationId);

    @Query(value = "SELECT DISTINCT r.id FROM Reservation r WHERE r.sitting.id = :sitId ")
    List<Long> getIdsInSitting(@Param("sitId") Long sittingIt);
}
