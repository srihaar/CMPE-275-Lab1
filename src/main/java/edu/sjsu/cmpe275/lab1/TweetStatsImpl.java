package edu.sjsu.cmpe275.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class TweetStatsImpl implements TweetStats {

    
	public static HashMap<String,ArrayList<String>> followers= new HashMap<String,ArrayList<String>>();
	//stores the followers details, where key is user and value is list of followers
	
	
	public static TreeMap<String,Integer> mostFollowed = new TreeMap<String,Integer>();//stores the username and followers count of most followed user
	//if there are multiple most followed users, we are storing
	
	
	public static TreeMap<String,Integer> longestTweet = new TreeMap<String,Integer>();/*stores the username and length of tweet who has longest tweet 
	attempted*/
	//if there are mutiple users with longest tweets, we are storing
	
	
	public static TreeMap<String,Integer> mostProductive  = new TreeMap<String,Integer>();
//stores the username and total lengths of successful tweets of all users
    
	
	public void resetStats() {
        // TODO Auto-generated method stub
    	longestTweet.clear(); //clear all the contents if reset is called
        followers.clear();
        mostFollowed.clear();
    	mostProductive.clear();

    }

    
	public int getLengthOfLongestTweetAttempted() {
        // TODO Auto-generated method stub
    	if(longestTweet.isEmpty()){
    		System.out.println("No tweets");
    		return 0;
    	}
    	else{
    
    			System.out.println("User with longest tweet attempted is"+" "+longestTweet.firstKey());
    	return longestTweet.get(longestTweet.firstKey());  // This will return length of longest tweet attempted 
    	//.firstKey() is used because if there are multiple users with longest tweet, returns the user which comes alphabetically first
    	}
        
        
    }

    public String getMostFollowedUser() {
        // TODO Auto-generated method stub
    	 if(mostFollowed.isEmpty()){
    		 return "nothing to show for now";
    	 }
    	 else{
            return mostFollowed.firstKey();  // This will return most follwed user 
  	//.firstKey() is used because if there are multiple users with same highest number of followers, returns the user which comes alphabetically first
    	}
    }

    public String getMostProductiveUser() {
        // TODO Auto-generated method stub
    	//returns the most productive user
    	//mostProductive TreeMap stores all the users and their productivity
    	//Below we are getting the username of highest productivity, if there are multilple, returns the user who comes forst alphabetically
    	//TreeMap gives flexibility sonce it sorts the data according to key
    	String s = null;
    	
    	if(mostProductive.isEmpty()){
    		return "nothing to show for now";
    	}
    	else{
    		
    	Set set = mostProductive.entrySet();
        Iterator i = set.iterator();
        int l=0;
        
        while(i.hasNext()) {
           Map.Entry me = (Map.Entry)i.next();
           
           if((Integer)me.getValue()>l){
        	   l=(Integer)me.getValue();
        	    s = (String)me.getKey();
           }
        }
    	
       
    	}
    
    return s;
    }
    //...

}



