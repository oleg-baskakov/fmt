package sonet.findmytwins.model;

import java.util.HashMap;



import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class TwinRender implements ListitemRenderer{
		public void render(Listitem row, java.lang.Object data) {
			
		if(data==null)return;
			 HashMap rawData= (HashMap)data;
//	            Div div = new Div();
		try {
			
	            Listcell cell=new Listcell();
	            row.setTooltiptext("id="+rawData.get("avatar"));
	            if(rawData.get("avatar")!=null){
	            	A a=new A("",""+rawData.get("avatar"));
	            	a.setHref(""+rawData.get("profileLink"));
	            	a.setTarget("_blank");
	            	a.setParent(cell);
//	          	cell.setImage( ""+rawData.get("avatar"));
	            }else{
	            	cell.setImage( "blank.gif");
	            }
	            cell.setParent(row);
	            cell=new Listcell();
	            cell.setLabel(""+rawData.get("name"));
	            cell.setParent(row);
	            
	            cell=new Listcell();
	            cell.setLabel(""+rawData.get("gender"));
	            cell.setParent(row);
	            cell=new Listcell();
	            cell.setLabel(""+rawData.get("country"));
	            cell.setParent(row);
	        //    row.setAttribute("profileLink", rawData.get("profileLink"));
	            
	            //	            icon.setParent(row);
//            	            div.appendChild(icon);
//	            new Label(ary[0]).setParent(div);
        //    row.appendChild(icon);
	            row.setHeight("32");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	        
		}
	}