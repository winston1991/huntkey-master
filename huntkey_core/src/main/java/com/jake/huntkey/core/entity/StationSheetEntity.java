package com.jake.huntkey.core.entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "工单/工站表")
public class StationSheetEntity {

    public String getStationSheet() {
        return stationSheet;
    }

    public void setStationSheet(String stationSheet) {
        this.stationSheet = stationSheet;
    }

    @SmartColumn(id = 1, name = "工单/工站表", fixed = true)
    private String stationSheet = "";
    @SmartColumn(id = 2, name = "AOI测试")
    private String aoiTest = "-";
    @SmartColumn(id = 3, name = "包装扫描")
    private String packingScan = "-";

    @SmartColumn(id = 4, name = "插件AOI测试")
    private String pluginAoiTest = "-";

    @SmartColumn(id = 5, name = "超声波")
    private String ultrasonic = "-";

    @SmartColumn(id = 6, name = "高压测试")
    private String heightPressureTest = "-";

    @SmartColumn(id = 7, name = "工单投入")
    private String sheetInvest = "-";

    @SmartColumn(id = 8, name = "功能初测")
    private String functionPrimaryTest = "-";

    @SmartColumn(id = 9, name = "功能终测")
    private String functionFinalTest = "-";

    @SmartColumn(id = 10, name = "共模测试")
    private String commonModeTest = "-";

    @SmartColumn(id = 11, name = "镭雕外观测试")
    private String laserAppearanceTest = "-";

    @SmartColumn(id = 12, name = "条码绑定")
    private String barCodeBinding = "-";

    @SmartColumn(id = 10, name = "条码替换")
    private String barCodeReplace = "-";

    @SmartColumn(id = 11, name = "条码转换")
    private String barCodeTransformation = "-";

    public String getAoiTest() {
        return aoiTest;
    }

    public void setAoiTest(String aoiTest) {
        this.aoiTest = aoiTest;
    }

    public String getPackingScan() {
        return packingScan;
    }

    public void setPackingScan(String packingScan) {
        this.packingScan = packingScan;
    }

    public String getPluginAoiTest() {
        return pluginAoiTest;
    }

    public void setPluginAoiTest(String pluginAoiTest) {
        this.pluginAoiTest = pluginAoiTest;
    }

    public String getUltrasonic() {
        return ultrasonic;
    }

    public void setUltrasonic(String ultrasonic) {
        this.ultrasonic = ultrasonic;
    }

    public String getHeightPressureTest() {
        return heightPressureTest;
    }

    public void setHeightPressureTest(String heightPressureTest) {
        this.heightPressureTest = heightPressureTest;
    }

    public String getSheetInvest() {
        return sheetInvest;
    }

    public void setSheetInvest(String sheetInvest) {
        this.sheetInvest = sheetInvest;
    }

    public String getFunctionPrimaryTest() {
        return functionPrimaryTest;
    }

    public void setFunctionPrimaryTest(String functionPrimaryTest) {
        this.functionPrimaryTest = functionPrimaryTest;
    }

    public String getFunctionFinalTest() {
        return functionFinalTest;
    }

    public void setFunctionFinalTest(String functionFinalTest) {
        this.functionFinalTest = functionFinalTest;
    }

    public String getCommonModeTest() {
        return commonModeTest;
    }

    public void setCommonModeTest(String commonModeTest) {
        this.commonModeTest = commonModeTest;
    }

    public String getLaserAppearanceTest() {
        return laserAppearanceTest;
    }

    public void setLaserAppearanceTest(String laserAppearanceTest) {
        this.laserAppearanceTest = laserAppearanceTest;
    }

    public String getBarCodeBinding() {
        return barCodeBinding;
    }

    public void setBarCodeBinding(String barCodeBinding) {
        this.barCodeBinding = barCodeBinding;
    }

    public String getBarCodeReplace() {
        return barCodeReplace;
    }

    public void setBarCodeReplace(String barCodeReplace) {
        this.barCodeReplace = barCodeReplace;
    }

    public String getBarCodeTransformation() {
        return barCodeTransformation;
    }

    public void setBarCodeTransformation(String barCodeTransformation) {
        this.barCodeTransformation = barCodeTransformation;
    }

    public String getPrefunctionTest() {
        return prefunctionTest;
    }

    public void setPrefunctionTest(String prefunctionTest) {
        this.prefunctionTest = prefunctionTest;
    }

    @SmartColumn(id = 12, name = "预功能测试")
    private String prefunctionTest = "-";


}
