package displaymanager;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class ContextMenu {
	private String[] options;
	private int index;
	private float Xdep;
	private float Ydep;
	private UnicodeFont font;
	private boolean show;
	private boolean[] enable;

	public ContextMenu() {
		this.options = null;
		this.enable = null;
		this.index = 0;
		this.show = true;
	}

	public void Init(UnicodeFont font) {
		this.font = font;
	}

	public void Render() {
		if (show && options != null) {
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
		if (!enable[index]) {
			Next();
		}
	}

	public void Previous() {
		index--;
		if (index < 0) {
			index = options.length - 1;
		}
		if (!enable[index]) {
			Previous();
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
		this.enable = new boolean[options.length];
		for (int i = 0; i < enable.length; i++) {
			enable[i] = true;
		}
	}

	public void DisableOption(int i) {
		enable[i] = false;
		if (index == i) {
			Next();
		}
	}

	public void Clear() {
		this.options = null;
		this.index = 0;
	}

	public int getIndex() {
		return this.index;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
