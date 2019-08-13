package javaFxDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import pso.LolFitnessFunction;
import pso.Particle;
import pso.Swarm;
import pso.MultiSwarm;
//import src.pso.Position;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.SceneAntialiasing;
import javax.swing.Timer;

public class Basic3dDriver extends PopulationDriver {
         int sColor=1;
	private Group root = new Group();
	private Group boundryGroup = new Group();
	private Xform world = new Xform();
	private PerspectiveCamera camera = new PerspectiveCamera(true);
	private Xform cameraXform = new Xform();
	private Xform cameraXform2 = new Xform();
	private Xform cameraXform3 = new Xform();
	private double cameraDistance = 2500;

	private Xform particleGroup = new Xform();
	private ArrayList<Cube> particles = new ArrayList<Cube>();
	private int size = 500;
	private int halfSize = size;

	private SubScene scene;
	private Cube goalCube = new Cube(20, 20, 20);
	private int particleSize = 70;
	
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	private LolFitnessFunction fitnessFunction = new LolFitnessFunction();
         MultiSwarm multiswarm;
        private Swarm[] ss;
	
	public Basic3dDriver(int[] searchSpaceDimensions, int[] initGoal, int numPopulations, int[] popSizes) {
		super(searchSpaceDimensions, initGoal, numPopulations, popSizes);
	        multiswarm = new MultiSwarm(5, 1000, new LolFitnessFunction());
		//---swarm setup---//
		this.paramList = new String[]{"X", "Y", "Z", "Red", "Green", "Blue", "Alpha", "Beta", "Gamma"};
		this.numDimensions = 9; //TODO: Set dynamically
		this.paramMult = new double[this.numDimensions];
		Arrays.fill(this.paramMult, 1);
		ss= multiswarm.getSwarm();
		
		//this.fitnessFunction = new FitnessDistance(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
		
		/*//---POPULATION SETUP---//
		for (int i = 0; i < this.numPopulations; i++) {
			PsoConfigOptions options = new PsoConfigOptions();
			options.c1 = 0.006f;
			options.c2 = 0.001f;
			options.speedLimit = 25;
			//Population p = new Population(size, popSizes[i], fitnessFunction, options);
			options.population = p;
			this.opts.add(options);
			this.populations.add(p);
		}*/
                root.getChildren().add(world);
                this.buildCamera();
		//this.buildBoundries(); 
              /*  int i = 5;
                while(i>0){
                multiswarm.mainLoop(i);
                i--;
                }*/
                this.buildParticles();  
                this.scene = new SubScene(root, width, height, true, SceneAntialiasing.BALANCED);
		this.scene.setFill(Color.color(0.85, 0.85, 1.0));
		this.scene.setCamera(camera);
                //root.getChildren().add(world);               
		//---UI SETUP---//
		
		
	}
    
        public MultiSwarm getMultiSwarm(){
            return multiswarm;
        }

	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);
                camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-cameraDistance);
		cameraXform.ry.setAngle(320.0);
	}

	//---BUILDS CUBE SKELETON FOR VISUAL SPATIAL REFERENCE---//


	private void buildParticles () {
		for (Swarm p : ss) {
                   
                    System.out.println( "Color"+  sColor);
                  System.out.println("****************************"+p);
			for (Particle particle : p.getParticles()) {
                            System.out.println("****************************"+particle);
				Color color = Color.color(
					sColor/10,
					 sColor/10,
					sColor/10
				);
				Cube box = new Cube(this.particleSize, this.particleSize, this.particleSize, color, color);
				box.translate(
					 particle.getPosition2()[0],
					 particle.getPosition2()[1],
					 particle.getPosition2()[2]
				);
				particles.add(box);
			}
                             sColor++;
		}
           
	
		for (Cube particle : particles) {
			this.particleGroup.getChildren().addAll(particle.getBox());
		}
		this.world.getChildren().addAll(this.particleGroup);

        }

	private int getPosNeg () {
		return (Math.random() < 0.5) ? 1 : -1;
	}

	@Override
	public void update (float elapsedTime,int flag, int maxim) {
               //int totParticleCnt = 0;
             //  System.out.println("update method called");
                if(flag!=0){
			multiswarm.mainLoop(maxim);
                        } 
                else{
                    int totParticleCnt = 0;
		for (Swarm p : ss) {
                        //System.out.println("Swarm"+p);
                        
                    /*    for (int i = 0; i<p.getParticles().length; i++) {
                            
				Particle particle = p.getParticles()[i];
                                System.out.println(p.getSNumber()  +"  "+p);
				Cube cube = particles.get(i);
				//int[] position = particle.getPosition().getVector();
				//---SET POSITION---//
                                //System.out.println(flag +"        "+particle.getPosition()[0]+","+particle.getPosition()[1]+","+particle.getPosition()[2]+","+particle.getPosition()[3]+","+particle.getPosition()[4]+","+particle.getPosition()[5]);
				cube.translate(particle.getPosition()[0], particle.getPosition()[1], particle.getPosition()[2]);
				//---SET COLOR---//
                                //System.out.println("translate");
				
			
                            }
                        
                    */    
                        
                     for (int i = totParticleCnt; i < totParticleCnt + p.getParticles().length; i++) {
				Particle particle = p.getParticles()[(i - totParticleCnt)];
				Cube cube = particles.get(i);
				//int[] position = particle.getPosition().getVector();
				//---SET POSITION---//
				cube.translate(particle.getPosition()[0], particle.getPosition()[1], particle.getPosition()[2]);
				//---SET COLOR---//
				
			}   
                        
                        totParticleCnt += p.getParticles().length;
                        
                        
                        
                        }
			
			
		}
        }
		
	

	
	@Override
	public Node getUiNode () {
		return this.scene;
	}
	
	public void setFullscreen (boolean isFullscreen, double w, double h) {
		if (isFullscreen) {
			this.scene.setWidth(w);
			this.scene.setHeight(h);
		}
		else {
			this.scene.setWidth(width);
			this.scene.setHeight(height);
		}
	}
	
	public String toString () {
		return "basic3D";
	}
	
	/*
	 * 3D camera rotate on mouse drag
	 * from http://docs.oracle.com/javafx/8/3d_graphics/sampleapp.htm
	 */
	

  /*  @Override
    public void update(float elapsedTime) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       multiswarm.mainLoop();
    }*/

}