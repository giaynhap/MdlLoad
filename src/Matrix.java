
public class Matrix {
	float [] data;
	public Matrix()
	{
		data = new float[16];
	}
	public Matrix(float [] matrix)
	{
		data = matrix;
	}
	public float get(int x, int y)
	{
		return data[x*4+y];
	}
	public void set(int x, int y,float val)
	{
		  data[x*4+y] = val;
	}
	public float[] get()
	{
		return data;
	}
	public Matrix Transforms (Matrix in2 )
	{
		Matrix out = new Matrix();
		out.set(0,0, this.get(0,0) * in2.get(0,0) + this.get(0,1) *  in2.get(1,0) +
				this.get(0,2)*  in2.get(2,0)) ;
		
		out.set(0,1, this.get(0,0) * in2.get(0,1) + this.get(0,1) *  in2.get(1,1) +
				this.get(0,2)*  in2.get(2,1)) ;
		
		out.set(0,2, this.get(0,0) * in2.get(0,2) + this.get(0,1) *  in2.get(1,2) +
				this.get(0,2)*  in2.get(2,2)  )  ;
	 
		out.set(0,3, this.get(0,0) * in2.get(0,3) + this.get(0,1) *  in2.get(1,3) +
				this.get(0,2)*  in2.get(2,3)+ this.get(0,3)) ;
		
		
		out.set(1,0, this.get(1,0) * in2.get(0,0) + this.get(1,1) *  in2.get(1,0) +
				this.get(1,2)*  in2.get(2,0)) ;
		
		out.set(1,1, this.get(1,0) * in2.get(0,1) + this.get(1,1) *  in2.get(1,1) +
				this.get(1,2)*  in2.get(2,1)) ;
		
		out.set(1,2, this.get(1,0) * in2.get(0,2) + this.get(1,1) *  in2.get(1,2) +
				this.get(1,2)*  in2.get(2,2)) ;
	 
		out.set(1,3, this.get(1,0) * in2.get(0,3) + this.get(1,1) *  in2.get(1,3) +
				this.get( 1,2)*  in2.get(2,3) + this.get(1,3)) ;
		 
		out.set(2,0, this.get(2,0) * in2.get(0,0) + this.get(2,1) *  in2.get(1,0) +
				this.get( 2,2)*  in2.get(2,0)) ;
		
		out.set(2,1, this.get(2,0) * in2.get(0,1) + this.get(2,1) *  in2.get(1,1) +
				this.get(2,2)*  in2.get(2,1)) ;
		
		out.set(2,2, this.get(2,0) * in2.get(0,2) + this.get(2,1) *  in2.get(1,2) +
				this.get(2,2)*  in2.get(2,2)) ;
	 
		out.set(2,3, this.get(2,0) * in2.get(0,3) + this.get(2,1) *  in2.get(1,3) +
				this.get(2,2)*  in2.get(2,3) + this.get(2,3) ) ;
		
		out.set(3, 0, 0.0f);
		out.set(3, 1, 0.0f);
		out.set(3, 2, 0.0f);
		out.set(3, 3, 1.0f);
	 return out;
		 
	}

