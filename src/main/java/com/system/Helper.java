package com.system;

import java.util.Base64;

public class Helper {
	public String encryptID(String id) {
		byte[] bytesEncoded = Base64.getEncoder().encode(id.getBytes());// encoding part
		return new String(bytesEncoded);
	}

	public int decodeID(String id) {
		byte[] valueDecoded = Base64.getDecoder().decode(id);// decoding part
		return Integer.parseInt(new String(valueDecoded));
	}

}
