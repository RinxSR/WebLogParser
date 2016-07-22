package logic;

import org.apache.commons.codec.binary.StringUtils;

import java.security.MessageDigest;
import java.util.List;

/**
 * Вспомогательный класс для нахождения контрольных сумм.
 */
public class CheckSumFinder {

    private CheckSumFinder() {

    }

    /**
     * Вычисление контрольной суммы по алгоритму MD5 для строки.
     *
     * @param stringToCheckSum входная строка для вычисления контрольной суммы
     * @return возвращает строку с контрольной суммой
     */
    public static String checkSumMD5(String stringToCheckSum) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(StringUtils.getBytesUtf8(stringToCheckSum));
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Вычисление контрольной суммы по алгоритму MD5 для списка.
     *
     * @param listToCheckSum входной список для вычисления контрольной суммы
     * @return возвращает строку с контрольной суммой
     */
    public static String checkSumMD5(List<String> listToCheckSum) {

        StringBuilder tempString = new StringBuilder();

        for (String s : listToCheckSum) {
            tempString.append(s);
        }

        return checkSumMD5(tempString.toString());
    }
}