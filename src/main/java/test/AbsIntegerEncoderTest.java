package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @program: nettystudy
 * @description: 测试编码器
 * @author: zhaoxudong
 * @create: 2020-02-07 15:15
 **/
public class AbsIntegerEncoderTest {

    @Test
    public void testAbsIntegerEndocer(){
        ByteBuf buffer = Unpooled.buffer();
        for (int i = -1; i > -9; i--) {
            buffer.writeInt(i);
        }
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new AbsIntegerEncoder());
        Assert.assertTrue(embeddedChannel.writeOutbound(buffer));
        assertTrue(embeddedChannel.finish());
        // 查看绝对值是否相等
        for (int i = 1; i < 8; i++) {
            assertEquals(i, embeddedChannel.readOutbound());
        }
    }
}
