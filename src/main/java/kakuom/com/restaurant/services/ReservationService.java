package kakuom.com.restaurant.services;

import kakuom.com.restaurant.inputDto.CreateRes;
import kakuom.com.restaurant.inputDto.DateInfo;
import kakuom.com.restaurant.models.Reservation;
import kakuom.com.restaurant.models.enums.CategoryEnum;
import kakuom.com.restaurant.models.enums.ResStatusEnum;
import kakuom.com.restaurant.outputDto.DateCategoryDto;
import kakuom.com.restaurant.outputDto.ResWithFreeTable;
import kakuom.com.restaurant.repositories.ReservationRepository;
import kakuom.com.restaurant.repositories.SittingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SittingRepository sittingRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              SittingRepository sittingRepository) {
        this.reservationRepository = reservationRepository;
        this.sittingRepository = sittingRepository;
    }

    public ResWithFreeTable getAllByDayAndCategory(DateInfo dateInfo, String categoryStr) {
        var dateCategoryDto = getDateCategoryDto(dateInfo, categoryStr);
        var sittingId = sittingRepository
                .getIdByDate(dateCategoryDto.getDate(), dateCategoryDto.getCategory())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Sitting not in record"));

        var reservations = reservationRepository.getAllBySittingId(sittingId);
        var cancelled = reservationRepository.getCancelledBySittingId(sittingId,
                ResStatusEnum.CANCELLED);
        return new ResWithFreeTable(reservations, cancelled);
    }

    public ResWithFreeTable getAllBySittingId(Long sittingId) {
        if (!sittingRepository.existsById(sittingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sitting not available");
        }
        var reservations = reservationRepository.getAllBySittingId(sittingId);
        var cancelled = reservationRepository.getCancelledBySittingId(sittingId,
                ResStatusEnum.CANCELLED);
        return new ResWithFreeTable(reservations, cancelled);
    }

    public ResWithFreeTable update(CreateRes info) {
        if (Objects.isNull(info.getReservationId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sitting id is required");
        }
        var sitting = sittingRepository.findById(info.getSittingId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sitting not available"));

        var reservation = reservationRepository.findById(info.getReservationId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sitting not available"));
        var currentCapacity = sittingRepository.getCurrentCapacity(sitting.getId()).orElse((long)0);
        var availableCapacity = sitting.getCapacity() - (currentCapacity.intValue() - reservation.getNoOfGuests());
        var guestNumbers = info.getResInfo().getNoOfGuests();
        if ( guestNumbers > availableCapacity){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not " +
                    "enough available slots for the number of guests");
        }

        reservation.updateReservation(info);
        if (guestNumbers == availableCapacity) {
            sitting.setCategory(CategoryEnum.BOOKED_OUT);
        }
        reservation.addTables(info.getResTableInfoSet());
        reservationRepository.save(reservation);
        sittingRepository.save(sitting);
        var reservations = reservationRepository.getAllBySittingId(sitting.getId());
        var cancelled = reservationRepository.getCancelledBySittingId(sitting.getId(),
                ResStatusEnum.CANCELLED);
        return new ResWithFreeTable(reservations, cancelled);
    }

    public ResWithFreeTable create(CreateRes info){
        var sitting = sittingRepository.findById(info.getSittingId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sitting not available"));

        if (sitting.getCategory() == CategoryEnum.BOOKED_OUT ||
                sitting.getCategory() == CategoryEnum.PRIVATE_EVENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sitting is booked out or is Private event");
        }

        var currentCapacity = sittingRepository.getCurrentCapacity(sitting.getId()).orElse((long)0);
        var availableCapacity = sitting.getCapacity() - currentCapacity.intValue();

        var guestNumbers = info.getResInfo().getNoOfGuests();
        if ( guestNumbers > availableCapacity){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not " +
                    "enough available slots for the number of guests");
        }

        var reservation = new Reservation(info.getResInfo());
        reservation.addTables(info.getResTableInfoSet());
        sitting.addReservation(reservation);
        if (guestNumbers == availableCapacity) {
            sitting.setCategory(CategoryEnum.BOOKED_OUT);
        }
        sittingRepository.save(sitting);

        var reservations = reservationRepository.getAllBySittingId(sitting.getId());
        var cancelled = reservationRepository.getCancelledBySittingId(sitting.getId(),
                ResStatusEnum.CANCELLED);
        return new ResWithFreeTable(reservations, cancelled);
    }

    public Reservation modifyState(Long reservationId, String action){
        var reservation = reservationRepository.getById(reservationId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not available"));

        switch (action) {
            case "confirm":
                reservation.confirm();
                break;
            case "seat":
                reservation.handleSeated();
                break;
            case "complete":
                reservation.handleCompleted();
                break;
            case "cancel":
                reservation.cancel();
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pick valid action");
        }

        reservationRepository.save(reservation);
        return reservation;
    }

    private DateCategoryDto getDateCategoryDto(DateInfo dateInfo, String categoryStr) {
        var date = LocalDate.of(dateInfo.getYear(),
                dateInfo.getMonth(), dateInfo.getDay());
        var category = CategoryEnum.valueOf(categoryStr);
        return new DateCategoryDto(date, category);
    }
}
