package kakuom.com.restaurant.controllers;

import kakuom.com.restaurant.inputDto.CreateRes;
import kakuom.com.restaurant.inputDto.DateInfo;
import kakuom.com.restaurant.models.Reservation;
import kakuom.com.restaurant.outputDto.ResWithFreeTable;
import kakuom.com.restaurant.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<ResWithFreeTable> create(
            @Valid @RequestBody CreateRes createRes){
        return ResponseEntity.ok(reservationService.create(createRes));
    }

    @PutMapping()
    public ResponseEntity<ResWithFreeTable> update(
            @Valid @RequestBody CreateRes createRes){
        return ResponseEntity.ok(reservationService.update(createRes));
    }

    @PatchMapping("/{action}/{reservationId}")
    public ResponseEntity<Reservation>  modifyState(
            @PathVariable Long reservationId, @PathVariable String action) {
        return ResponseEntity.ok(reservationService.modifyState(reservationId, action));
    }


    @GetMapping("/{sittingId}")
    public ResponseEntity<ResWithFreeTable> bySittingId(
            @PathVariable Long sittingId
    ){
        return ResponseEntity.ok(reservationService.getAllBySittingId(sittingId));
    }

    @GetMapping("/date/{categoryStr}")
    public ResponseEntity<ResWithFreeTable> byDate(
            @PathVariable String categoryStr,
            @Valid @RequestBody DateInfo dateInfo
    ){
        return ResponseEntity.ok(reservationService.getAllByDayAndCategory(dateInfo, categoryStr));
    }


}
