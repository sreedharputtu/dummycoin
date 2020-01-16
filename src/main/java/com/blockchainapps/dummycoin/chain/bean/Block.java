package com.blockchainapps.dummycoin.chain.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Block {
	private int index;
	private String timestamp;
	private String proof;
	private String previousHash;
	
	

}
