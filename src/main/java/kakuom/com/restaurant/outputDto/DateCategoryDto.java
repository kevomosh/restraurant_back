package kakuom.com.restaurant.outputDto;

import kakuom.com.restaurant.models.enums.CategoryEnum;

import java.time.LocalDate;

public class DateCategoryDto {
    private LocalDate date;
    private CategoryEnum category;

    public DateCategoryDto(LocalDate date, CategoryEnum category) {
        this.date = date;
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
