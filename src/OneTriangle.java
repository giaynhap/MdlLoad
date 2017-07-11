import java.awt.Font;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

 

public class OneTriangle {
	 static GLU glu ;
	 static Mdlheader header;
	 static Matrix[] boneTransforms;
	 static float m_frame =0;
	 static int m_anim =0;
	 
	 static TextRenderer  renderer = new TextRenderer(new Font("SansSerif",  Font.BOLD, 36));
	 static RenTime time = new RenTime();
	 public static float nextFrame()
	 {
		 float delta =  time.delta();
		
		 m_frame+= delta*header.m_Seq[m_anim].fps;
		
			
		 if (m_frame>=header.m_Seq[m_anim].numframes)
		 {
			 // test 
			 m_anim++;
			 if (m_anim>=header.numseq) m_anim=0;
			 m_frame=0;
		 }
		 return delta;
	 }
	 public static Matrix getMatrix(float delta,int bone)
	 {
		Mdlheader.seqdesc seqdesc  = header.m_Seq[m_anim];
		ArrayList<ArrayList<float[]>> pos = seqdesc.pos;
		int f_current = (int)(m_frame);
		
		 
		
		int nexFrame  = f_current+1;
		if (nexFrame>=header.m_Seq[m_anim].numframes) nexFrame =0;
			
		
		float[] pos_bone = pos.get(f_current).get(bone);
		float[] pos_bone2 = pos.get(nexFrame).get(bone);
		
		
		Vector  angleVec = new Vector(new float[]{pos_bone[3],pos_bone[4],pos_bone[5]});
		Vector  angleVec2 = new Vector(new float[]{pos_bone2[3],pos_bone2[4],pos_bone2[5]});
		Quaternion quat = angleVec.toQuaternion();
		quat.slerp(angleVec2.toQuaternion(),delta);
		
		
		Matrix angleMatrix  = quat.toMatrix();
		
		
		
		angleMatrix.set(0,3,pos_bone[0] * (1.0f-delta) + delta*pos_bone2[0] );
		angleMatrix.set(1,3,pos_bone[1] * (1.0f-delta) + delta*pos_bone2[1] );
		angleMatrix.set(2,3,pos_bone[2] * (1.0f-delta) + delta*pos_bone2[2] );

		return angleMatrix;
	 }
	 
	 
	 public static void transform ()
	 {
		
		 	float delta = nextFrame();
			for (int i=0;i<header.numbones;i++)
	    	{
			//	 Vector angleVec = new Vector(new float[]{header.m_Bone[i].value[3],header.m_Bone[i].value[4],header.m_Bone[i].value[5]});

				Matrix angleMatrix  = getMatrix(delta,i);
			 	//System.out.format("%f %f %f \n", pos_bone[0],pos_bone[1],pos_bone[2]);
				if (header.m_Bone[i].parent<=0)
				{
					 
					boneTransforms[i] = angleMatrix;
				}
				else
				{
				 
					Matrix bonetrans =    boneTransforms[header.m_Bone[i].parent].Transforms(angleMatrix);
				 
					boneTransforms[i] = bonetrans;
				}
				
	    	}
	 }
	 static void doconvert()
	 {
		 header = Mdlload.load();
	    	
		
    	 boneTransforms = new Matrix[header.numbones];
    	 transform ();
    	 
	 }
    protected static void setup( GL2 gl2, int width, int height ) {
    	
    	header.loadTextures(  gl2);
    	time.init();
       
    }
    static float[] eyse_postion = new float[]{40,10,3};
    static float eyse_lookat[] = {0,0,0};
    static float gRotatef[] = {0,0,0};
    static  float OldgAngle[] = {0,0,0};
    static int g_ambientlight = 32;
    static int g_shadelight = 192;
    static float g_lightcolor[]= {1,1,1};
    static float g_lightvec[] = {0,0,-1};
    protected static float Lighting (  int bone, int flags, Vector normal)
    {
    	float lv;
    	float 	illum;
    	float g_lambert = 0.4f;
    	float	lightcos;
    	Vector g_blight = new Vector(g_lightvec).IRotate(   boneTransforms[bone] );
    	illum = g_ambientlight;

    	if (flags == Mdlheader.STUDIO_NF_FLATSHADE)
    	{
    		illum += g_shadelight * 0.8;
    	} 
    	else 
    	{
    		float r;
    		lightcos = normal.dotProduct ( g_blight); 

    		if (lightcos > 1.0)
    			lightcos = 1;

    		illum += g_shadelight;

    		r = g_lambert;
    		if (r <= 1.0) r = 1.0f;

    		lightcos = (float) ((lightcos + (r - 1.0)) / r); 		// do modified hemispherical lighting
    		if (lightcos > 0.0) 
    		{
    			illum -= g_shadelight * lightcos; 
    		}
    		if (illum <= 0)
    			illum = 0;
    	}

    	if (illum > 255) 
    		illum = 255;
    	return illum / 255.0f;	// Light from 0 to 1.0
    }
 
