package com.example.pt12_variant2drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Actor que representa al T-Rex controlado por el jugador.
 */
public class TRexActor extends Actor {
    private Texture texture;
    private Rectangle bounds;
    private float speed = 400f;

    public TRexActor(Texture texture) {
        this.texture = texture;
        // Escalar a la mitad (0.5) del tamaño original
        setSize(texture.getWidth() * 0.5f, texture.getHeight() * 0.5f);
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Movimiento horizontal con teclas
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setX(getX() - speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setX(getX() + speed * delta);
        }

        // Movimiento horizontal con toque/clic
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            if (touchX < Gdx.graphics.getWidth() / 2f) {
                setX(getX() - speed * delta);
            } else {
                setX(getX() + speed * delta);
            }
        }

        // Limitar movimiento a la pantalla
        if (getX() < 0) setX(0);
        if (getX() > Gdx.graphics.getWidth() - getWidth()) setX(Gdx.graphics.getWidth() - getWidth());

        // Actualizar rectángulo de colisión
        bounds.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
