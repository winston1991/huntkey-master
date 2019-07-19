/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-10 16:0:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CheckVersionResponse {

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

        private String Version;
        private String Url;
        private String Content;
        private String Date;

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public String getVersion() {
            return Version;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public String getUrl() {
            return Url;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getContent() {
            return Content;
        }

        public void setDate(String String) {
            this.Date = String;
        }

        public String getDate() {
            return Date;
        }

    }
}