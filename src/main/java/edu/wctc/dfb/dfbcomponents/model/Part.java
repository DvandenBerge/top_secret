package edu.wctc.dfb.dfbcomponents.model;

import java.util.Objects;

/**
 *
 * @author dvandenberge
 */
public class Part {
    private String partName;
    private String partID;
    
    public Part(String name, String id){
        this.partName=setPartName(name);
        this.partID=setPartID(id);
    }
    
    public String setPartName(String name){
        if(name!=null){
            this.partName=name;
        }else{
            throw new IllegalArgumentException("The was no new Part Name specified");
        }
        return this.partName;
    }
    public String getPartName(){
        return partName;
    }
    
    public String setPartID(String id){
        if(id!=null){
            this.partID=id;
        }else{
            throw new IllegalArgumentException("No new Part ID was specified");
        }
        return this.partName;
    }
    public String getPartID(){
        return partID;
    }

    @Override
    public String toString() {
        return "This is "+partName+". Part id is "+partID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.partName);
        hash = 29 * hash + Objects.hashCode(this.partID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Part other = (Part) obj;
        if (!Objects.equals(this.partID, other.partID)) {
            return false;
        }
        return true;
    }

}
