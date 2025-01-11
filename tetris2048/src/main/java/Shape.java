import java.util.Random;
import java.awt.Color;
import java.awt.Point;

public class Shape {

    private static final char[] CurrentX = null;
    final Color color;
    public boolean[][] shpMatrix;
    public Point[][] matrixCrdnt;
    public int gridWidth, gridHeight;

    public Ground[][] groundMatrices = new Ground[4][4];
    Color boxColor;
    Color bGroundColor;
    int boxValue;
    Point boxPointer;
    public Shape(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        Random random = new Random();
        int red = random.nextInt(256), green = random.nextInt(256), blue = random.nextInt(256);
        color = new Color(red, green, blue);
        shapeGenerator();
        int n_rows = 4, n_cols = 4;
        matrixCrdnt = new Point[n_rows][n_cols];
        int lowerLeftCornerX = random.nextInt(gridWidth - (n_cols - 1)), lowerLeftCornerY = gridHeight;
        matrixCrdnt[n_rows - 1][0] = new Point(lowerLeftCornerX, lowerLeftCornerY);
        for (int row = n_rows - 1; row >= 0; row--)
            for (int col = 0; col < n_cols; col++) {
                if (row == n_rows - 1 && col == 0)
                    continue;
                else if (col == 0) {
                    int currentX = matrixCrdnt[row + 1][col].x;
                    int currentY = matrixCrdnt[row + 1][col].y + 1;
                    matrixCrdnt[row][col] = new Point(currentX, currentY);
                    continue;
                }
                int currentX = matrixCrdnt[row][col - 1].x + 1;
                int currentY = matrixCrdnt[row][col - 1].y;
                matrixCrdnt[row][col] = new Point(currentX, currentY);
            }
    }


    final boolean[][][] shapes = { 
            {{false, false, false, false}, {true, true, true, true}, {false, false, false, false}, {false, false, false, false}} , // shapeI
            {{false, false, false, false}, {false, true, true, true}, {false, true, false, false}, {false, false, false, false}}, // shapeL
            {{false, false, false, false}, {false, false, false, false}, {false, true, true, false}, {false, true, true, false}}, // shapeO
            {{false, false, false, false}, {false, true, true, true}, {false, false, true, false}, {false, false, false, false}}, // shapeT
            {{false, false, false, false}, {false, true, true, true}, {false, false, false, true}, {false, false, false, false}}, // shapeJ
            {{false, false, false, false}, {false, true, true, false}, {true, true, false, false}, {false, false, false, false}}, // shapeS
            {{false, false, false, false}, {false, true, true, false}, {false, false, true, true}, {false, false, false, false}}  // shapeZ
    };

