package mcd;
import robocode.*;
import java.awt.Color;
import robocode.util.*;
 //McDodges - a robot by Matt Gagne

public class McDodges extends AdvancedRobot {

	double moveDirection = 1;
	int move = 1;
	double eEnergy = 100;
	
	int cantFind = 6;
	
	public void run() {
		
		setBodyColor(new Color(000,000,000)); // red,green,blue
		setGunColor(new Color(000,000,000));
		setRadarColor(new Color(102,102,102));
		setBulletColor(new Color(000,000,000));
		setScanColor(new Color(000,000,000));
		
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		turnRadarRight(360);
		while(true) {
		
			cantFind++;	
			while(cantFind > 5){
				turnRadarRight(360);
			}
			
		}	
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		cantFind = 0;
		double aB = e.getBearingRadians() + getHeadingRadians();
		double bearing = e.getHeadingRadians() - aB;
		double eV = e.getVelocity();
		double b = 17; //bulletSpeed
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(aB - getRadarHeadingRadians()));
		
		if(e.getDistance() > 150){
			setTurnRight(e.getBearing() + 90 - 15 * moveDirection);
		}else{
			setTurnRight(e.getBearing() + 90 + 15 * moveDirection);
		}
		
		if(e.getEnergy() < eEnergy && e.getEnergy() > eEnergy - 3.1){
			setAhead(60* moveDirection);
		}
		
		setTurnGunRightRadians(Utils.normalRelativeAngle(aB - getGunHeadingRadians() + Math.sin(bearing) /b * eV ));
		setFire(1.1);
		eEnergy = e.getEnergy();
	}

	public void onHitByBullet(HitByBulletEvent e) {
	}
	
	public void onHitWall(HitWallEvent e) {
		moveDirection = -moveDirection;
	}	

	public void onBulletMissed(BulletMissedEvent e){
	}

	public void onBulletHit(BulletHitEvent e) { 
	}

	public void onHitRobot(HitRobotEvent e) {
	}

	public void onRobotDeath(RobotDeathEvent e) {
	}
}