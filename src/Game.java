import java.util.ArrayList;
import java.util.List;

public class Game {
	private Cell[][] board;
	private int score;
	public final int WIDTH=4;
	private boolean isGameEnded;
	private int rowLastCell=-1;
	private int columnLastCell=-1;
	private boolean hasMoved;
	private boolean hasMerged;
	
	public Game(){
		board=new Cell[WIDTH][WIDTH];
		score=0;
		isGameEnded=false;
		hasMoved=false;
		hasMerged=false;
	}
	public void start(){
		this.setNewCell();
		this.setNewCell();		
	}
	public int getRowLastCell(){return rowLastCell;}
	public int getColumnLastCell(){return columnLastCell;}
	public void moveLeft(){
		this.hasMerged=false;
		this.hasMoved=false;
		List<Cell> l;
		for(int row=0;row<WIDTH;row++){
			l=new ArrayList<Cell>();
			for(int column=0;column<WIDTH;column++){
				if(board[row][column]!=null){
					l.add(board[row][column]);
				}
			}
			int i=0;
			while(i<l.size()){
				if(i+1<l.size() && l.get(i).getNumber()==l.get(i+1).getNumber()){
					l.get(i).duplicate();
					l.remove(i+1);
					this.score+=l.get(i).getNumber();
					hasMerged=true;
				}
				i++;
			}
			int size=l.size();
			int n=0;
			while(n<size && !hasMoved){
				if(board[row][n]!= null && board[row][n]==l.get(n)){
					//do not moved
				}else{
					hasMoved=true;
				}
				n++;
			}
			
			
			for(int j=0;j<WIDTH;j++){
				if(j<size)
					board[row][j]=l.get(j);
				else
					board[row][j]=null;
			}
		}
		rowLastCell=-1;
		columnLastCell=-1;	
		if(hasMerged || hasMoved)
			this.setNewCell();
		if(!isMoveAvalible()){
			isGameEnded=true;
			return;
		}
	}
	public void moveRight(){
		List<Cell> l;
		this.hasMoved=false;
		this.hasMerged=false;
		for(int row=0;row<WIDTH;row++){
			l=new ArrayList<Cell>();
			for(int column=WIDTH-1;column>=0;column--){
				if(board[row][column]!=null){
					l.add(board[row][column]);
				}
			}
			int i=0;
			while(i<l.size()){
				if(i+1<l.size() && l.get(i).getNumber()==l.get(i+1).getNumber()){
					l.get(i).duplicate();
					l.remove(i+1);
					this.score+=l.get(i).getNumber();
					hasMerged=true;
				}
				i++;
			}
			int x=0;
			int size=l.size();
			int n=WIDTH-1;
			while(n>=0 && !hasMoved && x<size){
				if(board[row][n]!= null  && board[row][n]==l.get(x++)){
					//do not moved
				}else{
					hasMoved=true;
				}
				n--;
			}
			
			int index=0;
			for(int j=WIDTH-1;j>=0;j--){
				if(l.size()>index)					
					board[row][j]=l.get(index++);	
				else
					board[row][j]=null;				
			}
		}
		rowLastCell=-1;
		columnLastCell=-1;
		if(hasMoved || hasMerged)
			this.setNewCell();	
		if(!isMoveAvalible()){
			isGameEnded=true;
			return;
		}
		
	}
	public void moveUp(){
		List<Cell> l;
		this.hasMoved=false;
		this.hasMerged=false;
		for(int column=0;column<WIDTH;column++){
			l=new ArrayList<Cell>();
			for(int row=0;row<WIDTH;row++){
				if(board[row][column]!=null){
					l.add(board[row][column]);
				}
			}
			int i=0;
			while(i<l.size()){
				if(i+1<l.size() && l.get(i).getNumber()==l.get(i+1).getNumber()){
					l.get(i).duplicate();
					l.remove(i+1);
					this.score+=l.get(i).getNumber();
					hasMerged=true;
				}
				i++;
			}
			int size=l.size();
			int n=0;
			while(n<size && !hasMoved){
				if(board[n][column]!= null && board[n][column]==l.get(n)){
					//do not moved
				}else{
					hasMoved=true;
				}
				n++;
			}
			for(int j=0;j<WIDTH;j++){
				if(j<size)
					board[j][column]=l.get(j);
				else
					board[j][column]=null;
			}
		}
		rowLastCell=-1;
		columnLastCell=-1;
		if(hasMoved || hasMerged)
			this.setNewCell();
		if(!isMoveAvalible()){
			isGameEnded=true;
			return;
		}
	}
	public void moveDown(){
		List<Cell> l;
		this.hasMoved=false;
		this.hasMerged=false;
		for(int column=0;column<WIDTH;column++){
			l=new ArrayList<Cell>();
			for(int row=WIDTH-1;row>=0;row--){
				if(board[row][column]!=null){
					l.add(board[row][column]);
				}
			}
			int i=0;
			while(i<l.size()){
				if(i+1<l.size() && l.get(i).getNumber()==l.get(i+1).getNumber()){
					l.get(i).duplicate();
					l.remove(i+1);
					this.score+=l.get(i).getNumber();
					hasMerged=true;
				}
				i++;
			}
			int x=0;
			int size=l.size();
			int n=WIDTH-1;
			while(n>=0 && !hasMoved && x<size){
				if(board[n][column]!= null  && board[n][column]==l.get(x++)){
					//do not moved
				}else{
					hasMoved=true;
				}
				n--;
			}
			int index=0;
			for(int j=WIDTH-1;j>=0;j--){
				if(l.size()>index)
					board[j][column]=l.get(index++);
				else
					board[j][column]=null;
			}			
		}
		rowLastCell=-1;
		columnLastCell=-1;
		if(hasMoved || hasMerged)
			this.setNewCell();
		if(!isMoveAvalible()){
			isGameEnded=true;
			return;
		}
	}
	public int getScore(){
		return this.score;
	}
	private boolean isMoveAvalible(){
		boolean output=false;
		if(getEmptyCells()==0){
			int row=0;
			while(row<WIDTH && !output){
				int column=0;
				while(column<WIDTH && !output){
					//check to the right
					if(column+1<WIDTH && board[row][column].getNumber()==board[row][column+1].getNumber())
						output=true;
					//check down
					if(row+1<WIDTH && board[row+1][column].getNumber()==board[row][column].getNumber())
						output=true;
					column++;
				}
				row++;
			}
		}else{
			output=true;
		}
		return output;
	}
	private int getEmptyCells(){
		int empty=0;
		for(int row=0;row<WIDTH;row++){
			for(int column=0;column<WIDTH;column++){
				if(board[row][column]==null){
					empty++;
				}
			}
		}
		return empty;
	}
	private void setNewCell(){
		int index=(int)(Math.random()*getEmptyCells());
		int row=0;
		int column=0;
		boolean found=false;
		int r=0,c=0;
		while(row<WIDTH && !found){
			column=0;
			while(column<WIDTH && !found){
				if(index==0 && board[row][column]==null){
					found=true;
					r=row;
					c=column;
				}
				if(board[row][column]==null)
					index--;					
				column++;
			}
			row++;
		}
		board[r][c]=new Cell();
		rowLastCell=r;
		columnLastCell=c;
		if(Math.random()<0.05)
			board[r][c].duplicate();
	}
	public boolean hasChanged(){
		return (hasMerged || hasMoved);
	}
	public void print(){
		for(int row=0;row<WIDTH;row++){
			for(int column=0;column<WIDTH;column++){
				if(board[row][column]==null)
					System.out.print("0 ");
				else
					System.out.print(board[row][column].getNumber()+" ");
			}
			System.out.println("\n");
		}
		System.out.println();
	}
	public boolean isGameEnded(){
		return this.isGameEnded;
	}
	public Cell getCell(int row, int column){
		return this.board[row][column];
	}
}
