package test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @program: nettystudy
 * @description: Framdecoder 自定义栈帧块处理解码器
 * @author: zhaoxudong
 * @create: 2020-02-07 14:25
 **/
public class FramLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FramLengthFrameDecoder(int frameLength) {
        this.frameLength = frameLength;
    }


    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        while(in.readableBytes() >= frameLength){
//            ByteBuf buf = in.readBytes(frameLength);
//            out.add(buf);
//        }
        int readableBytes = in.readableBytes();
        if(readableBytes > frameLength){
            in.clear();
            throw new TooLongFrameException();
        }
        // 否则，读取对应的字节数据
        ByteBuf byteBuf = in.readBytes(readableBytes);
        out.add(byteBuf);

    }
}
