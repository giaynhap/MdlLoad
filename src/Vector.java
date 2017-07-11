
public class Vector {
	private float [] data;
	private final double EQUAL_EPSILON = 	0.001;

	public Vector()
	{
		data = new float[3];
	}
	
	public Vector(float [] vec)
	{
		data = vec;
	}
	public void set(int index,float val)
	{
		if (index>=3 || index <0) return;
		data[index] = val;
	}
	public float [] get()
	{
		return data;
	}
	public float  get(int index)
	{
		return data[index];
		 
	}


	public Vector Transform (Matrix matrix)
	{
		Vector in_1 = new Vector(new float[]{matrix.get(0,0),matrix.get(0,1),matrix.get(0,2)});
		Vector in_2 = new Vector(new float[]{matrix.get(1,0),matrix.get(1,1),matrix.get(1,2)});
		Vector in_3 = new Vector(new float[]{matrix.get(2,0),matrix.get(2,1),matrix.get(2,2)});
		
		float []out = new float[3];
		out[0] = dotProduct( in_1)  + 	matrix.get(0,3);
		out[1] = dotProduct( in_2)  +	matrix.get(1,3);
		out[2] =dotProduct(in_3) 	+	matrix.get(2,3);
		return new Vector(out);
	}

	public boolean compare (Vector v)
	{
		int		i;
		float [] v2Data =v.data;
				
		for (i=0 ; i<3 ; i++)
			if (Math.abs(data[i]-v2Data[i]) > EQUAL_EPSILON)
				return false;
				
		return true;
	}
	public Vector MA (float scale, Vector vecb)
	 {
		 Vector vecc = new Vector();
		 vecc.get()[0] = data[0] + scale*vecb.get()[0];
		 vecc.get()[1] = data[1] + scale*vecb.get()[1];
		 vecc.get()[2] = data[2] + scale*vecb.get()[2];
		 return vecc;
	 }
	public Vector crossProduct (Vector vecb )
	 {
		 Vector cross = new Vector();
	 	cross.get()[0] = data[1]*vecb.get()[2] - data[2]*vecb.get()[1];
	 	cross.get()[1] = data[2]*vecb.get()[0] - data[0]*vecb.get()[2];
	 	cross.get()[2] = data[0]*vecb.get()[1] - data[1]*vecb.get()[0];
	 	return cross;
	 }
	public float dotProduct ( Vector vecb )
	{
		return data[0]*vecb.get()[0] + data[1]*vecb.get()[1] + data[2]*vecb.get()[2];
	}
	

	public Vector  _Add (Vector vb )
	{
		Vector out = new Vector();
		out.get()[0] = data[0]+vb.get()[0];
		out.get()[1] = data[1]+vb.get()[1];
		out.get()[2] = data[2]+vb.get()[2];
		return out;
	}
	
	 

	public Vector  _Subtract (Vector vb )
	{
		Vector out = new Vector();
		out.get()[0] = data[0]-vb.get()[0];
		out.get()[1] = data[1]-vb.get()[1];
		out.get()[2] = data[2]-vb.get()[2];
		return out;
	}
	
	public void  add (Vector vb )
	{
	 
		  data[0]+=vb.get()[0];
		  data[1]+=vb.get()[1];
		  data[2]+=vb.get()[2];
		 
	}
	public void  subtract (Vector vb )
	{
		  data[0]-=vb.get()[0];
		  data[1]-=vb.get()[1];
		  data[2]-=vb.get()[2];
	}
	public void  scale (float scale )
	{
		  data[0]*=scale;
		  data[1]*=scale;
		  data[2]*=scale;
	}
	
	public void inverse ()
	{
		data[0] = -data[0];
		data[1] = -data[1];
		data[2] = -data[2];
	}
	
	double normalize ()
	{
		int		i;
		double	length;
	
		if ( Math.abs(data[1] - 0.000215956) < 0.0001)
			i=1;
	
		length = 0;
		for (i=0 ; i< 3 ; i++)
			length += data[i]*data[i];
		length = Math.sqrt (length);
		if (length == 0)
			return 0;
			
		for (i=0 ; i< 3 ; i++)
			data[i] /= length;	
	
		return length;
	}
	public Vector clone()
	{
		Vector clone = new Vector();
		clone.get()[0] = data[0];
		clone.get()[1] = data[1];
		clone.get()[2] = data[2];
		return clone;
	}
	public double length()
	{
		return   Math.sqrt(data[0]*data[0]+data[1]*data[1]+data[2]*data[2]);	
	}

