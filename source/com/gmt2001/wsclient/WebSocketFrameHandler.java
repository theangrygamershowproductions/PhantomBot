/*
 * Copyright (C) 2016-2022 phantombot.github.io/PhantomBot
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gmt2001.wsclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.json.JSONObject;

/**
 * Processes WebSocket frames and passes successful ones to the final handler
 *
 * @author gmt2001
 */
class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    /**
     * Reference to the attached {@link WSClient}
     */
    private final WSClient client;
    /**
     * Socket fully connected flag
     */
    boolean connected = false;

    /**
     * Default Constructor
     */
    WebSocketFrameHandler(WSClient client) {
        super();
        this.client = client;
    }

    /**
     * Handles incoming WebSocket frames
     *
     * @param ctx The {@link ChannelHandlerContext} of the session
     * @param frame The {@link WebSocketFrame} containing the request frame
     * @throws Exception Passes any thrown exceptions up the stack
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        this.client.handler.handleFrame(ctx, frame);
    }

    /**
     * Captures {@link HandshakeComplete} events and saves the {@link Channel}
     *
     * @param ctx The {@link ChannelHandlerContext} of the session
     * @param evt The event object
     * @throws Exception Passes any thrown exceptions up the stack
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketClientProtocolHandler.ClientHandshakeStateEvent && (WebSocketClientProtocolHandler.ClientHandshakeStateEvent) evt == WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            com.gmt2001.Console.debug.println("200 WS Client: Remote: [" + ctx.channel().remoteAddress().toString() + "]");
            ctx.channel().closeFuture().addListener((ChannelFutureListener) (ChannelFuture f) -> {
                this.connected = false;
                this.client.handler.onClose();
            });
            this.connected = true;
            this.client.handler.handshakeComplete(ctx);
        }
    }

    /**
     * Handles exceptions that are thrown up the stack
     *
     * @param ctx The {@link ChannelHandlerContext} of the session
     * @param cause The exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        com.gmt2001.Console.debug.printOrLogStackTrace(cause);
        ctx.close();
    }

    /**
     * Creates and prepares a text-type {@link WebSocketFrame} for transmission
     *
     * @param content The content to send
     * @return A {@link WebSocketFrame} that is ready to transmit
     */
    static WebSocketFrame prepareTextWebSocketResponse(String content) {
        return new TextWebSocketFrame(content);
    }

    /**
     * Creates and prepares a text-type {@link WebSocketFrame} for transmission from a {@link JSONObject}
     *
     * @param json The {@link JSONObject} to send
     * @return A {@link WebSocketFrame} that is ready to transmit
     */
    static WebSocketFrame prepareTextWebSocketResponse(JSONObject json) {
        return new TextWebSocketFrame(json.toString());
    }

    /**
     * Creates and prepares a binary-type {@link WebSocketFrame} for transmission
     *
     * @param content The binary content to send
     * @return A {@link WebSocketFrame} that is ready to transmit
     */
    static WebSocketFrame prepareBinaryWebSocketResponse(byte[] content) {
        return new BinaryWebSocketFrame(Unpooled.copiedBuffer(content));
    }

    /**
     * Creates and prepares a Close {@link WebSocketFrame} for transmission
     *
     * @param status The {@link WebSocketCloseStatus} to send
     * @return A {@link WebSocketFrame} that is ready to transmit
     */
    static WebSocketFrame prepareCloseWebSocketFrame(WebSocketCloseStatus status) {
        return new CloseWebSocketFrame(status);
    }

    /**
     * Creates and prepares a Close {@link WebSocketFrame} for transmission
     *
     * @param status The close status code to send
     * @param reason The reason string to send
     * @return A {@link WebSocketFrame} that is ready to transmit
     */
    static WebSocketFrame prepareCloseWebSocketFrame(int status, String reason) {
        return new CloseWebSocketFrame(status, reason);
    }

    /**
     * Transmits a {@link WebSocketFrame} back to the client
     *
     * @param ctx The {@link ChannelHandlerContext} of the session
     * @param reqframe The {@link WebSocketFrame} containing the request
     * @param resframe The {@link WebSocketFrame} to transmit
     */
    static void sendWsFrame(ChannelHandlerContext ctx, WebSocketFrame reqframe, WebSocketFrame resframe) {
        WebSocketFrameHandler.sendWsFrame(ctx.channel(), reqframe, resframe);
    }

    /**
     * Transmits a {@link WebSocketFrame} back to the client
     *
     * @param ch The {@link Channel} of the session
     * @param reqframe The {@link WebSocketFrame} containing the request
     * @param resframe The {@link WebSocketFrame} to transmit
     */
    static void sendWsFrame(Channel ch, WebSocketFrame reqframe, WebSocketFrame resframe) {
        ch.writeAndFlush(resframe);
    }

    /**
     * Sends a Close frame, then closes the connection
     *
     * @param ctx The {@link ChannelHandlerContext} of the session
     * @param closeFrame An optional close {@link WebSocketFrame} to transmit. Sends NORMAL_CLOSURE if null
     * @return A {@link ChannelFuture} that can be awaited
     */
    static ChannelFuture close(ChannelHandlerContext ctx, WebSocketFrame closeFrame) {
        return WebSocketFrameHandler.close(ctx.channel(), closeFrame);
    }

    /**
     * Sends a Close frame, then closes the connection
     *
     * @param ch The {@link Channel} of the session
     * @param closeFrame An optional close {@link WebSocketFrame} to transmit. Sends NORMAL_CLOSURE if null
     * @return A {@link ChannelFuture} that can be awaited
     */
    static ChannelFuture close(Channel ch, WebSocketFrame closeFrame) {
        if (closeFrame == null) {
            closeFrame = WebSocketFrameHandler.prepareCloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE);
        }

        WebSocketFrameHandler.sendWsFrame(ch, null, closeFrame);
        return ch.close();
    }
}
