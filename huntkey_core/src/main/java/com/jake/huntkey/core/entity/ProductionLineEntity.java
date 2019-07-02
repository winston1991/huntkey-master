package com.jake.huntkey.core.entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "线体、指标")
public class ProductionLineEntity {

    @SmartColumn(id = 1, name = "线体", fixed = true, autoMerge = true)
    private String linebody;
    @SmartColumn(id = 2, name = "指标")
    private String quota;

    public String getLinebody() {
        return linebody;
    }

    public void setLinebody(String linebody) {
        this.linebody = linebody;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    @SmartColumn(id = 3, name = "19.5")
    private String item1;
    @SmartColumn(id = 4, name = "19.6")
    private String item2;
    @SmartColumn(id = 5, name = "W25")
    private String item3;
    @SmartColumn(id = 6, name = "W26")
    private String item4;
    @SmartColumn(id = 7, name = "7.2")
    private String item5;
    @SmartColumn(id = 8, name = "7.3")
    private String item6;
    @SmartColumn(id = 9, name = "7.4")
    private String item7;

}
