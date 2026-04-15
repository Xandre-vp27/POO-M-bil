package com.example.pt12_variant2drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Actor que representa un meteorito que cae desde la parte superior.
 */
public class MeteorActor extends Actor {
    private Texture texture;
    private Rectangle bounds;
    private float speed = 250f;

    public MeteorActor(Texture texture) {
        this.texture = texture;
        // Escalar a un tercio (0.33) del tamaño original
        setSize(texture.getWidth() * 0.33f, texture.getHeight() * 0.33f);
        
        // Posición inicial aleatoria en la parte superior
        float x = MathUtils.random(0, Gdx.graphics.getWidth() - getWidth());
        float y = Gdx.graphics.getHeight();
        setPosition(x, y);

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // El meteorito cae
        setY(getY() - speed * delta);
        
        // Actualizar el rectángulo de colisión
        bounds.setPosition(getX(), getY());

        // Si el meteorito sale de la pantalla por abajo, se elimina
        if (getY() + getHeight() < 0) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
