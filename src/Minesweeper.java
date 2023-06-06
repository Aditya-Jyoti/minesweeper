import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Minesweeper {
    private final int CELLS_IN_ROW = 16;
    private final int CELLS_IN_COL = 16;
    private final int CELL_SIZE = 40;
    private final int NUM_MINES = 40;

    private JFrame frame = new JFrame("Minesweeper");

    private List<List<Cell>> gameBoard = new ArrayList<>();
    private List<Cell> bombCells = new ArrayList<>();
    private List<Cell> recursingCells = new ArrayList<>();

    private boolean gameOver = false;

    private void recursiveReveal() {
        if (recursingCells.size() == 0) {
            return;
        }

        Cell currCell = recursingCells.get(0);
        int currXIdx = currCell.getPosition().get("xIdx");
        int currYIdx = currCell.getPosition().get("yIdx");

        for (int nbrYIdx = -1; nbrYIdx < 2; nbrYIdx++) {
            for (int nbrXIdx = -1; nbrXIdx < 2; nbrXIdx++) {

                Cell nbrCell;

                try {
                    nbrCell = gameBoard.get(currYIdx + nbrYIdx).get(currXIdx + nbrXIdx);
                } catch (IndexOutOfBoundsException exception) {
                    continue;
                }

                if (nbrCell.getHidden() && !nbrCell.getBomb()) {
                    nbrCell.setHidden(false);
                    gameBoard.get(currYIdx + nbrYIdx).set(currXIdx + nbrXIdx, nbrCell);
                    nbrCell.updateButton();

                    if (currCell.getBombNumber() == 0) {
                        recursingCells.add(nbrCell);
                    }
                }

            }
        }
        recursingCells.remove(0);
        recursiveReveal();
    }

    private void createGameBoard() {
        for (int yIdx = 0; yIdx < CELLS_IN_COL; yIdx++) {
            List<Cell> boardRow = new ArrayList<>();
            for (int xIdx = 0; xIdx < CELLS_IN_ROW; xIdx++) {

                JButton cellButton = new JButton();
                cellButton.setMargin(new Insets(0, 0, 0, 0));
                cellButton.setBorder(BorderFactory.createEmptyBorder());

                boardRow.add(new Cell(xIdx, yIdx, cellButton));

            }
            gameBoard.add(boardRow);
        }

        for (int mine = 0; mine < NUM_MINES; mine++) {
            int mineXIdx = new Random().nextInt(CELLS_IN_ROW);
            int mineYIdx = new Random().nextInt(CELLS_IN_COL);

            while (gameBoard.get(mineYIdx).get(mineXIdx).getBomb()) {
                mineXIdx = new Random().nextInt(CELLS_IN_ROW);
                mineYIdx = new Random().nextInt(CELLS_IN_COL);
            }

            gameBoard.get(mineYIdx).get(mineXIdx).setBomb(true);
            gameBoard.get(mineYIdx).get(mineXIdx).setBombNumber(-1);
            bombCells.add(gameBoard.get(mineYIdx).get(mineXIdx));

            for (int nbrYIdx = -1; nbrYIdx < 2; nbrYIdx++) {
                for (int nbrXIdx = -1; nbrXIdx < 2; nbrXIdx++) {
                    Cell nbrCell;
                    try {
                        nbrCell = gameBoard.get(mineYIdx + nbrYIdx).get(mineXIdx + nbrXIdx);
                    } catch (IndexOutOfBoundsException exception) {
                        continue;
                    }
                    if (!nbrCell.getBomb()) {
                        nbrCell.setBombNumber(nbrCell.getBombNumber() + 1);
                        gameBoard.get(mineYIdx + nbrYIdx).set(mineXIdx + nbrXIdx, nbrCell);
                    }
                }
            }
        }
    }

    public void drawBoard() {
        for (List<Cell> rowCell : gameBoard) {
            for (Cell cell : rowCell) {
                cell.getButton().addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent event) {
                    }

                    public void mouseReleased(MouseEvent event) {
                    }

                    public void mouseEntered(MouseEvent event) {
                    }

                    public void mouseExited(MouseEvent event) {
                    }

                    public void mouseClicked(MouseEvent event) {
                        if (gameOver) {
                            return;
                        }

                        if (event.getButton() == MouseEvent.BUTTON1) {
                            if (!cell.getBomb()) {
                                if (cell.getBombNumber() == 0) {
                                    recursingCells.add(cell);
                                    recursiveReveal();
                                } else {
                                    cell.setHidden(false);
                                    cell.updateButton();
                                }
                            } else {
                                for (Cell bCell : bombCells) {
                                    bCell.showBomb();
                                }
                                gameOver = true;
                            }

                            recursingCells = new ArrayList<>();
                            cell.updateButton();
                        }
                        if (event.getButton() == MouseEvent.BUTTON3) {
                            if (cell.getHidden()) {
                                if (!cell.getFlagged()) {
                                    cell.setFlagged(true);
                                } else {
                                    cell.setFlagged(false);
                                }
                                cell.updateButton();
                            }
                        }
                    }
                });
                cell.getButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                    }
                });

                cell.updateButton();
                frame.add(cell.getButton());
            }
        }
    }

    public void runMinesweeper() {
        createGameBoard();

        frame.setSize(CELLS_IN_ROW * CELL_SIZE, CELLS_IN_COL * CELL_SIZE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("assets/logo.png").getImage());
        frame.getContentPane().setBackground(new Color(187, 187, 187));
        frame.setLayout(new GridLayout(CELLS_IN_ROW, CELLS_IN_COL));

        drawBoard();

        frame.setVisible(true);
    };
}
