package com.brickbreak.game;

import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class WorldController implements InputProcessor
{
	//ball
	public Ball ball;
	public Ball ballaux;
	public Sprite ballsprite;
	public Texture balltexture;
	//public Array<Ball> balls = new Array<Ball>();
	
	public Ball[][] balls = new Ball[6][6];
	private HashMap <Integer, Texture> imagens = new HashMap<Integer, Texture>();
	//ground
	//public Ground ground;
	//public Sprite groundsprite;
	//public Texture groundtexture;
	//mouse
	public Mouse mouse;
	//doideira
	public int yi,xi,i,j,n,m;
	public int x0=0,x1=0;
	public int y0=0,y1=0;
	public boolean first=true;
	public int troca;
	public int auxi;
	public int auxj;
	//first = true;
	//Removendo 3
	//public Tuple localdosnulls = new Tuple();
	//public Tuple[] localdosnulls = new Tuple[2];
	public Array<Tuple> localdosnullslinha  = new Array<Tuple>();
	public Array<Tuple> localdosnullscoluna  = new Array<Tuple>();
	public boolean removelinha;
	public boolean inserelinha;
	public boolean andalinha;
	public boolean removecoluna;
	public boolean inserecoluna;
	public boolean andacoluna;
	public int aux;
	public boolean auxl;
	public boolean auxc;

	
	/*
ex:
  	     ______________
  (0,20)|			   |
		|			   |
		|			   |   ---------------------> IDEIA
		|			   |
		|			   |
		|______________|
	(0,0)              (20,0)
	 _________________________________________________________________________________________
	|android:screenOrientation									                              |
	|The orientation of the activity's display on the device.                                 |
	|"landscape" ----> "portrait"								                              |
	|for more:http://developer.android.com/guide/topics/manifest/activity-element.html#screen |
	|_________________________________________________________________________________________|
	
	 _________________________________________________________________________________________
	|Building Games Using the MVC Pattern â€“ Tutorial and Introduction                         |
	|			____________																  |
	|			|controller|																  |
	|			  /      \																	  |
	|			 /	      \																	  |
	|			<	       >																  |
	|		|view|------->|model|          													  |
	|								                                                          |
	|http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/                     |
	|_________________________________________________________________________________________|
	
	 _____________________________________________________________
	|LIBGDX API - Overview				                          |
	|      							                              |
	|for more:http://libgdx.badlogicgames.com/nightlies/docs/api/ |
	|_____________________________________________________________|
	*/
		//constructor
		public WorldController()
		{
			
			init();	
			
		}
		//start the WorldController, good practice to not initialize the class with the Constructor(libgdx order)
		//if we need to reset an object in the game. With this method we save performance
		private void init()
		{
			initTestObjects();
		}
		//game logic, it will be called secveral ties per second.it can aply updates 
		//to the game world according to the fraction of time that has passed since the last rendered time.
		private void initTestObjects() //1vez
		{
			//time
		    Constants.state="notstarted";
			
			
			//REMOVE 3
			localdosnullslinha.add(new Tuple());
			localdosnullslinha.add(new Tuple());
			localdosnullslinha.add(new Tuple());
			localdosnullscoluna.add(new Tuple());
			localdosnullscoluna.add(new Tuple());
			localdosnullscoluna.add(new Tuple());
			removelinha=false;
			inserelinha=false;
			andalinha=false;
			removecoluna=false;		
			inserecoluna=false;	
			andacoluna=false;
			auxl=true;
			auxc=true;
				
			//MOUSE
		    mouse = new Mouse(100,600);

		    	//pode utilizar as duas maneiras
			 	//ballsprite= ball.getBallSprite();	
			 	//balltexture= ball.getBallTexture();
		    xi=315;
		    yi=500;		
			for(i=0;i<6;i++)
		    {
		    	 for(j=0;j<6;j++)
			    {
		    		 
		    		 ball = new Ball(xi,yi);
		    		 //balls.add(ball);
		    		 balls[i][j]=ball;
		    	
		    	xi=xi+100;
			    }
		    	xi=315; 
		    	yi=yi-100;	 
			
		    }
			 	//balls.add(ball);
				//ground = new Ground();
				//groundsprite= ground.getGroundSprite();
				
				//---------------------------------------------
			//System.out.println(balls.size);	
				
		}
		public void update() //loop
		{
			//MOUSE
		    Constants.mousex = (int) mouse.getMouseBoundsXY("x");
		    Constants.mousey = (int) mouse.getMouseBoundsXY("y");
			//time of the game
			if(Constants.state=="started")
			{
				Constants.elepsedTime=((-(System.currentTimeMillis() - Constants.startTime) / 1000)+200);
			
				//Se o tempo acabar fechar a aplicação
				if(Constants.elepsedTime==0)
				{
					dispose();
				}
			}
		//REMOVE3, ANALISA SE TEM 3 NOMES IGUAIS NUMA LINHA
		if(removecoluna==false)
		{	
			outerloop2:
			for(i=0;i<6;i++)
			{	
				for(j=0;j<4;j++)
				{
					if(balls[i][j]!=null)
					{
						if(balls[i][j+1]!=null)
						{
							if(balls[i][j+2]!=null)
							{
								if(balls[i][j].getBallName()==balls[i][j+1].getBallName())
								{
									if(balls[i][j+1].getBallName()==balls[i][j+2].getBallName())
									{		
										if(Constants.state=="started")
										{	
											//localdosnulls
											if(auxl==true)
											{
												andalinha=true;
												localdosnullslinha.get(0).setTuple((int)balls[i][j].getBallBoundsXY("x"), (int)balls[i][j].getBallBoundsXY("y"));
												localdosnullslinha.get(1).setTuple((int)balls[i][j+1].getBallBoundsXY("x"), (int)balls[i][j+1].getBallBoundsXY("y"));
												localdosnullslinha.get(2).setTuple((int)balls[i][j+2].getBallBoundsXY("x"), (int)balls[i][j+2].getBallBoundsXY("y"));
												auxl=false;
												Constants.pontos+=3;
											}			
										}	
										break outerloop2;
									}
								}	
							}
						}
					}
				
				}
			}
		}
			
			if(andalinha==true)
			{
				if(balls[i][j].getBallBoundsXY("x")>20 || balls[i][j].getBallBoundsXY("y")>100)
				{	
					
					if(balls[i][j].getBallBoundsXY("x")>20)
					{
						balls[i][j].setBallBoundsXConstant(-3);	
					}
					if(balls[i][j].getBallBoundsXY("y")>100)
					{
						balls[i][j].setBallBoundsYConstant(-3);
					}
					if(balls[i][j].getBallBoundsXY("y")<100)
					{
						balls[i][j].setBallBoundsYConstant(3);
					}	
					
					if((balls[i][j].getBallBoundsXY("x")>16 && balls[i][j].getBallBoundsXY("x")<24) && (balls[i][j].getBallBoundsXY("y")>96 && balls[i][j].getBallBoundsXY("y")<104))
					{
						balls[i][j].setBallBoundsX(20);
						balls[i][j].setBallBoundsY(100);
						//System.out.println("dando merda ainda");
						//auxl=true;
						//andalinha=false;
					}	
				}
				//----------------------------------------------------------------------------------------------------
				if(balls[i][j+1].getBallBoundsXY("x")>120 || balls[i][j+1].getBallBoundsXY("y")>100)
				{	
					
					if(balls[i][j+1].getBallBoundsXY("x")>120)
					{
						balls[i][j+1].setBallBoundsXConstant(-3);	
					}
					if(balls[i][j+1].getBallBoundsXY("y")>100)
					{
						balls[i][j+1].setBallBoundsYConstant(-3);
					}
					if(balls[i][j+1].getBallBoundsXY("y")<100)
					{
						balls[i][j+1].setBallBoundsYConstant(3);
					}	
					
					if((balls[i][j+1].getBallBoundsXY("x")>116 && balls[i][j+1].getBallBoundsXY("x")<124) && (balls[i][j].getBallBoundsXY("y")>96 && balls[i][j+1].getBallBoundsXY("y")<104))
					{
						balls[i][j+1].setBallBoundsX(120);
						balls[i][j+1].setBallBoundsY(100);
						//System.out.println("dando merda ainda");
						//auxl=true;
						//andalinha=false;
					}	
				}
				//----------------------------------------------------------------------------------------------------
				if(balls[i][j+2].getBallBoundsXY("x")>220 || balls[i][j+2].getBallBoundsXY("y")>100)
				{	
					
					if(balls[i][j+2].getBallBoundsXY("x")>220)
					{
						balls[i][j+2].setBallBoundsXConstant(-3);	
					}
					if(balls[i][j+2].getBallBoundsXY("y")>100)
					{
						balls[i][j+2].setBallBoundsYConstant(-3);
					}
					if(balls[i][j+2].getBallBoundsXY("y")<100)
					{
						balls[i][j+2].setBallBoundsYConstant(3);
					}	
					
					if((balls[i][j+2].getBallBoundsXY("x")>216 && balls[i][j+2].getBallBoundsXY("x")<224) && (balls[i][j+2].getBallBoundsXY("y")>96 && balls[i][j+2].getBallBoundsXY("y")<104))
					{
						balls[i][j+2].setBallBoundsX(220);
						balls[i][j+2].setBallBoundsY(100);
						//System.out.println("dando merda ainda");
						auxl=true;
						andalinha=false;
						removelinha=true;
					}	
				}

			}
			
			
		//REMOVE3
		if(Constants.state=="started")
		{
			if(removelinha==true)
			{
				if(balls[i][j].getBallBoundsXY("x")==20 && balls[i][j].getBallBoundsXY("y")==100)
				{
					if(balls[i][j+1].getBallBoundsXY("x")==120 && balls[i][j+1].getBallBoundsXY("y")==100)
					{
						if(balls[i][j+2].getBallBoundsXY("x")==220 && balls[i][j+2].getBallBoundsXY("y")==100)
						{
							balls[i][j]=null;
							balls[i][j+1]=null;
							balls[i][j+2]=null;
							inserelinha=true;
						}
					}
						
				}
			}
		removelinha=false;	
		}
		
		//INSERE3 ,ANALISA SE TEM NULL NA MATRIZ PRA INSERIR NOVAS GEMAS
		if(inserelinha==true)
		{	
			aux=0;
			for(i=0;i<6;i++)
		    {
		    	 for(j=0;j<6;j++)
			    {	 
		    		 if(balls[i][j]==null)
		    		 {
		    			 ball = new Ball(localdosnullslinha.get(aux).getX(),localdosnullslinha.get(aux).getY());
			    		 //balls.add(ball);
			    		 balls[i][j]=ball;
			    		 aux+=1;
		    		 }
			    }
		    }
		inserelinha=false;
		}	
			

			//------------------------------------------------------------------------------------------------------------
		//REMOVE3, ANALISA SE TEM 3 NOMES IGUAIS NUMA COLUNA
		if(removelinha==false)
		{	
			outerloop :
			for(j=0;j<6;j++)
			{	
				for(i=0;i<4;i++)
				{
					if(balls[i][j]!=null)
					{
						if(balls[i+1][j]!=null)
						{
							if(balls[i+2][j]!=null)
							{
								if(balls[i][j].getBallName()==balls[i+1][j].getBallName())
								{
									if(balls[i+1][j].getBallName()==balls[i+2][j].getBallName())
									{
										if(Constants.state=="started")
										{
											if(auxc==true)
											{
												andacoluna=true;
												//localdosnulls
											
												localdosnullscoluna.get(0).setTuple((int)balls[i][j].getBallBoundsXY("x"), (int)balls[i][j].getBallBoundsXY("y"));
												localdosnullscoluna.get(1).setTuple((int)balls[i+1][j].getBallBoundsXY("x"), (int)balls[i+1][j].getBallBoundsXY("y"));
												localdosnullscoluna.get(2).setTuple((int)balls[i+2][j].getBallBoundsXY("x"), (int)balls[i+2][j].getBallBoundsXY("y"));
												auxc=false;
												System.out.println("set localdosnullscoluna");
												Constants.pontos+=3;	
											
											}	
										}
									break outerloop;	
									}
								}	
							}
						}
					}
				
				}
			}
		}
		
		
		
		if(andacoluna==true)
		{
			if(balls[i][j].getBallBoundsXY("x")>20 || balls[i][j].getBallBoundsXY("y")>100)
			{	
				
				if(balls[i][j].getBallBoundsXY("x")>20)
				{
					balls[i][j].setBallBoundsXConstant(-3);	
				}
				if(balls[i][j].getBallBoundsXY("y")>100)
				{
					balls[i][j].setBallBoundsYConstant(-3);
				}
				if(balls[i][j].getBallBoundsXY("y")<100)
				{
					balls[i][j].setBallBoundsYConstant(3);
				}	
				
				if((balls[i][j].getBallBoundsXY("x")>16 && balls[i][j].getBallBoundsXY("x")<24) && (balls[i][j].getBallBoundsXY("y")>96 && balls[i][j].getBallBoundsXY("y")<104))
				{
					balls[i][j].setBallBoundsX(20);
					balls[i][j].setBallBoundsY(100);
					//System.out.println("dando merda ainda");
					//auxc=true;
					//andacoluna=false;
				}	
			}
			//----------------------------------------------------------------------------------------------------
			if(balls[i+1][j].getBallBoundsXY("x")>20 || balls[i+1][j].getBallBoundsXY("y")>200)
			{	
				
				if(balls[i+1][j].getBallBoundsXY("x")>20)
				{
					balls[i+1][j].setBallBoundsXConstant(-3);	
				}
				if(balls[i+1][j].getBallBoundsXY("y")>200)
				{
					balls[i+1][j].setBallBoundsYConstant(-3);
				}
				if(balls[i+1][j].getBallBoundsXY("y")<200)
				{
					balls[i+1][j].setBallBoundsYConstant(3);
				}	
				
				if((balls[i+1][j].getBallBoundsXY("x")>16 && balls[i+1][j].getBallBoundsXY("x")<24) && (balls[i+1][j].getBallBoundsXY("y")>196 && balls[i+1][j].getBallBoundsXY("y")<204))
				{
					balls[i+1][j].setBallBoundsX(20);
					balls[i+1][j].setBallBoundsY(200);
					//System.out.println("dando merda ainda");
					//auxc=true;
					//andacoluna=false;
				}	
			}
			//----------------------------------------------------------------------------------------------------
			if(balls[i+2][j].getBallBoundsXY("x")>20 || balls[i][j+2].getBallBoundsXY("y")>300)
			{	
				
				if(balls[i+2][j].getBallBoundsXY("x")>20)
				{
					balls[i+2][j].setBallBoundsXConstant(-3);	
				}
				if(balls[i+2][j].getBallBoundsXY("y")>300)
				{
					balls[i+2][j].setBallBoundsYConstant(-3);
				}
				if(balls[i+2][j].getBallBoundsXY("y")<300)
				{
					balls[i+2][j].setBallBoundsYConstant(3);
				}	
				
				if((balls[i+2][j].getBallBoundsXY("x")>16 && balls[i+2][j].getBallBoundsXY("x")<24) && (balls[i+2][j].getBallBoundsXY("y")>296 && balls[i+2][j].getBallBoundsXY("y")<304))
				{
					balls[i+2][j].setBallBoundsX(20);
					balls[i+2][j].setBallBoundsY(300);
					//System.out.println("dando merda ainda");
					auxc=true;
					andacoluna=false;
					removecoluna=true;
				}	
			}

		}
		
		
			//REMOVE3
			if(Constants.state=="started")
			{
				if(removecoluna==true)
				{
					if(balls[i][j].getBallBoundsXY("x")==20 && balls[i][j].getBallBoundsXY("y")==100)
					{
						if(balls[i+1][j].getBallBoundsXY("x")==20 && balls[i+1][j].getBallBoundsXY("y")==200)
						{
							if(balls[i+2][j].getBallBoundsXY("x")==20 && balls[i+2][j].getBallBoundsXY("y")==300)
							{
								System.out.println("coluna removida");
								balls[i][j]=null;
								balls[i+1][j]=null;
								balls[i+2][j]=null;
								inserecoluna=true;
							}
						}
					}	
				}
			removecoluna=false;	
			}
			
			
			//INSERE3 ,ANALISA SE TEM NULL NA MATRIZ PRA INSERIR NOVAS GEMAS
			if(inserecoluna==true)
			{	
				aux=0;
				for(i=0;i<6;i++)
			    {
			    	 for(j=0;j<6;j++)
				    {	 
			    		 if(balls[i][j]==null)
			    		 {
			    			 ball = new Ball(localdosnullscoluna.get(aux).getX(),localdosnullscoluna.get(aux).getY());
				    		 //balls.add(ball);
				    		 balls[i][j]=ball;
				    		 aux+=1;
			    		 }
				    }
			    }
			inserecoluna=false;
			}
			
			//------------------------------------------------------------------------------------------------------------
			
			//System.out.println(Constants.pontos);
			/*
			 if(Intersector.overlaps(ball.getBallBounds(),ground.getGroundBounds()))
			 {
				    
				    Constants.touch=4;
					Constants.velx=Constants.velx;
					Constants.vely=-Constants.vely;
					System.out.println("ai meu rim");
					
					//TENHO DE PARAR DE ANDAR COM O GROUND pular -6 em y , para nao bugar
					//ground.setGroundBoundsY(-6);		
			 }
			
			 		 		 
			 //LADO ESQUERDO DO GROUND
			 //ground(x1,y1)---------(x2,y2)    //  ball(x3,y3)---------(x4,y4)
			 //NÃƒO PODE SER RETAS PARALELAS (ERRO), POIS HÃ� VARIOS PONTOS DE INTERSECÃ‡ÃƒO
			 if(Intersector.intersectSegments(ground.getGroundBoundsXY("x")-20,ground.getGroundBoundsXY("y")+20,
					 ground.getGroundBoundsXY("x")-10,ground.getGroundBoundsXY("y"),
					 ball.getBallBoundsXY("x")+100, ball.getBallBoundsXY("y")+100,
					 ball.getBallBoundsXY("x")+100, ball.getBallBoundsXY("y"), new Vector2()))
			 {
				 System.out.println("BATEU NA ESQUERDA");
				 Constants.touch=2;
				 Constants.velx=-Constants.velx;
				 Constants.vely=Constants.vely;
				//TENHO DE PARAR DE ANDAR COM O GROUND pular 6 em X , para nao bugar
				 ground.setGroundBoundsX(6);
			 }
			 
			 //LADO DIREITO DO GROUND
			 //ground(x1,y1)---------(x2,y2)    //  ball(x3,y3)---------(x4,y4)
			 //NÃƒO PODE SER RETAS PARALELAS (ERRO), POIS HÃ� VARIOS PONTOS DE INTERSECÃ‡ÃƒO
			 if(Intersector.intersectSegments(ground.getGroundBoundsXY("x")+170,ground.getGroundBoundsXY("y")+20,
					 ground.getGroundBoundsXY("x")+160,ground.getGroundBoundsXY("y"),
					 ball.getBallBoundsXY("x"), ball.getBallBoundsXY("y")+100,
					 ball.getBallBoundsXY("x"), ball.getBallBoundsXY("y"), new Vector2()))
			 {
				 System.out.println("BATEU NA DIREITA");
				 Constants.touch=2;
				 Constants.velx=-Constants.velx;
				 Constants.vely=Constants.vely;
				//TENHO DE PARAR DE ANDAR COM O GROUND pular 6 em X , para nao bugar
				 ground.setGroundBoundsX(-6);
			 }
			*/	    
				
			
			
			//InteraÃ§Ã£o com o usuÃ¡rio
			handleDebugInput();
		}
		private void handleDebugInput()
		{
			//processor = new MyInputProcessor();	         //I DONT KNOW HOW TO INSERT MORE THAN 2 SETINPUTPROCESSOR
			//Gdx.input.setInputProcessor(processor);      //I DONT KNOW HOW TO INSERT MORE THAN 2 SETINPUTPROCESSOR
			//--------------------------------------------------------------------
			//CameraInputController processortest = new CameraInputController();
			//Gdx.input.setInputProcessor(processortest);
			//--------------------------------------------------------------------
			
			
			//float deltaTime=0.5f;
			if(Gdx.app.getType()==ApplicationType.Desktop)
			{
				 		
				/*if(Gdx.input.isKeyPressed(Keys.A))
				{
					//Faz o acesso a minha variÃ¡vel ground e mexe no seu eixo x (seto minha velocidade a esquerda)
					ground.setGroundBoundsX(-5);					
					
				}
				/*if(keyDown(Keys.SPACE)==true)
				{
					//Faz o acesso a minha variÃ¡vel ground e mexe no seu eixo x (seto minha velocidade a esquerda)
					x0=(int)(balls.get(0).getBallBoundsXY("x"));
					x1=(int)(balls.get(1).getBallBoundsXY("x"));
					balls.get(0).setBallBoundsX(x1);	
					balls.get(1).setBallBoundsX(x0);
					
					
				}
				if(Gdx.input.isKeyPressed(Keys.D))
				{
					//Faz o acesso a minha variÃ¡vel ground e mexe no seu eixo x (seto minha velocidade a direita)
					ground.setGroundBoundsX(5);				
					
				}
				if(Gdx.input.isKeyPressed(Keys.ENTER))
				{
					//ComeÃ§a o Jogo
					Constants.playball=true;
				}
				if(Gdx.input.isKeyPressed(Keys.S))
				{
					//ground.setGroundBoundsY(-5);
				}
				*/
				
				if(Gdx.input.isKeyPressed(Keys.W))
				{
					balls[i][j].setBallBoundsXConstant(-1);
				}
				
			}
			if(Gdx.app.getType()==ApplicationType.Android)
			{
				/*
				if(Gdx.input.isTouched())
				{
					//pego a coordenada x da minha sprite
					if(Gdx.input.getX()-100==ground.getGroundBoundsXY("x")+75)
					{
						
					}
					else
					{
						ground.setGroundBoundsWithTouch(Gdx.input.getX()-100);
					}
					
			
					//quando a tela for tocada posso ativar o update da bola
					if(Constants.contplayandroid==0)
					{
						Constants.contplayandroid++;
						Constants.playball=true;
					}
					
				}
				*/					
				
			}
		}
		public void dispose()
		{
			Gdx.app.exit();
		}
		@Override
		public boolean keyDown(int keycode) {
			if(keycode == Keys.ESCAPE)
			{
				dispose();
				//x0=(int)(balls.get(0).getBallBoundsXY("x"));
				//x1=(int)(balls.get(1).getBallBoundsXY("x"));
				//balls.get(0).setBallBoundsX(x1);	
				//balls.get(1).setBallBoundsX(x0);
				//System.out.println("x0: "+x0);
				//System.out.println("x1: "+x1);
			}
			if(keycode == Keys.SPACE)
			{
				Constants.startTime = System.currentTimeMillis();
				Constants.state="started";
				//x0=(int)(balls.get(0).getBallBoundsXY("x"));
				//x1=(int)(balls.get(1).getBallBoundsXY("x"));
				//balls.get(0).setBallBoundsX(x1);	
				//balls.get(1).setBallBoundsX(x0);
				//System.out.println("x0: "+x0);
				//System.out.println("x1: "+x1);
			}
			
			return false;
		}
		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean keyTyped(char character) {
			//balls[i][j].setBallBoundsXConstant(-2);
			System.out.println("oi15166511651651");
			return false;
		}
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,	int button) 
		{
			if(Constants.state=="started")
			{
				System.out.println("oi");
				for(i=0;i<6;i++)
				{
					for(j=0;j<6;j++)
					{
						if(balls[i][j]!=null)
						{
							 if(Intersector.overlaps(balls[i][j].getBallBounds(),mouse.getMouseBounds()))
							 {
								    
							 
								if(first==true)
								{
									mouse.setMouseBoundsX(screenX);
									mouse.setMouseBoundsY(800-screenY);
									//System.out.println("primeiro");			
									troca=troca+1;
									first=false;
									auxi=i;
									auxj=j;
								}
								else
								{
									
									troca=troca+1;
									first=true;
								}
								if(troca==2)
								{
									//so pega em cima/baixo , esquerda/direita
									if(i==auxi+1 || i==auxi-1  ||j==auxj+1 || j==auxj-1)
									{
										//nao deixa pegar na diagonal
										if(i==auxi ||j==auxj)
										{
											System.out.println("i: "+i+" "+"auxi: "+auxi);
												//MUDANDO NA MATRIZ REAL
												ballaux=balls[i][j];
												balls[i][j]=balls[auxi][auxj];
												balls[auxi][auxj]=ballaux;
												
												//MUDANDO NA ,MATRIZ TELA
												//alterando x
												x0=(int)(balls[i][j].getBallBoundsXY("x"));
												x1=(int)(balls[auxi][auxj].getBallBoundsXY("x"));
												balls[i][j].setBallBoundsX(x1);	
												balls[auxi][auxj].setBallBoundsX(x0);
												//alterando y
												y0=(int)(balls[i][j].getBallBoundsXY("y"));
												y1=(int)(balls[auxi][auxj].getBallBoundsXY("y"));
												balls[i][j].setBallBoundsY(y1);	
												balls[auxi][auxj].setBallBoundsY(y0);
												
												System.out.println("trocou");
												//System.out.println("x0: "+x0);
												//System.out.println("x1: "+x1);
											if(!comb())
											{
												System.out.println("i: "+i+" "+"auxi: "+auxi);	
												//MUDANDO NA MATRIZ REAL
												//ballaux=null;
												ballaux=balls[i][j];
												balls[i][j]=balls[auxi][auxj];
												balls[auxi][auxj]=ballaux;
												//MUDANDO NA ,MATRIZ TELA
												//alterando x
												x0=(int)(balls[i][j].getBallBoundsXY("x"));
												x1=(int)(balls[auxi][auxj].getBallBoundsXY("x"));
												balls[i][j].setBallBoundsX(x1);	
												balls[auxi][auxj].setBallBoundsX(x0);
												//alterando y
												y0=(int)(balls[i][j].getBallBoundsXY("y"));
												y1=(int)(balls[auxi][auxj].getBallBoundsXY("y"));
												balls[i][j].setBallBoundsY(y1);	
												balls[auxi][auxj].setBallBoundsY(y0);
												
												Constants.pontos= Constants.pontos -2;
												System.out.println("volta vagabundo");
												//System.out.println("x0: "+x0);
												//System.out.println("x1: "+x1);
		
											}
										}
									}
								troca=0;	
								}
							 }
						 }
					}
				}
			}	
			
			return false;
		}
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			
			mouse.setMouseBoundsX(screenX);
			mouse.setMouseBoundsY(580-screenY);
			//System.out.println(screenX+" : "+ (800-screenY));			
	
			return false;
		}
		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean comb()
		{
				for(n=0;n<6;n++)
				{	
					for(m=0;m<4;m++)
					{
						if(balls[n][m]!=null)
						{
							if(balls[n][m+1]!=null)
							{
								if(balls[n][m+2]!=null)
								{
									if(balls[n][m].getBallName()==balls[n][m+1].getBallName())
									{
										if(balls[n][m+1].getBallName()==balls[n][m+2].getBallName())
										{
											System.out.println("true");	
											return true;
											
										}
									}	
								}
							}
						}
					
					}
				}

				//------------------------------------------------------------------------------------------------------------
				
				for(m=0;m<6;m++)
				{	
					for(n=0;n<4;n++)
					{
						if(balls[n][m]!=null)
						{
							if(balls[n+1][m]!=null)
							{
								if(balls[n+2][m]!=null)
								{
									if(balls[n][m].getBallName()==balls[n+1][m].getBallName())
									{
										if(balls[n+1][m].getBallName()==balls[n+2][m].getBallName())
										{
											System.out.println("true");	
											return true;
										}
									}	
								}
							}
						}
					
					}
				}
				System.out.println("false");	
		return false;		
		}

}
