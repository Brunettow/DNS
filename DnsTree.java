
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package question;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class represents each of the nodes in the DNS tree structure.
 * 
 * @author Bengisu Takkin
 */

class DnsTree {
	
	private DnsNode root;  //to reach the tree structure
	private Map<String,Queue<String>> map= new HashMap<String,Queue<String>>();
	
	DnsTree() {
		DnsNode root = new DnsNode();
		this.root=root;
		this.root.setDomainName("Root");
	
	}
	
	/**
	 * inserts new record to the tree structure, if such a node exists, adds IP address to tree structure.
	 * 
	 * @param domainName name
	 * @param ipAddress IP address
	 */
	
	void insertRecord(String domainName, String ipAddress) {
		
		DnsNode node = root; //current node
		String name =""; //name of the current node
		
		while(domainName.contains(".")) { //until domain name only consists of a single name
			
			name = domainName.substring(domainName.lastIndexOf(".")+1)+"."+name;  //taking the last part and adding it to current name
			domainName = domainName.substring(0,domainName.lastIndexOf("."));  //updating domain node, discarding last part
			
			if(name.charAt(name.length()-1)=='.') {  //to avoid single node names with "." 
				name = name.substring(0,name.length()-1);
			}
			
			if(node.getChildNodeList().containsKey(name)) {  //if node is exists in mother node's child node list
				node = node.getChildNodeList().get(name);  //updating node as mother node

			} else {  //if node does not exist creates new node				
				DnsNode newNode = new DnsNode();  //node initialization
				newNode.setDomainName(name);  //gives name to node	
				node.getChildNodeList().put(name, newNode);  //puts node to the child list of mother node, adding it to tree
				node = node.getChildNodeList().get(name);  //updating node as mother node
			}	
		}
		
		name = domainName+"."+name;  //full name of the node
		
		if(node.getChildNodeList().containsKey(name)) {   //if node is exists in mother node's child node list
			
			node=node.getChildNodeList().get(name);
			node.addIpAddress(ipAddress);  //adding the new IP address to the node since it already exists
			
			Queue<String> ips = new LinkedList<String>(); 
			ips.addAll(node.ipAddresses); //IP addresses as queue to update map of records
			this.map.replace(name,ips); //Replacing new IP address list with old one
			
		} else { //if node does not exist
			
			DnsNode newNode = new DnsNode();  //node initialization
			newNode.setDomainName(name);   //giving node a name
			newNode.addIpAddress(ipAddress);  //adding IP
			
			node.getChildNodeList().put(name, newNode);  //puts node to the child list of mother node, adding it to tree
			node = newNode;
			
			Queue<String> ips = new LinkedList<String>(); 
			ips.addAll(node.ipAddresses);//IP addresses as queue to update map of records
			this.map.put(name, ips); //Adding it as a new record to map
		}
	}
			
	/**
	 * Removes a node from a tree if it is a leaf. if not, clears IP addresses of given node.
	 *  
	 * @param domainName name of the node
	 * @return whether node is successfully removed
	 */
	
	boolean removeRecord(String domainName) { 
		
		DnsNode motherNode = getMotherNode(domainName);  //mother node of the node if it exists in the tree structure
	
		if(motherNode!=null) {  //mother exists
			if(motherNode.getChildNodeList().containsKey(domainName)) {  //if mother node has such child node
				DnsNode node = motherNode.getChildNodeList().get(domainName); 
				
				if(node.getChildNodeList().isEmpty()) { //if node is a leaf node
					motherNode.getChildNodeList().remove(node); //removes it from tree structure
				}else {  //if it's not a leaf node
					node.flush();  //removes all the IP's in the node
				}
				this.map.remove(domainName); //discarding the record from map of records
				return true;		 
			} 
		}
		return false;  //failed to remove
	}
	
	/**
	 * Removes a specific nodes a specific IP, returns true if IP successfully removed.
	 * 
	 * @param domainName name of the node
	 * @param ipAddress IP address that will be removed
	 * @return whether IP has successfully removed or not
	 */
	
