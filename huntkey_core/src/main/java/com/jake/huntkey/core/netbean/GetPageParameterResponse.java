/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:15:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetPageParameterResponse {

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

        private String time_now;
        private String time_now2;
        private String refresh_time;
        private String section_time;
        private String marquee_content;
        private String marquee_title;
        private String marquee_direction;
        private String database_searchtime;
        private String layt_dept_code;
        private String layt_dept_codes;
        public void setTime_now(String time_now) {
            this.time_now = time_now;
        }
        public String getTime_now() {
            return time_now;
        }

        public void setTime_now2(String time_now2) {
            this.time_now2 = time_now2;
        }
        public String getTime_now2() {
            return time_now2;
        }

        public void setRefresh_time(String refresh_time) {
            this.refresh_time = refresh_time;
        }
        public String getRefresh_time() {
            return refresh_time;
        }

        public void setSection_time(String section_time) {
            this.section_time = section_time;
        }
        public String getSection_time() {
            return section_time;
        }

        public void setMarquee_content(String marquee_content) {
            this.marquee_content = marquee_content;
        }
        public String getMarquee_content() {
            return marquee_content;
        }

        public void setMarquee_title(String marquee_title) {
            this.marquee_title = marquee_title;
        }
        public String getMarquee_title() {
            return marquee_title;
        }

        public void setMarquee_direction(String marquee_direction) {
            this.marquee_direction = marquee_direction;
        }
        public String getMarquee_direction() {
            return marquee_direction;
        }

        public void setDatabase_searchtime(String database_searchtime) {
            this.database_searchtime = database_searchtime;
        }
        public String getDatabase_searchtime() {
            return database_searchtime;
        }

        public void setLayt_dept_code(String layt_dept_code) {
            this.layt_dept_code = layt_dept_code;
        }
        public String getLayt_dept_code() {
            return layt_dept_code;
        }

        public void setLayt_dept_codes(String layt_dept_codes) {
            this.layt_dept_codes = layt_dept_codes;
        }
        public String getLayt_dept_codes() {
            return layt_dept_codes;
        }

    }
}