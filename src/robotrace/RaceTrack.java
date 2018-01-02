package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        gl.glBegin(GL_TRIANGLE_STRIP);
            int numberOfVectors = 150;
            for(int j = 1; j <= 4; j ++){
            double t = 0;
            gl.glColor3d(0.2,0,(1.0/j));
                for(int i = 0; i <= numberOfVectors; i ++){
                    t = t + (1.0/numberOfVectors);
                    Vector point = getPoint(t);
                    gl.glVertex3d(point.x() + (j-1)*laneWidth*Math.cos(2*t*Math.PI),
                                  point.y() + (j-1)*laneWidth*Math.sin(2*t*Math.PI),
                                  1); 
                    gl.glVertex3d(point.x() + j*laneWidth*Math.cos(2*t*Math.PI),
                                  point.y() + j*laneWidth*Math.sin(2*t*Math.PI),
                                  1);
                }  
            }
        gl.glEnd();
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){
        Vector v = new Vector (0,0,1);
        if (lane == 1){
            v.x = 10.61*Math.cos(2*t*Math.PI);
            v.y = 14.61*Math.sin(2*t*Math.PI);
        } else if (lane == 2){
            v.x = 11.83*Math.cos(2*t*Math.PI);
            v.y = 15.83*Math.sin(2*t*Math.PI);            
        } else if (lane == 3){
            v.x = 13.05*Math.cos(2*t*Math.PI);
            v.y = 17.05*Math.sin(2*t*Math.PI);            
        } else if (lane == 4){
            v.x = 14.27*Math.cos(2*t*Math.PI);
            v.y = 18.27*Math.sin(2*t*Math.PI);            
        }
        return v;
    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        Vector v = new Vector (0,0,0);
        if (lane == 1){
            v.x = -2*Math.PI*10.61*Math.sin(2*t*Math.PI);
            v.y = 2*Math.PI*14.61*Math.cos(2*t*Math.PI);
        } else if (lane == 2){
            v.x = -2*Math.PI*11.83*Math.sin(2*t*Math.PI);
            v.y = 2*Math.PI*15.83*Math.cos(2*t*Math.PI);            
        } else if (lane == 3){
            v.x = -2*Math.PI*13.05*Math.sin(2*t*Math.PI);
            v.y = 2*Math.PI*17.05*Math.cos(2*t*Math.PI);            
        } else if (lane == 4){
            v.x = -2*Math.PI*14.27*Math.sin(2*t*Math.PI);
            v.y = 2*Math.PI*18.27*Math.cos(2*t*Math.PI);            
        }
        return v;
    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
