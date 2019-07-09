/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-09 14:21:20
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetFpyRateResponse {

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


    public static class Pass7DayRate {

        private String date_str;
        private String otpt_start_time;
        private String fpy_rate;

        public void setDate_str(String date_str) {
            this.date_str = date_str;
        }

        public String getDate_str() {
            return date_str;
        }

        public void setOtpt_start_time(String otpt_start_time) {
            this.otpt_start_time = otpt_start_time;
        }

        public String getOtpt_start_time() {
            return otpt_start_time;
        }

        public void setFpy_rate(String fpy_rate) {
            this.fpy_rate = fpy_rate;
        }

        public String getFpy_rate() {
            return fpy_rate;
        }

    }


    public static class PassRateTop5 {

        private String otpt_process_id;
        private String layt_name;
        private String rate;

        public void setOtpt_process_id(String otpt_process_id) {
            this.otpt_process_id = otpt_process_id;
        }

        public String getOtpt_process_id() {
            return otpt_process_id;
        }

        public void setLayt_name(String layt_name) {
            this.layt_name = layt_name;
        }

        public String getLayt_name() {
            return layt_name;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRate() {
            return rate;
        }
    }


    public static class LossRateTop5 {

        private String otpt_process_id;
        private String layt_name;
        private String rate;

        public void setOtpt_process_id(String otpt_process_id) {
            this.otpt_process_id = otpt_process_id;
        }

        public String getOtpt_process_id() {
            return otpt_process_id;
        }

        public void setLayt_name(String layt_name) {
            this.layt_name = layt_name;
        }

        public String getLayt_name() {
            return layt_name;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRate() {
            return rate;
        }
    }


    public static class Content {

        private String Rate;
        private List<Pass7DayRate> Pass7DayRate;
        private List<PassRateTop5> PassRateTop5;
        private List<LossRateTop5> LossRateTop5;

        public void setRate(String Rate) {
            this.Rate = Rate;
        }

        public String getRate() {
            return Rate;
        }

        public void setPass7DayRate(List<Pass7DayRate> Pass7DayRate) {
            this.Pass7DayRate = Pass7DayRate;
        }

        public List<Pass7DayRate> getPass7DayRate() {
            return Pass7DayRate;
        }

        public void setPassRateTop5(List<PassRateTop5> PassRateTop5) {
            this.PassRateTop5 = PassRateTop5;
        }

        public List<PassRateTop5> getPassRateTop5() {
            return PassRateTop5;
        }

        public void setLossRateTop5(List<LossRateTop5> LossRateTop5) {
            this.LossRateTop5 = LossRateTop5;
        }

        public List<LossRateTop5> getLossRateTop5() {
            return LossRateTop5;
        }

    }
}