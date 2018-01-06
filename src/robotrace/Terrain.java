package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        gl.glBegin(GL_QUADS);
        gl.glColor3d(0.1,0.4,0.1);
        for(int i = -20; i < 20; i++){
            for(int j = -20; j < 20; j++){
                gl.glVertex3d(i,
                              j,
                              0); 
                gl.glVertex3d(i,
                              (j+1),
                              0); 
                gl.glVertex3d((i+1),
                              (j+1),
                               0);
                gl.glVertex3d((i+1),
                               j,
                               0);
            }
        }
        gl.glEnd();
    }
    
}
