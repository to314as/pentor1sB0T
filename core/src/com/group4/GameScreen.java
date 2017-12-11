package com.group4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Arrays;

import static com.group4.Constants.COLS;
import static com.group4.Constants.MARGIN;
import static com.group4.Constants.ROWS;
import static com.group4.Constants.TIMESPAN_SUPERFAST;

/**
 * Created by Tobias on 08/11/2017.
 */

public class GameScreen extends GameLogic implements Screen {
    public static TetrisGame game = null;
    ShapeRenderer renderer;

    private int[][] board=new int[ROWS][COLS];
    private long time;
    private Texture background;
    private SpriteBatch batch;
    private Texture aimBlock;
    private int[] next;
    private Texture block1;
    private Stage stage;
    private BitmapFont font;
    private TextButton newGameButton;
    private Skin skin;
    private Texture score1;
    private int[][] clumps;
    private Texture block2;
    private Texture block3;
    private Texture block4;
    private Texture block5;
    private Texture block6;
    private Texture block7;
    private Texture block8;
    private Texture block9;
    private Texture block10;
    private Texture block11;
    private Texture block12;
    private HighScore highscore;
    private FitViewport viewport;
    private float height;
    private float width;
    private TextButton pauseButton;
    private Texture pauseGraphic;
    private GeneticBot bot;

    public GameScreen(TetrisGame game) {
        this.game = game;
    }
    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

    @Override
    public void show() {
        height = ROWS*91f;
        width=COLS*91f;
        viewport = new FitViewport(10f, 10f);
        board=super.getBoard();
        clumps=super.getClumps();
        background = new Texture(Gdx.files.internal("tetris_bg_main"+COLS+".png"));
        block1 = new Texture(Gdx.files.internal("tetris_block1.png"));
        block2 = new Texture(Gdx.files.internal("tetris_block2.png"));
        block3 = new Texture(Gdx.files.internal("tetris_block3.png"));
        block4 = new Texture(Gdx.files.internal("tetris_block4.png"));
        block5 = new Texture(Gdx.files.internal("tetris_block5.png"));
        block6 = new Texture(Gdx.files.internal("tetris_block6.png"));
        block7 = new Texture(Gdx.files.internal("tetris_block7.png"));
        block8 = new Texture(Gdx.files.internal("tetris_block8.png"));
        block9 = new Texture(Gdx.files.internal("tetris_block9.png"));
        block10 = new Texture(Gdx.files.internal("tetris_block10.png"));
        block11 = new Texture(Gdx.files.internal("tetris_block11.png"));
        block12 = new Texture(Gdx.files.internal("tetris_block12.png"));
        aimBlock = new Texture(Gdx.files.internal("aim_block.png"));
        pauseGraphic =new Texture(Gdx.files.internal("pause.png"));
        //aimBlock = new Texture(Gdx.files.internal("tetris_block_sinter.png")); christmas mode
        score1=new Texture(Gdx.files.internal("score_1.png"));
        highscore= new HighScore();
        batch = new SpriteBatch();
        time=System.currentTimeMillis();
        renderer= new ShapeRenderer();
        font = new BitmapFont();
        createBasicSkin();
        stage = new Stage();
        newGameButton = new TextButton("New game", skin); // Use the initialized skin
        newGameButton.setPosition(6*Gdx.graphics.getWidth()/10, 3*Gdx.graphics.getHeight()/20);
        newGameButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(newGameButton);
        pauseButton = new TextButton("Pause", skin); // Use the initialized skin
        pauseButton.setPosition(6*Gdx.graphics.getWidth()/10, 1*Gdx.graphics.getHeight()/20);
        pauseButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(pauseButton);
        bot=new GeneticBot(game);
        if(bot.getPoolCount()==0)
            bot.createPool();
        super.init();
        super.setRun(true);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        aimBlock.dispose();
        block1.dispose();
        block2.dispose();
        block3.dispose();
        block4.dispose();
        block5.dispose();
        block6.dispose();
        block7.dispose();
        block8.dispose();
        block9.dispose();
        block10.dispose();
        block11.dispose();
        block12.dispose();
        pauseGraphic.dispose();
        background.dispose();
        batch.dispose();
        score1.dispose();
        font.dispose();
        renderer.dispose();
    }


