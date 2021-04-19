package kakuom.com.restaurant.controllers;

import kakuom.com.restaurant.inputDto.DateInfo;
import kakuom.com.restaurant.inputDto.SittingInfo;
import kakuom.com.restaurant.models.Sitting;
import kakuom.com.restaurant.services.SittingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sitting")
@CrossOrigin
public class SittingController {

    private final SittingService sittingService;

    public SittingController(SittingService sittingService) {
        this.sittingService = sittingService;
    }

    @GetMapping()
    public ResponseEntity<List<Sitting>> getAllSittings(){
        return ResponseEntity.ok(sittingService.getAll());
    }

    @GetMapping({"/{sittingId}"})
    public ResponseEntity<Sitting> getById(@PathVariable Long sittingId) {
        return ResponseEntity.ok(sittingService.getById(sittingId));
    }
    @PostMapping()
    public ResponseEntity<Sitting> createSitting(@Valid @RequestBody SittingInfo sittingInfo){
        return ResponseEntity.ok(sittingService.create(sittingInfo));
    }

    @PostMapping("/day")
    public ResponseEntity<List<Sitting>> getSittingsOfDay(@Valid @RequestBody DateInfo dateInfo) {
        return ResponseEntity.ok(sittingService.getSittingsOfDay(dateInfo));
    }

    @PutMapping()
    public ResponseEntity<Sitting>update(@Valid @RequestBody SittingInfo sittingInfo) {
        return ResponseEntity.ok(sittingService.update(sittingInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(sittingService.delete(id));
    }
}

