package kakuom.com.restaurant.inputDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class DateInfo {
    @Max(2022)
    @Min(2021)
    private int year;

    @Max(12)
    @Min(1)
    private int month;

    @Min(1)
    @Max(31)
    private int day;

    public DateInfo(@Max(2022) @Min(2021) int year,
                    @Max(12) @Min(1) int month,
                    @Min(1) @Max(31) int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