    @Override
    public void render(float sth) {
        if(!super.getPause()){
            board = super.getBoard();
            if (action()) {
                viewport.apply();
                renderer.setProjectionMatrix(viewport.getCamera().combined);
                Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.draw(background, 0, 0, width, height);
                batch.end();
                drawShape(height, width);
                drawNext(height, width);
                drawText(height, width);
                if (super.getFullLines() > 0) {
                    batch.begin();
                    batch.draw(score1, width / 2 - 3 / 2 * (width / COLS), height / 2 - (height / ROWS), 3 * (width / COLS), 2 * (height / ROWS));
                    batch.end();
                }
                stage.act();
                stage.draw();
            } else {
                HighScore highscore=new HighScore();
                highscore.add(super.getScore(),"BOT");
                bot.evolve();
                super.reset();
            }
        }
        else{
            viewport.apply();
            renderer.setProjectionMatrix(viewport.getCamera().combined);
            Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(background, 0, 0, width, height);
            batch.draw(pauseGraphic, 0, height/2, width, 3*(height / ROWS));
            batch.end();
            drawNext(height, width);
            drawText(height, width);
            stage.act();
            stage.draw();
        }
    }

    public boolean action() {
        if (System.currentTimeMillis() - time >= TIMESPAN_SUPERFAST) {
            if (!super.fall()) {
                super.checkFullLines();
                if (!super.init()){
                    return false;
                }
            }
            time = System.currentTimeMillis();
        }
        return true;
    }

    private void drawText(float height,float width) {
        batch.begin();
        font.setColor(Color.BLACK);
        font.getData().setScale(2f);
        font.draw(batch, "Score", Math.round(1.5*width),(ROWS-10)*(height / ROWS)-MARGIN);
        font.getData().setScale(2f);
        font.draw(batch, super.toString(getScore()), Math.round(1.5*width),(ROWS-10)*(height / ROWS)-6*MARGIN);
        batch.end();
        batch.begin();
        font.draw(batch, "Highscore", Math.round(1.5*width),(ROWS-9)*(height / ROWS));
        font.draw(batch, super.toString(highscore.TopScore()), Math.round(1.5*width),(ROWS-9)*(height / ROWS)-5*MARGIN);
        batch.end();
        batch.begin();
        font.draw(batch, "Games: "+super.toString(bot.getGameCount()), Math.round(1.5*width),(ROWS-6)*(height / ROWS));
        font.draw(batch, "Chromoson: "+super.toString(bot.getChromosome()), Math.round(1.5*width),(ROWS-7)*(height / ROWS));
        font.draw(batch, "Updates: "+super.toString(bot.getUpdates()), Math.round(1.5*width),(ROWS-8)*(height / ROWS));
        font.getData().setScale(0.6f);
        font.draw(batch, Arrays.deepToString(bot.getWeights(bot.getChromosome())), Math.round(1.5*width),(ROWS-8)*(height / ROWS)-5*MARGIN);
        batch.end();
    }

    private void drawNext(float height,float width) {
        next=super.displayNext();
        batch.begin();
        switch (next[0]) {
            case 1:  batch.draw(block1,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block1,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS)); //bright green
                }
                break;
            case 2:  batch.draw(block2,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block2,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS)); //purple
                }
                break;
            case 3:  batch.draw(block3,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block3,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS)); //orange
                }
                break;
            case 4:  batch.draw(block4,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block4,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS)); //yellow
                }
                break;
            case 5:  batch.draw(block5,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block5,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS)); //dark green
                }
                break;
            case 6:  batch.draw(block6,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block6,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//olive green
                }
                break;
            case 7:  batch.draw(block7,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block7,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//dark blue
                }
                break;
            case 8:  batch.draw(block8,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block8,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//dark red
                }
                break;
            case 9:  batch.draw(block9,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block9,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//moss green
                }
                break;
            case 10: batch.draw(block10,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block10,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//lilac
                }
                break;
            case 11: batch.draw(block11,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block11,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//brown
                }
                break;
            case 12: batch.draw(block12,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block12,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//gold
                }
                break;
            default: batch.draw(block1,Math.round(1.5*width), (ROWS-2)*(height / ROWS), (width / COLS), (height / ROWS));
                for(int i=1;i<next.length;i+=2){
                    batch.draw(block1,Math.round(1.5*width)+next[i+1] * (width / COLS), (ROWS-2-next[i])*(height / ROWS), (width / COLS), (height / ROWS));//bright green default
                }
                break;
        }
        batch.end();
    }

    private void drawShape(float height,float width) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] > 0) {
                    batch.begin();
                    switch (board[i][j]) {
                        case 1:  batch.draw(block1,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 2:  batch.draw(block2,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 3:  batch.draw(block3,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 4:  batch.draw(block4,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 5:  batch.draw(block5,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 6:  batch.draw(block6,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 7:  batch.draw(block7,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 8:  batch.draw(block8,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 9:  batch.draw(block9,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 10: batch.draw(block10,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 11: batch.draw(block11,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        case 12: batch.draw(block12,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                        default: batch.draw(block1,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                            break;
                    }
                    batch.end();
                }
                else if(board[i][j] == -1){
                    batch.begin();
                    batch.draw(aimBlock,j * (width / COLS), (ROWS-1-i) * (height / ROWS), (width / COLS), (height / ROWS));
                    batch.end();
                }
            }
        }
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
}
