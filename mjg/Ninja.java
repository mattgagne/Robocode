package mjg;
import robocode.*;
import java.awt.Color;
import robocode.util.*;

public class Ninja extends AdvancedRobot
{		
	
	int cantFind = 0;
	int	moveDirection = 1;
		
	double xLength = getBattleFieldWidth();
	double yLength = getBattleFieldHeight();
	
	public void run() {
		setBodyColor(new Color(000,000,000)); // red,green,blue
		setGunColor(new Color(000,000,000));
		setRadarColor(new Color(000,000,000));
		setBulletColor(new Color(000,222,000));
		setScanColor(new Color(000,000,000));
		
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		
		
		while(true) {
			cantFind++;
			
			setTurnRightRadians(Math.tan((getY()- (yLength/2)/(getX()- (xLength/2)))));
			setTurnRadarRight(360);
			ahead(10);
			
			while(cantFind > 5){
				turnRadarRight(45);
				//if(xLength-getX() > 60){
					//setTurnRightRadians(Utils.normalRelativeAngle(getHeadingRadians() + Math.PI/2));
				//}
			}
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		cantFind = 0;
		
		double absBearing = e.getBearingRadians() + getHeadingRadians();
		double eDistance = e.getDistance();
		double firePower = 3;
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()));
		setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() 
			+ Math.sin(e.getHeadingRadians() - absBearing)*e.getVelocity()/(20-3*firePower)));
		
		setFire(firePower);
		
		//setTurnRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() + 5*(3.14/6)));
		//setAhead(moveDirection*1000/eDistance);
		
		execute();
		
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
