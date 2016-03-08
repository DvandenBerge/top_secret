package edu.wctc.dfb.dfbcomponents.model;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dvandenberge
 */
public interface DBStrategy {
    public abstract void openConnection(String driverName,String url,String username, String password) throws ClassNotFoundException,SQLException;
    public abstract void closeConnection() throws SQLException;
    public abstract List<Map<String,Object>> findAllRecords(String tableName, int upperLimit) throws SQLException;
    public abstract void createRecord(String tableName, List<Object> columns, List<Object> values) throws ClassNotFoundException, SQLException;
    public abstract int deleteRecordById(String tableName, String pkColumn, Object value) throws SQLException;
    public abstract int updateRecordById(String tableName, List colDescriptors, List colValues,String whereField, 
                Object whereValue, boolean closeConnection) throws SQLException, Exception;
    public abstract Map<String,Object> findRecordById(String table, String pkField, Object value) throws SQLException;
}
