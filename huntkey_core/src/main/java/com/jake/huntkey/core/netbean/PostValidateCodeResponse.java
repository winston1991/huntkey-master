package com.jake.huntkey.core.netbean;

import java.util.List;

public class PostValidateCodeResponse extends BaseResponse {

    private List<Content> Content;

    public List<Content> getContent() {
        return Content;
    }

    public void setContent(List<Content> content) {
        Content = content;
    }


    public static class Content {

        private String Token;
        private String Msg;

        public void setToken(String Token) {
            this.Token = Token;
        }

        public String getToken() {
            return Token;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }

        public String getMsg() {
            return Msg;
        }

    }
}
