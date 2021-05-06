package game;

public class Tetrimino {

    private static short[] IBlock = {
            0b0000111100000000,
            0b0010001000100010,
            0b0000000011110000,
            0b0100010001000100
    };
    private static short[] OBlock = {
            0b1111,
            0b1111,
            0b1111,
            0b1111
    };
    private static short[] TBlock = {
            0b010111000,
            0b010011010,
            0b000111010,
            0b010110010
    };
    private static short[] SBlock = {
            0b011110000,
            0b010011001,
            0b000011110,
            0b100110010
    };
    private static short[] ZBlock = {
            0b110011000,
            0b001011010,
            0b000110011,
            0b010110100
    };
    private static short[] JBlock = {
            0b100111000,
            0b011010010,
            0b000111001,
            0b010010110
    };
    private static short[] LBlock = {
            0b001111000,
            0b010010011,
            0b000111100,
            0b110010010
    };
    private static short[][] styles = {
            IBlock, OBlock, TBlock, SBlock, ZBlock, JBlock, LBlock
    };

    private static int[] sizes = {4, 2, 3, 3, 3, 3, 3};

    public boolean isStopped() {
        return stop;
    }

    public boolean isBlockOut() {
        return blockOut;
    }

    private boolean stop;
    private boolean blockOut = false;

    private int rotation;
    private short[] style;
    private int delay = 5;
    private int size;
    private int color;
    private int x, y;
    private Board board;

    private static final int N_OF_ROTATIONS = 4;

    Tetrimino(int col, int row, int style, Board board) {
        this.board = board;
        rotation = 0;
        this.style = styles[style];
        size = sizes[style];
        color = style + 1;
        x = col;
        y = style == 0 ? row - 1 : row;
        stop = false;
    }

    void update(int ticks) {
        if (ticks % delay == 0 && !stop) {
            removeFromBoard();
            y++;
            System.out.println("y = " + y);
            boolean atboard = isAtBoard();
            System.out.println(atboard);
            boolean sucess = putOnBoard(!atboard);
            //System.out.println(check);
            if(!atboard && checkBlockOut()) {
                System.out.println("hello");
                blockOut = true;
                return;
            }
            if(!sucess && atboard) {
                System.out.println("error");
                y--;
                putOnBoard(false);
                stop = true;
            }
        }
    }

    public void moveLeft() {
        removeFromBoard();
        x--;
        if(!putOnBoard(false)) {
            x++;
            putOnBoard(false);
        }
    }

    public void moveRight() {
        removeFromBoard();
        x++;
        if(!putOnBoard(false)) {
            x--;
            putOnBoard(false);
        }
    }

    public void rotate() {
        removeFromBoard();
        rotation = (rotation + 1 == N_OF_ROTATIONS? 0 : rotation + 1);
        if(!putOnBoard(false)) {
            removeFromBoard();
            rotation = (rotation - 1 < 0? N_OF_ROTATIONS - 1 : rotation - 1);
            putOnBoard(false);
        }
    }

    public void speedUp() {
        delay = 10;
    }

    public void slowDown() {
        delay = 60;
    }

    private boolean checkBlockOut() {
        if(isAtBoard()) return false;
        int temp = y;
        y = (size == 4? -1 : 0);
        if(!putOnBoard(false)) {
            return true;
        } else {
            removeFromBoard();
            y = temp;
            return false;
        }
    }

    public void removeFromBoard() {
        short tetrimino = style[rotation];
        for (int c = 0; c < size * size; c++) {
            if (isBlock(tetrimino, c)) {
                int i = Math.floorDiv(c, size);
                int j = c % size;
                board.removeBlock(x + j, i + y);
            }
        }
    }

    public boolean putOnBoard(boolean unsafe) {
        short tetrimino = style[rotation];
        boolean sucess = true;
        int c = 0;
        for (; c < size * size; c++)
            if (isBlock(tetrimino, c)) {
                int i = Math.floorDiv(c, size);
                int j = c % size;
                sucess = board.putBlock(x + j, i + y, color);
                if(!sucess) break;
            }
        if(sucess || unsafe) return true;
        for(c--; c >= 0; c--) {
            if(isBlock(tetrimino, c)) {
                int i = Math.floorDiv(c, size);
                int j = c % size;
                board.removeBlock(x + j, i + y);
            }
        }
        return false;
    }

    private boolean isBlock(short tetrimino, int c) {
        int v = tetrimino >> size * size - c - 1;
        //System.out.println("v = " + Integer.toBinaryString(v));
        return (v & 1) == 1;
    }

    private boolean isAtBoard() {
        short tetrimino = style[rotation];
        for (int c = 0; c < size * size; c++) {
            if (isBlock(tetrimino, c)) {
                int i = Math.floorDiv(c, size);
                if (y + i < 0) return false;
            }
        }
        return true;
    }

    public void setPos(int col, int row, Board board) {
        x = col;
        y = size == 4 ? row - 1 : row;
        this.board = board;
    }
}
