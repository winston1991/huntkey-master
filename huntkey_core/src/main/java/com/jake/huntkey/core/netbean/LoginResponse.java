/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2019-07-04 8:37:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */


public class LoginResponse {

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

    public static class Content implements Serializable {

        private ArrayList<Factorys> Factorys;
        private String Token;
        private String Result;
        private String User;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        private String Message;
        @Override
        public String toString() {
            return "LoginResponse{" +
                    "Factorys=" + Factorys +
                    ", Token='" + Token + '\'' +
                    ", Result='" + Result + '\'' +
                    ", User='" + User + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }

        private String Name;

        public void setFactorys(ArrayList<Factorys> Factorys) {
            this.Factorys = Factorys;
        }

        public List<Factorys> getFactorys() {
            return Factorys;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public String getToken() {
            return Token;
        }

        public void setResult(String Result) {
            this.Result = Result;
        }

        public String getResult() {
            return Result;
        }

        public void setUser(String User) {
            this.User = User;
        }

        public String getUser() {
            return User;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getName() {
            return Name;
        }
    }


    public static class Factorys implements Serializable {

        private String Name;
        private String Sid;

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getName() {
            return Name;
        }

        public void setSid(String Sid) {
            this.Sid = Sid;
        }

        @Override
        public String toString() {
            return "Factorys{" +
                    "Name='" + Name + '\'' +
                    ", Sid='" + Sid + '\'' +
                    '}';
        }

        public String getSid() {
            return Sid;
        }

    }
}






