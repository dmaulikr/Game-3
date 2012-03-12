package entity;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Animation {
	public String basicPath = "content/Sprites/";

	protected Texture sprites;
	protected String path;
	
	protected float speed;
	protected float timer;
	protected int count;
	protected int index;

	/*public Animation(float speed, int count) {
		this.speed = speed;
		this.count = count;
		this.path = basicPath + path;
	}*/

	public void Load() {
		try {
			this.sprites = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void IncIndex() {
		this.index++;
		if (this.index == this.count) {
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
		return sprites;
	}

	public int getIndex() {
		return index;
	}
}
