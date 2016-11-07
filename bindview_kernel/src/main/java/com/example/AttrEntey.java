package com.example;

/**
 * Created by CXY on 2016/11/1.
 */

public  class AttrEntey {
    private  String name;
    private  String type;
    private  int value;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AttrEntey{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                '}';
    }

    public AttrEntey(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }


     static AttrEntey create(String string, String substring, int value) {
        return new AttrEntey(string,substring,value);
    }
}
