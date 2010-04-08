package jge3d;

//Required for file reading
import java.io.BufferedReader;
import java.io.FileReader;

//LWJGL input
import org.lwjgl.LWJGLException;

public class Main {
	public static void main(String[] args) throws LWJGLException {
		try{
			//the game always runs (except when it doesn't)
			boolean isRunning = true;
			
			//Make game "pieces"
			Camera camera;
			Editor editor;
			Window window;
			Physics physics;
			Input input;
			Level level;
			Renderer render;
			
			//create the window and all that jazz
 			window = new Window();
			
			//Renderer for drawing stuff
			render = new Renderer();
			
			//setup the initial perspective
			render.initGL(window);

			//Make some physics
			physics = new Physics();
			
			//Read in a level 
			BufferedReader levelfile;
			levelfile = new BufferedReader(new FileReader("lib/Levels/new.map"));
			level = new Level(levelfile, physics);
			
			//Camera
			camera = new Camera(0,0,0,level.getHeight(), level.getWidth());
			camera.goToStart(level.getHeight(), level.getWidth());
			
			//Create inputs
			input = new Input();
			
			//Make an editor
			editor = new Editor();

			//Just to show off the physics
			physics.dropBox(17,15,0,1.0f);
			
			while (isRunning) {
				//read keyboard and mouse
				input.handleMouse(camera, window, physics, editor);
				input.handleKeyboard();
				
				//Update the world's physical layout
				physics.clientUpdate();

				//Draw world
				render.draw(level, editor, physics, camera);

				//Print FPS to title bar
				window.updateFPS();
			}
		} catch(Exception e) {
			System.out.print("\nError Occured.  Exiting." + e.toString());
			System.exit(-1);
		}
	}
}