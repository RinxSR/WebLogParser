package logic;

/**
 * Класс для обработки сообщений.
 */
public class Message implements Comparable<Message> {

    private String message;

    private String timeOfMessage;
    private String textOfMessage;
    private String checksumOfMessage;

    public String getTimeOfMessage() {
        return timeOfMessage;
    }

    public void setTimeOfMessage(String timeOfMessage) {
        this.timeOfMessage = timeOfMessage;
    }

    public String getTextOfMessage() {
        return textOfMessage;
    }

    public void setTextOfMessage(String textOfMessage) {
        this.textOfMessage = textOfMessage;
    }

    public String getChecksumOfMessage() {
        return checksumOfMessage;
    }

    public void setChecksumOfMessage(String checksumOfMessage) {
        this.checksumOfMessage = checksumOfMessage;
    }

    private int dayOfMessage;
    private int monthOfMessage;
    private int yearOfMessage;
    private int hourOfMessage;
    private int minuteOfMessage;
    private int secondOfMessage;
    private int millisecondOfMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDayOfMessage() {
        return dayOfMessage;
    }

    public void setDayOfMessage(int dayOfMessage) {
        this.dayOfMessage = dayOfMessage;
    }

    public int getMonthOfMessage() {
        return monthOfMessage;
    }

    public void setMonthOfMessage(int monthOfMessage) {
        this.monthOfMessage = monthOfMessage;
    }

    public int getYearOfMessage() {
        return yearOfMessage;
    }

    public void setYearOfMessage(int yearOfMessage) {
        this.yearOfMessage = yearOfMessage;
    }

    public int getHourOfMessage() {
        return hourOfMessage;
    }

    public void setHourOfMessage(int hourOfMessage) {
        this.hourOfMessage = hourOfMessage;
    }

    public int getMinuteOfMessage() {
        return minuteOfMessage;
    }

    public void setMinuteOfMessage(int minuteOfMessage) {
        this.minuteOfMessage = minuteOfMessage;
    }

    public int getSecondOfMessage() {
        return secondOfMessage;
    }

    public void setSecondOfMessage(int secondOfMessage) {
        this.secondOfMessage = secondOfMessage;
    }

    public int getMillisecondOfMessage() {
        return millisecondOfMessage;
    }

    public void setMillisecondOfMessage(int millisecondOfMessage) {
        this.millisecondOfMessage = millisecondOfMessage;
    }

    public Message(String message) {

        this.message = message;

        timeOfMessage = message.substring(0, 23);
        textOfMessage = message.substring(24, message.indexOf("CRC_"));
        checksumOfMessage = message.substring(message.indexOf("CRC_"));

        dayOfMessage = Integer.valueOf(message.substring(0, 2));
        monthOfMessage = Integer.valueOf(message.substring(3, 5));
        yearOfMessage = Integer.valueOf(message.substring(6, 10));
        hourOfMessage = Integer.valueOf(message.substring(11, 13));
        minuteOfMessage = Integer.valueOf(message.substring(14, 16));
        secondOfMessage = Integer.valueOf(message.substring(17, 19));
        millisecondOfMessage = Integer.valueOf(message.substring(20, 23));
    }

    @Override
    public int compareTo(Message message) {
        if (yearOfMessage == message.yearOfMessage &&
                monthOfMessage == message.monthOfMessage &&
                dayOfMessage == message.dayOfMessage &&
                hourOfMessage == message.hourOfMessage &&
                minuteOfMessage == message.minuteOfMessage &&
                secondOfMessage == message.secondOfMessage &&
                millisecondOfMessage == message.millisecondOfMessage) {
            return 0;
        }

        if (yearOfMessage > message.yearOfMessage) {
            return 1;
        } else if (yearOfMessage < message.yearOfMessage) {
            return -1;
        } else if (monthOfMessage > message.monthOfMessage) {
            return 1;
        } else if (monthOfMessage < message.monthOfMessage) {
            return -1;
        } else if (dayOfMessage > message.dayOfMessage) {
            return 1;
        } else if (dayOfMessage < message.dayOfMessage) {
            return -1;
        } else if (hourOfMessage > message.hourOfMessage) {
            return 1;
        } else if (hourOfMessage < message.hourOfMessage) {
            return -1;
        } else if (minuteOfMessage > message.minuteOfMessage) {
            return 1;
        } else if (minuteOfMessage < message.minuteOfMessage) {
            return -1;
        } else if (secondOfMessage > message.secondOfMessage) {
            return 1;
        } else if (secondOfMessage < message.secondOfMessage) {
            return -1;
        } else if (millisecondOfMessage > message.millisecondOfMessage) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.
                append(timeOfMessage).
                append(" ").
                append(textOfMessage).
                append(checksumOfMessage);

        return new String(builder);
    }
}
