package com.jnetty.util.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class ServletByteBufOutputStream extends ServletOutputStream {
	private ByteBuf buf = null;
	
	public ServletByteBufOutputStream(ByteBuf buf) {
		this.buf = buf;
	}
		
	@Override
	public void write(int b) throws IOException {
		this.buf.writeByte(b);
	}

}
