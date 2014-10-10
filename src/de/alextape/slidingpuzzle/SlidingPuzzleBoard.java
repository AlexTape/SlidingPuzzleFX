package de.alextape.slidingpuzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * The Class SlidingPuzzleBoard.
 */
public class SlidingPuzzleBoard extends Pane {

    /** The cols. */
    private int cols;

    /** The rows. */
    private int rows;

    /**
     * Instantiates a new sliding puzzle board.
     *
     * @param colCount the col count
     * @param rowCount the row count
     */
    SlidingPuzzleBoard(final int colCount, final int rowCount) {
        setStyle("-fx-background-color: #f3f3f3; "
                + "-fx-border-color: #f3f3f3; ");

        cols = colCount;
        rows = rowCount;

        double gameBoardWidth = SlidingPuzzlePiece.getPuzzleSize() * colCount;
        double gameBoardHeigth = SlidingPuzzlePiece.getPuzzleSize() * rowCount;
        setPrefSize(gameBoardWidth, gameBoardHeigth);
        setMaxSize(gameBoardWidth, gameBoardHeigth);

        autosize();

        final int i70 = 70;
        Path gameBoardGrid = new Path();
        gameBoardGrid.setStroke(Color.rgb(i70, i70, i70));
        getChildren().add(gameBoardGrid);

        final int i5 = 5;
        for (int spalte = 0; spalte < colCount - 1; spalte++) {
            gameBoardGrid.getElements().addAll(
                    new MoveTo(SlidingPuzzlePiece.getPuzzleSize()
                            + SlidingPuzzlePiece.getPuzzleSize() * spalte, i5),
                    new LineTo(SlidingPuzzlePiece.getPuzzleSize()
                            + SlidingPuzzlePiece.getPuzzleSize() * spalte,
                            SlidingPuzzlePiece.getPuzzleSize()
                            * rowCount - i5));
        }

        for (int zeile = 0; zeile < rowCount - 1; zeile++) {
            gameBoardGrid.getElements().addAll(
                    new MoveTo(i5, SlidingPuzzlePiece.getPuzzleSize()
                            + SlidingPuzzlePiece.getPuzzleSize() * zeile),
                    new LineTo(SlidingPuzzlePiece.getPuzzleSize() * colCount
                            - i5, SlidingPuzzlePiece.getPuzzleSize()
                            + SlidingPuzzlePiece.getPuzzleSize() * zeile));
        }
    }

    /**
     * Gets the cols.
     *
     * @return the cols
     */
    public final int getCols() {
        return cols;
    }

    /**
     * Gets the rows.
     *
     * @return the rows
     */
    public final int getRows() {
        return rows;
    }

}
