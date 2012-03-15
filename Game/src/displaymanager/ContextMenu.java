package displaymanager;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class ContextMenu {
	private String[] options;
	private int index;
	private float Xdep;
	private float Ydep;
	private UnicodeFont font;

	public ContextMenu() {
		this.options = null;
		this.index = 0;
	}

	public void Init(UnicodeFont font) {
		this.font = font;

	}

	public void Render() {
		if (options != null) {
			int i = 0;
			for (String s : options) {
				if (i == index) {
					font.drawString(Xdep, (Ydep + (25f * i)), s, Color.white);
				} else {
					font.drawString(Xdep, (Ydep + (25f * i)), s, Color.gray);
				}
				i++;
			}
		}
	}

	public void Next() {
		index++;
		if (index >= options.length) {
			index = 0;
		}
	}

	public void Previous() {
		index--;
		if (index < 0) {
			index = options.length - 1;
		}
	}

	public void setXdep(float xdep) {
		Xdep = xdep;
	}

	public void setYdep(float ydep) {
		Ydep = ydep;
	}

	public void setMenu(String[] options) {
		this.options = options;
		this.index = 0;
	}

	public void Clear() {
		this.options = null;
		this.index = 0;
	}

	public int getIndex() {
		return this.index;
	}
}
