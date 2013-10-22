package sonet.findmytwins.utils;

import java.util.HashMap;

public class Profiler {

	private static Profiler instance;
	private static long startTime;
	private static HashMap<Integer,Long> times;
	private static int num;
	static{
		instance=new Profiler();  
	}
	
	private Profiler() {
		times=new HashMap<Integer, Long>();
	}

	public synchronized static int start(){
		startTime=System.currentTimeMillis();
		times.put(++num, startTime);
		return num;
	}

	public synchronized static int start(String msg){
		startTime=System.currentTimeMillis();
		times.put(++num, startTime);
		System.out.println("Profiler has been started. "+msg);
		return num;
	}

	public synchronized static void fin(int key){
		long finishTime = System.currentTimeMillis();
		if(key==0){
			fin();
			return;
		}
		long startTime=times.get(key);
		System.out.println("Job time is "+(finishTime-startTime));
		times.remove(key);
	}
	
	public synchronized static void fin(){
		long finishTime = System.currentTimeMillis();
		long startTime=times.get(num);
		System.out.println("Job time is "+(finishTime-startTime));
		times.remove(num);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
