package logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CheckSumFinderTest {

    @Test
    public void testCheckSumMD5() throws Exception {

        String stringToCheckSum = "Привет, я строка, для тестирования метода CheckSumMD5";
        String result = CheckSumFinder.checkSumMD5(stringToCheckSum);

        Assert.assertEquals("f22e2256b24c0d688b1171d18dd47cbb", result);

    }

    @Test
    public void testCheckSumMD51() throws Exception {
        ArrayList<String> listToCheckSum = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listToCheckSum.add("Привет, я строка для тестирования метода CheckSumMD5 #" + (i + 1));
        }

        String result = CheckSumFinder.checkSumMD5(listToCheckSum);

        Assert.assertEquals("434c2ff66094f79a7bec734309406e29", result);
    }
}