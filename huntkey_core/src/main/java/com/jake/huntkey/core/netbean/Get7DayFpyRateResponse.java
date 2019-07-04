/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:25:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Get7DayFpyRateResponse {

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
}