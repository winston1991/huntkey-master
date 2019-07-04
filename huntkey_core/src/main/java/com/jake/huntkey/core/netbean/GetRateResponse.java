/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:40:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetRateResponse {

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

    public  static class Content {

        private String line_name;
        private String nbrs;
        private String parts;
        private String upm;
        private String otpt_start_time;
        private String reachrate;
        private String loss_rate;
        private String rate;
        private String synthesize_rate;
        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }
        public String getLine_name() {
            return line_name;
        }

        public void setNbrs(String nbrs) {
            this.nbrs = nbrs;
        }
        public String getNbrs() {
            return nbrs;
        }

        public void setParts(String parts) {
            this.parts = parts;
        }
        public String getParts() {
            return parts;
        }

        public void setUpm(String upm) {
            this.upm = upm;
        }
        public String getUpm() {
            return upm;
        }

        public void setOtpt_start_time(String otpt_start_time) {
            this.otpt_start_time = otpt_start_time;
        }
        public String getOtpt_start_time() {
            return otpt_start_time;
        }

        public void setReachrate(String reachrate) {
            this.reachrate = reachrate;
        }
        public String getReachrate() {
            return reachrate;
        }

        public void setLoss_rate(String loss_rate) {
            this.loss_rate = loss_rate;
        }
        public String getLoss_rate() {
            return loss_rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
        public String getRate() {
            return rate;
        }

        public void setSynthesize_rate(String synthesize_rate) {
            this.synthesize_rate = synthesize_rate;
        }
        public String getSynthesize_rate() {
            return synthesize_rate;
        }

    }
}