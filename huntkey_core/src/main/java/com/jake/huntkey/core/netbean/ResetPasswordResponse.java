package com.jake.huntkey.core.netbean;

import java.util.List;

public class ResetPasswordResponse extends BaseResponse {

    private List<String> Content;

    public List<String> getContent() {
        return Content;
    }

    public void setContent(List<String> content) {
        Content = content;
    }
}
