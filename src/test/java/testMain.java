import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.Date;

/**
 * Description: testMain
 * Author: DIYILIU
 * Update: 2020-02-25 10:03
 */
public class testMain {


    @Test
    public void test() {
        String str = "123";

        byte[] bytes = str.getBytes();

        System.out.println(Bytes.toHex(bytes));
    }

    @Test
    public void testStr(){
        String str = "0000000000000000000000000000ffffffffffff000000000000000000000000";

        System.out.println(new String(Bytes.fromHex(str)));
    }


    @Test
    public void testTime() {
        String str = "02e6035f000000007b73000000000000";

        byte[] bytes = Bytes.fromHex(str);
        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        long sec = buf.readLongLE();
        long ms = buf.readLongLE();

        System.out.println(new Date(sec * 1000 + ms));
    }


    @Test
    public void testIP(){
        String str = "3139322E3136382E322E323031000000";
        str = "3139322e3136382e32322e3135340000";
        byte[] bytes = Bytes.fromHex(str);

        System.out.println(new String(bytes));
    }


    @Test
    public void testVehicle(){
        String str = "CDCC8C3FCDCC0C4033335340CDCC8C4031D40000";

        byte[] bytes = Bytes.fromHex(str);
        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        System.out.println(buf.readFloatLE());
        System.out.println(buf.readFloatLE());
        System.out.println(buf.readFloatLE());
        System.out.println(buf.readFloatLE());
        System.out.println(buf.readUnsignedIntLE());
    }


    @Test
    public void testRedis(){
        String timestamp = String.valueOf(new Date().getTime() / 1000);

        System.out.println(timestamp);
    }


    @Test
    public void testTime2() {
        String str = "7856341200000000D0000000000020C90000002B0F035F000000000A85090000000000170900003139322E3136382E33322E3138330000005844453132302D31000000000000000000000000000000000000000000000000000038419A99D9406666D640CDCC8C40804F12000000000001000000C8B44F5B7E515D40B6F6A22523B348400B2593533BC0874000000040E9C300C0000000C05DCD2A4000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        byte[] bytes = Bytes.fromHex(str);
        ByteBuf buf = Unpooled.copiedBuffer(bytes);
        buf.readBytes(new byte[19]);

        long sec = buf.readLongLE();
        long ms = buf.readLongLE();

        System.out.println(new Date(sec * 1000 + ms));
    }
}
