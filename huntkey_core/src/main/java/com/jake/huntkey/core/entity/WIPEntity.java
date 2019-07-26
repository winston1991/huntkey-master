package com.jake.huntkey.core.entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "WIP统计")
public class WIPEntity {

    @SmartColumn(id =1,name = "序号",fixed = true)
    private String  id;
    @SmartColumn(id =2,name = "抽检批号")
    private String  samplingNumber;
    @SmartColumn(id =3,name = "制令单号")
    private String makeFlowNumber;
    @SmartColumn(id =4,name = "数量")
    private String  quantity;
    @SmartColumn(id =5,name = "抽检结果")
    private String  samplingResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSamplingNumber() {
        return samplingNumber;
    }

    public void setSamplingNumber(String samplingNumber) {
        this.samplingNumber = samplingNumber;
    }

    public String getMakeFlowNumber() {
        return makeFlowNumber;
    }

    public void setMakeFlowNumber(String makeFlowNumber) {
        this.makeFlowNumber = makeFlowNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSamplingResult() {
        return samplingResult;
    }

    public void setSamplingResult(String samplingResult) {
        this.samplingResult = samplingResult;
    }

}
