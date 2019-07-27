/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:17:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetQueryWarnResponse extends  BaseResponse{


    private List<Content> Content;

    public void setContent(List<Content> Content) {
         this.Content = Content;
     }
     public List<Content> getContent() {
         return Content;
     }


    public static class Content {

        private String tcr_yellow_begin;
        private String tcr_yellow_end;
        private String fpy_yellow_begin;
        private String fpy_yellow_end;
        private String fpy_red;
        private String cpr_yellow_begin;
        private String cpr_yellow_end;
        private String cpr_red;
        public void setTcr_yellow_begin(String tcr_yellow_begin) {
            this.tcr_yellow_begin = tcr_yellow_begin;
        }
        public String getTcr_yellow_begin() {
            return tcr_yellow_begin;
        }

        public void setTcr_yellow_end(String tcr_yellow_end) {
            this.tcr_yellow_end = tcr_yellow_end;
        }
        public String getTcr_yellow_end() {
            return tcr_yellow_end;
        }

        public void setFpy_yellow_begin(String fpy_yellow_begin) {
            this.fpy_yellow_begin = fpy_yellow_begin;
        }
        public String getFpy_yellow_begin() {
            return fpy_yellow_begin;
        }

        public void setFpy_yellow_end(String fpy_yellow_end) {
            this.fpy_yellow_end = fpy_yellow_end;
        }
        public String getFpy_yellow_end() {
            return fpy_yellow_end;
        }

        public void setFpy_red(String fpy_red) {
            this.fpy_red = fpy_red;
        }
        public String getFpy_red() {
            return fpy_red;
        }

        public void setCpr_yellow_begin(String cpr_yellow_begin) {
            this.cpr_yellow_begin = cpr_yellow_begin;
        }
        public String getCpr_yellow_begin() {
            return cpr_yellow_begin;
        }

        public void setCpr_yellow_end(String cpr_yellow_end) {
            this.cpr_yellow_end = cpr_yellow_end;
        }
        public String getCpr_yellow_end() {
            return cpr_yellow_end;
        }

        public void setCpr_red(String cpr_red) {
            this.cpr_red = cpr_red;
        }
        public String getCpr_red() {
            return cpr_red;
        }

    }
}