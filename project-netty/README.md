# netty学习笔记
## chapter3
### 流程
1. create channel 
2. register EventLoop with channel
3. process I/O with EventLoop during entire lifetime
### 组件
1. Channel[socket通信]
2. EventLoop[控制流、多线程、并发]
3. ChannelFuture[异步通知]
4. ChannelHandler
5. ChannelPipeline[包含多个ChannelHandler]
## chapter4
1. TRANSPORT API
## chapter5
1. ByteBuf——Netty's data container
    1. contain readIndex and writeIndex
    2. default limit:Integer.MAX_VALUE
    3. store data in the heap space of the JVM
    4. pattern is similar to ByteBuffer.【Heap buffers,Direct buffers,Composite buffers】
    5. ByteBuffer——java NIO's byte container
2. interface ByteBufHolder
## chapter6
1. ChannelHandler[synchronization] 
    1. Channel lifecycle.
       ChannelRegistered[registered to an *EventLoop*]
       -->ChannelActive[channel is active]
       -->ChannelInactive[is not connected to the remote peer]
       -->ChannelUnregistered[is not registered to EventLoop]
    2. ChannelHandler lifecycle.ChannelHandler has been added to or removed from a ChannelPipeline.
       1. ChannelInboundHandler.
       2. ChannelOutboundHandler.
2. ChannelPipeline
## chapter7
1. threading model overview
2. interface eventloop


