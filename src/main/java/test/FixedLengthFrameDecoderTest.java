package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @program: nettystudy
 * @description: 自定义解码器单元测试
 * @author: zhaoxudong
 * @create: 2020-02-07 14:30
 **/
public class FixedLengthFrameDecoderTest {

    @Test
    public void testFixedLengthFrameDecoder() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);

        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        // 标记为完成的状态
        assertTrue(embeddedChannel.finish());

        // 测试是否为3个划分的
        assertEquals(buf.readSlice(3), embeddedChannel.readInbound());
        assertEquals(buf.readSlice(3), embeddedChannel.readInbound());
        assertEquals(buf.readSlice(3), embeddedChannel.readInbound());

        assertNull(embeddedChannel.readInbound());
    }

    @Test
    public void testFiexdLengthFrameDecoder2(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);

        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FramLengthFrameDecoder(3));
//        assertFalse(embeddedChannel.writeInbound(input.readBytes(4)));
        try {
            embeddedChannel.writeInbound(input.readBytes(2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
