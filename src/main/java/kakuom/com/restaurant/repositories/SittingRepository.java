package kakuom.com.restaurant.repositories;

import kakuom.com.restaurant.models.Sitting;
import kakuom.com.restaurant.models.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SittingRepository extends JpaRepository<Sitting, Long> {
    Optional<Sitting> findByDateAndCategory(LocalDate date, CategoryEnum category);

    Boolean existsByDateAndCategory(LocalDate date, CategoryEnum category);


    @Query(value = "SELECT SUM(no_of_guests) FROM reservation r" +
            " WHERE r.sitting_id = :sitId AND r.status != 'CANCELLED'"
            ,nativeQuery = true)
    Optional<Long> getCurrentCapacity(@Param("sitId") Long sittingId);

    @Query(value = "SELECT s.id FROM Sitting s " +
            "WHERE s.date = :sittingDate AND s.category = :category")
    Optional<Long> getIdByDate(@Param("sittingDate") LocalDate sittingDate,
                               @Param("category") CategoryEnum sittingCategory);


    @Query(value = "SELECT s FROM Sitting s WHERE s.date = :sittingDate")
    List<Sitting> getAllByDate(@Param("sittingDate") LocalDate sittingDate);

    @Query(value = "SELECT s  FROM Sitting s " +
            "WHERE s.date = :sittingDate AND s.category = :category")
    Optional<Sitting> getByDate(@Param("sittingDate") LocalDate sittingDate,
                                @Param("category") CategoryEnum sittingCategory);
}
