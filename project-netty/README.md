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