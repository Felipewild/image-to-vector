package com.edge;

import Helper.Imagem;
import Helper.Pixel;
import Helper.Vetor;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;

public class Edge extends ApplicationAdapter {

    Pixmap image;
    Pixmap vetor;
    Stage stage;
    Table main;
    Label lb;
    boolean modificou = true;
    
    int edge = 1;
    int limit = 10;
    float time = 0;

    ArrayList<Vetor> vts = new ArrayList();

    @Override
    public void create() {
        image = new Pixmap(Gdx.files.internal("img1.bmp"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        vetorizacao();
        
        Skin skin = new Skin(Gdx.files.internal("skin/dark-peel-ui.json"));

        main = new Table();
        stage.addActor(main);
        lb = new Label("debug", skin);
        stage.addActor(lb);
        main.setFillParent(true);
    }

    private void vetorizacao() {
        Pixel[][] pm = Imagem.getPixelMap(image);
        pm = Imagem.getRecursiveEdge(pm, edge);
        vet(pm, limit);
        vetor = Imagem.getPixelMapToImage(pm);
        modificou = true;
    }
    
    private void vet(Pixel[][] pm, int limit){
        for (int h = 0; h < pm[0].length; h++) {
            for (int w = 0; w < pm.length; w++) {
                if(pm[w][h].getRed()>limit){
                    pm[w][h].setRed(0xFF);
                } else {
                    pm[w][h].setRed(0x00);
                }
                if(pm[w][h].getGreen()>limit){
                    pm[w][h].setGreen(0xFF);
                } else {
                    pm[w][h].setGreen(0x00);
                }
                if(pm[w][h].getBlue()>limit){
                    pm[w][h].setBlue(0xFF);
                } else {
                    pm[w][h].setBlue(0x00);
                }
            }
        }
    }

    private void update(float time) {
        this.time += time;
        lb.setText("edge= "+edge+" - limit= "+limit);
        if(this.time>2){
            this.time = 0;
            limit--;
            if(limit<=0){
                limit = 10;
                edge++;
                if(edge>3){
                    edge = 1;
                }
            }
            vetorizacao();
        }
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
        if (modificou) {
            modificou = false;
            main.reset();
            main.add(new Image(new Texture(image)));
            main.add(new Image(new Texture(vetor)));
            Pixmap img = new Pixmap(image.getWidth(), image.getHeight(), Pixmap.Format.RGBA8888);
            img.setColor(0x00000000);
            img.fill();
            img.setColor(0xFF0000FF);
            for (int i = 0; i < vts.size(); i++) {
                vts.get(i).draw(img);
            }
            stage.addActor(new Image(new Texture(img)));
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        image.dispose();
        vetor.dispose();
        stage.dispose();
    }
}
