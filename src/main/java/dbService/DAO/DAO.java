package dbService.DAO;

import dbService.dataSets.DataSet;

public interface DAO {

    long insert(DataSet dataSet);

    DataSet select(Long id);
}
