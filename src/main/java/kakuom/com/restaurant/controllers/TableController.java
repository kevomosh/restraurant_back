package kakuom.com.restaurant.controllers;

import kakuom.com.restaurant.models.Tables;
import kakuom.com.restaurant.models.enums.AreaEnum;
import kakuom.com.restaurant.models.enums.TableNumberEnum;
import kakuom.com.restaurant.services.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tables/")
@CrossOrigin
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("all/{sittingId}")
    public ResponseEntity<Map<AreaEnum, List<Tables>>> allInSitting(@PathVariable Long sittingId) {
        return ResponseEntity.ok(tableService.getAllInSitting(sittingId));
    }

    @GetMapping("free/{sittingId}")
    public ResponseEntity<List<TableNumberEnum>> freeInSitting(@PathVariable Long sittingId) {
        return ResponseEntity.ok(tableService.getFreeInSitting(sittingId));
    }
}
