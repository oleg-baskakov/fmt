package findmytwins.ingame;

import org.zkoss.zul.Image;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class GridGameRender implements RowRenderer {

	public GridGameRender() {
		// TODO Auto-generated constructor stub
	}

	public void render(Row arg0, Object arg1) throws Exception {
		
		Object[] data=(Object[]) arg1;
		Image img;
		String[] props;
		for (int j = 0; j < data.length; j++) {
			props=(String[]) data[j];
			img=new Image(props[0]);
			img.setId(props[1]);
			img.setWidth("100");
			img.setHeight("100");
			img.setTooltip(props[0]);
			img.setParent(arg0);
			//img.addEventListener(arg0, arg1)
			
		}
		

	}

}
