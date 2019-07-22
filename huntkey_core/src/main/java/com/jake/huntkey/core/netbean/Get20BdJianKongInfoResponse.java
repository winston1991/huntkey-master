
package com.jake.huntkey.core.netbean;

import java.util.List;


public class Get20BdJianKongInfoResponse extends BaseResponse {


    private List<Content> Content;


    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public static class Data {

        private String[] Titles;
        private List<List<String>> Data;

        public void setTitles(String[] Titles) {
            this.Titles = Titles;
        }

        public String[] getTitles() {
            return Titles;
        }

        public void setData(List<List<String>> Data) {
            this.Data = Data;
        }

        public List<List<String>> getData() {
            return Data;
        }

    }


    public static class Content {

        private String LaytName;
        private Data Data;

        public void setLaytName(String LaytName) {
            this.LaytName = LaytName;
        }

        public String getLaytName() {
            return LaytName;
        }

        public void setData(Data Data) {
            this.Data = Data;
        }

        public Data getData() {
            return Data;
        }

    }
}