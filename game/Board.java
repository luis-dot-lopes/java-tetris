package game;

import java.awt.*;

public class Board {

    private int[][] board;
    private int ROWS;
    private int COLUMNS;
    private static final int CELL_SIZE = 25;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;
    private int x, y;
    private Color[] colors;

    Board(int x, int y, int cols, int rows) {
        COLUMNS = cols;
        ROWS = rows;
        this.x = x;
        this.y = y;
        board = new int[16][10];
        colors = new Color[] {
                Color.CYAN,
                Color.BLUE,
                Color.RED,
                Color.YELLOW,
                Color.ORANGE,
                Color.GREEN,
                new Color(64, 0, 128)
        };
        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLUMNS; j++)
                board[i][j] = 0;
    }

    public boolean putBlock(int col, int row, int color) {
        if(col < 0 || col > 9) return false;
        if(row < 0 || row > 15) return false;
        if(board[row][col] != 0) return false;
        board[row][col] = color;
        return true;
    }

    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x-2, y-2, COLUMNS * CELL_SIZE + 3, ROWS * CELL_SIZE + 3);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, COLUMNS * CELL_SIZE, ROWS * CELL_SIZE);

        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == 0) continue;
                drawBlock(i, j, colors[board[i][j] - 1], g);
            }
    }

    private void drawBlock(int i, int j, Color color, Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(x + j * CELL_SIZE,  y + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g.setColor(color);
        g.fillRect(x + j * CELL_SIZE + 3, y + i * CELL_SIZE + 3,
                CELL_SIZE - 6, CELL_SIZE - 6);
    }

    public int checkRows() {
        for(int i = 0; i < ROWS; i++) {
            boolean filled = true;
            for(int j = 0; j < COLUMNS; j++) {
                if(board[i][j] == 0) {
                    filled = false;
                    break;
                }
            }
            if(filled) return i;
        }
        return -1;
    }

    public void removeBlock(int col, int row) {
        if(col < 0 || col > 9) return;
        if(row < 0 || row > 15) return;
        //if(board[row][col] != color)
        board[row][col] = 0;
    }

    public boolean isBlock(int col, int row) {
        if(col < 0 || col > 9) return false;
        if(row < 0 || row > 15) return false;
        return board[row][col] != 0;
    }

    public void cleanRow(int row) {
        for (int j = 0; j < COLUMNS; j++) {
            board[row][j] = 0;
        }
        int[] temp;
        for(int i = row; i > 0; i --) {
            temp = board[i - 1];
            board[i - 1] = board[i];
            board[i] = temp;
        }
    }
}
