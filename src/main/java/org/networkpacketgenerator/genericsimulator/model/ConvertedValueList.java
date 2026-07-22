package org.networkpacketgenerator.genericsimulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvertedValueList {

    private static ConvertedValueList singletonInstance;

    private final List<byte[]> valueList;

    public ConvertedValueList() {
        this.valueList = new ArrayList<>();
    }

    public static synchronized ConvertedValueList getInstance(){
        if(singletonInstance==null){
            singletonInstance = new ConvertedValueList();
        }

        return singletonInstance;
    }

    public void addList(byte[] value){
        if(value!=null && value.length>0){
          this.valueList.add(value);
        }
    }

    public List<byte[]> getValueList() {
        return Collections.unmodifiableList(valueList);
    }

    public void clear(){
        this.valueList.clear();
    }

    public int getSize(){
        return this.valueList.size();
    }
}
