package com.brickbreak.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class Mouse
{
	private Texture texture_mouse;
	private Sprite mouse;
	
	//Encapsula um retângulo 2D, TENHO EIXO X E Y ,poderei utilizar o método de intersecção entre objetos(sprites)
	//overlaps = se esse retângulo sobrepõe a outro retângulo.Vamos usar essa ideia para colisão
	private Rectangle bounds;
	
	public Mouse(int xi, int yi)
	{
		texture_mouse = new Texture(Gdx.files.internal("ball2p.png"));
		mouse = new Sprite(texture_mouse);
		bounds = new Rectangle(xi,yi,5,5);
		
		

	}
	public Texture getBallTexture()
	{
		return texture_mouse;
	}
	public Sprite getBallSprite()
	{
		return mouse;
	}
	public void setMouseBoundsX(int newx)
	{
		//bounds.x+=newx;
		bounds.x=newx;
	}
	public void setMouseBoundsY(int newy)
	{
		//bounds.y+=newy;
		bounds.y=newy;
	}
	public float getMouseBoundsXY(String valor)
	{
		
			if(valor.equals("x"))
			{
				return bounds.x;
			}
			if(valor.equals("y"))
			{
				return bounds.y;
			}
				
			System.out.println("por favor use x ou y");
		return 0;	
	}
	public Rectangle getMouseBounds()
	{
		return bounds;
	}
}
