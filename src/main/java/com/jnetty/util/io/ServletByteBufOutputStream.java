package com.jnetty.util.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ServletByteBufOutputStream extends ServletOutputStream {
	private ByteBuf buf = null;
	
	public ServletByteBufOutputStream(ByteBuf buf) {
		this.buf = buf;
	}
		
	@Override
	public void write(int b) throws IOException {
		this.buf.writeByte(b);
	}

	@Override
	public boolean isReady() {
		return this.buf.isReadable();
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {

	}
}
