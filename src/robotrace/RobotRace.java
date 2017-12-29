package robotrace;

import static com.jogamp.opengl.GL2.*;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Handles all of the RobotRace graphics functionality,
 * which should be extended per the assignment.
 * 
 * OpenGL functionality:
 * - Basic commands are called via the gl object;
 * - Utility commands are called via the glu and
 *   glut objects;
 * 
 * GlobalState:
 * The gs object contains the GlobalState as described
 * in the assignment:
 * - The camera viewpoint angles, phi and theta, are
 *   changed interactively by holding the left mouse
 *   button and dragging;
 * - The camera view width, vWidth, is changed
 *   interactively by holding the right mouse button
 *   and dragging upwards or downwards; (Not required in this assignment)
 * - The center point can be moved up and down by
 *   pressing the 'q' and 'z' keys, forwards and
 *   backwards with the 'w' and 's' keys, and
 *   left and right with the 'a' and 'd' keys;
 * - Other settings are changed via the menus
 *   at the top of the screen.
 * 
 * Textures:
 * Place your "track.jpg", "brick.jpg", "head.jpg",
 * and "torso.jpg" files in the folder textures. 
 * These will then be loaded as the texture
 * objects track, bricks, head, and torso respectively.
 * Be aware, these objects are already defined and
 * cannot be used for other purposes. The texture
 * objects can be used as follows:
 * 
 * gl.glColor3f(1f, 1f, 1f);
 * Textures.track.bind(gl);
 * gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0);
 * gl.glVertex3d(0, 0, 0);
 * gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0);
 * gl.glTexCoord2d(1, 1);
 * gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1);
 * gl.glVertex3d(0, 1, 0);
 * gl.glEnd(); 
 * 
 * Note that it is hard or impossible to texture
 * objects drawn with GLUT. Either define the
 * primitives of the object yourself (as seen
 * above) or add additional textured primitives
 * to the GLUT object.
 */
public class RobotRace extends Base {
    
    /** Array of the four robots. */
    private final Robot[] robots;
    
    /** Instance of the camera. */
    private final Camera camera;
    
    /** Instance of the race track. */
    private final RaceTrack[] raceTracks;
    
