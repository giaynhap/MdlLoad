
public class Quaternion {
private float [] data;
	public Quaternion()
	{
		data = new float[4];
		
	}
	public Quaternion(float [] data)
	{
		this.data = data;
		
	}
	public float [] get()
	{
		return data;
	}
	public  Matrix toMatrix(  )
	{
		Matrix matrix = new Matrix();;
		matrix.set(0,0, 1.0f - 2.0f * data[1] * data[1] - 2.0f * data[2] * data[2]);
		matrix.set(1,0, 2.0f * data[0] * data[1] + 2.0f * data[3] * data[2]);
		matrix.set(2,0, 2.0f * data[0] * data[2] - 2.0f * data[3] * data[1]);
	
		matrix.set(0,1, 2.0f * data[0] * data[1] - 2.0f * data[3] * data[2]);
		matrix.set(1,1, 1.0f - 2.0f * data[0] * data[0] - 2.0f * data[2] * data[2]);
		matrix.set(2,1, 2.0f * data[1] * data[2] + 2.0f * data[3] * data[0]);
	
		matrix.set(0,2, 2.0f * data[0] * data[2] + 2.0f * data[3] * data[1]);
		matrix.set(1,2, 2.0f * data[1] * data[2] - 2.0f * data[3] * data[0]);
		matrix.set(2,2, 1.0f - 2.0f * data[0] * data[0] - 2.0f * data[1] * data[1]);
		return matrix;
	}

	public Quaternion slerp(   Quaternion q, float t  )
	{
		int i;
		float omega, cosom, sinom, sclp, sclq;
		Quaternion p = this;
		Quaternion qt = new Quaternion();
		// decide if one of the quaternions is backwards
		float a = 0;
		float b = 0;
		for (i = 0; i < 4; i++) {
			a += (p.data[i]-q.data[i])*(p.data[i]-q.data[i]);
			b += (p.data[i]+q.data[i])*(p.data[i]+q.data[i]);
		}
		if (a > b) {
			for (i = 0; i < 4; i++) {
				q.data[i] = -q.data[i];
			}
		}
	
		cosom = p.data[0]*q.data[0] + p.data[1]*q.data[1] + p.data[2]*q.data[2] + p.data[3]*q.data[3];
	
		if ((1.0 + cosom) > 0.00000001) {
			if ((1.0 - cosom) > 0.00000001) {
				omega = (float) Math.acos( cosom );
				sinom = (float) Math.sin( omega );
				sclp = (float) (Math.sin( (1.0 - t)*omega) / sinom);
				sclq = (float) (Math.sin( t*omega ) / sinom);
			}
			else {
				sclp = 1.0f - t;
				sclq = t;
			}
			for (i = 0; i < 4; i++) {
				qt.data[i] = sclp * p.data[i] + sclq * q.data[i];
			}
		}
		else {
			qt.data[0] = -p.data[1];
			qt.data[1] = p.data[0];
			qt.data[2] = -p.data[3];
			qt.data[3] = p.data[2];
			sclp = (float) Math.sin( (1.0 - t) * 0.5 * Math.PI);
			sclq = (float) Math.sin( t * 0.5f * Math.PI);
			for (i = 0; i < 3; i++) {
				qt.data[i] = sclp * p.data[i] + sclq * qt.data[i];
			}
		}
		return qt;
	}


}
