package mjg;
import robocode.*;
import java.awt.Color;
import robocode.util.*;
/**
 * NightTerrors - a robot by Matt Gagne
 */
public class NightTerrors extends AdvancedRobot
{
	boolean needTarget = true;
	boolean nearingWall = false;

	int cantFind = 0;
	int moveDirection = 1;
	int firePower;
	int bumper = 75;
	int missCount = 0;
	
	double xAmt,yAmt;
	
	public void run() {
		setBodyColor(new Color(000,000,127)); // red,green,blue
		setGunColor(new Color(255,255,255));
		setRadarColor(new Color(000,255,000));
		setBulletColor(new Color(000,255,000));
		setScanColor(new Color(255,255,255));
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		xAmt = getBattleFieldWidth();
		yAmt = getBattleFieldHeight();
		
		while(true) {
			cantFind++;
			while(needTarget || cantFind > 5){
				setTurnRadarRight(360);
				setAhead(800 * moveDirection);
				setTurnRight(1);
				execute();
			}
			scan();
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		cantFind = 0;
		needTarget = false;

		double absoluteBearing = e.getBearingRadians() + getHeadingRadians();
		double eDistance = e.getDistance();
		
		if(!needTarget){
			turnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
		}
		setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians()
		 + (Math.sin(e.getHeadingRadians() - absoluteBearing) * e.getVelocity()/(19 - 3*firePower))));
		//setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians()));
		//19 - 3*firePower equals the velocity of the bullet -1
		if(eDistance <= 200){
			firePower = 3;
		}else if(200 < eDistance && eDistance < 500){
			firePower = 1;
		}else{
			firePower = 0;
		}
		if(getGunHeat()==0){
			setFire(Math.min(firePower , getEnergy()/2));
		}//fires according to distance unless energy is really low
		if(moveDirection == 1){
			setTurnRight(e.getBearing() + Math.min(90, 9000/ eDistance));
		}else{
			setTurnRight(e.getBearing() - Math.min(90, 9000/ eDistance) + 180);
		}
		setAhead(e.getDistance() * moveDirection);
		if(xAmt - bumper < getX() || bumper > getX() || yAmt - bumper < getY() || bumper > getY()){
			if(!nearingWall){
				nearingWall = true;
				moveDirection = moveDirection * -1; 
			}
		}else{
			nearingWall = false;
		}
	}
	public void onHitWall(HitWallEvent e) {
		moveDirection = moveDirection * -1;
	}	
	public void onHitRobot(HitRobotEvent e) {
		moveDirection = moveDirection * -1;
	}	
	public void onBulletMissed(BulletMissedEvent e){
	}

	public void onBulletHit(BulletHitEvent e) { 
		missCount = 0;
	}
}