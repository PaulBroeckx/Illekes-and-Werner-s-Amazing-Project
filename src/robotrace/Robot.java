package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
        gl.glColor3d(1,0.5,0.5);
        gl.glTranslated(0,0,1.35);
        glut.glutSolidSphere(0.45, 45, 45);
        gl.glColor3d(0,0,0);
        gl.glTranslated(0.20,0.30,0);
        glut.glutSolidSphere(0.18, 18, 18);
        gl.glTranslated(-0.20,-0.30,0);
        gl.glTranslated(-0.20,0.30,0);
        glut.glutSolidSphere(0.18, 18, 18);
        gl.glTranslated(0.20,-0.30,-0.75);
        gl.glColor3d(0.5,0.5,0.8);
        glut.glutSolidCylinder(0.15, 0.40, 15, 40);
        
        
        gl.glPopMatrix();
    }
    
}
