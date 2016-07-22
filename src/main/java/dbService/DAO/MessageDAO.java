package dbService.DAO;

import dbService.dataSets.DataSet;
import dbService.dataSets.MessageDataSet;
import org.hibernate.Session;

public class MessageDAO implements DAO {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public MessageDAO(Session session) {
        setSession(session);
    }

    @Override
    public long insert(DataSet dataSet) {
        session.save(dataSet);
        return 1;
    }

    @Override
    public DataSet select(Long id) {
        return session.get(MessageDataSet.class, id);
    }
}
