package mjg;
import robocode.*;
import java.awt.Color;
import robocode.util.*;

public class Charge extends AdvancedRobot
{
	boolean hitRobot = false;
	
	int moveDirection = 1;
	int cantFind = 0;
	
	double xAmt,yAmt;
		
	public void run() {
		setBodyColor(new Color(111,222,127)); // red,green,blue
		setGunColor(new Color(255,255,255));
		setRadarColor(new Color(000,000,000));
		setBulletColor(new Color(100,255,100));
		setScanColor(new Color(255,255,255));
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		xAmt = getBattleFieldWidth();
		yAmt = getBattleFieldHeight();
		
		while(true) {
			cantFind++;
			while(cantFind > 4){
				setTurnRadarRight(360);
				setTurnRight(5);
				setAhead(50*moveDirection);
				execute();
			}
			scan();
			
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		cantFind = 0;
		
		double absBearing = e.getBearingRadians() + getHeadingRadians();
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()));
		setTurnRightRadians(Utils.normalRelativeAngle(absBearing - getHeadingRadians()));
		setAhead(15);
		if(e.getDistance()*Math.abs(e.getVelocity()) < 1600 ){
			setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + Math.sin(e.getHeadingRadians()-getGunHeadingRadians())*e.getVelocity()/14 ));
			setFire(2);
		}else{
			setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians())); 
		}	
	}
	public void onHitWall(HitWallEvent e) {
		moveDirection = moveDirection * -1;
	}	
	public void onHitRobot(HitRobotEvent e) {
		hitRobot = true;
	}	
	public void onBulletMissed(BulletMissedEvent e){
		hitRobot = false;
	}

	public void onBulletHit(BulletHitEvent e) { 
	}
}			