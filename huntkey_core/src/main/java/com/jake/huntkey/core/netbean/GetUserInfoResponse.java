/**
  * Copyright 2019 bejson.com 
  */
package com.jake.huntkey.core.netbean;
import com.jake.huntkey.core.activitys.BaseActivity;

import java.util.List;

/**
 * Auto-generated: 2019-07-15 10:1:37
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetUserInfoResponse extends BaseResponse {


    private List<Content> Content;

    public void setContent(List<Content> Content) {
         this.Content = Content;
     }
     public List<Content> getContent() {
         return Content;
     }


    public static  class Content {

        private String Id;
        private String Name;
        private String Phone;
        private String DeptCode;
        private String DeptName;
        public void setId(String Id) {
            this.Id = Id;
        }
        public String getId() {
            return Id;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
        public String getName() {
            return Name;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }
        public String getPhone() {
            return Phone;
        }

        public void setDeptCode(String DeptCode) {
            this.DeptCode = DeptCode;
        }
        public String getDeptCode() {
            return DeptCode;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }
        public String getDeptName() {
            return DeptName;
        }

    }
}