package kakuom.com.restaurant.inputDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SittingInfo {
    private Long id;

    @NotBlank(message = "Category is required")
    private String categoryStr;

    @NotNull
    private DateInfo date;

    @NotNull
    private TimeInfo startTime;

    @NotNull
    private TimeInfo endTime;

    @Min(1)
    @Max(100)
    private int capacity;

    private boolean isClosed;

    public SittingInfo(Long id,String categoryStr, DateInfo date, TimeInfo start,
                       TimeInfo end, int capacity, boolean isClosed) {
        this.id = id;
        this.categoryStr = categoryStr;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
        this.capacity = capacity;
        this.isClosed = isClosed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryStr() {
        return categoryStr;
    }

    public void setCategoryStr(String categoryStr) {
        this.categoryStr = categoryStr;
    }

    public DateInfo getDate() {
        return date;
    }

    public void setDate(DateInfo date) {
        this.date = date;
    }

    public TimeInfo getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeInfo startTime) {
        this.startTime = startTime;
    }

    public TimeInfo getEndTime() {
        return endTime;
    }

    public void setEndTime(TimeInfo endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
