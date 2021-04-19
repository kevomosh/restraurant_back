package kakuom.com.restaurant.services;

import kakuom.com.restaurant.inputDto.DateInfo;
import kakuom.com.restaurant.inputDto.SittingInfo;
import kakuom.com.restaurant.models.Sitting;
import kakuom.com.restaurant.models.enums.CategoryEnum;
import kakuom.com.restaurant.repositories.SittingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class SittingService {
    private final SittingRepository sittingRepository;

    public SittingService(SittingRepository sittingRepository) {
        this.sittingRepository = sittingRepository;
    }

    public Sitting create(SittingInfo sittingInfo){
        var date = sittingInfo.getDate();
        var localDate =
                LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        var category = CategoryEnum.valueOf(sittingInfo.getCategoryStr());

        //TODO chenck if error message is being sent,, cause oits empty at the momemnt
        if (sittingRepository.existsByDateAndCategory(localDate, category)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Sitting already there, maybe just update it");
        }

        var sitting = new Sitting(sittingInfo);
        sittingRepository.save(sitting);
        return sitting;
    }

    public String delete(Long id) {
        if (sittingRepository.existsById(id)) {
            sittingRepository.deleteById(id);
            return "Successfully deleted";
        } else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sitting not present");
        }
    }

    public Sitting update(SittingInfo sittingInfo){
        var sitting = sittingRepository.findById(sittingInfo.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Sitting not present"));

        var date = sittingInfo.getDate();
        var localDate =
                LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        var category = CategoryEnum.valueOf(sittingInfo.getCategoryStr());


        sittingRepository.findByDateAndCategory(localDate, category)
                .ifPresent(sit -> {
                    if (!sit.getId().equals(sitting.getId())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "theres " +
                                "a sitting for the day and Time already, change date or category");
                    }
                });

        sitting.updateSitting(sittingInfo);
        sittingRepository.save(sitting);
        return sitting;
    }

    public Sitting getById(Long sittingId) {
        return sittingRepository.findById(sittingId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not present"));
    }

    public List<Sitting> getAll(){
        return sittingRepository.findAll();
    }

    public List<Sitting> getSittingsOfDay(DateInfo dateInfo) {
        var sittingDate = LocalDate.of(dateInfo.getYear(),
                dateInfo.getMonth(), dateInfo.getDay());
        return sittingRepository.getAllByDate(sittingDate);
    }
}
