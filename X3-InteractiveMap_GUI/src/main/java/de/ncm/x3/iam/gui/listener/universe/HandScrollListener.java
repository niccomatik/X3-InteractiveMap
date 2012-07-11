
package de.ncm.x3.iam.gui.listener.universe;


import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JViewport;

public class HandScrollListener extends MouseAdapter {
	
	private final Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	private final Cursor moveableCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
	private final Point pp = new Point();
	private JComponent comp;
	
	public HandScrollListener(JComponent comp) {
		this.comp = comp;
	}
	
	@Override
	public void mouseDragged(final MouseEvent e) {
		JViewport vport = (JViewport) e.getSource();
		Point cp = e.getPoint();
		Point vp = vport.getViewPosition();
		vp.translate(pp.x - cp.x, pp.y - cp.y);
		comp.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
		pp.setLocation(cp);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		pp.setLocation(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		comp.setCursor(moveableCursor);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		comp.setCursor(defaultCursor);
	}
	
}
