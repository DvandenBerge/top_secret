package edu.wctc.dfb.dfbcomponents.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;

/**
 *
 * @author dvandenberge
 */
@Dependent
public class MySqlDBStrategy implements DBStrategy {

    private Connection connection;

    public MySqlDBStrategy() {
    }

    @Override
    public void openConnection(String driverName, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        this.connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Method that returns a certain number of records from a table in a a
     * database in a ArrayList up to a certain point: upperLimit If upperLimit
     * is 0, retrieve all records TODO Convert ArayList to Array
     *
     * @param tableName Table to be queried
     * @param upperLimit Max number of records to be retrieved
     * @return An ArrayList populated with records from a database in the form
     * of a Map
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int upperLimit) throws SQLException {
        List<Map<String, Object>> records = new ArrayList();
        String sqlQuery = (upperLimit > 0) ? "SELECT * FROM " + tableName + " LIMIT " + upperLimit : "SELECT * FROM " + tableName;
        if (connection != null) {

            Statement sqlStatement = connection.createStatement();
            ResultSet rs = sqlStatement.executeQuery(sqlQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> record = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    record.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                records.add(record);
            }
        } else {
            System.out.println("No connection could be established");
        }
        return records;
    }

    @Override
    public void createRecord(String tableName, List<Object> columns, List<Object> values) throws SQLException {
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " (");
        for (Object col : columns) {
            sb.append(col.toString()).append(",");
        }

        sb = sb.deleteCharAt(sb.length() - 1);  //delete end comma
        sb.append(") VALUES (");
        for (Object val : values) {
            sb.append("?,");
        }

        sb = sb.deleteCharAt(sb.length() - 1);    //delete end comma
        sb.append(")");

        PreparedStatement pStmt = connection.prepareStatement(sb.toString());

        for (int i = 0; i < values.size(); i++) {
            pStmt.setObject(i + 1, values.get(i));
        }

        pStmt.executeUpdate();
    }

    @Override
    public int updateRecordById(String tableName, List colDescriptors, List colValues,
            String whereField, Object whereValue, boolean closeConnection)
            throws SQLException, Exception {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        // do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildUpdateStatement(connection, tableName, colDescriptors, whereField);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj = null;

            // set params for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // and finally set param for wehere value
            pstmt.setObject(index, whereValue);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                if (closeConnection) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw e;
            } // end try
        } // end finally

        return recsUpdated;
    }

    @Override
    public int deleteRecordById(String tableName, String pkColumn, Object value) throws SQLException {
        String sqlQuery = "DELETE FROM " + tableName + " WHERE " + pkColumn + " = ?";
        PreparedStatement pStmt = connection.prepareStatement(sqlQuery);
        pStmt.setObject(1, value);
        int result = pStmt.executeUpdate();
        return result;
    }

    public Map<String, Object> findRecordById(String table, String pkField, Object value) throws SQLException {
        String sqlQuery = "SELECT * FROM " + table + " WHERE " + pkField + " = ?";
        PreparedStatement pStmt = connection.prepareStatement(sqlQuery);
        pStmt.setObject(1, value);
        ResultSet rs = pStmt.executeQuery();

        ResultSetMetaData rsmd = rs.getMetaData();
        Map<String, Object> record = new HashMap();
        if (rs.next()) {
            int fieldNumber = rsmd.getColumnCount();
            for (int i = 1; i <= fieldNumber; i++) {
                record.put(rsmd.getColumnName(i), rs.getObject(i));
            }
        }
        pStmt.close();
        return record;
    }

    private PreparedStatement buildUpdateStatement(Connection c, String tableName, List colDescriptors, String whereField) throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return c.prepareStatement(finalSQL);
    }

}
