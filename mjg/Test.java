package mjg;
import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Test - a robot by (your name here)
 */
public class Test extends AdvancedRobot
{
	int turnDirection = 1;
	int moveDirection = 1;
	int countRadar = 0;//counts the amount of times the radar has turned
	int missCount = 0;//counts the amount of misses in a row
	
	double xAmt;
	double yAmt;
	double firePower = 3.1;
	double turnGunAmt = 0;
	double gunPredictionTurn;
	
	String target;
	
	boolean findTarget;
	boolean hitWall = false;
	
	/**
	 * run: Test's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		setBodyColor(new Color(255,000,100)); // red,blue,green
		setGunColor(new Color(255,000,10));
		setRadarColor(new Color(000,000,000));
		setBulletColor(new Color(255,100,100));
		setScanColor(new Color(255,100,50));
				
		// Robot main loop
		xAmt = getBattleFieldWidth();
		yAmt = getBattleFieldHeight();
		setTurnGunRight(360);
		turnRadarLeft(10);
		findTarget = true;
		
		while(true) {
			//hopefully, the robot will never execute the following code unless it has no target
			turnRadarRight(360);
			countRadar++;
			if(countRadar >= 4){
				setTurnRight(30000 / (xAmt + yAmt));//the bigger the arena, the bigger the circle the
				setAhead(300);						//robot makes when looking for a target
				execute();
			}
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		//code here will be run on any scanned robot
		countRadar = 0;//reset the counter
		if(findTarget == true){//if we need a target
			if(target != e.getName()){
				target = e.getName();//the first scanned robot is our new target
			}
		}
		findTarget = false;//we dont need a target anymore
		if(target != null && !e.getName().equals(target)){//if we have a taget and the scanned robot is not that target, keep scanning
			execute();
			return;
		} //code after this point will only be run if the scanned robot is the target
		turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());// (Math.abs(normalRelativeAngleDegrees((e.getBearing() + e.getHeading()))/2)) * e.getVelocity()/8;
		turnGunRight(turnGunAmt);
		if(getGunHeat() == 0){
			if(e.getDistance() < 200){
				fire(3);
			}else{
				fire(1.1);
			}
		}
		setAdjustGunForRobotTurn(true);
		if(e.getDistance() < 200 && hitWall == false){
			setTurnRight(e.getBearing() + 45);
		}else{
			setTurnRight(e.getBearing());
		}
		hitWall = false;
		setAhead(e.getDistance() * .66);
		scan();
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
	}
	
	public void onHitWall(HitWallEvent e) {
		hitWall = true;
		moveDirection = moveDirection * -1;
	}	
	
	public void onHitRobot(HitRobotEvent e) {
		if(e.getBearing() <= 90 && e.getBearing() > - 90){
			back(60);
		}
		if(e.getBearing() < -90 || e.getBearing() > 90){
			ahead(60);
		}
	}

	public void onBulletHit(BulletHitEvent e) { 
		missCount = 0;
	}
	
	public void onBulletMissed(BulletMissedEvent e){
		missCount++;
		if(missCount >= 9 && getOthers() != 1){
			findTarget = true;
		}//if you cant easily hit the target and it is not the last opponent left, find a new target
	}
	
	public void onRobotDeath(RobotDeathEvent e) {	
		findTarget = true;
	}
}
