package motionProfileGenerator;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class Generator {
	
	//CONSTANTS BANK: HIGH GEAR: VELOCITY = 15, ACCL = 3.2, LOW GEAR: VELOCITY = 7.25,ACCL = 6
	
	public static Waypoint[] points = {new Waypoint(0,0,0),
								new Waypoint(10,0,0)};
	public static double timeStep = 0.02;
	public static double velocity = 7.25;
	public static double acceleration = 6;
	public static double jerk = 10;
	public static double wheelBaseWidth = 2.25;
	public static double wheelBaseDiameter = 5.875/12; //this is not relevant for tank drives
	public static Trajectory.FitMethod fitMethod = Trajectory.FitMethod.HERMITE_CUBIC;
	public static String filename = "STRAIGHT_LINE"; //sans the csv!
	
	public static Trajectory traj,left,right;
	public static Trajectory.Config config;
	public static TankModifier modifier;
	
	public static File leftOutput;
	public static File rightOutput;
	
	public static void main (String[] args) {
		config = new Trajectory.Config(fitMethod,Trajectory.Config.SAMPLES_HIGH,timeStep,velocity,acceleration,jerk);
		traj = Pathfinder.generate(points, config);
		modifier = new TankModifier(traj).modify(wheelBaseWidth);
		
		left = modifier.getLeftTrajectory();
		right = modifier.getRightTrajectory();
		
		leftOutput = new File("C:/Users/Titanium/git/1160_Motion_Profile_Generator/generatedTrajectories/" + filename + "_LEFT.csv");
		rightOutput = new File("C:/Users/Titanium/git/1160_Motion_Profile_Generator/generatedTrajectories/" + filename + "_RIGHT.csv");
		
		Pathfinder.writeToCSV(leftOutput, left);
		Pathfinder.writeToCSV(rightOutput, right);
	}
}
