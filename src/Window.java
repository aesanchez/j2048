import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Window extends JFrame{
	private Game game;
	private final int dim=400;
	private final int xc=25;
	private class KeysAction extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent key) {
			if(game.isGameEnded())
				return;
			switch(key.getKeyCode()){
				case KeyEvent.VK_DOWN:
					game.moveDown();
					if(game.hasChanged())
					repaint1();
					break;
				case KeyEvent.VK_UP:
					game.moveUp();
					if(game.hasChanged())
					repaint1();
					break;
				case KeyEvent.VK_RIGHT:
					game.moveRight();
					if(game.hasChanged())
					repaint1();
					break;
				case KeyEvent.VK_LEFT:
					game.moveLeft();
					if(game.hasChanged())
					repaint1();
					break;
			}
			
		}				
	}
	
	public static void main(String[]args){
		Window w=new Window();
		w.setVisible(true);
	}
	public Window(){
		game=new Game();
		game.start();
		addKeyListener(new KeysAction());
		this.getBufferStrategy();
		setTitle("2048");
		setLayout(null);
		setSize(dim,dim+xc);
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);//en el medio
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void repaint1(){
		if(game.getRowLastCell()!=-1){
			int i=0;
			int j=0;
			while(i<10){
				paint(this.getGraphics(),i,j);
				if(i>=5) j+=2;
				i++;
				try{
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
			
		}
		paint(this.getGraphics());
	}
	public void paint(Graphics g,int rx,int ry){
		//background
		g.setColor(new Color(238, 228, 218).darker());
		g.fillRect(0,0,this.dim,this.dim+xc);
		
		//cells
		for(int row=0;row<game.WIDTH;row++){
			for(int column=0;column<game.WIDTH;column++){
				//color box
				if(game.getCell(row, column)!=null){					
					if(!(game.getRowLastCell()==row && game.getColumnLastCell()==column)){
						g.setColor(game.getCell(row, column).getColor());
						g.fillRect(column*(this.dim/4), row*(this.dim/4)+xc, this.dim/4, this.dim/4);
					}
				}					
				//number
				g.setColor(Color.BLACK);
				if(game.getCell(row, column)!=null){
					String str=game.getCell(row, column).getNumber()+" ";
					g.setFont(new Font("Consolas",Font.PLAIN,20));
					FontMetrics fm=g.getFontMetrics();
					int x=(this.dim/4 -fm.stringWidth(str))/2;
					int y = (fm.getAscent() + (this.dim/4 - (fm.getAscent() + fm.getDescent())) / 2);
					g.drawString(str, ((this.dim/4)*column)+x,(this.dim/4*row)+y+xc);
				}
			}
		}
		//grid lines
		g.setColor(Color.BLACK);
		for(int i=1;i<game.WIDTH;i++){
			g.drawLine(0, xc+(dim/4)*i, dim, xc+(dim/4)*i);
			g.drawLine((dim/4)*i, 0, (dim/4)*i, dim+xc);
		}
		//exapnding cell
		g.setColor(game.getCell(game.getRowLastCell(), game.getColumnLastCell()).getColor());
		g.fillRect(game.getColumnLastCell()*(this.dim/4)-(rx-ry)*(this.dim/100), game.getRowLastCell()*(this.dim/4)+xc-(rx-ry)*(this.dim/100), this.dim/4+2*(rx-ry)*(this.dim/100), this.dim/4+2*(rx-ry)*(this.dim/100));
		g.setColor(Color.BLACK);
		String str=game.getCell(game.getRowLastCell(), game.getColumnLastCell()).getNumber()+" ";
		g.setFont(new Font("Consolas",Font.PLAIN,20));
		FontMetrics fm=g.getFontMetrics();
		int x=(this.dim/4 -fm.stringWidth(str))/2;
		int y = (fm.getAscent() + (this.dim/4 - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(str, ((this.dim/4)*game.getColumnLastCell())+x,(this.dim/4*game.getRowLastCell())+y+xc);
	}
	
	public void paint(Graphics g){
		//background
		g.setColor(new Color(238, 228, 218).darker());
		g.fillRect(0,0,this.dim,this.dim+xc);
		//cells
		for(int row=0;row<game.WIDTH;row++){
			for(int column=0;column<game.WIDTH;column++){
				if(game.getCell(row, column)!=null){
					g.setColor(game.getCell(row, column).getColor());
					g.fillRect(column*(this.dim/4), row*(this.dim/4)+xc, this.dim/4, this.dim/4);
				}					
				
				g.setColor(Color.BLACK);
				if(game.getCell(row, column)!=null){
					String str=game.getCell(row, column).getNumber()+" ";
					g.setFont(new Font("Consolas",Font.PLAIN,20));
					FontMetrics fm=g.getFontMetrics();
					int x=(this.dim/4 -fm.stringWidth(str))/2;
					int y = (fm.getAscent() + (this.dim/4 - (fm.getAscent() + fm.getDescent())) / 2);
					g.drawString(str, ((this.dim/4)*column)+x,(this.dim/4*row)+y+xc);
				}
			}
		}
		//grid lines
		g.setColor(Color.BLACK);
		for(int i=1;i<game.WIDTH;i++){
			g.drawLine(0, xc+(dim/4)*i, dim, xc+(dim/4)*i);
			g.drawLine((dim/4)*i, 0, (dim/4)*i, dim+xc);
		}
		//gameover
		if(game.isGameEnded()){
			g.setColor(new Color(255,255,255,200));
			g.fillRect(0, 0, this.dim, this.dim+xc);
			g.setColor(Color.BLACK);
			String str="GAME OVER";
			FontMetrics fm=g.getFontMetrics();
			int x=(this.dim -fm.stringWidth(str))/2;
			int y = (fm.getAscent() + (this.dim - (fm.getAscent() + fm.getDescent())) / 2);
			g.drawString(str, x,y+xc);
		}
	}
	
}
