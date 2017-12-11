package com.group4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import static com.group4.Constants.COLS;
import static com.group4.Constants.PADDING;
import static com.group4.Constants.ROWS;
import static com.group4.Constants.SIDE;
import static com.group4.Constants.WIDTH;

/**
 * Created by Tobias on 28/11/2017.
 */

public class GameOverScreen extends GameLogic implements Screen{
        private final TetrisGame game;
        private Stage stage;
        private Label text;
        private int score;
        private Label scoreLabel;
        private Label rank;
        private Texture background;
        private TextButton backButton;
        private Skin skin;
    private TextField txtUsername;
    private SpriteBatch batch;
    private BitmapFont font;
    private TextButton okButton;

    public GameOverScreen(TetrisGame game) {
            this.game = game;
        }

        private void createBasicSkin(){
            //Create a font
            BitmapFont font = new BitmapFont();
            skin = new Skin();
            skin.add("default", font);

            //Create a texture
            Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/2,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            skin.add("background",new Texture(pixmap));

            //Create a button style
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.up = skin.newDrawable("background", Color.BLACK);
            textButtonStyle.down = skin.newDrawable("background", Color.WHITE);
            textButtonStyle.checked = skin.newDrawable("background", Color.BLACK);
            textButtonStyle.over = skin.newDrawable("background", Color.BLUE);
            textButtonStyle.font = skin.getFont("default");
            font.getData().setScale(2f);
            skin.add("default", textButtonStyle);

        }

        @Override
        public void show() {
            stage = new Stage();
            batch = new SpriteBatch();
            score=super.getScore();
            background = new Texture(Gdx.files.internal("highscore_background.png"));
            Gdx.input.setInputProcessor(this.stage);
            createBasicSkin();
            backButton = new TextButton("BACK", skin); // Use the initialized skin
            backButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , 1*Gdx.graphics.getHeight()/20);
            backButton.addListener(new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                    GameOverScreen.super.resetScore();
                    game.setScreen(new StartScreen(game));
                }
            });
            stage.addActor(backButton);
            okButton = new TextButton("OK", skin); // Use the initialized skin
            okButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , 4*Gdx.graphics.getHeight()/20);
            okButton.addListener(new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                    String username = txtUsername.getText();
                    HighScore highscore=new HighScore();
                    highscore.add(score,username);
                    GameOverScreen.super.resetScore();
                    game.setScreen(new HighscoreScreen(game,highscore.getNames(),highscore.getPoints()));
                }
            });
            stage.addActor(okButton);
            font = new BitmapFont();
            TextField.TextFieldStyle styleField = new TextField.TextFieldStyle();
            styleField.font = new BitmapFont();
            styleField.fontColor = new Color(Color.WHITE);
            styleField.background = skin.newDrawable("background",Color.BLACK);
            styleField.font.getData().setScale(2f);
            skin.add("default", styleField);
            txtUsername = new TextField("Enter your Name...", styleField);
            txtUsername.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , Gdx.graphics.getHeight()/2);
            txtUsername.setSize(500, 100);
            txtUsername.addListener(new FocusListener() {
                                        public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                                            if (focused == true) {
                                                txtUsername.setText("");
                                            } else if (focused == false) {
                                                txtUsername.setText("Enter your Name...");
                                            }
                                        }
            });
            stage.addActor(txtUsername);

        }

        @Override
        public void render(float delta) {
            int height = Gdx.graphics.getHeight();
            int width=COLS*100*Gdx.graphics.getWidth()/(WIDTH+SIDE);
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act();
            stage.getBatch().begin();
            stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
            stage.draw();
            batch.begin();
            font.setColor(Color.WHITE);
            font.getData().setScale(4f);
            font.draw(batch, "GAME OVER", width -50,(ROWS-2)*(height / ROWS));
            font.getData().setScale(2f);
            font.draw(batch, "YOURE SCORE", width,(ROWS-4)*(height / ROWS));
            font.draw(batch, super.toString(score), width+PADDING,(ROWS-4)*(height / ROWS)-50);
            batch.end();
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            stage.dispose();
            background.dispose();
            skin.dispose();
        }
    }
