package com.github.marwen;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.java.Log;

@Log(topic = "Client")
public class AppClient {
    @Getter
    private String serverHost;
    @Getter
    private int serverPort;

    public AppClient() {

        this.serverHost = System.getProperty("server.host", "localhost");
        this.serverPort = Integer.parseInt(System.getProperty("server.port", "1080"));
        log.fine(serverHost);
        log.fine(Integer.toString(serverPort));

    }

    public void start() throws InterruptedException{
        log.info("Starting Client");
        EventLoopGroup grp = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(grp).channel(NioSocketChannel.class).remoteAddress(serverHost, serverPort)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new AppClientHandler());
                        }
                    });
            boot.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
            .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = boot.connect().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e){
            log.warning(e.getMessage());
        } finally {
            grp.shutdownGracefully().sync();
        }

    }

}
