import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.Date;

/**
 * Description: ZipTest
 * Author: DIYILIU
 * Update: 2020-07-07 13:42
 */
public class ZipTest {

    @Test
    public void test() {
        String str = "H4sIAAAAAAAAAF2Qy26DMBBFfwV5TSw/MDbsqBK1qtKHmijpFpkpsQI4ItCqqvrvHaeERSwv7Dtn" +
                "7lz7h9yfzlvXAsnJ49hEJo4EEyziImcMd1Q8kZgs4dNZePYIvS9XXLCFQHUHB2cb2H6fsJvP9zV0" +
                "9XBAhVM1i3tXBU3RdJYewNWHgeSMspjsfX98Kys3nkme0GSGNtB87CcQ54YV8OsoX1aTAT7jtQfr" +
                "zs53iAZpXQ5uGCsMl2RUZlqLhDFuUqmx5rt6KnKuqdDScJGiO2cSpxfNtVUrQZXmjGPwoquhCeZG" +
                "ZiphMhVSGZZJGZPNCQCjSGouGbkQwohUJatFcLMWGugxTsh2ifvSQl1O56JvL86bL9fVN9qd9+0t" +
                "Njh7vMVGe4T/v/z9A0m3+7DTAQAA";
        System.out.println(str.length());

        str = GZIPUtils.uncompress(str);
        System.out.println(str);

        System.out.println(str.length());
    }

    @Test
    public void test2(){
        String str = "H4sIAAAAAAAAALWQUQ4DIQhErzQIKBxnBb3/Eeqm3cR+bbNJ38+AwcGRiZ1LGUxc2Zbyp1tg4RkN" +
                "N8yNu9mn5FYXhF+1B0S3rcfX6DDQqXyTElATEV0nhVGSy5M3sgn54Z4uOJ07CyIjLAQGmbS70lt6" +
                "OokVtaqkKdBZex8+hvUiJliXetYGJxNrcuVCUB0zeV6ptY3Gxn/7/195ATvavrRQAgAA";

        str = GZIPUtils.uncompress(str);
        System.out.println(str);


        /*
        # IP 地址 16
        3139322e3136382e32322e3135340000
        # 端口 4
        9dc70000
        # UID 32
        0000000000000000000000000000ffffffffffff000000000000000000000000
        # deviceNo 32
        0000000000000000000000000000000000000000000000000000000000000000
        # 长度 4
        d0000000

        00 #协议版本
        00 #加密方式
        20 #消息ID

        c9000000 # 长度  4
        # 时间 16
        e8a1685e000000000a0a060000000000

        16 + 4 + 32 + 32 + 4 + 3 + 4 = 95
        */

        byte[] bytes = Bytes.fromHex(str);
        ByteBuf buf = Unpooled.copiedBuffer(bytes);
        buf.readBytes(new byte[95]);

        long sec = buf.readLongLE();
        long ms = buf.readLongLE();

        System.out.println(new Date(sec * 1000 + ms / 1000));
    }
}
