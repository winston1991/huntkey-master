/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:32:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetWeekEmpRateResponse {

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

        private String xdate;
        private String emp_num;
        private String a_emp_num;
        private String a_emp_num_real;
        private String a_rate;
        private String b_emp_num;
        private String b_emp_num_real;
        private String b_rate;
        public void setXdate(String xdate) {
            this.xdate = xdate;
        }
        public String getXdate() {
            return xdate;
        }

        public void setEmp_num(String emp_num) {
            this.emp_num = emp_num;
        }
        public String getEmp_num() {
            return emp_num;
        }

        public void setA_emp_num(String a_emp_num) {
            this.a_emp_num = a_emp_num;
        }
        public String getA_emp_num() {
            return a_emp_num;
        }

        public void setA_emp_num_real(String a_emp_num_real) {
            this.a_emp_num_real = a_emp_num_real;
        }
        public String getA_emp_num_real() {
            return a_emp_num_real;
        }

        public void setA_rate(String a_rate) {
            this.a_rate = a_rate;
        }
        public String getA_rate() {
            return a_rate;
        }

        public void setB_emp_num(String b_emp_num) {
            this.b_emp_num = b_emp_num;
        }
        public String getB_emp_num() {
            return b_emp_num;
        }

        public void setB_emp_num_real(String b_emp_num_real) {
            this.b_emp_num_real = b_emp_num_real;
        }
        public String getB_emp_num_real() {
            return b_emp_num_real;
        }

        public void setB_rate(String b_rate) {
            this.b_rate = b_rate;
        }
        public String getB_rate() {
            return b_rate;
        }

    }
}