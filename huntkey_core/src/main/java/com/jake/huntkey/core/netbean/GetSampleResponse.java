/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:43:11
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetSampleResponse {

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

        private String sapm_lot;
        private String sapm_wo_nbr;
        private String sapm_act_qty;
        private String code_name;
        private String sapm_remark;
        public void setSapm_lot(String sapm_lot) {
            this.sapm_lot = sapm_lot;
        }
        public String getSapm_lot() {
            return sapm_lot;
        }

        public void setSapm_wo_nbr(String sapm_wo_nbr) {
            this.sapm_wo_nbr = sapm_wo_nbr;
        }
        public String getSapm_wo_nbr() {
            return sapm_wo_nbr;
        }

        public void setSapm_act_qty(String sapm_act_qty) {
            this.sapm_act_qty = sapm_act_qty;
        }
        public String getSapm_act_qty() {
            return sapm_act_qty;
        }

        public void setCode_name(String code_name) {
            this.code_name = code_name;
        }
        public String getCode_name() {
            return code_name;
        }

        public void setSapm_remark(String sapm_remark) {
            this.sapm_remark = sapm_remark;
        }
        public String getSapm_remark() {
            return sapm_remark;
        }

    }
}