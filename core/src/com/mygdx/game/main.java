package com.mygdx.game;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import javafx.scene.layout.Background;


class main extends InputAdapter implements ApplicationListener {
	private float deltaTime = 0;
	private float distance = 0;
	OrthographicCamera camera;


	private Stage stage;
	private Label outputLabel;
	private boolean hover;

	Texture background;

	private int scrollX;
	SpriteBatch batch;
	Sprite sprite;

	ParallaxLayer[] layers;

	@Override
	public void create () {
		//Background
		stage = new Stage(new ScreenViewport());
		int Help_Guides = 12;
		int row_height = Gdx.graphics.getWidth() / 12;
		int col_width = Gdx.graphics.getWidth() / 12;
		Gdx.input.setInputProcessor(stage);
		background = new Texture((Gdx.files.internal("Background/background1.png")));
		batch = new SpriteBatch();
		//Display
		camera = new OrthographicCamera(background.getWidth(), background.getHeight());

		layers = new ParallaxLayer[8];

		layers[0] = new ParallaxLayer(background, 0.1f, true, false);

		layers[1] = new ParallaxLayer(new Texture("Background/far_mountains.png"), 0.6f, true, true);
		layers[2] = new ParallaxLayer(new Texture("Background/grassy_mountains.png"), 0.6f, true, false);
		layers[3] = new ParallaxLayer(new Texture("Background/clouds_mid_t.png"), 0.6f, true, false);
		layers[4] = new ParallaxLayer(new Texture("Background/clouds_mid.png"), 0.6f, true, false);
		layers[5] = new ParallaxLayer(new Texture("Background/hill.png"), 0.6f, true, false);
		layers[6] = new ParallaxLayer(new Texture("Background/clouds_front_t.png"), 0.6f, true, false);
		layers[7] = new ParallaxLayer(new Texture("Background/clouds_front.png"), 0.6f, true, false);








		for (ParallaxLayer layer : layers) {
			layer.setCamera(camera);
		}
		//Labels
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/pixelfont.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		parameter.borderWidth = 1;
		parameter.color = Color.WHITE;
		parameter.shadowOffsetX = 3;
		parameter.shadowOffsetY = 3;
		parameter.shadowColor = new Color(0, 0.5f, 5, 0.75f);
		BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
		generator.dispose();

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font24;

		Label Pixelfont = new Label("Summit Battle",labelStyle);
		Pixelfont.setSize((float) Gdx.graphics.getWidth() /Help_Guides*5,row_height);
		Pixelfont.setPosition((float) Gdx.graphics.getWidth() /2 - 125,Gdx.graphics.getHeight()-100);
		stage.addActor(Pixelfont);

		//Button
		Skin mySkin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));
		Button FindBattle = new TextButton("Find a battle", mySkin);
		FindBattle.setSize(col_width * 4, row_height);
		FindBattle.setPosition(10, Gdx.graphics.getHeight()-200);
		FindBattle.addListener(new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {



            }
			});
		stage.addActor(FindBattle);

		Button Settings = new TextButton("        Settings", mySkin);
		Settings.setSize(col_width*2 , row_height);
		Settings.setPosition(10, Gdx.graphics.getHeight()-300);

		Settings.addListener(new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			}
		});
		stage.addActor(Settings);

		Button Character = new TextButton("         Change Character", mySkin);
		Character.setSize(col_width * 4, row_height);
		Character.setPosition(10, Gdx.graphics.getHeight()-400);
		Character.addListener(new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hover = true;
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				hover = false;


			}
		});
		stage.addActor(Character);







	}








	@Override
	public void resize(int width, int height) {

	}

	// @Override
	//public void resize(int width, int height) {

	//}
	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		float delta = distance + (20*deltaTime);


		Gdx.gl.glClearColor(255, 1, 1, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int speed = 100;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y -= speed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y += speed * Gdx.graphics.getDeltaTime();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (ParallaxLayer layer : layers) {
			layer.render(batch);
		}
		batch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}}

	//@Override
	//public void pause() {

	//}

	//@Override
	//public void resume() {

	//}

	//@Override
	//public void dispose() {

	//}