    public void shapeGenerator() {
        Random random = new Random();
        int random_number = random.nextInt(7);
        shpMatrix = shapes[random_number];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(shpMatrix[i][j])
                    groundMatrices[i][j] = new Ground(boxColor, bGroundColor, boxValue, boxPointer);
            }
        }
    }
    
    public Ground[][] to2DArray128() {
        Ground groundNew[][]= new Ground[12][8];
        int k = 0;
        for (int i = 0; i < groundMatrices.length; i++) {
            for (int j = 0; j < groundMatrices[0].length; j++) {
                groundNew[i][j] = groundMatrices[i][j];
            }

        }
        return groundNew;
    }

    public boolean rotate(Grid gameGrid) {
        Ground[][] temp= new Ground[4][4];
        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                temp[i][j] = groundMatrices[i][j];
        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                groundMatrices[j][i] = temp[i][3-j];
        return false;
    }




    public void display() {
        for (int row = 0; row < matrixCrdnt.length; row++) { 
            for (int col = 0; col < matrixCrdnt[0].length; col++) {
                Point point = matrixCrdnt[row][col];
                if(point.y   < gridHeight   && groundMatrices[row][col] != null) { 
                    groundMatrices[row][col].displayGround(groundMatrices,matrixCrdnt);
                }
            }
        }
    }

    
    public boolean goDown(Grid gameGrid) {
        boolean canGoDown = true;
        Point dummyPoint = new Point(-1, -1);
        Point[] bottommostBlock = {dummyPoint, dummyPoint, dummyPoint, dummyPoint};
        for (int col = 0; col < groundMatrices[0].length; col++) {
            for (int row = groundMatrices.length - 1; row >= 0; row--) {
                if (groundMatrices[row][col] != null) {
                    bottommostBlock[col] = matrixCrdnt[row][col];
                    if (bottommostBlock[col].y == 0)
                        canGoDown = false;
                    break;
                }
            }
            if (!canGoDown)
                break;
        }
        if (canGoDown) {
            for (int i = 0; i < bottommostBlock.length; i++) {
                if (bottommostBlock[i].equals(dummyPoint))
                    continue;
                if (bottommostBlock[i].y > gridHeight)
                    continue;
                if (gameGrid.isOccupied(bottommostBlock[i].y - 1, bottommostBlock[i].x)) {
                    canGoDown = false;
                    break;
                }
            }
        }
        if (canGoDown) {
            for (int row = 0; row < matrixCrdnt.length; row++)
                for (int col = 0; col < matrixCrdnt[0].length; col++)
                    matrixCrdnt[row][col].y--;
        }
        return canGoDown;
    }


    public Point[] getOccupiedSquares() {
        Point[] occupiedSquares = new Point[1];
        Point[] occupiedSquares1 = new Point[1];
        Point[] occupiedSquares2 = new Point[1];
        Point[] occupiedSquares3 = new Point[1];
        int count = 0;

        for (int row = 0; row < matrixCrdnt.length; row++)
            for (int col = 0; col < matrixCrdnt[0].length; col++)
                if (groundMatrices[row][col] != null) {
                    occupiedSquares[count] = matrixCrdnt[row][col];
                    occupiedSquares1[count] = matrixCrdnt[row][col];
                    occupiedSquares2[count] = matrixCrdnt[row][col];
                    occupiedSquares3[count] = matrixCrdnt[row][col];
                }

        Point x[] = { occupiedSquares[0], occupiedSquares1[0],occupiedSquares2[0],occupiedSquares3[0]};
        return x;
    }

    
    public boolean goLeft(Grid gameGrid) {
        boolean canGoLeft = true;
        Point dummyPoint = new Point(-1, -1);
        Point[] leftmostBlock = {dummyPoint, dummyPoint, dummyPoint, dummyPoint};
        for (int row = 0; row < groundMatrices.length; row++) {
            for (int col = 0; col < groundMatrices[0].length; col++) {
                if (groundMatrices[row][col] != null) {
                    leftmostBlock[row] = matrixCrdnt[row][col];
                    if (leftmostBlock[row].x == 0)
                        canGoLeft = false;
                    break;
                }
            }
            if (!canGoLeft)
                break;
        }
        if (canGoLeft) {
            for (int i = 0; i < leftmostBlock.length; i++) {
                if (leftmostBlock[i].equals(dummyPoint))
                    continue;
                if (leftmostBlock[i].y >= gridHeight)
                    continue;
                if (gameGrid.isOccupied(leftmostBlock[i].y, leftmostBlock[i].x - 1)) {
                    canGoLeft = false;
                    break;
                }
            }
        }
        if (canGoLeft) {
            for (int row = 0; row < matrixCrdnt.length; row++)
                for (int col = 0; col < matrixCrdnt[0].length; col++)
                    matrixCrdnt[row][col].x--;
        }
        return canGoLeft;
    }

    
    public boolean goRight(Grid gameGrid) {
        boolean canGoRight = true;
        Point dummyPoint = new Point(-1, -1);
        Point[] rightmostBlock = {dummyPoint, dummyPoint, dummyPoint, dummyPoint};
        for (int row = 0; row < groundMatrices.length; row++) {
            for (int col = groundMatrices[0].length - 1; col >= 0; col--) {
                if (groundMatrices[row][col] != null) {
                    rightmostBlock[row] = matrixCrdnt[row][col];
                    if (rightmostBlock[row].x == gridWidth - 1)
                        canGoRight = false;
                    break;
                }
            }
            if (!canGoRight)
                break;
        }
        if (canGoRight) {
            for (int i = 0; i < rightmostBlock.length; i++) {
                if (rightmostBlock[i].equals(dummyPoint))
                    continue;
                if (rightmostBlock[i].y >= gridHeight)
                    continue;
                if (gameGrid.isOccupied(rightmostBlock[i].y, rightmostBlock[i].x + 1)) {
                    canGoRight = false;
                    break;
                }
            }
        }
        if (canGoRight) {
            for (int row = 0; row < matrixCrdnt.length; row++)
                for (int col = 0; col < matrixCrdnt[0].length; col++)
                    matrixCrdnt[row][col].x++;
        }
        return canGoRight;
    }
}