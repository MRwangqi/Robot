package com.rebot;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetWorkOK {
 public static Boolean isOK(Context context)
 {  
	 Boolean  flag=false;
    ConnectivityManager cm=  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      if(cm.getActiveNetworkInfo()!=null){
    	   if(cm.getActiveNetworkInfo().isAvailable()){
    		   flag=true;
    	   }
      }
	 return flag;
   }
}
