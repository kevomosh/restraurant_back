package kakuom.com.restaurant.inputDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class TimeInfo {
    @Min(6)
    @Max(23)
    private int hour;

    @Min(0)
    @Max(59)
    private int minute;

    public TimeInfo(@Min(6) @Max(23) int hour, @Min(0) @Max(59) int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
