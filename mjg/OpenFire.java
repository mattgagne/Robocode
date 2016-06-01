package mjg;
import robocode.*;
import java.awt.Color;
import robocode.util.*;
import robocode.RateControlRobot;

public class OpenFire extends AdvancedRobot
{
	boolean hitRobot = false;
	boolean noTarget = true;
	boolean clearWall = false;
	
	int moveDirection = 1;
	int cantFind = 0;
	int predict = 1;
	int missCount = 0;
	
	double xAmt,yAmt;
	double gunFlip;
	
	String eName = "b";
		
	public void run() {
		setBodyColor(new Color(255,50,50)); // red,green,blue
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
			//if(getX() - 150 < 0 || xAmt < getX() + 150 || getY() - 150 < 0 || yAmt < getY() + 150){
				//lsetVelocityRate(4);
			//}else{
				//setVelocityRate(8);
			//}
			//execute();
			
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		if(noTarget){
			eName = getName();
			noTarget = false;
		}
		if(eName != getName() && eName != "b"){
			cantFind = 5;
		}else{
			cantFind = 0;
		}
		
		double absBearing = e.getBearingRadians() + getHeadingRadians();
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()));
		setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians()
			+ gunFlip/(Math.max((e.getDistance()/100),1))));
		setFire(3);
		gunFlip = (double)(Math.random()*1.2)-.6;
		if(((double)(Math.random()*10)>9)){
			moveDirection = moveDirection*-1;
		}
		setTurnRightRadians(Utils.normalRelativeAngle(absBearing - getHeadingRadians() + 3.141/2 + (double)(Math.random()*1.8)-.9));
		setAhead(100*moveDirection);	
	}
	public void onHitWall(HitWallEvent e) {
		moveDirection = moveDirection * -1;
	}	
	public void onHitRobot(HitRobotEvent e) {
	}	
	public void onBulletMissed(BulletMissedEvent e){
		//missCount++;
		//if(missCount > 6){
		//	predict++;
		//}
	}

	public void onBulletHit(BulletHitEvent e) {
		//missCount = 0; 
	}
	public void onHitByBullet(HitByBulletEvent e) {
	}
}			