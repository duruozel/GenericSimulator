package org.networkpacketgenerator.genericsimulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvertedValueList {
    private final List<byte[]> valueList;

    public ConvertedValueList(List<byte[]> valueList) {
        this.valueList = new ArrayList<>();
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
