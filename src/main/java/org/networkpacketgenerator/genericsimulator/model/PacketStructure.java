package org.networkpacketgenerator.genericsimulator.model;

import java.util.ArrayList;
import java.util.List;

public class PacketStructure {
    private List<PacketElement> elements;

    public PacketStructure() {
        this.elements = new ArrayList<>();
    }

    public PacketStructure(List<PacketElement> elements) {
        this.elements = elements != null ? elements : new ArrayList<>();
    }

    public void addElement(PacketElement element)
    {
        if(element==null){
            throw new IllegalArgumentException("Eklenecek paket elementi bos olamaz.");
        }
        this.elements.add(element);
    }

    public void removeElement(int index){
        if(index>=0 && index< elements.size()){
            this.elements.remove(index);
        }
    }

    public void clear() {
        this.elements.clear();
    }

    public List<PacketElement> getElements() {
        return elements;
    }

    public void setElements(List<PacketElement> elements) {
        this.elements = elements != null ? elements : new ArrayList<>();
    }
}
