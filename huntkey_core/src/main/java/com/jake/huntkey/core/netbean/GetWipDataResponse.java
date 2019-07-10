/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-10 15:5:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetWipDataResponse {

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

        private List<String> Titles;
        private List<List<String>> Data;

        public void setTitles(List<String> Titles) {
            this.Titles = Titles;
        }

        public List<String> getTitles() {
            return Titles;
        }

        public void setData(List<List<String>> Data) {
            this.Data = Data;
        }

        public List<List<String>> getData() {
            return Data;
        }

    }
}