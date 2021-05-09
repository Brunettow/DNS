
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package question;

/**
 * Represents the client side, send DNS names and receives ipAddresses, sends them to other servers.
 * 
 * @author Bengisu Takkin
 */

class Client {  
	
	private DnsTree root;  //to access the tree structure
	private String ipAddress; //IP address of the client
	private CachedContent[] cacheList = new CachedContent[10];  //place where received ip addresses stored
				
				/**
				 * Helps storing IP addresses.
				 * 
				 * @author Bengisu Takkin
				 */
	
				private class CachedContent {
					
					private String domainName;  //name
					private String ipAddress;  //IP
					private int hitNo;  //representation of how many times this record is used
					
					private CachedContent(String domainName, String ipAddress) {
						this.domainName=domainName;
						this.ipAddress=ipAddress;
					}
				}
				
	Client(String ipAddress, DnsTree root) {
		this.ipAddress=ipAddress;
		this.root=root;
	}
	
	/**
	 * Receives IP with given domain name and returns the IP address of the requested domain name to server.
	 * 
	 * @param domainName given domain name
	 * @return IP address of the given domain name
	 */
	
	String sendRequest(String domainName) {  
		
		String ipReturned = cacheListGetIpAddress(domainName);  //checking whether given the domain name is exists in cache list
	
		if(ipReturned!=null) {  //if ip is exists in cache list
			return ipReturned;  //returns ip to server
		} else {  //if ip does not exist in cache list
			ipReturned = this.root.queryDomain(domainName);  //sends domain name to DNS tree to receive ip from the tree  
			
			if(ipReturned!=null) { //if ip was exist in tree
				this.addToCache(domainName, ipReturned);  //adds it to cache list
			}
			return ipReturned;  //sends ip to server
		}
	}
	
	/**
	 * With given domain name (which is key) searches for IP address and returns it if its exist. 
	 *
	 * @param key domain name of IP address
	 * @return IP address of given domain name
	 */
	
	String cacheListGetIpAddress (String key) {  

		int i=0; //the nth place of cache list

		while(i<cacheList.length) {  
			if(cacheList[i]!=null) {  //Checks if the object exists
				if(this.cacheList[i].domainName==key) {  //Checks whether it's the element that is wanted
					this.cacheList[i].hitNo++;  //incrementing hit no, since its used
					return this.cacheList[i].ipAddress;  //Returns IP
				} 
			}	
				i++;
		}
		return null;  //if given key does not exist, returns null
	}
	
	/**
	 * Adds given IP to proper place in cache list.
	 * 
	 * @param domainName name
	 * @param ipAddress IP address of given domain name
	 */

	void addToCache(String domainName, String ipAddress) { 
		
		if(this.cacheList[0]!=null) {  //checks whether first element is empty
			int owner = 0; //owner of the minimum hit no
			int hitno = this.cacheList[0].hitNo; //hit no 
			int count = 1;  //counter for cache list
			
			while(count<this.cacheList.length) {  
				if(this.cacheList[count]!=null) { //checks whether the element exists
					if(hitno>this.cacheList[count].hitNo) {  //compares hit nos
						hitno = this.cacheList[count].hitNo;  //changes hit no with minimum one
						owner = count;  //changes owner of the minimum hit no
					}
					
				}else {  //if element does not exist
					break; 
				}
				count++;
			}
			
			
			if(count==cacheList.length) { //if cache list is full, changes element with min hit no with new record
				this.cacheList[owner]= new CachedContent(domainName, ipAddress);	
			} else {                      //cache has empty places, puts new record to nearest
				this.cacheList[count]= new CachedContent(domainName, ipAddress);	
			}
			
		} else {  //if first element was empty, puts new element to first place
			this.cacheList[0]= new CachedContent(domainName, ipAddress);
		}
	}
	
	/**
	 * Clears record in the cache list.
	 */
	
	void flushCache() {
		
		for(int i=0; i<this.cacheList.length;i++) {
			this.cacheList[i]=null;
		}
	}
	
	/**
	 * Returns IP address of the client.
	 * 
	 * @return IP address of the client
 	 */

	public String getIpAddress() {
		return ipAddress;
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

