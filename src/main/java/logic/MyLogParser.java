package logic;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация обработки лог-файла.
 */
public class MyLogParser implements LogParser {

    private String sourceLog;
    private String handledLog;

    private ArrayList<String> listOfTmeString;
    private ArrayList<String> listOfSimpleString;
    private ArrayList<String> listOfCkecksumString;
    private ArrayList<Message> messagesToWrite;

    public String getHandledLog() {
        return handledLog;
    }

    public void setHandledLog(String handledLog) {
        this.handledLog = handledLog;
    }

    public String getSourceLog() {
        return sourceLog;
    }

    public void setSourceLog(String sourceLog) {
        this.sourceLog = sourceLog;
    }

    public ArrayList<String> getListOfTmeString() {
        return listOfTmeString;
    }

    public void setListOfTmeString(ArrayList<String> listOfTmeString) {
        this.listOfTmeString = listOfTmeString;
    }

    public ArrayList<String> getListOfSimpleString() {
        return listOfSimpleString;
    }

    public void setListOfSimpleString(ArrayList<String> listOfSimpleString) {
        this.listOfSimpleString = listOfSimpleString;
    }

    public ArrayList<String> getListOfCkecksumString() {
        return listOfCkecksumString;
    }

    public void setListOfCkecksumString(ArrayList<String> listOfCkecksumString) {
        this.listOfCkecksumString = listOfCkecksumString;
    }

    public ArrayList<Message> getMessagesToWrite() {
        return messagesToWrite;
    }

    public void setMessagesToWrite(ArrayList<Message> messagesToWrite) {
        this.messagesToWrite = messagesToWrite;
    }

    /**
     * Инициализация MyLogParser.
     */
    public MyLogParser() {
        listOfTmeString = new ArrayList<>();
        listOfSimpleString = new ArrayList<>();
        listOfCkecksumString = new ArrayList<>();
        messagesToWrite = new ArrayList<>();
    }

    @Override
    public void process(InputStream is, OutputStream os) throws IOException {

        PrintWriter writer = new PrintWriter(os);
        writeMessagesToFile(writer, parseLogFile(is));
    }

    /**
     * Обработка входящего потока логов.
     *
     * @param is входящий поток
     * @return коллекция сообщений
     * @throws IOException ошибка ввода-вывода
     */
    public ArrayList<Message> parseLogFile(InputStream is) throws IOException {

        String inputString;
        StringBuilder sourceLogBuilder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        while ((inputString = reader.readLine()) != null) {

            sourceLogBuilder.append(inputString);
            sourceLogBuilder.append("\n");

            switch (stringChecker(inputString)) {
                case TIME_STRING:
                    listOfTmeString.add(inputString);
                    Collections.sort(listOfTmeString);
                    break;
                case SIMPLE_STRING:
                    listOfSimpleString.add(inputString);
                    break;
                case CHECKSUM_STRING:
                    listOfCkecksumString.add(inputString);
                    findMessageOnChecksum();
                    break;
            }
        }
        sourceLog = new String(sourceLogBuilder);

        Collections.sort(messagesToWrite);
        StringBuilder messagesToWriteBuilder = new StringBuilder();
        for (Message message : messagesToWrite) {
            messagesToWriteBuilder.append(message.toString());
        }
        handledLog = new String(messagesToWriteBuilder);

        return messagesToWrite;
    }

    /**
     * Перебор коллекции (1 TimeString + n SimpleString) для поиска совпадения с входящей контрольной суммой.
     */
    private void findMessageOnChecksum() {
        for (int i = 0; i < listOfCkecksumString.size(); i++) {
            for (int j = 0; j < listOfTmeString.size(); j++) {

                String checkSum = listOfCkecksumString.get(i).substring(4);
                ArrayList<String> tempList = getListToCheck(listOfTmeString.get(j));


                for (int k = (int) Math.pow(2, tempList.size()) - 1; k > 1; k--) {

                    String binaryForm = Integer.toBinaryString(k);


                    ArrayList<String> listForCheckMD5 = new ArrayList<>();

                    for (int l = 0; l < binaryForm.length(); l++) {
                        if (binaryForm.charAt(l) == '1') {
                            listForCheckMD5.add(tempList.get(l));
                        }
                    }

                    if (CheckSumFinder.checkSumMD5(listForCheckMD5).equals(checkSum)) {

                        StringBuilder message = new StringBuilder();

                        for (String s : listForCheckMD5) {
                            message.append(s);
                            message.append("\n");
                            listOfSimpleString.remove(s);
                        }

                        message.append(listOfCkecksumString.get(i));
                        message.append("\n");

                        listOfTmeString.remove(j);
                        listOfCkecksumString.remove(i);

                        messagesToWrite.add(new Message(message.toString()));

                        return;
                    }
                }
            }
        }
    }

    /**
     * Сортировка сообщений по дате и запись в файл.
     *
     * @param writer - райтер для записи в OutFile
     * @param messagesToWrite коллекция сообщений для записи
     */
    private void writeMessagesToFile(PrintWriter writer, ArrayList<Message> messagesToWrite) {

        for (Message message : messagesToWrite) {
            writer.print(message.toString());
        }
        writer.flush();
    }

    /**
     * Определение типа строки.
     *
     * @param s - проверяемая строка
     * @return тип строки (Enum StringType)
     */
    private StringType stringChecker(String s) {

        if (checkOnTimeString(s)) {
            return StringType.TIME_STRING;
        } else if (checkOnChecksumString(s)) {
            return StringType.CHECKSUM_STRING;
        } else {
            return StringType.SIMPLE_STRING;
        }
    }

    /**
     * Проверка строки на соответствие типу TIME_STRING.
     *
     * @param stringToCheck - проверяемая строка
     * @return true если регуярное выражение присутствует в строке
     */
    private boolean checkOnTimeString(String stringToCheck) {

        String regexForTimeString = "\\d\\d\u002E\\d\\d\u002E\\d\\d\\d\\d\u0020\\d\\d\u003A\\d\\d\u003A\\d\\d\u002E\\d\\d\\d";

        Matcher matcher = Pattern.compile(regexForTimeString).matcher(stringToCheck);
        return matcher.lookingAt();
    }

    /**
     * Проверка строки на соответствие типу CHECKSUM_STRING.
     *
     * @param stringToCheck - проверяемая строка
     * @return true если строка начинается с CRC_
     */
    private boolean checkOnChecksumString(String stringToCheck) {
        return stringToCheck.startsWith("CRC_");
    }

    /**
     * Создание коллекции из TimeString и набора свободных SimpleString.
     *
     * @param timeString - входящая строка с датой
     * @return возвращает созданную коллекцию
     */
    private ArrayList<String> getListToCheck(String timeString) {
        ArrayList<String> tempList = new ArrayList<>();

        tempList.add(timeString);

        for (String simpleString : listOfSimpleString) {
            tempList.add(simpleString);
        }
        return tempList;
    }
}
