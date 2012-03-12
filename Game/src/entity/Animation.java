package entity;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Animation {
	private Texture[] sprites;
	private float speed;
	private float timer;
	private int index;
	private String path;

	public Animation(String path, float speed, int count) {
		this.speed = speed;
		this.sprites = new Texture[count];
		this.path = path;
	}

	public void Load() {
		for (int i = 0; i < sprites.length; i++) {
			try {
				this.sprites[i] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.path + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void IncIndex() {
		this.index++;
		if (this.index == this.sprites.length) {
			this.index = 0;
		}
	}

	public void Update(float gameTime) {
		this.timer += gameTime;
		while (this.timer >= this.speed) {
			this.timer -= this.speed;
			this.IncIndex();
		}
	}

	public Texture getTexture() {
		return sprites[index];
	}
}
