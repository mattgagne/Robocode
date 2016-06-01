package mjg;
import robocode.*;
import java.awt.Color;
import robocode.util.*;

public class AdvancedSample extends AdvancedRobot
{		
	public void run() {
		setBodyColor(new Color(000,000,000)); // red,green,blue
		setGunColor(new Color(000,000,000));
		setRadarColor(new Color(000,000,000));
		setBulletColor(new Color(000,000,000));
		setScanColor(new Color(000,000,000));
		
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		while(true) {
			turnGunRight(360);
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}
	public void onHitWall(HitWallEvent e) {
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
