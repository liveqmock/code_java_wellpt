package com.wellsoft.pt.ldx.web.mps;

public class common {

	
	
	  private int maxnum=1000000;
	public common(){
		
		
		
	}
	
	public  void  Filter(String [][] re ,int x,int y){
		
		System.out.println(x);
		System.out.println(y);
		
	   
		
	
		
		
		for(int i=0;i<x;i++)
			for(int j=0;j<y;j++)
			{
				
				try{
					
					
				 	if(re[i][j].equals("null")){
				 			     // System.out.println(re[i][j]);
				 	}
				 			
					
				}
				catch(NullPointerException w){
					
					re[i][j]=" ";
					
				}
			}
		
		
		
		
		
	}
	
	
	
}
