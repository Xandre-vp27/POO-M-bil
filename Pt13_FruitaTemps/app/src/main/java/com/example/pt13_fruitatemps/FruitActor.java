package com.example.pt13_fruitatemps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.math.MathUtils;

public class FruitActor extends Actor {
    private final Texture texture;
    private final boolean isBomb;
    private float velocityY;
    private final float gravity = -400f; // Efecte de la gravetat
    private boolean clicked = false;

    public FruitActor(Texture texture, boolean isBomb, float x) {
        this.texture = texture;
        this.isBomb = isBomb;
        setSize(texture.getWidth(), texture.getHeight());
        setPosition(x, -getHeight()); // Apareixen des de sota
        
        // Velocitat inicial cap amunt aleatòria
        velocityY = MathUtils.random(500f, 800f);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!clicked) {
                    clicked = true;
                    handleCollision();
                }
            }
        });
    }

    private void handleCollision() {
        if (getStage() instanceof GameScreen.GameStage) {
            ((GameScreen.GameStage) getStage()).onActorClicked(this);
        }
        remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        
        // Lògica de gravetat manual per al "vol"
        velocityY += gravity * delta;
        setY(getY() + velocityY * delta);
        
        // Si surt per sota, l'eliminem
        if (getY() < -getHeight() * 2 && velocityY < 0) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public boolean isBomb() {
        return isBomb;
    }
}