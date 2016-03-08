package edu.wctc.dfb.dfbcomponents.model;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author dvandenberge
 */
@SessionScoped
public class PartService implements Serializable{
    //TODO add PartDAO
    
    public PartService(){
    //Empty constructor provided for dependency injection
    }
    
    public List<Part> getPartInventory(){
        //TODO implement PartDAO to retrieve part list
    }
    
    public void createPart(Object partName, String partID){
        //TODO implement PartDAO to create new part
    }
    
    public void deletePartByID(String partID){
        //TODO implement PartDAO to delete part
    }
    
    public void updatePart(String newValue, Object updateField){
        //TODO implement PartDAO to update part
    }
    
    public Part getPartByID(String partID){
        //TODO implement PartDAO to find specific part
    }
    
    public PartDAO getPartDAO(){
        //return dao
    }
    
    public void setPartDAO(PartDAO p){
        //this.dao=p;
    }
}
