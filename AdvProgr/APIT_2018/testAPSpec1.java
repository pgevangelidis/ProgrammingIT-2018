package APIT_2018;

public class testAPSpec1{
	public static void main(String[] args)
	{
		/*========================
		 * This test is for the random thread generator with constant number
		 * ========================*/
		int numberVehicles = 50;
		int counter=2*numberVehicles;
		int northCounter=0, westCounter=0;
		int flag = 0;

		for(int i=0; i<counter; i++)
		{
			if(northCounter==numberVehicles)
			{
				//System.out.println("West");
				westCounter++;
			}else if(westCounter==numberVehicles)
			{
				//System.out.println("North");
				northCounter++;
			}else
			{
				flag= (int) (Math.random()*2+1);
				if(flag==1)
				{
					//System.out.println("North");
					northCounter++;
				}
				else if(flag==2)
				{
					//System.out.println("West");
					westCounter++;
				}
			}


		}
		System.out.println("ncounter: " + northCounter + " wcounter: "+ westCounter);

		/*==============
		 * This test is for the speed random generator
		 * ================*/
		int randomSpeed = 0;
		int generate = 0;
		int temp = 0;
		int min = 1000, max=0;
		for(int i=0; i<180; i++)
		{
			randomSpeed = (int) (Math.random()*400+50);
			
			temp = (int) (4*(Math.round(Math.cos(i*Math.PI/180)*100)));
			generate = Math.abs(temp)+50; // regulates the traffic.
			
			/*if (randomSpeed>max)
				max = randomSpeed;
			else if(randomSpeed < min)
				min = randomSpeed;*/
			
			System.out.println("random speed: "+randomSpeed+" cosX speed: "+generate);
		}
		//System.out.println("Min speed: "+min+" Max speed: "+max);

		/*int r = 10;
		max=0;
		min=100;
		for(int j=0; j<50; j++)
		{
			int rowLane = (int) (Math.round(Math.random()*(r-1)));
			if (rowLane < min)
				min = rowLane;
			else if (rowLane > max)
				max = rowLane;
			System.out.println("the row lane is: "+rowLane);
		}
		System.out.println("Min row lane: "+min+" Max row lane: "+max);*/

		/*for(int j=0; j<50; j++)
		{
			int direction = (int) Math.round(Math.random());
			System.out.println(direction);
		}*/
		
	/*	boolean alarm = true;
		long t0 = System.currentTimeMillis();
	    while (alarm) {
	      long t1 = System.currentTimeMillis();
	      int seconds = (int) (t1-t0)/1;
	      int seconds2 = (int) (t1-t0)/100;
	      //System.out.print("Elapsed: " + seconds + " Elapsed original: "+ seconds2 + "\r");
	      try {
	        Thread.sleep(100);
	      } catch (InterruptedException e) {
	        // nothing to say
	      }
	      alarm = false;
	    }*/
		
		/*for(int i=0; i<=540; i++)
		{
			double rad = (i*Math.PI)/180.0;
			System.out.println("degree: "+ i + " sin(x): " + Math.round(Math.sin(rad)*100)/100.0 + " cos(x): "+ Math.round(Math.cos(rad)*100)/100.0);
		}*/
	}
}
