package com.jake.huntkey.core.netbean;

public class BaseResponse {
    public String ErrorMsg;
    public String Status;

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
