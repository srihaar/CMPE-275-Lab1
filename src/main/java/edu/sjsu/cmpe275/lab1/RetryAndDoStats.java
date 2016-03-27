package edu.sjsu.cmpe275.lab1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.IOException;
import java.util.ArrayList;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RetryAndDoStats implements MethodInterceptor {
    /***
     * Following is the dummy implementation of advice.
     * Students are expected to complete the required implementation as part of lab completion.
     */
  
  public Object invoke(MethodInvocation invocation) throws Throwable {
       Object result = null;
       int count = 1;
    while(true){

        try{

          //System.out.println("Method " + invocation.getMethod() + " is called");
        
              result =  invocation.proceed();
                if(invocation.getMethod().getName()=="tweet"){ // If the called function is tweet, control comes here
                    Object[] arg= invocation.getArguments();
                    String username = (String)arg[0];//storing the arguments of tweet function
                    String message = (String)arg[1];
                    if(username==null||message==null){ //if username or tweet is null, we are returning
                        System.out.println("Username or tweet can not be null");
                        break;
                    }
                    if(message.length()>140){ 
                        throw new IllegalArgumentException();
                    }
                    storeLongestTweet(username,message);//updating the longestTweet treemap
                    storeProductiveUser(username,message);//adding data to mostProductive Treemap 
                    break;
            
                }
        
                if(invocation.getMethod().getName()=="follow"){ //If called function is follow, control comes here
                    Object[] arg= invocation.getArguments();
                    String follower = (String)arg[0];
                    String followee = (String)arg[1];
                    if(followee==null||follower==null){//If any of the follower or followee is null,we are returning
                       System.out.println("Follower or Followee can not be null");
                       break;
                    }
        
                    if(follower!=followee){ //follow should not be equal to followee
                         if(TweetStatsImpl.followers.containsKey(followee)){//If followee is already in followers treemap
          
                             ArrayList<String> l = TweetStatsImpl.followers.get(followee);//getting the list of followers of that particular followee
                             if(l.contains(follower)){//Checking if that followers list contain that followee, we are returning
                             System.out.println(follower+" "+"is already following"+" "+followee);
                             break;
                             }
           
                          else{//If the followee is not present in the followers list, we will add now
                              l.add(follower);
                              TweetStatsImpl.followers.put(followee, l);//adding the followee to the list
                              updateMostFollowed(followee,follower);//update the mostFollowers Treemap
                              break;
                          }
                        }
                        else{//followee is not present in followers treemap, it will add
                          ArrayList<String> l = new ArrayList<String>();
                          l.add(follower);
                          TweetStatsImpl.followers.put(followee, l);
                          updateMostFollowed(followee,follower);//update mostFollowed Treemap
                          break;
                        }
                    }
                    else{
                       System.out.println("Follower and followee can not be same");
                       break;
                    }
                }
       }
        
       catch(IllegalArgumentException e){
          
          Object[] arg= invocation.getArguments();
          String username = (String)arg[0];
          String message = (String)arg[1];
            
          storeLongestTweet(username,message);
          System.out.println("Tweet length can not be more than 140 characters");
          return 0;
        }
       catch(IOException e)
       {
          
          
         System.out.println("Network failure, retrying for"+" "+count+" "+"time");
         count = count+1;
         if(count>3){
            if(invocation.getMethod().getName()=="tweet"){ // If the called function is tweet, control comes here
                  Object[] arg= invocation.getArguments();
                    String username = (String)arg[0];//string the arguments of tweet function
                    String message = (String)arg[1];
                    storeLongestTweet(username,message);
            }
            System.out.println("Network failure, retried for 3 times and exiting now");
            return 0;
          }
          
          
        }
       
    }
        
    return result;
        
        
        
        
        
    }

   
    
    
    public String storeLongestTweet(String username, String message){

     if(TweetStatsImpl.longestTweet.isEmpty()){
       TweetStatsImpl.longestTweet.put(username, message.length());//if empty we are directly storing
     }
     
     
    
      else{
           
           int presentLength  = TweetStatsImpl.longestTweet.get(TweetStatsImpl.longestTweet.firstKey());
           
           if(message.length()>presentLength){ //Current message length is greater than previous one, we clearing and adding latest
             TweetStatsImpl.longestTweet.clear();
             TweetStatsImpl.longestTweet.put(username, message.length());
              }
           
           if(message.length()==presentLength){ //current message is same as previous, we are adding because different users
             
             TweetStatsImpl.longestTweet.put(username, message.length());
             }
       }
      

     return "";

   }
   
   
    
    
    public String storeProductiveUser(String username,String message){
     
     if(TweetStatsImpl.mostProductive.containsKey(username)){
       int msgLength = TweetStatsImpl.mostProductive.get(username);
       msgLength = msgLength+message.length();
       TweetStatsImpl.mostProductive.put(username, msgLength);
       
     }
     else{
       TweetStatsImpl.mostProductive.put(username, message.length());
     }
     return "";
     
   }
   
   
    
    
    
    public String updateMostFollowed(String username,String follower){
     
    if(TweetStatsImpl.mostFollowed.isEmpty()){
      TweetStatsImpl.mostFollowed.put(username, 1);
    }
    else{
      ArrayList<String> l = TweetStatsImpl.followers.get(username);//getting the followers of that user
      int presentHigh = TweetStatsImpl.mostFollowed.get(TweetStatsImpl.mostFollowed.firstKey());//getting present value of high followers
      if(l.size()>presentHigh){//if present is higher, we are adding
        TweetStatsImpl.mostFollowed.clear();
        TweetStatsImpl.mostFollowed.put(username, l.size());
      }
      if(l.size()==presentHigh){/*if equal, we are adding because mostFollowed treemap contains all users who have high followers,
                                we will return the user when needed, user who comes first alphabetically*/
                                            
        TweetStatsImpl.mostFollowed.put(username, l.size());
      }
      
    }
     return "";
     
   }
}