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
        String str = "H4sIAAAAAAAAAF2Qy07DMBBFfyXyOkTjt51deQiEAlS0omwjZ0itJnGVJiCE+HecEirU3cyZe8d3" +
                "/EVu94e1b5Hk5H5sEp0mDBgkLOcyFzpZPpCUXOO7d/gYoub1hnKAqwsa8QtuvWtw/bmPbnbqC+zq" +
                "YUtymtET2/hqQjzjJ3SHvt4OJIcMUrIJ/e65rPx4ILmawCxaYfO2mYVScEb/zYpQVrM93rDs0fmD" +
                "D930cERFOfhhrGIyYTNBueZaADVKxQRF6Op5SKnOmFbW8HgWUBA2JYvmz2qAZmoiXY3NtFhSA5YL" +
                "pbQ0mpmUrPaIvyk4k9ZyY6XkoAF0dDmHDfYxyJTqGPSpxbqc60XfHveuPnxXn7HLENpz2eDd7lw2" +
                "uh3+/uH3D5w4tlXKAQAA";
        System.out.println(str.length());

        str = GZIPUtils.uncompress(str);
        System.out.println(str);

        System.out.println(str.length());
    }

    @Test
    public void test2(){
        String str = "H4sIAAAAAAAAAK1QQXLEMAj7krDAxs+Jsf3/J5TMZrvpKdNOdREagxCmsLOURWGlJ/NSQkWiz2h4" +
                "wL7hqfevmLe6IPqrCkqD2mfr7gTv0VBP4sOVEHO1VDzHlWVSfpcvZoQH98kIRTrRLvNAapn6Y+Dy" +
                "N4aVLj4OE8uWdUhZmWbbUPXUYTJclhum97dFhYQe2/l9+VwDB/hv/0/DaLpkt1cO1rkaqO+ce42W" +
                "T71V7E8u4AsIr/n2UAIAAA==";

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
