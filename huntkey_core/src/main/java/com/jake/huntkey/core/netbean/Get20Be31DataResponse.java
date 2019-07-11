/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-08 20:37:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Get20Be31DataResponse extends  BaseResponse{


    private List<Content> Content;

    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public class Content {

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