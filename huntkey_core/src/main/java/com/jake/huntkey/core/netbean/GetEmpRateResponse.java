/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-10 11:31:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetEmpRateResponse {

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


    public static class WeekEmpRate {

        private List<String> xdate;
        private List<String> emp_num;
        private List<String> a_emp_num;
        private List<String> a_emp_num_real;
        private List<String> a_rate;
        private List<String> b_emp_num;
        private List<String> b_emp_num_real;
        private List<String> b_rate;

        public void setXdate(List<String> xdate) {
            this.xdate = xdate;
        }

        public List<String> getXdate() {
            return xdate;
        }

        public void setEmp_num(List<String> emp_num) {
            this.emp_num = emp_num;
        }

        public List<String> getEmp_num() {
            return emp_num;
        }

        public void setA_emp_num(List<String> a_emp_num) {
            this.a_emp_num = a_emp_num;
        }

        public List<String> getA_emp_num() {
            return a_emp_num;
        }

        public void setA_emp_num_real(List<String> a_emp_num_real) {
            this.a_emp_num_real = a_emp_num_real;
        }

        public List<String> getA_emp_num_real() {
            return a_emp_num_real;
        }

        public void setA_rate(List<String> a_rate) {
            this.a_rate = a_rate;
        }

        public List<String> getA_rate() {
            return a_rate;
        }

        public void setB_emp_num(List<String> b_emp_num) {
            this.b_emp_num = b_emp_num;
        }

        public List<String> getB_emp_num() {
            return b_emp_num;
        }

        public void setB_emp_num_real(List<String> b_emp_num_real) {
            this.b_emp_num_real = b_emp_num_real;
        }

        public List<String> getB_emp_num_real() {
            return b_emp_num_real;
        }

        public void setB_rate(List<String> b_rate) {
            this.b_rate = b_rate;
        }

        public List<String> getB_rate() {
            return b_rate;
        }

    }


    public static class LineEmpRate {

        private List<String> dept_code;
        private List<String> dept_name;
        private List<String> type_str;
        private List<String> emp_num;
        private List<String> emp_num_real;
        private List<String> emp_num_should;
        private List<String> rate;

        public void setDept_code(List<String> dept_code) {
            this.dept_code = dept_code;
        }

        public List<String> getDept_code() {
            return dept_code;
        }

        public void setDept_name(List<String> dept_name) {
            this.dept_name = dept_name;
        }

        public List<String> getDept_name() {
            return dept_name;
        }

        public void setType_str(List<String> type_str) {
            this.type_str = type_str;
        }

        public List<String> getType_str() {
            return type_str;
        }

        public void setEmp_num(List<String> emp_num) {
            this.emp_num = emp_num;
        }

        public List<String> getEmp_num() {
            return emp_num;
        }

        public void setEmp_num_real(List<String> emp_num_real) {
            this.emp_num_real = emp_num_real;
        }

        public List<String> getEmp_num_real() {
            return emp_num_real;
        }

        public void setEmp_num_should(List<String> emp_num_should) {
            this.emp_num_should = emp_num_should;
        }

        public List<String> getEmp_num_should() {
            return emp_num_should;
        }

        public void setRate(List<String> rate) {
            this.rate = rate;
        }

        public List<String> getRate() {
            return rate;
        }

    }


    public static class Emp7DayRate {

        private List<String> xdate;
        private List<String> emp_num;
        private List<String> a_emp_num;
        private List<String> a_emp_num_real;
        private List<String> a_rate;
        private List<String> b_emp_num;
        private List<String> b_emp_num_real;
        private List<String> b_rate;

        public void setXdate(List<String> xdate) {
            this.xdate = xdate;
        }

        public List<String> getXdate() {
            return xdate;
        }

        public void setEmp_num(List<String> emp_num) {
            this.emp_num = emp_num;
        }

        public List<String> getEmp_num() {
            return emp_num;
        }

        public void setA_emp_num(List<String> a_emp_num) {
            this.a_emp_num = a_emp_num;
        }

        public List<String> getA_emp_num() {
            return a_emp_num;
        }

        public void setA_emp_num_real(List<String> a_emp_num_real) {
            this.a_emp_num_real = a_emp_num_real;
        }

        public List<String> getA_emp_num_real() {
            return a_emp_num_real;
        }

        public void setA_rate(List<String> a_rate) {
            this.a_rate = a_rate;
        }

        public List<String> getA_rate() {
            return a_rate;
        }

        public void setB_emp_num(List<String> b_emp_num) {
            this.b_emp_num = b_emp_num;
        }

        public List<String> getB_emp_num() {
            return b_emp_num;
        }

        public void setB_emp_num_real(List<String> b_emp_num_real) {
            this.b_emp_num_real = b_emp_num_real;
        }

        public List<String> getB_emp_num_real() {
            return b_emp_num_real;
        }

        public void setB_rate(List<String> b_rate) {
            this.b_rate = b_rate;
        }

        public List<String> getB_rate() {
            return b_rate;
        }

    }

    public static class Content {

        private String Rate;
        private WeekEmpRate WeekEmpRate;
        private LineEmpRate LineEmpRate;
        private Emp7DayRate Emp7DayRate;

        public void setRate(String Rate) {
            this.Rate = Rate;
        }

        public String getRate() {
            return Rate;
        }

        public void setWeekEmpRate(WeekEmpRate WeekEmpRate) {
            this.WeekEmpRate = WeekEmpRate;
        }

        public WeekEmpRate getWeekEmpRate() {
            return WeekEmpRate;
        }

        public void setLineEmpRate(LineEmpRate LineEmpRate) {
            this.LineEmpRate = LineEmpRate;
        }

        public LineEmpRate getLineEmpRate() {
            return LineEmpRate;
        }

        public void setEmp7DayRate(Emp7DayRate Emp7DayRate) {
            this.Emp7DayRate = Emp7DayRate;
        }

        public Emp7DayRate getEmp7DayRate() {
            return Emp7DayRate;
        }

    }
}