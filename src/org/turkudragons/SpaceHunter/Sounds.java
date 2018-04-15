package org.turkudragons.SpaceHunter;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	
	static Sound DragonRoar;
	static Sound Warning;
	static Sound Death;
	static Sound Crash;
	static Sound bullet;
	static Sound fire;
	static Sound grenade;
	static Sound missile;
	static Sound plop;
	static Sound rifle;
	static Sound shotgun;
	static Sound bite;
	
	public static void initiateSounds() throws SlickException {
		DragonRoar = new Sound("res/DragonRoar.ogg");
		Warning = new Sound("res/Warning.ogg");
		Death = new Sound ("res/RobotDeath.ogg");
		Crash = new Sound ("res/Slam.ogg");
		bullet = new Sound("res/Bullet.ogg");
		fire = new Sound("res/Fire.ogg");
		grenade = new Sound("res/Grenade.ogg");
		missile = new Sound("res/Missile.ogg");
		plop = new Sound("res/Plop.ogg");
		rifle = new Sound("res/Rifle.ogg");
		shotgun = new Sound("res/Shotgun.ogg");
		bite = new Sound("res/Bite.ogg");
	}
	
	public static boolean soundIsPlaying() {
		if(DragonRoar.playing() || Warning.playing() || Death.playing() || Crash.playing() || bullet.playing() || fire.playing() ||
				grenade.playing() || missile.playing() || plop.playing() || rifle.playing() || shotgun.playing() || bite.playing())
			return true;
		return false;
	}
	
}
