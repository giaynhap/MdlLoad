import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
 
/**
 * JOGL 2.0 Example 2: Rotating 3D Shapes (GLCanvas)
 */
@SuppressWarnings("serial")
public class viewForm extends GLCanvas implements GLEventListener {
   // Define constants for the top-level container
   private static String TITLE = "MdlViewer";  // window's title
   private static final int CANVAS_WIDTH = 800;  // width of the drawable
   private static final int CANVAS_HEIGHT = 600; // height of the drawable
   private static final int FPS = 60; // animator's target frames per second
 
   /** The entry main() method to setup the top-level container and animator */
   public static void main(String[] args) {
	   
	   OneTriangle.doconvert();
	 
	   
	   
	   
	   
	   
	   
	   
	   
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
             
            GLCanvas canvas = new viewForm();
            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
            // Create a animator that drives canvas' display() at the specified FPS.
            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
 
            // Create the top-level container
            final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
            frame.getContentPane().add(canvas);
       
            frame.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                  // Use a dedicate thread to run the stop() to ensure that the
                  // animator stops before program exits.
                  new Thread() {
                     @Override
                     public void run() {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                     }
                  }.start();
               }
            });
            frame.setTitle(TITLE);
            frame.pack();
            frame.setVisible(true);
            animator.start(); // start the animation loop
         }
      });
   }
 
   // Setup OpenGL Graphics Renderer
 
   private GLU glu;  // for the GL Utility
   private float anglePyramid = 0;    // rotational angle in degree for pyramid
   private float angleCube = 0;       // rotational angle in degree for cube
   private float speedPyramid = 2.0f; // rotational speed for pyramid
   private float speedCube = -1.5f;   // rotational speed for cube
 
   /** Constructor to setup the GUI for this Component */
   public viewForm() {
      this.addGLEventListener(this);
   }
 
   // ------ Implement methods declared in GLEventListener ------
 
   /**
    * Called back immediately after the OpenGL context is initialized. Can be used
    * to perform one-time initialization. Run only once.
    */
   @Override
   public void init(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
      glu = new GLU();                         // get GL Utilities
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
      gl.glClearDepth(1.0f);      // set clear depth value to farthest
      gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
      gl.glDepthFunc(GL2.GL_LEQUAL);  // the type of depth test to do
      gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
      gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
   }
 
   /**
    * Call-back handler for window re-size event. Also called when the drawable is
    * first set to visible.
    */
   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
 
      if (height == 0) height = 1;   // prevent divide by zero
      float aspect = (float)width / height;
 
      
      gl.glViewport(0, 0, width, height);
 
      gl.glClearDepth(1.0f);
      gl.glEnable(GL.GL_DEPTH_TEST);
      gl.glDepthFunc(GL.GL_LEQUAL);
      gl.glShadeModel(GL.GL_LINE_SMOOTH);
      gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

      gl.glEnable(GL2.GL_DEPTH_TEST);
      gl.glEnable(GL2.GL_NORMALIZE);
      gl.glEnable(GL2.GL_LIGHTING);
      gl.glEnable(GL2.GL_LIGHT0);
      gl.glEnable(GL2.GL_COLOR_MATERIAL);
  
      gl.glMatrixMode(GL2.GL_PROJECTION);  
      gl.glLoadIdentity();           
      glu.gluPerspective(60.0, aspect, 0.1, 1000.0); 

      float []lightPos={-40,100,40,0};
      float ambient[] = {0.1f, 0.1f, 0.1f,1.0f} ;
      float diffuse[] = {0.5f, 0.5f, 0.5f,1.0f} ;
      float spec[]    = {0.0f, 0.0f, 0.0f,1.0f};
      float emiss[]   = {0.0f, 0.0f, 0.0f,2.0f}; 
  	gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
  	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos,0);
  	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient,0);
  	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse,0);
     
      gl.glMatrixMode(GL2.GL_MODELVIEW);
      gl.glLoadIdentity(); // reset
      OneTriangle.setup(gl, width, height);
   }
 
   
   @Override
   public void display(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();   
    
    //  gl.glEnable(GL2.GL_LIGHTING);
    // gl.glEnable(GL2.GL_LIGHT0);
	  
	  	gl.glClearColor( 0.3f, 0.6f, 0.8f,0.0f);
	  	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); 
	  	
	    // OldgAngle[0]+=0.1;
	     //   glu.gluLookAt(	eyse_postion[0]*Math.cos((OldgAngle[0]+gRotatef[0])*Math.PI/180)*Math.cos((OldgAngle[1]+gRotatef[1])*Math.PI/180),eyse_postion[0]*Math.sin((OldgAngle[1]+gRotatef[1])*Math.PI/180),eyse_postion[0]*Math.sin((OldgAngle[0]+gRotatef[0])*Math.PI/180)*-Math.cos((OldgAngle[1]+gRotatef[1])*Math.PI/180),
			//		eyse_lookat[0], eyse_lookat[1], eyse_lookat[2],0.0f, Math.cos((OldgAngle[1]+gRotatef[1])*Math.PI/180)/Math.abs(Math.cos((OldgAngle[1]+gRotatef[1])*Math.PI/180)),0.0f);
	     
	  	
	  	gl.glMatrixMode (gl.GL_MODELVIEW);
		gl.glPushMatrix ();
		gl.glLoadIdentity ();
 
     // glu.gluLookAt(100,0,0,0,0,0,0,1,0);
      //gl.glRotatef(anglePyramid, 0.0f, 1.0f, 0.0f); 
 //     gl.glOrtho (0.0f,800 , 600, 0.0f, 1.0f, 0);
     // gl.glScaled(0.1f, 0.1f, 0.1f);
      
      
      
    
      gl.glEnable(GL2.GL_DEPTH_TEST);
  
      gl.glDisable(GL2.GL_CULL_FACE);
      gl.glPolygonMode (gl.GL_FRONT_AND_BACK, gl.GL_FILL);
 
      OneTriangle.drawMesh(gl);

      gl.glDisable(GL2.GL_BLEND);
      gl.glDisable(GL2.GL_DEPTH_TEST);
      gl.glDisable(GL2.GL_CULL_FACE);
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glDisable(GL2.GL_TEXTURE_2D);
  
   //   OneTriangle.drawBone(gl);
   
      anglePyramid += speedPyramid;
      
   }
 
   /**
    * Called back before the OpenGL context is destroyed. Release resource such as buffers.
    */
   @Override
   public void dispose(GLAutoDrawable drawable) { }
}