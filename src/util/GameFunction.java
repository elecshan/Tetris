package util;

public class GameFunction {
	
	public static long getSleepTimeByLevel(int level) {
		long sleep = -40 * level + 740;
		sleep = sleep < 100 ? 100 : sleep;
		return sleep;
	}
}
