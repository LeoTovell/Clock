package com.gdx.clock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class clock extends ApplicationAdapter {

	DateTimeFormatter secondsFormat;
	DateTimeFormatter minutesFormat;
	DateTimeFormatter hoursFormat;
	ShapeRenderer sr;
	int clockCenterX;
	int clockCenterY;
	
	@Override
	public void create() {
		secondsFormat = DateTimeFormatter.ofPattern("ss");
		minutesFormat = DateTimeFormatter.ofPattern("mm");
		hoursFormat = DateTimeFormatter.ofPattern("hh");
		
		sr = new ShapeRenderer();
		clockCenterX = Gdx.graphics.getWidth()/2;
		clockCenterY = Gdx.graphics.getHeight()/2 - 20;
	}

	@Override
	public void render() {
		LocalTime localTime = LocalTime.now();
		int seconds = Integer.parseInt(secondsFormat.format(localTime));
		float percentSeconds = seconds/60f;
		float secondsAngle = percentSeconds * 360;
		secondsAngle = (float) ((secondsAngle * Math.PI / 180) - 90 * Math.PI / 180);
		
		
		float secondX = (float) (clockCenterX + 180 * Math.cos(-secondsAngle));
		float secondY = (float) (clockCenterY + 180 * Math.sin(-secondsAngle));
		
		int minutes = Integer.parseInt(minutesFormat.format(localTime));
		float percentMinutes = minutes/60f;
		float minutesAngle = percentMinutes * 360;
		minutesAngle = (float)((minutesAngle * Math.PI / 180) - 90 * Math.PI / 180);
		
		float minuteX = (float) (clockCenterX + 160 * Math.cos(-minutesAngle));
		float minuteY = (float) (clockCenterY + 160 * Math.sin(-minutesAngle));
		
		int hours = Integer.parseInt(hoursFormat.format(localTime));
		float percentHours = hours/60f;
		float hoursAngle = percentHours * 360;
		hoursAngle = (float)((hoursAngle * Math.PI / 180) - 90 * Math.PI / 180);
		
		float hourX = (float) (clockCenterX + 120 * Math.cos(-hoursAngle));
		float hourY = (float) (clockCenterY + 120 * Math.sin(-hoursAngle));
		
		ScreenUtils.clear(Color.WHITE);
		
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		sr.circle(clockCenterX, clockCenterY, 200);
		sr.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rectLine(clockCenterX, clockCenterY, hourX, hourY, 7);
		sr.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rectLine(clockCenterX, clockCenterY, minuteX, minuteY, 7);
		sr.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rectLine(clockCenterX, clockCenterY, secondX, secondY, 5);
		sr.end();
		
	}

	@Override
	public void dispose() {

	}
}