    protected static void drawMesh( GL2 gl ) {
    	transform ();
    	
    	gl.glTranslatef(-header.eyeposition[0], -header.eyeposition[1], -header.eyeposition[2]); 
    	for (int k=0;k<header.numbodyparts;k++)
    	{
    	
    		for (int m=0; m<1;m++)
    		{
    			Mdlheader.model model = header.m_Bodypart[k].model[m];
    			
    			/*
    			for (int i=0;i<model.numverts;i++)
    			{
    				
    				gl.glPointSize(2.0f);
    				int bone = model.m_Vertbone[i];
    				Vector vert_3 = new Vector(new float[]{model.m_Verts[i*3],model.m_Verts[i*3+1],model.m_Verts[i*3+2]});
				 
					Vector mvert3 = vert_3.Transform(boneTransforms[bone]) ;
					
					
    				gl.glBegin(gl.GL_POINTS);
       		     
		        	gl.glColor3f(1.0f, 0.0f, 0.0f);
		        	gl.glVertex3f(mvert3.get(0), mvert3.get(1),mvert3.get(2));
		        	gl.glEnd();
		        
		        	  
    			} */
    			
    			for (int n=0;n<model.nummesh;n++)
    			{
    				
    				Mdlheader.mesh mesh = model.m_Mesh[n];
    				 
    				
    				int trix =0;
    				do
    				{
    					int i= mesh.tris[trix];
    					trix++;
    					
						gl.glEnable(GL2.GL_TEXTURE_2D);
						gl.glBindTexture( GL2.GL_TEXTURE_2D,header.m_Textures[mesh.skinref].index ); //g_texnum );	
						if (Mdlheader.STUDIO_NF_ADDITIVE == header.m_Textures[mesh.skinref].flags  || header.m_Textures[mesh.skinref].flags ==Mdlheader.STUDIO_NF_ADDCHROME )
						{
						    gl. glEnable (gl.GL_BLEND);
						    gl.glBlendFunc (gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);
						  //  gl.glDepthMask(false);
						    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
						    
						  
						}
						else if (Mdlheader.STUDIO_NF_ALPHATEST == header.m_Textures[mesh.skinref].flags )
						{
							gl.glEnable(GL2.GL_ALPHA_TEST);
							gl.glAlphaFunc(GL2.GL_GREATER, 0.5f);
						}
						
    					if (i < 0)
    					{
    						gl.glBegin(GL2.GL_TRIANGLE_FAN);
    						i = -i;
    					}
    					else
    					{
    						gl.glBegin(GL2.GL_TRIANGLE_STRIP);
    					}
    					if (i!=0);
    				
    					for( ; i > 0; i--, trix += 4)
    					{
    						
    						float s = 1.0f/(float)header.m_Textures[mesh.skinref].width;
    						float t = 1.0f/(float)header.m_Textures[mesh.skinref].height;
    						
    								if (trix+5>=mesh.tris.length) break;
    							 	int tri = mesh.tris[trix];
    							 	int tri_nor = mesh.tris[trix+1];
    							 	Vector vert_1 = null;
    							 	Vector normal_1 = null ;
    							 	try
    							 	{
    								  vert_1 = new Vector(new float[]{model.m_Verts[tri *3],model.m_Verts[tri*3+1],model.m_Verts[tri*3+2]});
    								  normal_1 = new Vector(new float[]{model.m_Norms[tri_nor *3],model.m_Norms[tri_nor*3+1],model.m_Norms[tri_nor*3+2]});
    							 	} catch( ArrayIndexOutOfBoundsException e)
    							 	{
    							 		continue;
    							 	}
    							
    								
    								int bone_1 = model.m_Vertbone[tri];
    								int bone_2 = model.m_Normbone[tri_nor];
    								Vector mvert1 =  vert_1.Transform(boneTransforms[bone_1]) ;
    								Vector mnormal =  normal_1.IRotate(boneTransforms[bone_2])  ;
    								float alpha =1.0f;
    								float lv = Lighting( bone_1,  header.m_Textures[mesh.skinref].flags, normal_1);
    								gl.glTexCoord2f(mesh.tris[trix+2]*s, mesh.tris[trix+3]*t);
    								lv =1;
    								
    								gl.glColor4f(lv*g_lightcolor[0], lv*g_lightcolor[1],lv*g_lightcolor[2],alpha);
    								//gl.glNormal3f(mnormal.get(0), mnormal.get(1),mnormal.get(2));
    								gl.glVertex3f(mvert1.get(0), mvert1.get(1),mvert1.get(2));
    								
    							
    					}
    					gl.glEnd();
    				  
    				}while(trix+5<mesh.tris.length);
    				if (Mdlheader.STUDIO_NF_ADDITIVE == header.m_Textures[mesh.skinref].flags )
					{
					    gl.glDisable (gl.GL_BLEND);
		 
					}
					else if (Mdlheader.STUDIO_NF_ALPHATEST == header.m_Textures[mesh.skinref].flags )
					{
						  gl.glDisable (gl.GL_BLEND);
						gl.glDisable(GL2.GL_ALPHA_TEST);
						gl.glAlphaFunc(GL2.GL_GREATER, 0.0f);
					}
    				
    			/*	for (int i =0;i< mesh.numtris-4;i+=5)
    				{
    					System.out.println("num "+ mesh.tris[i]);
    					
    				
    				}*/
    			
    				
    			}
    			
    		}
    		
    	}
    	
    	
    }
    protected static void drawBone( GL2 gl2 ) {
        
      
    	
    	int numbone = header.numbones;
    	for (int i=0;i<numbone;i++)
    	{
    		gl2.glPointSize(2.0f);
    		gl2.glBegin(gl2.GL_POINTS);
    		float size;
        	size = 10.0f;
        	gl2.glColor3f(1.0f, 0.0f, 0.0f);
    		gl2.glVertex3f(boneTransforms[i].get(0, 3),boneTransforms[i].get(1, 3), boneTransforms[i].get(2, 3));
    		gl2.glEnd();
    		
    		
    		if (header.m_Bone[i].parent>-1)
    		{
    			gl2.glLineWidth(2);
    			gl2.glBegin(gl2.GL_LINES);
    			gl2.glColor3f(1.0f, 1.0f, 0.0f);
    			gl2.glVertex3f(boneTransforms[i].get(0, 3),boneTransforms[i].get(1, 3), boneTransforms[i].get(2, 3));
    			gl2.glVertex3f(boneTransforms[header.m_Bone[i].parent].get(0, 3),boneTransforms[header.m_Bone[i].parent].get(1, 3), boneTransforms[header.m_Bone[i].parent].get(2, 3));
    			gl2.glEnd();
    		}
    	}
    	 
    }
}