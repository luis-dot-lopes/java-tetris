package game;

import engine.core.Game;
import engine.core.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TetrisGame extends Game {

    private Board display;
    private Board board;
    private Tetrimino tetrimino;
    private Tetrimino nextTetrimino;
    private Random rand;

    boolean start;
    boolean gameover = false;

    private int score = 0;

    TetrisGame() {
        start = true;
        rand = new Random();
        board = new Board(40, 50, 10, 16);
        display = new Board(310, 50, 6, 4);
        int style1 = rand.nextInt(7);
        int style2 = rand.nextInt(7);
        tetrimino = new Tetrimino(4, -2, style1, board);
        nextTetrimino = new Tetrimino(1, 1, style2, display);
        nextTetrimino.putOnBoard(false);
    }

    @Override
    protected void onLoad() {
        //do nothing
    }

    @Override
    protected void onUnload() {
        //do nothing
    }

    private void handleKeys() {
        InputManager input = InputManager.getInstance();
        if(input.isPressed(KeyEvent.VK_ESCAPE))
             terminate();
        if(input.isJustPressed(KeyEvent.VK_LEFT))
            tetrimino.moveLeft();
        if(input.isJustPressed(KeyEvent.VK_RIGHT))
            tetrimino.moveRight();
        if(input.isJustPressed(KeyEvent.VK_UP))
            tetrimino.rotate();
        if(input.isPressed(KeyEvent.VK_SPACE))
            if(start) {
                start = false;
            } else if(gameover) {
                restart();
            } else {
                tetrimino.speedUp();
            }
        if(input.isReleased(KeyEvent.VK_SPACE))
            tetrimino.slowDown();
    }

    private void restart() {
        gameover = false;
        score = 0;
        rand = new Random();
        board = new Board(40, 50, 10, 16);
        display = new Board(310, 50, 6, 4);
        int style1 = rand.nextInt(7);
        int style2 = rand.nextInt(7);
        tetrimino = new Tetrimino(4, -2, style1, board);
        nextTetrimino = new Tetrimino(1, 1, style2, display);
        nextTetrimino.putOnBoard(false);
    }

    @Override
    protected void onUpdate(int ticks) {
        handleKeys();
        if(start || gameover) return;
        tetrimino.update(ticks);
        if(tetrimino.isStopped()) {
            if (tetrimino.isBlockOut()) gameover = true;
            int row;
            int counter = 0;
            do {
                row = board.checkRows();
                if(row != -1) {
                    board.cleanRow(row);
                    counter++;
                }
                row = board.checkRows();
            } while( row != -1);
            switch (counter) {
                case 1:
                    score += 40;
                    break;
                case 2:
                    score += 100;
                    break;
                case 3:
                    score += 300;
                    break;
                case 4:
                    score += 1200;
                    break;
            }
            nextTetrimino.removeFromBoard();
            tetrimino = nextTetrimino;
            tetrimino.setPos(4, -2, board);
            nextTetrimino = new Tetrimino(1, 1, rand.nextInt(7), display);
            nextTetrimino.putOnBoard(false);
        }
    }

    @Override
    protected void onRender(Graphics2D g) {
        if(start) {
            drawStart(g);
            return;
        }
        if(gameover) {
            drawGameover(g);
            return;
        }
        board.draw(g);
        display.draw(g);

        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", Font.BOLD,36));
        g.drawString("Score: " + score, 310, 300);
    }

    private void drawGameover(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD,36));
        g.drawString("Press SPACE to restart", 200, 220);
        g.drawString("Press ESC to exit", 240, 320);
    }

    private void drawStart(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD,36));
        g.drawString("Press SPACE to start", 200, 250);
    }
}
