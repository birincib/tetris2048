import java.awt.Color;
import java.awt.Point;



public class Grid {
    final Color freeSquare;
    final Ground[][] matriGround;

    private int points = 0;
    Color textColorr;
    Color backgroundColor;
    int valueTile;
    Point pointerTile;


    public Grid (int i_rows, int n_cols) {
        freeSquare = StdDraw.MAGENTA;
        matriGround = new Ground[i_rows][n_cols];
        initMatrix();

        points =0;

    }




    public void initMatrix() {
        for (int row = 0; row < matriGround.length; row++)
            for (int col = 0; col < matriGround[0].length; col++) {
                matriGround[row][col]=new Ground(textColorr,backgroundColor,valueTile,pointerTile);
                matriGround[row][col].backgroundColor = freeSquare;
            }
    }

    public boolean isInside(int row, int col) {
        if (row < 0 || row >= matriGround.length)
            return false;
        if (col < 0 || col >= matriGround[0].length)
            return false;
        return true;
    }

    public boolean isOccupied(int row, int col) {
        return matriGround[row][col].backgroundColor != freeSquare;
    }

    public void updateGrid(Ground[][] a213, Point [][] pointer) {
        for (int i = 0; i < matriGround.length; i++) {
            for (int j = 0; j < matriGround[0].length; j++) {
                if(a213[i][j] != null)
                    a213[i][j].position = pointer[i][j];
                if(matriGround[i][j]!= null && a213[i][j] != null) {
                    matriGround[i][j].postion = a213[i][j].position;
                    matriGround[i][j].value = a213[i][j].value;
                    matriGround[matriGround[i][j].postion.y][matriGround[i][j].postion.x].backgroundColor=a213[i][j].backgroundColor;

                }
            }
        }
    }

    public void display() {

        for (int row = 0; row < matriGround.length; row++)
            for (int col = 0; col < matriGround[0].length; col++) {

                StdDraw.setPenColor(matriGround[row][col].backgroundColor);
                StdDraw.filledSquare(col, row, 0.5);
            }
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        for (double x = -0.5; x < matriGround[0].length; x++)
            StdDraw.line(x, -0.5, x, matriGround.length - 0.5);
        for (double y = -0.5; y < matriGround.length; y++)
            StdDraw.line(-0.5, y, matriGround[0].length - 0.5, y);

        for (int row = 0; row < matriGround.length; row++) {
            for (int col = 0; col < matriGround[0].length; col++) {
                if(matriGround[row][col] != null && matriGround[row][col].postion !=null) {
                    Color text = matriGround[row][col]. textColor();
                    StdDraw.setPenColor(text);
                    String textofTile=String.valueOf(matriGround[row][col].value);
                    StdDraw.text(matriGround[row][col].postion.x, matriGround[row][col].postion.y, textofTile);}
            }}

    }


    public boolean isFull() {
        for(int r=0; r<8; r++)
            if(matriGround[11][r].backgroundColor !=freeSquare)
                return true;
        return false;
    }
    public void fullRows() {
        int completedRows = 0;
        int totalPoints = 0;
        for (int i = 0; i < 8; i++) {
            if (isRowFull(i)) {
                int rowPoints = calculateRowPoints(i);
                totalPoints += rowPoints;
                removeRow(i);
                completedRows++;
            }
        }
        if (completedRows > 0) {
            System.out.println("Total soints: " + totalPoints);
        }
    }

    public int calculateRowPoints(int row) {
        switch (row) {
            case 2:
                points += 2;
                break;
            case 4:
                points += 4;
                break;
            case 8:
                points += 8;
                break;
            case 16:
                points += 16;
                break;
            case 32:
                points += 32;
                break;
            case 64:
                points += 64;
                break;
            case 128:
                points += 128;
                break;
            case 256:
                points += 256;
                break;
            case 512:
                points += 512;
                break;
            case 1024:
                points += 1024;
                break;
            case 2048:
                points += 2048;
                break;
            default:
                break;
        }
        return points;
    }

    public boolean isRowFull(int row) {
        for(int j=0; j<8; j++)
            if(matriGround[row][j].backgroundColor == freeSquare)
                return false;
        return true;
    }

    public void removeRow(int row) {
        for(int j=0; j<8; j++)
        matriGround[row][j].backgroundColor = freeSquare;
        for(int i=row; i<11; i++) {
            for(int j=0; j<8; j++) {
                matriGround[i][j] = matriGround[i+1][j];

            }
        }
    }

}