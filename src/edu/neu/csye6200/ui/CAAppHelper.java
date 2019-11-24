package edu.neu.csye6200.ui;

import java.awt.*;

/* This is a companion class It handles all of the mechanics related to hexagon grids. */

public class CAAppHelper
{

	private static boolean XYVertex=true;	//true: x,y are the co-ords of the first vertex.

	private static int BORDERS=50;	//default number of pixels for the border.

	private static int s = 0;	// length of one side
	private static int t = 0;	// short side of 30o triangle outside of each hex
	private static int r = 0;	// radius of inscribed circle (centre to middle of each side). r= h/2
	private static int h = 0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

	public static void setXYasVertex(boolean b) {
		XYVertex=b;
	}
	public static void setBorders(int b){
		BORDERS=b;
	}

	/**
	 * This functions takes the Side length in pixels and uses that as the basic dimension of the hex.
	 * It calculates all other needed constants from this dimension.
	*/
	public static void setS(int s) {
		CAAppHelper.s = s;
		t =  (int) (CAAppHelper.s / 2);			//t = s sin(30) = (int) CalculateH(s);
		r =  (int) (CAAppHelper.s * 0.8660254037844);	//r = s cos(30) = (int) CalculateR(s);
		h=2*r;
	}
	public static void setHeight(int height) {
		h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
		r = h/2;			// r = radius of inscribed circle
		s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
		t = (int) (r / 1.73205);	// t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
	}

	/*********************************************************
	Name: hex()
	Parameters: (x0,y0) This point is normally the top left corner
		of the rectangle enclosing the hexagon.
		However, if XYVertex is true then (x0,y0) is the vertex of the
		top left corner of the hexagon.
	Returns: a polygon containing the six points.
	Called from: drawHex(), fillhex()
	Purpose: This function takes two points that describe a hexagon
	and calculates all six of the points in the hexagon.
	*********************************************************/
	public static Polygon hex (int x0, int y0) {

		int y = y0 + BORDERS;
		int x = x0 + BORDERS;
		if (s == 0  || h == 0) {
			System.out.println("ERROR: size of hex has not been set");
			return new Polygon();
		}

		int[] cx,cy;

		if (XYVertex)
			cx = new int[] {x,x+ s,x+ s + t,x+ s,x,x- t};  //this is for the top left vertex being at x,y. Which means that some of the hex is cutoff.
		else
			cx = new int[] {x+ t,x+ s + t,x+ s + t + t,x+ s + t,x+ t,x};	//this is for the whole hexagon to be below and to the right of this point

		cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
		return new Polygon(cx,cy,6);
	}

	/********************************************************************
	Name		:drawHex()
	Parameters	: (i,j) : the x,y coordinates of the inital point of the hexagon
	 g2			: the Graphics2D object to draw on.
	Returns		: void
	Calls		: hex()
	Purpose		: This function draws a hexagon based on the initial point (x,y).
				  The hexagon is drawn in the colour specified in hexgame.COLOURELL.
	*********************************************************************/
	public static void drawHex(int i, int j, Graphics2D g2) {
		int x = i * (s + t);
		int y = j * h + (i%2) * h/2;
		Polygon poly = hex(x,y);
		g2.setColor(CAApp.COLOURCELL);
		g2.fillPolygon(CAAppHelper.hex(x,y));
		g2.fillPolygon(poly);
		g2.setColor(CAApp.COLOURGRID);
		g2.drawPolygon(poly);
	}

	/***************************************************************************
	* Name: fillHex()
	* Parameters: (i,j) : the x,y coordinates of the initial point of the hexagon
			n   : an integer number to indicate a letter to draw in the hex
			g2  : the graphics context to draw on
	* Return: void
	* Called from:
	* Calls: hex()
	*Purpose: This draws a filled in polygon based on the coordinates of the hexagon.
		  The colour depends on whether n is negative or positive.
		  The colour is set by hexgame.COLOURONE and hexgame.COLOURTWO.
		  The value of n is converted to letter and drawn in the hexagon.
	*****************************************************************************/
	public static void fillHex(int i, int j, int n, Graphics2D g2) {
		int x = i * (s + t);
		int y = j * h + (i%2) * h/2;
		if (n ==1) {
			g2.setColor(CAApp.COLOURONE);
			g2.fillPolygon(hex(x,y));
		}
	}

}