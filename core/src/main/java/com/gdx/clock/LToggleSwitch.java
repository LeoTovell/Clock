package com.gdx.clock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LToggleSwitch {
	
	int x, y, width, height;
	int toggleX, toggleY, radius;
	int dx, dy, distance;
	Color color;
	Color toggleColor = Color.LIGHT_GRAY;
	boolean moving = false;
	boolean toggleLeft = true;
	int priority;

	public LToggleSwitch(int x, int y, int width, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = (int) (width*0.48);
		
		this.toggleX = (int) (x+(width*0.266));
		this.toggleY = (int) (y+(height*0.5));
		this.radius = (int) (height*0.277);
		
		this.color = color;
	}
	
	public void draw(ShapeRenderer sr, Batch batch) {
		// Mouse pos?
		
		if (moving) {
			if(toggleLeft) {
				if(toggleX < x + (int) (width*0.733)) {
					toggleX += 1;
				}
				else {
					toggleLeft = false;
					moving = false;
					toggleColor = Color.GREEN;
				}
			}
			else {
				if(toggleX > x + (int) (width *0.266)) {
					toggleX -= 1;
				}
				else {
					toggleLeft = true;
					moving = false;
					toggleColor = Color.LIGHT_GRAY;
				}
			}
		}
		else {
			boolean M1 = Gdx.input.isButtonPressed(Buttons.LEFT);
			float mouseX = Gdx.input.getX();
			float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
			
			dx = (int) (mouseX - toggleX);
			dy = (int) (mouseY - toggleY);
			distance = (int) Math.hypot(dx, dy);
					
			if(distance < radius & M1 & !moving) {
				moving = true;
			}
		}
		
		
		
		sr.begin(ShapeType.Filled);
		sr.setColor(this.color);
		sr.ellipse(x, y, width, height);
		sr.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(this.toggleColor);
		sr.circle(toggleX, toggleY, radius);
		sr.end();
	}
	
	public boolean getStatus() {
		if (toggleLeft) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return this.priority;
	}
}
