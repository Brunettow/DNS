
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package question;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class represents each of the nodes in the DNS tree structure. Nodes consists of domain names, set of IP addresses
 * and a list of the child nodes.
 * 
 * @author Bengisu Takkin
 */

public class DnsNode {
	
	private Map<String, DnsNode> childNodeList;  /*for tree structure keys current node is .uk, the key â€œacâ€� refers to the node â€œ.ac.uk" and the key â€œcoâ€� refers to the node of ".co.uk".*/
	boolean validDomain;  //valid domain or subdomain
	Set<String> ipAddresses = new TreeSet<String>();  //domain name node's list of ip addresses, may more than one
	public String domainName;
	
		DnsNode() {
			this.childNodeList=new TreeMap<String, DnsNode>();
			this.validDomain=false;
		}
		/**
		 * Removes all IP addresses in the object.
		 */
		void flush() {
			this.ipAddresses.clear();
			this.validDomain=false;
		}
		
		/**
		 * Adds an ip address, if it isn't exist.
		 * 
		 * @param IpAddress given IP address
		 */
		
		void addIpAddress(String IpAddress) {
			if(!this.ipAddresses.contains(IpAddress)) {
			this.ipAddresses.add(IpAddress);
			this.validDomain=true;
		
			}
		}
		
		/**
		 * Returns list of the child nodes.
		 * 
		 * @return list of the child nodes
		 */
		
		Map<String, DnsNode> getChildNodeList() {
			return childNodeList;
		}
		
		/**
		 * Returns domain name.
		 * 
		 * @return domain name
		 */
		
		String getDomainName() {
			return this.domainName;
		}
		/**
		 * Sets domain name.
		 * 
		 * @param domainName
		 */
		void setDomainName(String domainName) {
			this.domainName=domainName;
		}
		
		/**
		 * Returns whether object has any IP address.
		 * 
		 * @return validDomain
		 */
		boolean getValidDomain() {
			return this.validDomain;
		}
		
		/**
		 * Sets valid domain according to existence of IP addresses.
		 * 
		 * @param valid validDomain
		 */
		void setValidDomain(boolean valid) {
			this.validDomain=valid;
		}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

