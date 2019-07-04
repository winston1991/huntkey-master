/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:30:19
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Get7DayTcrRateResponse {

    private String ErrorMsg;
    private String Status;
    private List<Content> Content;

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }

    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public static class Content {

        private String otpt_start_time;
        private String targetqty;
        private String shift;
        private String oqty;
        private String reachrate;
        private String shift1;
        private String oqty1;
        private String reachrate1;

        public void setOtpt_start_time(String otpt_start_time) {
            this.otpt_start_time = otpt_start_time;
        }

        public String getOtpt_start_time() {
            return otpt_start_time;
        }

        public void setTargetqty(String targetqty) {
            this.targetqty = targetqty;
        }

        public String getTargetqty() {
            return targetqty;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public String getShift() {
            return shift;
        }

        public void setOqty(String oqty) {
            this.oqty = oqty;
        }

        public String getOqty() {
            return oqty;
        }

        public void setReachrate(String reachrate) {
            this.reachrate = reachrate;
        }

        public String getReachrate() {
            return reachrate;
        }

        public void setShift1(String shift1) {
            this.shift1 = shift1;
        }

        public String getShift1() {
            return shift1;
        }

        public void setOqty1(String oqty1) {
            this.oqty1 = oqty1;
        }

        public String getOqty1() {
            return oqty1;
        }

        public void setReachrate1(String reachrate1) {
            this.reachrate1 = reachrate1;
        }

        public String getReachrate1() {
            return reachrate1;
        }

    }
}