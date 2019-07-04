/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:22:19
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetQueryRouteResponse {

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

        private String layt_id;
        private String laty_name;
        private String layt_desc;
        private String roud_order;
        private String roud_necessary;
        public void setLayt_id(String layt_id) {
            this.layt_id = layt_id;
        }
        public String getLayt_id() {
            return layt_id;
        }

        public void setLaty_name(String laty_name) {
            this.laty_name = laty_name;
        }
        public String getLaty_name() {
            return laty_name;
        }

        public void setLayt_desc(String layt_desc) {
            this.layt_desc = layt_desc;
        }
        public String getLayt_desc() {
            return layt_desc;
        }

        public void setRoud_order(String roud_order) {
            this.roud_order = roud_order;
        }
        public String getRoud_order() {
            return roud_order;
        }

        public void setRoud_necessary(String roud_necessary) {
            this.roud_necessary = roud_necessary;
        }
        public String getRoud_necessary() {
            return roud_necessary;
        }

    }
}