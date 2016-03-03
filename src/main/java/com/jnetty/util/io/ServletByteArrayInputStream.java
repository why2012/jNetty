package com.jnetty.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class ServletByteArrayInputStream extends ServletInputStream {
	private ByteArrayInputStream bains = null;
	
	public ServletByteArrayInputStream(ByteArrayInputStream bains) {
		this.bains = bains;
	}

	@Override
	public int read() throws IOException {
		return this.bains.read();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return this.bains.available() > 0;
	}

	@Override
	public void setReadListener(ReadListener readListener) {

	}
}
