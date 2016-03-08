package edu.wctc.dfb.dfbcomponents.model;

import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author dvandenberge
 */
@Dependent
public class PartDAO implements Serializable{
    @Inject
    private DBStrategy db;
    
    private String driver;
    private String url;
    private String password;
    private String username;
    
    private final String TABLE_NAME="part";
    private final String TABLE_PK_NAME="part_id";
    
    public PartDAO(){
        //Empty contructor for dependency injection
    }
    
    
}
