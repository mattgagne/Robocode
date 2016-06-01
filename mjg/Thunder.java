package mjg;
import robocode.*;
import java.awt.Color;
import robocode.util.*;

public class Thunder extends AdvancedRobot
{		
	int moveY;
	int moveX;
	int moveDirection = 1;
		
	
	public void run() {
		setBodyColor(new Color(121,111,000)); // red,green,blue
		setGunColor(new Color(000,000,000));
		setRadarColor(new Color(000,000,000));
		setBulletColor(new Color(000,000,000));
		setScanColor(new Color(000,000,000));
		
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		double yLength = getBattleFieldHeight();
		double xLength = getBattleFieldWidth();
		
		
		
		while(true) {
			turnRightRadians(((Math.atan2((100 - getY()),(100 - getX())) + getHeadingRadians())));
			setTurnGunRight(360);
			execute();
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		moveY = 5;
		moveX = 5;
		fire(1);
	}
	public void onHitWall(HitWallEvent e) {
		moveDirection = moveDirection * -1;
	}	
	public void onHitRobot(HitRobotEvent e) {
	}	
	public void onBulletMissed(BulletMissedEvent e){
	}
	public void onBulletHit(BulletHitEvent e) {
	}
	public void onHitByBullet(HitByBulletEvent e) {
	}
}			
