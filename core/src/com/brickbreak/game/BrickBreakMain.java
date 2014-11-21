package com.brickbreak.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BrickBreakMain extends ApplicationAdapter
{
	WorldController worldController;
	WorldRenderer worldRenderer;
	
	@Override
	public void create ()
	{
		 //initialize controller and renderer
		 worldController = new WorldController();
	     worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void render ()//LOOP , LOOP , LOOP , LOOP , LOOP, LOOP 
	{
		worldRenderer.render();
		//O que pode acontecer com os objetos durante o jogo;
		worldController.update();
		
	}
	public void resize(int width, int height)
	{
		worldRenderer.camera.viewportWidth = width;
		worldRenderer.camera.viewportHeight = height;
		worldRenderer.camera.update();
	}
	public void dispose()
	{
		worldController.dispose();
		worldRenderer.dispose();	
	}
}
