package com.mygdx.game;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;


class main extends InputAdapter implements ApplicationListener {
	private float deltaTime = 0;
	private float distance = 0;
	OrthographicCamera camera;


	private Stage stage;





	@Override
	public void create () {

		//Background
		stage = new Stage(new ScreenViewport());
		int Help_Guides = 12;
		int row_height = Gdx.graphics.getWidth() / 12;
		int col_width = Gdx.graphics.getWidth() / 12;
		Texture texture = new Texture((Gdx.files.internal("Background/background1.png")));
		Image background = new Image(texture);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setPosition((float) Gdx.graphics.getWidth()-background.getWidth(), (float) (Gdx.graphics.getHeight()-background.getHeight()));
		stage.addActor(background);
		//Display
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Labels
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/pixelfont.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 30;
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
		Pixelfont.setSize(Gdx.graphics.getWidth()/Help_Guides*5,row_height);
		Pixelfont.setPosition(Gdx.graphics.getWidth()/2 - 125,Gdx.graphics.getHeight()-100);
		stage.addActor(Pixelfont);
		//Button

		Skin Button = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

		Button button2 = new TextButton("Text Button",Button,"small");
		button2.setSize(col_width*4,row_height);
		button2.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);


		button2.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Pixelfont.setText("Press a Button");
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Pixelfont.setText("Pressed Text Button");
				return true;
			}
		});
		stage.addActor(button2);



	}

	@Override
	public void resize(int width, int height) {

	}

	// @Override
	//public void resize(int width, int height) {

	//}
	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 1, 1, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		deltaTime = Gdx.graphics.getDeltaTime();
		distance = distance + (20*deltaTime);

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

