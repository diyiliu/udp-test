import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: Bytes
 * Author: DIYILIU
 * Update: 2019-10-18 10:00
 */
public class Bytes {

    public static final Pattern HEX_PATTERN = Pattern.compile("^[a-fA-F0-9]+$");

    public static boolean isHex(String hex) {
        Matcher matcher = HEX_PATTERN.matcher(hex);
        return matcher.find();
    }

    /**
     * 将字节数组转换为16进制表示
     *
     * @param bytes
     * @return
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, true);
    }


    /**
     * 将字节数组转换为16进制表示
     *
     * @param bytes
     * @param upper 大小写
     * @return
     */
    public static String toHex(byte[] bytes, boolean upper) {
        return toHex(bytes, upper, "");
    }

    /**
     * 将字节数组转换为16进制表示
     *
     * @param bytes
     * @param upper     大小写
     * @param byteSplit 字节分隔字符
     * @return
     */
    public static String toHex(byte[] bytes, boolean upper, String byteSplit) {
        if (bytes == null || bytes.length == 0)
            return "";

        StringBuilder builder = new StringBuilder(bytes.length * 3);
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0 && byteSplit != null && byteSplit != "")
                builder.append(byteSplit);

            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2)
                builder.append("0");
            builder.append(hex);
        }
        String result = builder.toString();
        if (true)
            return result.toUpperCase();
        else
            return result;

    }

    /**
     * 将16进制字符串转换为字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] fromHex(String hex) {
        return fromHex(hex, "");
    }

    /**
     * 将16进制字符串转换为字节数组
     *
     * @param hex
     * @param byteSplit 字节分隔字符
     * @return
     */
    public static byte[] fromHex(String hex, String byteSplit) {
        if (hex == null || hex.equals("")) {
            return null;
        }

        hex = hex.toUpperCase();
        if (byteSplit != null && byteSplit != "")
            hex = hex.replaceAll(byteSplit, "");

        if (!isHex(hex))
            throw new IllegalArgumentException("hex不符合16进制格式");
        if (hex.length() % 2 != 0)
            throw new IllegalArgumentException("hex的长度必须是偶数");

        int length = hex.length() / 2;
        char[] hexChars = hex.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return bytes;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
