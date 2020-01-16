package com.blockchainapps.dummycoin.controller;

import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockchainapps.dummycoin.chain.bean.Block;
import com.blockchainapps.dummycoin.chain.service.BlockChain;

@RestController
@RequestMapping("/blockchain")
public class BlockChainController {
	
	@Autowired
	BlockChain blockChain;
	
	@GetMapping("/addBlock")
	public ResponseEntity<Block> addBlock() {
		Block prevBlock = blockChain.getPreviousBlock();
		String proofOfWork = blockChain.proofOfWork(prevBlock.getProof());
		String prevBlockHash = blockChain.getHash(prevBlock);
		Block block =blockChain.createBlock(proofOfWork, prevBlockHash);
		return new ResponseEntity<Block>(block,HttpStatus.OK);	 
	}
	
	@GetMapping("/")
	public List<Block> getBlockChain() {
		return blockChain.getChain();
	}
	
	@GetMapping("/isChainValid")
	public Object isChainValid() throws ParseException {
		String response = "{\"message\":\"not valid\"}";
		if(blockChain.isChainValid()) {
		response = "{\"message\":\"valid\"}";
		}
		
		JSONParser parser = new JSONParser(response);
		Object jsonObject = parser.parse();
		return jsonObject;
	}

}
