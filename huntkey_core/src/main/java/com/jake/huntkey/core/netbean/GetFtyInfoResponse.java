/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:47:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetFtyInfoResponse {

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
}