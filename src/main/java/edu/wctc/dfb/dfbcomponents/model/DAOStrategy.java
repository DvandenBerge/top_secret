/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.dfb.dfbcomponents.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dvandenberge
 */
public interface DAOStrategy {
    public abstract List<Part> getPartInventory() throws ClassNotFoundException,SQLException;
    
    public abstract void deletePartByID(String id) throws ClassNotFoundException,SQLException;

    public abstract void updateRecordByID(String table, 
}
