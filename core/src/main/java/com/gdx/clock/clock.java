package com.gdx.clock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class clock extends ApplicationAdapter {

	DateTimeFormatter secondsFormat;
	DateTimeFormatter minutesFormat;
	DateTimeFormatter hoursFormat;
	DateTimeFormatter digitalFormat;
	ShapeRenderer sr;
	int clockCenterX;
	int clockCenterY;
	BitmapFont font;
	SpriteBatch batch;
	LToggleSwitch clockType;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		secondsFormat = DateTimeFormatter.ofPattern("ss");
		minutesFormat = DateTimeFormatter.ofPattern("mm");
		hoursFormat = DateTimeFormatter.ofPattern("hh");
		digitalFormat = DateTimeFormatter.ofPattern("hh : mm : ss");
		
		sr = new ShapeRenderer();
		clockCenterX = Gdx.graphics.getWidth()/2;
		clockCenterY = Gdx.graphics.getHeight()/2 - 20;
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		
		clockType = new LToggleSwitch(50, Gdx.graphics.getHeight() - 100, 60, Color.BLACK);
	}

	@Override
	public void render() {
		LocalTime localTime = LocalTime.now();
		
		ScreenUtils.clear(Color.WHITE);
		
		if(clockType.getStatus()) {
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
						
			for(int i = 0; i < 60; i++) {
				int tick = i;
				float percentTick = tick/60f;
				float tickAngle = percentTick * 360;
				tickAngle = (float) ((tickAngle * Math.PI/180) - 90 * Math.PI / 180);
				
				float innerX = (float) (clockCenterX + 190 * Math.cos(-tickAngle));
				float innerY = (float) (clockCenterY + 190 * Math.sin(-tickAngle));
				
				float outerX = (float) (innerX + 10 * Math.cos(-tickAngle));
				float outerY = (float) (innerY + 10 * Math.sin(-tickAngle));
				
				float textX = (float) (outerX + 15 * Math.cos(-tickAngle));
				float textY = (float) (outerY + 15 * Math.sin(-tickAngle));
				
				sr.begin(ShapeType.Filled);
				sr.setColor(Color.BLACK);
				sr.rectLine(innerX, innerY, outerX, outerY, 4);
				sr.end();
				
			if(i % 5 == 0) {	
					batch.begin();
					GlyphLayout layout = new GlyphLayout(font, String.valueOf(i));
					font.draw(batch, layout, (float) (textX - 0.5 * layout.width), (float) (textY + 0.5 * layout.height));
					batch.end();
			}
				
			}
			
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
			
			clockType.draw(sr, batch);
		}
		else{			
			batch.begin();
			GlyphLayout layout = new GlyphLayout(font, digitalFormat.format(localTime));
			System.out.println(Gdx.graphics.getWidth()/2 - layout.width/2);
			font.draw(batch, layout, Gdx.graphics.getWidth()/2 - (layout.width/2), Gdx.graphics.getHeight()/2 - (layout.height/2));
			batch.end();
			
			clockType.draw(sr, batch);
		}
		
	}

	@Override
	public void dispose() {

	}
}