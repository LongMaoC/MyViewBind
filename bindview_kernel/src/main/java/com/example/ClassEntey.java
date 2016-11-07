package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by CXY on 2016/11/1.
 */

 final class ClassEntey {
    private  String classSimpleName;
    private  String parkageName;
    private  List<AttrEntey> arrts;

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public String getParkageName() {
        return parkageName;
    }

    public ClassEntey(String classSimpleName, String parkageName) {
        this.classSimpleName = classSimpleName;
        this.parkageName = parkageName;
        arrts = new ArrayList<>();
    }


    public List<AttrEntey> getArrts() {
        return arrts;
    }

    @Override
    public String toString() {
        return "ClassEntey{" +
                "classSimpleName='" + classSimpleName + '\'' +
                ", parkageName='" + parkageName + '\'' +
                ", arrts=" + arrts +
                '}';
    }

     void addAttr(AttrEntey attrEntey) {
        arrts.add(attrEntey);
    }

    static ClassEntey create(String classSimpleName, String parkageNmae) {
        return new ClassEntey(classSimpleName,parkageNmae);
    }


}