    /** Instance of the terrain. */
    private final Terrain terrain;
        
    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {
        
        // Create a new array of four robots
        robots = new Robot[4];
        
        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD
                
        );
        
        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER
              
        );
        
        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD
              
        );

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE
                
        );
        
        // Initialize the camera
        camera = new Camera();
        
        // Initialize the race tracks
        raceTracks = new RaceTrack[2];
        
        // Track 1
        raceTracks[0] = new ParametricTrack();
        
        // Track 2
        float g = 3.5f;
        raceTracks[1] = new BezierTrack(
                
            new Vector[] {}
       
        );
        
        // Initialize the terrain
        terrain = new Terrain();
    }
    
    /**
     * Called upon the start of the application.
     * Primarily used to configure OpenGL.
     */
    @Override
    public void initialize() {
		
        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                
        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);
		
        // Enable face culling for improved performance
        // gl.glCullFace(GL_BACK);
        // gl.glEnable(GL_CULL_FACE);
        
	    // Normalize normals.
        gl.glEnable(GL_NORMALIZE);
        
	// Try to load four textures, add more if you like in the Textures class         
        Textures.loadTextures();
        reportError("reading textures");
        
        // Try to load and set up shader programs
        ShaderPrograms.setupShaders(gl, glu);
        reportError("shaderProgram");
        
    }
   
    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);
        
        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 10*gs.vDist);
        
        // Set camera.
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
        
        // Add light source
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0f,0f,0f,1f}, 0);
               
        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        camera.update(gs, robots[0]);
        glu.gluLookAt(camera.eye.x(),    camera.eye.y(),    camera.eye.z(),
                      camera.center.x(), camera.center.y(), camera.center.z(),
                      camera.up.x(),     camera.up.y(),     camera.up.z());
    }
    
    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {
        
        gl.glUseProgram(defaultShader.getProgramID());
        reportError("program");
        
        // Background color.
        gl.glClearColor(1f, 1f, 1f, 0f);
        
        // Clear background.
        gl.glClear(GL_COLOR_BUFFER_BIT);
        
        // Clear depth buffer.
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        // Set color to black.
        gl.glColor3f(0f, 0f, 0f);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
        // Draw the axis frame.
        if (gs.showAxes) {
            drawAxisFrame();
        }
        
        // Draw the (first) robot.
        gl.glUseProgram(robotShader.getProgramID()); 
        
        robots[0].draw(gl, glu, glut, 0);
        
        
        // Draw the race track.
        gl.glUseProgram(trackShader.getProgramID());
        raceTracks[gs.trackNr].draw(gl, glu, glut);
        
        // Draw the terrain.
        gl.glUseProgram(terrainShader.getProgramID());
        terrain.draw(gl, glu, glut);
        reportError("terrain:");
        
        
    }
    
    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {
        /*
        gl.glPushMatrix();
        gl.glColor3d(1,0,0); //x axis
        drawArrow(1,0,0);
        gl.glColor3d(0,1,0); //y axis
        drawArrow(0,1,0);
        gl.glColor3d(0,0,1); //z axis
        drawArrow(0,0,1);
        gl.glColor3d(1,1,0); //yellow sphere
        glut.glutSolidSphere(0.1, 5, 5);
        gl.glPopMatrix();
        */
        gl.glPushMatrix();
        gl.glColor3f(1,1,0);
        glut.glutSolidSphere(0.12f, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glColor3f(0,0,1);
        drawArrow();
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glColor3f(1,0,0);
        gl.glRotatef(-90,1,0,0);
        drawArrow();
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glColor3f(0,1,0);
        gl.glRotatef(-90,0,1,0);
        drawArrow();
        gl.glPopMatrix();
    }
    
    /**
     * Draws a single arrow
     */
    public void drawArrow() {
        /*
        if (x != 0){
            gl.glTranslated(0.5*x,-0.01,-0.01);
            gl.glScaled(x,0.02,0.02);
            glut.glutSolidCube(1);
            gl.glScaled((1/x),50,50);
            gl.glTranslated(-0.5*x,0.01,0.01);
            gl.glTranslated(x,0,0);
            gl.glRotated(90,0,1,0);
            glut.glutSolidCone(0.05,0.1,5,5);
            gl.glRotated(-90,0,1,0);
            gl.glTranslated(-x,0,0);
        } else if (y != 0){
            gl.glTranslated(-0.01,0.5*y,-0.01);
            gl.glScaled(0.02,y,0.02);
            glut.glutSolidCube(1);  
            gl.glScaled(50,(1/y),50);
            gl.glTranslated(0.01,-0.5*y,0.01);
            gl.glTranslated(0,y,0);
            gl.glRotated(-90,1,0,0);
            glut.glutSolidCone(0.05,0.1,5,5);
            gl.glRotated(90,1,0,0);
            gl.glTranslated(0,-y,0);
        } else if (z != 0) {
            gl.glTranslated(-0.01,-0.01,0.5*z);
            gl.glScaled(0.02,0.02,z);
            glut.glutSolidCube(1); 
            gl.glScaled(50,50,(1/z));
            gl.glTranslated(0.01,0.01,-0.5*z);
            gl.glTranslated(0,0,z);
            glut.glutSolidCone(0.05,0.1,5,5);
            gl.glTranslated(0,0,-z);
        }
        */
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, 0.8f);
        glut.glutSolidCone(0.1f, 0.2f, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glScalef(0.1f, 0.1f, 1f);
        gl.glTranslatef(0f, 0f, 0.4f);
        glut.glutSolidCube(0.8f);
        gl.glPopMatrix();
    }
    
    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
}
