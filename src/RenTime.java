
	public class RenTime
	{
		private static long  time =0;
		public static void init()
		{
			time = System.currentTimeMillis();
		}
		
		public static float  delta()
		{
			long current = System.currentTimeMillis();
			float delta =(current-time)/1000.0f;
			time= System.currentTimeMillis();
			return delta;
		}
	}
	