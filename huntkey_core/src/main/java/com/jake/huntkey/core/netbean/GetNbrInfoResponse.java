/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:23:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetNbrInfoResponse extends  BaseResponse{


    private List<Content> Content;

    public void setContent(List<Content> Content) {
         this.Content = Content;
     }
     public List<Content> getContent() {
         return Content;
     }

    public static class Content {

        private String otpt_wo_nbr;
        private String otpt_part;
        private String upm;

        @Override
        public String toString() {
            return "Content{" +
                    "otpt_wo_nbr='" + otpt_wo_nbr + '\'' +
                    ", otpt_part='" + otpt_part + '\'' +
                    ", upm='" + upm + '\'' +
                    ", line_name='" + line_name + '\'' +
                    '}';
        }

        private String line_name;
        public void setOtpt_wo_nbr(String otpt_wo_nbr) {
            this.otpt_wo_nbr = otpt_wo_nbr;
        }
        public String getOtpt_wo_nbr() {
            return otpt_wo_nbr;
        }

        public void setOtpt_part(String otpt_part) {
            this.otpt_part = otpt_part;
        }
        public String getOtpt_part() {
            return otpt_part;
        }

        public void setUpm(String upm) {
            this.upm = upm;
        }
        public String getUpm() {
            return upm;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }
        public String getLine_name() {
            return line_name;
        }

    }
}