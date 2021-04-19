package kakuom.com.restaurant.outputDto;

import kakuom.com.restaurant.models.enums.AreaEnum;
import kakuom.com.restaurant.models.enums.TableNumberEnum;
import kakuom.com.restaurant.models.enums.TableStatusEnum;

public class TableDto {
    private AreaEnum area;
    private TableNumberEnum number;
    private TableStatusEnum status = TableStatusEnum.AVAILABLE;

    public TableDto(TableNumberEnum tableNumberEnum){
        var strValue = tableNumberEnum.toString();
        switch (strValue.charAt(0)){
            case 'M':
                this.area = AreaEnum.MAIN;
                break;
            case 'O':
                this.area= AreaEnum.OUTSIDE;
                break;
            default:
                this.area = AreaEnum.BALCONY;
        }
        this.number = tableNumberEnum;
    }

    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }

    public TableNumberEnum getNumber() {
        return number;
    }

    public void setNumber(TableNumberEnum number) {
        this.number = number;
    }

    public TableStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TableStatusEnum status) {
        this.status = status;
    }
}
