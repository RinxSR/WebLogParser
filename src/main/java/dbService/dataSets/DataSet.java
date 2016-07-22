package dbService.dataSets;

import javax.persistence.*;

@MappedSuperclass
public abstract class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataSet() {

    }

    public DataSet(Long id) {
        setId(id);
    }
}
