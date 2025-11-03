package model;

import java.sql.SQLException;
import java.util.Collection;

public interface DAOInterface<T, K> {
    T doRetrieveByKey(K code) throws SQLException;
    Collection<T> doRetriveAll(String order) throws SQLException;
    void doSave(T product) throws SQLException;
    void doUpdate(T product) throws SQLException;
    boolean doDelete(K code) throws SQLException;
}
