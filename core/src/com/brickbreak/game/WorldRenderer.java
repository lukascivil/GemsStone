package com.brickbreak.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable
{
	/** the camera **/
	protected OrthographicCamera camera;
	private WorldController worldController;
	
	private BitmapFont font;
	
	SpriteBatch batch;
	SpriteBatch batch2;
	private Rectangle glViewport;
	
	//TiledMap
	//public TiledMap map;
	//public OrthogonalTiledMapRenderer renderer;
	//public TiledMapTileLayer collisionlayer;
	
	//RectangleMapObject rectangleObject;
	Batch x;
	//time
	//private long startTime;
	public int y;
	
		public WorldRenderer(WorldController worldController)
		{
			
			this.worldController = worldController;
			init();
			
		
		}
		//Apenas é inicializado pelo BrickBreakMain
		private void init()
		{
			Gdx.input.setInputProcessor(worldController);
			//Necessário para carregar o sprite(ela que faz desenhar)
			batch= new SpriteBatch();
			batch2= new SpriteBatch();
			font = new BitmapFont();
			
			//camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			//camera.position.set(Gdx.graphics.getWidth() / 2+100000, Gdx.graphics.getHeight() / 2, 0);
			
			//glViewport = new Rectangle(0, 0, 480, 800);
			
			
			//map = new TmxMapLoader().load("map.tmx");
			//renderer = new OrthogonalTiledMapRenderer(map);	
			camera = new OrthographicCamera(480, 800);
		}
		
		// método que irá conter a lógica de definir qual ordem do jogo
		// objetos são desenhados sobre os outros
		//Está no loop do loop BrickBreakMain
		public void render()
		{
			//System.out.println("babaloo");
			//collisionlayer =  (TiledMapTileLayer) map.getLayers().get(0);
			//System.out.println(collisionlayer.getProperties().get("block"));
			//collisionlayer.getCell(2, 2).getTile();
			/*if(collisionlayer.getCell(0, 0).getTile().getTextureRegion().getRegionX()==worldController.ball.getBallBoundsXY("x")
					&&collisionlayer.getCell(0, 0).getTile().getTextureRegion().getRegionY()==worldController.ball.getBallBoundsXY("y"))
			{
				
				System.out.println(collisionlayer.getCell(0, 0).getTile().getTextureRegion().getRegionHeight());
			}
			// search the collision layer
			for(int i=0;i<map.getLayers().getCount();i++)
			{
				
			
			}
			*/
			/*if(collisionlayer.getCell(0,0).getTile().getProperties().get("block")!=null)
			{
				collisionlayer.getCell(0,0);
				System.out.println(collisionlayer.getProperties().g);
			}*/
		
			
			  /* if(Intersector.overlaps(rectangleObject.getRectangle(), worldController.balls.get(0).getBallBounds())) 
			    {
			        // collision happened
			    }*/
			
			
			renderTestObjects();	
			
		}
		//Está no loop do loop BrickBreakMain
		private void renderTestObjects()
		{
				// clear the screen and setup the projection matrix
				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				//camera.update();
				//renderer.setView(camera);
				//renderer.render();
				//SpriteBatch sprite = (SpriteBatch) renderer.getSpriteBatch();
				
				
				
				batch.begin();
					//batch desenha (sprite,x,y), onde x e y sao eixos das coordenadas
					//batch.draw(worldController.ballsprite,worldController.ball.getBallBoundsXY("x"),worldController.ball.getBallBoundsXY("y"));
					//batch.draw(worldController.groundsprite,worldController.ground.getGroundBoundsXY("x"),worldController.ground.getGroundBoundsXY("y"));
					
					
					for(int i=0;i<6;i++)
					{
						for(int j=0;j<6;j++)
						{
						
							if(worldController.balls[i][j]!=null)
							{
								batch.draw(worldController.balls[i][j].getBallSprite(),worldController.balls[i][j].getBallBoundsXY("x"),worldController.balls[i][j].getBallBoundsXY("y"));
							}
						}

					}
					//batch.draw(worldController.ball..getBallSprite(),worldController.ball.getBallBoundsXY("x"),worldController.ball.getBallBoundsXY("y"));	
					
				batch.end();	
				
				
				batch2.begin();
					font.draw(batch2,"Fps:"+ Gdx.graphics.getFramesPerSecond() ,0,570);
					//font.draw(batch2,"Cam x:"+ camera.viewportWidth ,50,500);
					//font.draw(batch2,"Cam y:"+ camera.viewportHeight ,140,500);
					font.draw(batch2,"Height "+Gdx.graphics.getHeight(),50, 570);
					font.draw(batch2,"width: "+Gdx.graphics.getWidth(),140, 570);
					//font.draw(batch2,"Ground x: "+worldController.ground.getGroundBoundsXY("x") ,270, 500);
					//font.draw(batch2,"Ground y: "+worldController.ground.getGroundBoundsXY("y") ,380, 500);
					//font.draw(batch2,"Ground x: "+worldController.ground.getGroundBounds().getPosition(new Vector2()) ,150, 80);
					//font.draw(batch2,"Ground y: "+worldController.ground.getGroundBoundsXY("y") ,380, 20);
					font.draw(batch2,"Pontos: "+Constants.pontos ,0, 530);
					font.draw(batch2,"Mouse x: "+Constants.mousex ,100, 500);
					font.draw(batch2,"Mouse y: "+Constants.mousey ,200, 500);
					font.draw(batch2,"Por Favor Aperte a Tecla Space para iniciar o Jogo" ,0, 400);
					font.draw(batch2,"Time elapsed in seconds = "+ Constants.elepsedTime,0, 550);
					if(Constants.state=="started")
					{
						font.draw(batch2,"JOGO INICIADO" ,40, 380);
					}
					//font.draw(batch2,"Time elapsed in seconds = " + ((-(System.currentTimeMillis() - startTime) / 1000)+200),120, 440);
					//font.draw(batch2,"Ball y: "+worldController.ball.getBallBoundsXY("y") ,380, 480);

					//font.draw(batch2,"x "+collisionlayer.getCell(0, 0).getTile().getTextureRegion().getRegionX(),50, 400);
					//font.draw(batch2,"y: "+collisionlayer.getCell(0, 0).getTile().getTextureRegion().getRegionY(),140, 400);
				batch2.end();
				
		}

		@Override
		public void dispose() 
		{
			//Objects dispose
			 worldController.dispose();
			 //renderer.dispose();
			 //map.dispose();
		}
}
