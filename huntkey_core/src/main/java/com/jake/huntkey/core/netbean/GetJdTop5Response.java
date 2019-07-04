/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-04 21:28:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetJdTop5Response {

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

        private String xdata;
        private String ydata;

        public void setXdata(String xdata) {
            this.xdata = xdata;
        }

        public String getXdata() {
            return xdata;
        }

        public void setYdata(String ydata) {
            this.ydata = ydata;
        }

        public String getYdata() {
            return ydata;
        }

    }
}