	boolean removeRecord(String domainName, String ipAddress) { 
		
		DnsNode node = getMotherNode(domainName);  //mother node of the node if it exists in the tree structure

		if(node!=null) {  //mother exists
			
			if(node.getChildNodeList().containsKey(domainName)) {   //if mother node has such child node
				node = node.getChildNodeList().get(domainName);  
				
				if(node.ipAddresses.contains(ipAddress)) {  //if node contains given IP
					node.ipAddresses.remove(ipAddress);  //removes IP
					if(node.ipAddresses.size()==0) {  //if node doesn't have any IP address left
						node.setValidDomain(false);  //changes valid domain with false
					} else {
						Queue<String> ips = new LinkedList<String>();  
						ips.addAll(node.ipAddresses);  //A queue with node's IP's
						this.map.replace(domainName, ips); //updating map of records 
					}
					return true;
				}
			}
		}
		return false;  //failed to remove IP
	}

	/**
	 * Takes domain name of a node and returns mother node
	 * 
	 * @param domainName name of the node
	 * @return mother node of desired node
	 */
	
	DnsNode getMotherNode (String domainName) { 
		
		String name="";  //name of the node
		DnsNode motherNode = this.root;  //mother node, first one is root
	
		if(domainName.contains(".")) {  //until domain name only consists of a single name
			
			while(domainName.contains(".")) {  
				name = domainName.substring(domainName.lastIndexOf(".")+1)+"."+name;  //taking the last part of the domain name and adding it to name
				domainName = domainName.substring(0, domainName.lastIndexOf(".")); //discarding last part from domain name
	
				if(name.charAt(name.length()-1)=='.') {  //to avoid single node names with "." 
					name = name.substring(0,name.length()-1);
				}
				
				if(motherNode.getChildNodeList().containsKey(name)) {  //if mother node contains other mother node
					motherNode = motherNode.getChildNodeList().get(name); //updating mother node, going deeper in tree structure
				} else {
					return null; //such a mother node does node exists
				}
			}	
			name = domainName + "." + name;	//full domain name
		}
		
		if(motherNode.getChildNodeList().containsKey(name)) { //if mother has the node as a child
			return motherNode; 
		
		}else {
			return null;
		}
	}
	
	/**
	 * with given domain name, finds the desired node and returns one of the IP addresses according to round robin algorithm.
	 *  
	 * @param domainName name of the node
	 * @return IP address if it exists, else null
	 */
	
	String queryDomain(String domainName) { 
		
		if(this.map.containsKey(domainName)) {  //if node with given name exists 

			Queue<String> ipAddresses = new LinkedList<String>();  //implementing a queue
			ipAddresses = this.map.get(domainName); 
			String ip = ipAddresses.remove();  //taking desired IP and updating order of the queue
			ipAddresses.add(ip);
			
			this.map.replace(domainName, ipAddresses); //Replacing IP list with same IP list but changed order
			return ip;  //returns IP
			
		} else {
			return null;  //if IP with such node does not exist, returns null
		}
	}
	/**
	 * implements a map with all records in the tree, uses domain names as keys and IP address list as values.
	 *   
	 * @return map with all records in tree structure
	 */
	
	Map<String, Set<String>> getAllRecords(){ 

		return getAllRecords(new HashMap<String,Set<String>>(), this.root);
	}
	
	Map<String, Set<String>> getAllRecords(Map<String, Set<String>> map, DnsNode node){  
		
		if(node.getValidDomain()) {  //if node contains any IP address
		
			map.put(node.getDomainName(),node.ipAddresses);  //puts it as a new record to the map
			
			if(node.getChildNodeList().isEmpty()) { //if the node is a leaf node
				return map;  //returns
			}
		}
	
		Iterator<DnsNode> itr = node.getChildNodeList().values().iterator();
		while(itr.hasNext()) {  
			DnsNode nod = itr.next(); 
			this.getAllRecords(map,nod); //one by one implementing same method to the children of the node
		}		
		return map;  //returns finished map
	}
	
	/**
	 * Returns root of the tree structure.
	 * 
	 * @return root node
	 */
	
	DnsNode getRoot() {
		return this.root;
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

