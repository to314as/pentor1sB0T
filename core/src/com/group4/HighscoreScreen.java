package com.group4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

/**
 * Created by Tobias on 27/11/2017.
 */

public class HighscoreScreen implements Screen{
    private final TetrisGame game;
    private final ArrayList<String> names;
    private final ArrayList<Integer> scores;
    private Stage stage;
    private Label text;
    private Label score;
    private Label rank;
    private Texture background;
    private TextButton backButton;
    private Skin skin;

    /** HighscoreScreen constructor. Can be deployed to create an Highscorescreen object which is used
     * to display the Names and scores of all Games.
     *
     * @param game
     * @param names
     * @param scores
     */
    public HighscoreScreen(TetrisGame game, ArrayList<String> names, ArrayList<Integer> scores) {
        this.game = game;
        this.names=names;
        this.scores=scores;
    }

    /** create a layout for the TextButton*/
    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/2,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
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

    /**main method - load grapohics, inact layout*/
    @Override
    public void show() {
        stage = new Stage();
        background = new Texture(Gdx.files.internal("highscore_background.png"));
        Gdx.input.setInputProcessor(this.stage);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        style.fontColor = new Color(Color.WHITE);
        final Table scrollTable = new Table();
        style.font.getData().setScale(2f);
        for(int i=0;i<scores.size();i++){
            rank=new Label(Integer.toString(i+1)+".",style);
            text = new Label(names.get(i), style);
            score=new Label(Integer.toString(scores.get(i)),style);
            rank.setAlignment(Align.left);
            text.setAlignment(Align.left);
            score.setAlignment(Align.right);
            scrollTable.add(rank).padRight(40f);;
            scrollTable.add(text).padRight(80f);;
            scrollTable.add(score);
            scrollTable.row();}


        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();
        stage.addActor(table);
        createBasicSkin();
        backButton = new TextButton("BACK", skin); // Use the initialized skin
        backButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , Gdx.graphics.getHeight()/100);
        backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new StartScreen(game));
            }
        });
        stage.addActor(backButton);

    }

    /** display/refresh screen at 60 fps*/
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();

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

    /** dispose Graphics*/
    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
