package com.brickbreak.game;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class Ball
{
	private Texture texture_ball;
	private Sprite ball;
	private String gemaName; 
	//private HashMap <Integer, Texture> imagens = new HashMap<Integer, Texture>();
	private Random gerador = new Random();
	
	//Encapsula um retângulo 2D, TENHO EIXO X E Y ,poderei utilizar o método de intersecção entre objetos(sprites)
	//overlaps = se esse retângulo sobrepõe a outro retângulo.Vamos usar essa ideia para colisão
	private Rectangle bounds;
	
	

	
	
	public Ball(int xi, int yi)
	{
		//texture_ball = new Texture(Gdx.files.internal("ball2p.png"));
		//ball = new Sprite(texture_ball);
		
		//System.out.println(gerador.nextInt(6));
		switch(gerador.nextInt(6))
		{
			case 0:
				texture_ball = new Texture(Gdx.files.internal("gemBlue.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemBlue.png";
			break;
			case 1:
				texture_ball = new Texture(Gdx.files.internal("gemGreen.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemGreen.png";
			break;
			case 2:
				texture_ball = new Texture(Gdx.files.internal("gemOrange.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemOrange.png";
			break;
			case 3:
				texture_ball = new Texture(Gdx.files.internal("gemPurple.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemPurple.png";
			break;
			case 4:
				texture_ball = new Texture(Gdx.files.internal("gemRed.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemRed.png";
			break;
			case 5:
				texture_ball = new Texture(Gdx.files.internal("gemWhite.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemWhite.png";
			break;
			case 6:
				texture_ball = new Texture(Gdx.files.internal("gemYellow.png"));
				ball = new Sprite (texture_ball);
				this.gemaName="gemYellow.png";
			break;
		}	
		
		bounds = new Rectangle(xi,yi,74,74);
		

	}
	public Texture getBallTexture()
	{
		return texture_ball;
	}
	public Sprite getBallSprite()
	{
		return ball;
	}
	public void setBallBoundsX(int newx)
	{
		//bounds.x+=newx;
		bounds.x=newx;
	}
	public void setBallBoundsY(int newy)
	{
		//bounds.y+=newy;
		bounds.y=newy;
	}
	public float getBallBoundsXY(String valor)
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
	public Rectangle getBallBounds()
	{
		return bounds;
	}
	public String getBallName()
	{
		return gemaName;
	}
}
