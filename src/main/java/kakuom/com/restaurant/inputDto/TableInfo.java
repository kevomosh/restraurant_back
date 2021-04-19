package kakuom.com.restaurant.inputDto;

import javax.validation.constraints.NotBlank;

public class TableInfo {
    @NotBlank(message = "Area information Required")
    private String areaStr;

    @NotBlank(message = "Table number required")
    private String numberStr;

    public TableInfo(String areaStr, String numberStr) {
        this.areaStr = areaStr;
        this.numberStr = numberStr;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }
}
