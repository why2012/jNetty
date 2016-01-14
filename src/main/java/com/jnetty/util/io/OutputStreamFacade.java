package com.jnetty.util.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamFacade extends OutputStream {
	private ByteBuf buf = null;
	
	public OutputStreamFacade(ByteBuf buf) {
		this.buf = buf;
	}

	@Override
	public void write(int b) throws IOException {
		this.buf.writeByte(b);
	}
	
	@Override
	public void flush() throws IOException {
		super.flush();
	}
	
	@Override
	public void write(byte[] b) {
		this.buf.writeBytes(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) {
		this.buf.writeBytes(b, off, len);
	}
}
