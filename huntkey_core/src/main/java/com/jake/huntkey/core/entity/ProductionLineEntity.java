package com.jake.huntkey.core.entity;



public class ProductionLineEntity {


    public String linebody;
    public String quota;
    public String item1;
    public String item2;
    public String item3;
    public String item4;
    public String item5;
    public String item6;
    public String item7;
    public String item8;
    public String item9;

    public ProductionLineEntity( ) {
        
    }

    public ProductionLineEntity(String linebody, String quota, String item1, String item2, String item3, String item4, String item5, String item6, String item7, String item8, String item9) {
        this.linebody = linebody;
        this.quota = quota;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.item7 = item7;
        this.item8 = item8;
        this.item9 = item9;
    }
}
