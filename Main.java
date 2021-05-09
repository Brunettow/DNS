package question;

import java.util.*;

public class Main {

	static Set<String> set=new HashSet<String>();
	
	public static void main(String[] args) {
		DnsTree tree=new DnsTree();
		
		tree.insertRecord("bbc.co.uk", "7.7.7.7");
		tree.insertRecord("cambridge.ac.uk", "8.8.8.8");
		tree.insertRecord("google.com", "3.3.3.3");
		tree.insertRecord("mail.google.com", "4.4.4.4");
		tree.insertRecord("twitter.com", "5.5.5.5");
        tree.insertRecord("developer.twitter.com", "6.6.6.6");
        tree.insertRecord("boun.edu.tr", "1.1.1.1");
        tree.insertRecord("cmpe.boun.edu.tr", "2.2.2.2");
        tree.insertRecord("metu.edu.tr", "1.1.1.1");
        tree.insertRecord("google.com", "4.4.4.4");
        tree.insertRecord("machine.boun.edu.tr", "19.9.9.9");
        tree.insertRecord("facebook.com", "10.10.10.10");
      tree.removeRecord("abc","4.4.4.4");
       tree.removeRecord("google.com","4.4.4.4");
        tree.removeRecord("google.com");
        
       Client c= new Client("192.168.1.1",tree);
       System.out.println(c.sendRequest("google.com")+"************");
       System.out.println(c.sendRequest("google.com")+"************");
       System.out.println(c.sendRequest("google.com")+"************");
       System.out.println(c.sendRequest("boun.edu.tr")+"************");
       System.out.println(c.sendRequest("boun.edu.tr")+"************");
       System.out.println(c.sendRequest("cmpe.boun.edu.tr")+"************");
       System.out.println(c.sendRequest("cmpe.boun.edu.tr")+"************");
       System.out.println(c.sendRequest("metu.edu.tr")+"************");
       System.out.println(c.sendRequest("metu.edu.tr")+"************");
       System.out.println(c.sendRequest("twitter.com")+"************");
       System.out.println(c.sendRequest("twitter.com")+"************");
       System.out.println(c.sendRequest("bbc.co.uk")+"************");
       System.out.println(c.sendRequest("bbc.co.uk")+"************");
       System.out.println(c.sendRequest("cambridge.ac.uk")+"************");
       System.out.println(c.sendRequest("cambridge.ac.uk")+"************");
       System.out.println(c.sendRequest("mail.google.com")+"************");
       System.out.println(c.sendRequest("mail.google.com")+"************");
       System.out.println(c.sendRequest("developer.twitter.com")+"************");
       System.out.println(c.sendRequest("developer.twitter.com")+"************");
       System.out.println(c.sendRequest("machine.boun.edu.tr")+"************");
       System.out.println(c.sendRequest("facebook.com")+"************");
       System.out.println(c.sendRequest("f.com")+"************");
  
        System.out.println("All Valids Domains ( "+tree.getAllRecords().size()+" )  :  "+tree.getAllRecords().keySet());
    
        printChilds(tree.getRoot());
        System.out.println("\n"+"Valid Domains : "+set.size());
        for(String s: set) {
        	System.out.println(s);	
        }
        
	}
	
	public static void printChilds(DnsNode root) {
		if(root.validDomain) {
			set.add("That is a valid Domain--> "+root.domainName +"  with IP:"+root.ipAddresses);
		}
		if(root.getChildNodeList().size()!=0) {
		System.out.println(root.getChildNodeList().keySet()+"                          ROOT="+root.domainName);
		Iterator<String> itr=root.getChildNodeList().keySet().iterator();
		while(itr.hasNext()) {
			printChilds(root.getChildNodeList().get(itr.next()));
		}
		}
	}
	


}