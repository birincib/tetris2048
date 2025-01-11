import java.awt.Point;
import java.awt.event.KeyEvent;

public class MainClass {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(-0.52, 12);
        StdDraw.setYscale(-0.97, 11.5);
        StdDraw.enableDoubleBuffering();

        Grid board = new Grid(12, 8);
        Shape shape1= new Shape(12, 8);

        for (int y = 0; y < 12; y++) {
            for (int b = 0; b < 8; b++) {
                StdDraw.square(i + 0.5, b + 0.5, 0.5);
            }
        }

        for (int y = 0; y < 12; y++) {
            for (int b = 8; b < 12; b++) {
                StdDraw.square(y + 0.5, b + 0.5, 0.5);
            }
        }

        int point = 0;
        StdDraw.setPenColor(StdDraw.BLACK);

        boolean gameOnHold = false;

        boolean needNewTetromino = false;
        while (true)  {
            boolean success = false;
            if (StdDraw.hasNextKeyTyped()) {
                char ch = StdDraw.nextKeyTyped();
                if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                    System.exit(0);
                }
                if (ch == 'r')
                    success = shape1.rotate(board);
                if (ch == 'a' && !gameOnHold)
                    success = shape1.goLeft(board);
                if (ch == 's' && !gameOnHold) {
                    success = shape1.goDown(board);
                }

                else if (ch == 'd' && !gameOnHold )
                    success = shape1.goRight(board);
                else if (ch == 'p')
                    gameOnHold = !gameOnHold;
            }
            if(!gameOnHold) {

                if (!success)
                    success = shape1.goDown(board);

                needNewTetromino = !success;
                if (needNewTetromino) {
                    Point[][] pointsOfCoordinateMatrix = shape1.coordinateMatrix;
                    Ground[][] groundOfShapes = shape1.to2DArray128();

                    board.updateGrid(groundOfShapes, pointsOfCoordinateMatrix);

                    shape1 = new Shape(12, 8);

                }
                if (board.isFull()) {
                    needNewTetromino = false;
                    System.out.println("Game over");
                    System.exit(0);
                }
                board.fullRows();

                StdDraw.clear(StdDraw.WHITE);
                board.display();
                shape1.display();

                StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                StdDraw.text(9.5, 10.5, "point: " + board.calculateRowScore(10));

                StdDraw.show();
                StdDraw.pause(400);
                board.fullRows();
            }

        }

    }

}