# dummycoin
Simple Block chain implementation in java

Operations :\
           <ol>
  <li>Add Block to chain (http://server:port/blockchain/addBlock) : <p> computes proof of work and adds block to the chain <p></li> 
  <li>get Chain (http://server:port/blockchain/) : <p> returns the chain </li>
  <li>Validate chain (http://server:port/blockchain/isChainValid) :<p>Validates chain ,checks previous hash and proof of work</li>
           </ol>
