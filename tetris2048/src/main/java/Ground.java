import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Ground {
    public Point[][] matrixCor;
    Color textColor;
    Color backgroundColor;
    public int value;
    public Point position;
    public Point postion;

    public Ground(Color textColor, Color backgroundColor, int value, Point position) {
        this.value=getValue2048();
        this.textColor=textColor();
        this.backgroundColor=getBackground();
        this.position = position;
    }
	 

    public static int getValue2048() {
        int array[]= {2,4,8};
        int randomValue = new Random().nextInt(array.length);
        return array[randomValue];
    }

    public Color textColor()
    {
        if(value == 8 || value == 16 )
            return Color.GREEN;
        return Color.BLUE;
    }

    public Color getBackground()
    {

        switch (value )
        {
            case 2:
                return new Color(0xede0c8);
            case 4:
                return new Color(0xf2b179);
            case 8:
                return new Color(0xf59563);

            case 16:
                return new Color(0xf67c5f);
            case 32:
                return new Color(0xf65e3b);
            case 64:
                return new Color(0xedcf72);
            case 128:
                return new Color(0xedcc61);
            case 256:
                return new Color(0xedc850);
            case 512:
                return new Color(0xedc53f);
            case 1024:
                return new Color(0xedc22e);
            case 2048:
                return new Color(0xEEC22C);
        }

        return new Color( 0xcdc1b4 );
    }



    public void merge(Ground[][] matriGro) {
        for (int i = 0; i < matriGro.length; i++) {
            for (int j = 0; j < matriGro[i].length; j++) {
                if(matriGro[i][j].value == matriGro[i-1][j].value && matriGro[i][j] != null && matriGro[i-1][j] != null) {
                    matriGro[i][j-1].value= matriGro[i][j].value*2;
                    matriGro[i][j].value=0;
                }
                if(matriGro[i][j].value == matriGro[i][j-1].value && matriGro[i][j] != null && matriGro[i][j-1] != null) {
                    matriGro[i][j-1].value= matriGro[i][j-1].value*2;
                    matriGro[i][j].value=0;
                }
                if(matriGro[i][j].value == matriGro[i-1][j-1].value && matriGro[i][j] != null && matriGro[i-1][j-1] != null) {
                    matriGro[i-1][j-1].value= matriGro[i-1][j-1].value*2;
                    matriGro[i][j].value=0;
                }}
        }

    }

    public void displayGround(Ground[][] matriGro, Point [][]coordinate) {

        for (int row = 0; row < matriGro.length; row++) {
            for (int col = 0; col < matriGro.length; col++) {
                Point point = coordinate[row][col];
                if(point.y < 12 && matriGro[row][col] != null) {
                    Color color = matriGro[row][col].backgroundColor;
                    Color text = matriGro[row][col].textColor;
                    StdDraw.setPenColor(color);
                    StdDraw.filledSquare(point.x, point.y ,0.5);
                    StdDraw.setPenColor(text);
                    String textOfTile=String.valueOf(matriGro[row][col].value);
                    StdDraw.text(point.x, point.y, textOfTile);
                }


            }
        }
    }

}