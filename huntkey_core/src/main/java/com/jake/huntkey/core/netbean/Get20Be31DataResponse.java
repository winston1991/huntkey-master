/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-08 9:58:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Get20Be31DataResponse {

    private String ErrorMsg;
    private String Status;

    @Override
    public String toString() {
        return "Get20Be31DataResponse{" +
                "ErrorMsg='" + ErrorMsg + '\'' +
                ", Status='" + Status + '\'' +
                ", Content=" + Content +
                '}';
    }

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

    public  static  class Content {

        private String tmrm_month;
        private String tmrm_line_id;
        private String layt_name;
        private String tmrm_rate;
        private String tmrm_loss_rate;

        @Override
        public String toString() {
            return "Content{" +
                    "tmrm_month='" + tmrm_month + '\'' +
                    ", tmrm_line_id='" + tmrm_line_id + '\'' +
                    ", layt_name='" + layt_name + '\'' +
                    ", tmrm_rate='" + tmrm_rate + '\'' +
                    ", tmrm_loss_rate='" + tmrm_loss_rate + '\'' +
                    ", tmrm_synthesize_rate='" + tmrm_synthesize_rate + '\'' +
                    '}';
        }

        private String tmrm_synthesize_rate;

        public void setTmrm_month(String tmrm_month) {
            this.tmrm_month = tmrm_month;
        }

        public String getTmrm_month() {
            return tmrm_month;
        }

        public void setTmrm_line_id(String tmrm_line_id) {
            this.tmrm_line_id = tmrm_line_id;
        }

        public String getTmrm_line_id() {
            return tmrm_line_id;
        }

        public void setLayt_name(String layt_name) {
            this.layt_name = layt_name;
        }

        public String getLayt_name() {
            return layt_name;
        }

        public void setTmrm_rate(String tmrm_rate) {
            this.tmrm_rate = tmrm_rate;
        }

        public String getTmrm_rate() {
            return tmrm_rate;
        }

        public void setTmrm_loss_rate(String tmrm_loss_rate) {
            this.tmrm_loss_rate = tmrm_loss_rate;
        }

        public String getTmrm_loss_rate() {
            return tmrm_loss_rate;
        }

        public void setTmrm_synthesize_rate(String tmrm_synthesize_rate) {
            this.tmrm_synthesize_rate = tmrm_synthesize_rate;
        }

        public String getTmrm_synthesize_rate() {
            return tmrm_synthesize_rate;
        }

    }
}