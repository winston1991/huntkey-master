/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:33:43
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetLineEmpRateResponse {

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

        private String dept_code;
        private String dept_name;
        private String type_str;
        private String emp_num;
        private String emp_num_real;
        private String emp_num_should;
        private String rate;

        public void setDept_code(String dept_code) {
            this.dept_code = dept_code;
        }

        public String getDept_code() {
            return dept_code;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setType_str(String type_str) {
            this.type_str = type_str;
        }

        public String getType_str() {
            return type_str;
        }

        public void setEmp_num(String emp_num) {
            this.emp_num = emp_num;
        }

        public String getEmp_num() {
            return emp_num;
        }

        public void setEmp_num_real(String emp_num_real) {
            this.emp_num_real = emp_num_real;
        }

        public String getEmp_num_real() {
            return emp_num_real;
        }

        public void setEmp_num_should(String emp_num_should) {
            this.emp_num_should = emp_num_should;
        }

        public String getEmp_num_should() {
            return emp_num_should;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRate() {
            return rate;
        }

    }
}