	public Matrix toIMatrix (   )
	{
		Matrix 	matrix = new Matrix();
		float		angle;
		float		sr, sp, sy, cr, cp, cy;
		
		angle = data[2];//(float) (data[2] * (Math.PI*2 / 360));
		sy = (float) Math.sin(angle);
		cy = (float)Math.cos(angle);
		angle = data[1];//(float)(data[1] * (Math.PI*2 / 360));
		sp = (float)Math.sin(angle);
		cp = (float)Math.cos(angle);
		angle = data[0];//(float)(data[0] * (Math.PI*2 / 360));
		sr = (float)Math.sin(angle);
		cr = (float)Math.cos(angle);
	
		// matrix = (Z * Y) * X
		matrix.set(0,0, cp*cy);
		matrix.set(0,1, cp*sy) ;
		matrix.set(0,2, -sp) ;
		matrix.set(1,0, sr*sp*cy+cr*-sy) ;
		matrix.set(1,1, sr*sp*sy+cr*cy) ;
		matrix.set(1,2, sr*cp) ;
		matrix.set(2,0,  (cr*sp*cy+-sr*-sy)) ;
		matrix.set(2,1, (cr*sp*sy+-sr*cy)) ;
		matrix.set(2,2, cr*cp) ;
		matrix.set(0,3, 0) ;
		matrix.set(1,3, 0) ;
		matrix.set(2,3, 0) ;
		return matrix;
	}
	

	public Matrix  toMatrix ( )
	{
		float		angle;
		float		sr, sp, sy, cr, cp, cy;
		Matrix 	matrix = new Matrix();
		

		angle = data[2];//(float) (data[2] * (Math.PI*2 / 360));
		sy = (float) Math.sin(angle);
		cy = (float)Math.cos(angle);
		angle = data[1];//(float)(data[1] * (Math.PI*2 / 360));
		sp = (float)Math.sin(angle);
		cp = (float)Math.cos(angle);
		angle = data[0];//(float)(data[0] * (Math.PI*2 / 360));
		sr = (float)Math.sin(angle);
		cr = (float)Math.cos(angle);
	
 
		matrix.set(0,0, cp*cy);
		matrix.set(1,0, cp*sy) ;
		matrix.set(2,0, -sp) ;
		
		matrix.set(0,1, sr*sp*cy+cr*-sy) ;
		matrix.set(1,1, sr*sp*sy+cr*cy) ;
		matrix.set(2,1, sr*cp) ;
		matrix.set(0,2,  (cr*sp*cy+-sr*-sy)) ;
		matrix.set(1,2, (cr*sp*sy+-sr*cy)) ;
		matrix.set(2,2, cr*cp) ;
		matrix.set(0,3, 0) ;
		matrix.set(1,3, 0) ;
		matrix.set(2,3, 0) ;
		return matrix;
	}
	
	public Vector rotate (Matrix matrix)
	{
		
		float []out = new float[3];
		Vector in_1 = new Vector(new float[]{matrix.get(0,1),matrix.get(0,2),matrix.get(0,3)});
		Vector in_2 = new Vector(new float[]{matrix.get(1,1),matrix.get(1,2),matrix.get(1,3)});
		Vector in_3 = new Vector(new float[]{matrix.get(2,1),matrix.get(2,2),matrix.get(2,3)});
		
		out[0] = dotProduct( in_1);
		out[1] = dotProduct(in_2);
		out[2] = dotProduct(in_3);
		
		return new Vector(out);
	}

	
	public Vector IRotate (Matrix matrix)
	{
		float []out = new float[3];
		out[0] = data[0]*matrix.get(0, 0) + data[1]*matrix.get(1, 0) + data[2]*matrix.get(2, 0);
		out[1] = data[0]*matrix.get(0, 1) + data[1]*matrix.get(1, 1) + data[2]*matrix.get(2, 1);
		out[2] = data[0]*matrix.get(0, 2) + data[1]*matrix.get(1, 2)+ data[2]*matrix.get(2, 2);
		return new Vector(out);
	}


public Quaternion toQuaternion(   )
{
	Quaternion quaternion = new Quaternion();
	float		angle;
	float		sr, sp, sy, cr, cp, cy;

	// FIXME: rescale the inputs to 1/2 angle
	angle = data[2] * 0.5f;
	sy = (float) Math.sin(angle);
	cy = (float) Math.cos(angle);
	angle = data[1] * 0.5f;
	sp = (float) Math.sin(angle);
	cp = (float) Math.cos(angle);
	angle = data[0] * 0.5f;
	sr = (float) Math.sin(angle);
	cr = (float) Math.cos(angle);

	quaternion.get()[0] = sr*cp*cy-cr*sp*sy; // X
	quaternion.get()[1] = cr*sp*cy+sr*cp*sy; // Y
	quaternion.get()[2] = cr*cp*sy-sr*sp*cy; // Z
	quaternion.get()[3] = cr*cp*cy+sr*sp*sy; // W
	return quaternion;
}
	public String toString()
	{
		return data[0]+" "+data[1]+" "+data[2];
	}
}
