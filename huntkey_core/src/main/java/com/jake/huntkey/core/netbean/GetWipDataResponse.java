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
public class GetWipDataResponse extends BaseResponse{


    private List<Content> Content;

    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public static class Content {

        private String[] Titles;

        public String[][] getData() {
            return Data;
        }

        public void setData(String[][] data) {
            Data = data;
        }

        private String[][]  Data;

        public String[] getTitles() {
            return Titles;
        }

        public void setTitles(String[] titles) {
            Titles = titles;
        }
    }
}