	public Matrix Inverse()
	  {
	      Matrix invOut =new Matrix();
	      Matrix inv =new Matrix();
	      double det;
	      int i;
	   
	      inv.set(0,0 , this.get(1,1) *  this.get(2,2) *  this.get(3,3) - this.get(1,1) *  this.get(2,3) *  this.get(3,2) -  this.get(2,1) *  this.get(1,2) *  this.get(3,3) 
	    		  		+  this.get(2,1)  * this.get(1,3) * this.get(3,2) + this.get(3,1) *  this.get(1,2) *  this.get(2,3) - this.get(3,1) * this.get(1,3) *  this.get(2,2));
	      inv.set( 1,0, -this.get(1,0) *this.get(2,2) *this.get(3,3) + this.get(1,0) * this.get(2,3) *this.get(3,2) + this.get(2,0) * this.get(1,2) *this.get(3,3) - this.get(2,0)
	    		  	*this.get(1,3) * this.get(3,2) - this.get(3,0) * this.get(1,2) * this.get(2,3) + this.get(3,0) * this.get(1,3) * this.get(2,2));
	      inv.set(2,0,this.get(1,0) *this.get(2,1) *this.get(3,3) -this.get(1,0) *this.get(2,3) *this.get(3,1) -this.get(2,0) *this.get(1,1) *this.get(3,3) +this.get(2,0) *this.get(1,3) *this.get(3,1) +this.get(3,0) *this.get(1,1) *this.get(2,3) -this.get(3,0) *this.get(1,3) *this.get(2,1));
	      inv.set(3,0, - this.get(1,0) *this.get(2,1) *this.get(3,2) +this.get(1,0) *this.get(2,2) *this.get(3,1) +this.get(2,0) *this.get(1,1) *this.get(3,2) -this.get(2,0) *this.get(1,2) *this.get(3,1) -this.get(3,0) *this.get(1,1) *this.get(2,2) +this.get(3,0) *this.get(1,2) *this.get(2,1));
	      inv.set(0,1, - this.get(0,1) *this.get(2,2) *this.get(3,3) +this.get(0,1) *this.get(2,3) *this.get(3,2) +this.get(2,1) *this.get(0,2) *this.get(3,3) -this.get(2,1) *this.get(0,3) *this.get(3,2) -this.get(3,1) *this.get(0,2) *this.get(2,3) +this.get(3,1) *this.get(0,3) *this.get(2,2));
	      inv.set(1,1, this.get(0,0) *this.get(2,2) *this.get(3,3) -this.get(0,0) *this.get(2,3) *this.get(3,2) -this.get(2,0) *this.get(0,2) *this.get(3,3) +this.get(2,0) *this.get(0,3) *this.get(3,2) +this.get(3,0) *this.get(0,2) *this.get(2,3) -this.get(3,0) *this.get(0,3) *this.get(2,2));
	      inv.set(2,1, - this.get(0,0) *this.get(2,1) *this.get(3,3) +this.get(0,0) *this.get(2,3) *this.get(3,1) +this.get(2,0) *this.get(0,1) *this.get(3,3) -this.get(2,0) *this.get(0,3) *this.get(3,1) -this.get(3,0) *this.get(0,1) *this.get(2,3) +this.get(3,0) *this.get(0,3) *this.get(2,1));
	      inv.set(3,1, this.get(0,0) *this.get(2,1) *this.get(3,2) -this.get(0,0) *this.get(2,2) *this.get(3,1) -this.get(2,0) *this.get(0,1) *this.get(3,2) +this.get(2,0) *this.get(0,2) *this.get(3,1) +this.get(3,0) *this.get(0,1) *this.get(2,2) -this.get(3,0) *this.get(0,2) *this.get(2,1));
	      inv.set(0,2, this.get(0,1) *this.get(1,2) *this.get(3,3) -this.get(0,1) *this.get(1,3) *this.get(3,2) -this.get(1,1) *this.get(0,2) *this.get(3,3) +this.get(1,1) *this.get(0,3) *this.get(3,2) +this.get(3,1) *this.get(0,2) *this.get(1,3) -this.get(3,1) *this.get(0,3) *this.get(1,2));
	      inv.set(1,2, - this.get(0,0) *this.get(1,2) *this.get(3,3) +this.get(0,0) *this.get(1,3) *this.get(3,2) +this.get(1,0) *this.get(0,2) *this.get(3,3) -this.get(1,0) *this.get(0,3) *this.get(3,2) -this.get(3,0) *this.get(0,2) *this.get(1,3) +this.get(3,0) *this.get(0,3) *this.get(1,2));
	      inv.set(2,2, this.get(0,0) *this.get(1,1) *this.get(3,3) -this.get(0,0) *this.get(1,3) *this.get(3,1) -this.get(1,0) *this.get(0,1) *this.get(3,3) +this.get(1,0) *this.get(0,3) *this.get(3,1) +this.get(3,0) *this.get(0,1) *this.get(1,3) -this.get(3,0) *this.get(0,3) *this.get(1,1));
	      inv.set(2,3, - this.get(0,0) *this.get(1,1) *this.get(3,2) +this.get(0,0) *this.get(1,2) *this.get(3,1) +this.get(1,0) *this.get(0,1) *this.get(3,2) -this.get(1,0) *this.get(0,2) *this.get(3,1) -this.get(3,0) *this.get(0,1) *this.get(1,2) +this.get(3,0) *this.get(0,2) *this.get(1,1));
	      inv.set(0,3, - this.get(0,1) *this.get(1,2) *this.get(2,3) +this.get(0,1) *this.get(1,3) *this.get(2,2) +this.get(1,1) *this.get(0,2) *this.get(2,3) -this.get(1,1) *this.get(0,3) *this.get(2,2) -this.get(2,1) *this.get(0,2) *this.get(1,3) +this.get(2,1) *this.get(0,3) *this.get(1,2));
	      inv.set(1,3, this.get(0,0) *this.get(1,2) *this.get(2,3) -this.get(0,0) *this.get(1,3) *this.get(2,2) -this.get(1,0) *this.get(0,2) *this.get(2,3) +this.get(1,0) *this.get(0,3) *this.get(2,2) +this.get(2,0) *this.get(0,2) *this.get(1,3) -this.get(2,0) *this.get(0,3) *this.get(1,2));
	      inv.set(2,3, - this.get(0,0) *this.get(1,1) *this.get(2,3) +this.get(0,0) *this.get(1,3) *this.get(2,1) +this.get(1,0) *this.get(0,1) *this.get(2,3) -this.get(1,0) *this.get(0,3) *this.get(2,1) -this.get(2,0) *this.get(0,1) *this.get(1,3) +this.get(2,0) *this.get(0,3) *this.get(1,1));
	      inv.set(3,3, this.get(0,0) *this.get(1,1) *this.get(2,2) -this.get(0,0) *this.get(1,2) *this.get(2,1) -this.get(1,0) *this.get(0,1) *this.get(2,2) +this.get(1,0) *this.get(0,2) *this.get(2,1) +this.get(2,0) *this.get(0,1) *this.get(1,2) -this.get(2,0) *this.get(0,2) *this.get(1,1));
	   
	      det =this.get(0,0) * inv.get(0,0) +this.get(0,1) * inv.get(1,0) +this.get(0,2) * inv.get(2,0) +this.get(0,3) * inv.get(3,0);
	   
	      if(det == 0)
	          return invOut;
	   
	      det = 1.0f / det;
	   
	      for(i = 0; i < 4; i++)
	  		for(int j = 0; j < 4;j++)
	          invOut.set( i,j,(float)(inv.get(i,j) * det));

	      return invOut;
	  }
	public String toString()
	{
	 
		return String.format("%f\t%f\t%f\t%f\n%f\t%f\t%f\t%f\n%f\t%f\t%f\t%f\n%f\t%f\t%f\t%f", this.get(0,0), this.get(0,1), this.get(0,2), this.get(0,3), this.get(1,0), this.get(1,1), this.get(1,2), this.get(1,3), this.get(2,0), this.get(2,1), this.get(2,2), this.get(2,3), this.get(3,0), this.get(3,1), this.get(3,2), this.get(3,3));
	}
}
