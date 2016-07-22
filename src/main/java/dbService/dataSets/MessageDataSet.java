package dbService.dataSets;

import logic.Message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class MessageDataSet extends DataSet {

    @Column(name = "time")
    private String time;

    @Column(name = "text")
    private String text;

    @Column(name = "checksum")
    private String checksum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public MessageDataSet() {

    }

    public MessageDataSet(Long id, Message message) {
        super(id);
        setTime(message.getTimeOfMessage());
        setText(message.getTextOfMessage());
        setChecksum(message.getChecksumOfMessage());
    }

    public MessageDataSet(Message message) {
        setTime(message.getTimeOfMessage());
        setText(message.getTextOfMessage());
        setChecksum(message.getChecksumOfMessage());
    }

    @Override
    public String toString() {
        return time + " " + text + checksum;
    }
}
