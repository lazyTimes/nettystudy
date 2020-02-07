package test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @program: nettystudy
 * @description: 自定义编码器
 * @author: zhaoxudong
 * @create: 2020-02-07 15:10
 **/
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    /**
     * 自定义编码器
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    protected void encode(ChannelHandlerContext ctx, ByteBuf buf, List out) throws Exception {
        while(buf.readableBytes() > 4){
            int abs = Math.abs(buf.readInt());
            out.add(abs);
        }
    }
}
