/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-19 8:39:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetJiePaiResponse extends BaseResponse {


    private List<Content> Content;


    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public static class Content {

        private String[] Titles;
        private String[][] Data;

        public void setTitles(String[] Titles) {
            this.Titles = Titles;
        }

        public String[] getTitles() {
            return Titles;
        }

        public void setData(String[][] Data) {
            this.Data = Data;
        }

        public String[][] getData() {
            return Data;
        }

    }

}