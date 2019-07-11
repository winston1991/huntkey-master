/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-10 10:9:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetTcrRateResponse  extends  BaseResponse {


    private List<Content> Content;


    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public static class Tcr7DayRate {

        private List<String> otpt_start_time;
        private List<String> targetqty;
        private List<String> shift;
        private List<String> oqty;
        private List<String> reachrate;
        private List<String> shift1;
        private List<String> oqty1;
        private List<String> reachrate1;

        public void setOtpt_start_time(List<String> otpt_start_time) {
            this.otpt_start_time = otpt_start_time;
        }

        public List<String> getOtpt_start_time() {
            return otpt_start_time;
        }

        public void setTargetqty(List<String> targetqty) {
            this.targetqty = targetqty;
        }

        public List<String> getTargetqty() {
            return targetqty;
        }

        public void setShift(List<String> shift) {
            this.shift = shift;
        }

        public List<String> getShift() {
            return shift;
        }

        public void setOqty(List<String> oqty) {
            this.oqty = oqty;
        }

        public List<String> getOqty() {
            return oqty;
        }

        public void setReachrate(List<String> reachrate) {
            this.reachrate = reachrate;
        }

        public List<String> getReachrate() {
            return reachrate;
        }

        public void setShift1(List<String> shift1) {
            this.shift1 = shift1;
        }

        public List<String> getShift1() {
            return shift1;
        }

        public void setOqty1(List<String> oqty1) {
            this.oqty1 = oqty1;
        }

        public List<String> getOqty1() {
            return oqty1;
        }

        public void setReachrate1(List<String> reachrate1) {
            this.reachrate1 = reachrate1;
        }

        public List<String> getReachrate1() {
            return reachrate1;
        }

    }

    public static class OQty {

        private List<String> otpt_start_time;
        private List<String> targetqty;
        private List<String> oqty;

        public void setOtpt_start_time(List<String> otpt_start_time) {
            this.otpt_start_time = otpt_start_time;
        }

        public List<String> getOtpt_start_time() {
            return otpt_start_time;
        }

        public void setTargetqty(List<String> targetqty) {
            this.targetqty = targetqty;
        }

        public List<String> getTargetqty() {
            return targetqty;
        }

        public void setOqty(List<String> oqty) {
            this.oqty = oqty;
        }

        public List<String> getOqty() {
            return oqty;
        }

    }


    public static class Content {

        private String Rate;
        private Tcr7DayRate Tcr7DayRate;
        private OQty OQty;
        private List<List<String>> MonitorData;

        public void setRate(String Rate) {
            this.Rate = Rate;
        }

        public String getRate() {
            return Rate;
        }

        public void setTcr7DayRate(Tcr7DayRate Tcr7DayRate) {
            this.Tcr7DayRate = Tcr7DayRate;
        }

        public Tcr7DayRate getTcr7DayRate() {
            return Tcr7DayRate;
        }

        public void setOQty(OQty OQty) {
            this.OQty = OQty;
        }

        public OQty getOQty() {
            return OQty;
        }

        public void setMonitorData(List<List<String>> MonitorData) {
            this.MonitorData = MonitorData;
        }

        public List<List<String>> getMonitorData() {
            return MonitorData;
        }

    }
}