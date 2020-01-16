package com.blockchainapps.dummycoin.chain.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Component;

import com.blockchainapps.dummycoin.chain.bean.Block;
import com.google.common.hash.Hashing;

@Component
public class BlockChain {
	
	private List<Block> chain =  new ArrayList<Block>();
	
	public BlockChain() {
		createBlock("1","0");
	}
	
	public List<Block> getChain() {
		return chain;
	}
	
	public Block createBlock(String proof,String previousHash) {
		Block block = new Block();
		block.setIndex(chain.size()	);
		block.setProof(proof);
		block.setPreviousHash(previousHash);
		block.setTimestamp(new Date().toString());
		chain.add(block);
		return block;
	}
	
	public String proofOfWork(String prevProof) {
		boolean findProof = false;
		int newProof = 1;
		int prevProofNum = Integer.valueOf(prevProof);
		while(!findProof) {
			String strToHash = ((newProof*2) - (prevProofNum*2))+"";
			String hash = Hashing.sha256().hashString(strToHash, StandardCharsets.UTF_8).toString();
			if(hash.startsWith("0000")) {
				findProof = true;
				return newProof+"";
			}
			newProof = newProof+1;
		}
		return null;
	}
	
	public String getHash(Block block) {
		return Hashing.sha256().hashString(block.toString(), StandardCharsets.UTF_8).toString();
	}
	
	public Block getPreviousBlock() {
		return chain.get(chain.size()-1);
	}
	
	public boolean isChainValid(){
		Block prevBlock = null;
		boolean isValid = true;
		
		for(Block block : chain) {
			//First block of the chain is skipped
			if(prevBlock!=null) {
				//check current block's previous hash is equals to previous block hash
				if(getHash(prevBlock).equals(block.getPreviousHash())) {
					int currentProof = Integer.valueOf(block.getProof());
					int prevProof = Integer.valueOf(prevBlock.getProof());
					//check current block's proof of work is valid
					String hash = Hashing.sha256().hashString(((currentProof*2) - (prevProof*2))+"",StandardCharsets.UTF_8).toString();
					if(!hash.startsWith("0000")) {
						isValid = false;
					}
				}else {
					isValid = false;
				}
			}
			prevBlock = block;
		}
		return isValid;
	}
	
	

}
