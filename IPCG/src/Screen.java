import java.awt.Component;
import java.awt.event.KeyListener;


public interface Screen extends KeyListener{
	public Component getView();
	public void init();
//	public void finalise();
}