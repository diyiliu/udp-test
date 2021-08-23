import io.netty.util.NetUtil;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * NetTest
 *
 * @author: DIYILIU
 * @date: 2021/08/23
 */
public class NetTest {


    @Test
    public void test() {

        NetworkInterface ni = NetUtil.LOOPBACK_IF;
        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

        while (inetAddresses.hasMoreElements()) {
            InetAddress address = inetAddresses.nextElement();
            System.out.println(address.getHostAddress() + ":" + (address instanceof Inet4Address));
        }
    }
}
