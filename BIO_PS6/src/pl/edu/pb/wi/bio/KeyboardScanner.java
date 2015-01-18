package pl.edu.pb.wi.bio;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardScanner implements NativeKeyListener {

	private String lastPressedKey = "none";
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		
		if (!App.lastKeyPressed.equals(NativeKeyEvent.getKeyText(e.getKeyCode()))) {
			App.pressedT = System.currentTimeMillis();
		} else {
			App.lastKeyPressed = NativeKeyEvent.getKeyText(e.getKeyCode());
		}
		System.out.println("last Pressed "+lastPressedKey);
		//System.out.println("pres = " + pressedT);
		//System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		//System.out.println("rel ="+System.currentTimeMillis());
		//lastPressedKey = "none";
		App.releasedT = System.currentTimeMillis();
		App.rel();

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

}
