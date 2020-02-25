import org.junit.Test;

/**
 * Description: testMain
 * Author: DIYILIU
 * Update: 2020-02-25 10:03
 */
public class testMain {


    @Test
    public void test(){
        String str = "123";

        byte[] bytes = str.getBytes();

        System.out.println(bytesToHex(bytes));
    }


    public String bytesToHex(byte[] bytes){
        StringBuffer str = new StringBuffer();
        for (byte b: bytes){
            str.append(String.format("%02X", b & 0xFF));
        }

        return str.toString();
    }
}
