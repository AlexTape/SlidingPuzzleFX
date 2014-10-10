package de.alextape.slidingpuzzle;

import java.util.Vector;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * The Class SlidingPuzzlePiece.
 */
public class SlidingPuzzlePiece extends Parent {

    /** The puzzle piece. */
    private static Vector<SlidingPuzzlePiece> puzzlePiece;

    /** The puzzle size. */
    private static int puzzleSize;

    /**
     * Gets the free field.
     *
     * @return the free field
     */
    public static SlidingPuzzleField getFreeField() {
        SlidingPuzzleField free = null;
        for (SlidingPuzzlePiece thisPuzzlePiece
                : SlidingPuzzlePiece.getPuzzlePiece()) {
            if (thisPuzzlePiece.getField().getFree()) {
                free = thisPuzzlePiece.getField();
                break;
            }
        }
        return free;
    }

    /**
     * Gets the puzzle piece.
     *
     * @return the puzzle piece
     */
    public static Vector<SlidingPuzzlePiece> getPuzzlePiece() {
        return puzzlePiece;
    }

    /**
     * Gets the puzzle size.
     *
     * @return the puzzle size
     */
    public static int getPuzzleSize() {
        return puzzleSize;
    }

    /**
     * Sets the puzzle piece.
     *
     * @param newPuzzlePiece the new puzzle piece
     */
    public static void setPuzzlePiece(
            final Vector<SlidingPuzzlePiece> newPuzzlePiece) {
        SlidingPuzzlePiece.puzzlePiece = newPuzzlePiece;
    }

    /**
     * Sets the puzzle size.
     *
     * @param newPuzzleSize the new puzzle size
     */
    public static void setPuzzleSize(final int newPuzzleSize) {
        puzzleSize = newPuzzleSize;
    }

    /** The field. */
    private SlidingPuzzleField field;

    /** The image. */
    private ImageView image = new ImageView();

    /** The is visible. */
    private Boolean isVisible;

    /** The piece border. */
    private Shape pieceBorder;

    /** The piece cut. */
    private Shape pieceCut;

    /** The root x. */
    private final double rootX;

    /** The root y. */
    private final double rootY;

    /**
     * Instantiates a new sliding puzzle piece.
     *
     * @param bgImage the bg image
     * @param thisRootX the this root x
     * @param thisRootY the this root y
     * @param gameBoardWidth the game board width
     * @param gameBoardHeigth the game board heigth
     * @param thisField the this field
     */
    public SlidingPuzzlePiece(final Image bgImage, final double thisRootX,
            final double thisRootY, final double gameBoardWidth,
            final double gameBoardHeigth, final SlidingPuzzleField thisField) {
        this.rootX = thisRootX;
        this.rootY = thisRootY;

        setField(thisField);

        pieceCut = createPiece();
        pieceCut.setFill(Color.WHITE);
        pieceCut.setStroke(Color.WHITE);

        pieceBorder = createPiece();
        pieceBorder.setFill(null);
        pieceBorder.setStroke(Color.WHITE);

        image.setImage(bgImage);
        image.setClip(pieceCut);
        setFocusTraversable(true);
        getChildren().addAll(image, pieceBorder);

        setVisible(true);
        setCache(true);

        if (SlidingPuzzlePiece.puzzlePiece == null) {
            SlidingPuzzlePiece.puzzlePiece = new Vector<SlidingPuzzlePiece>();
        }
        puzzlePiece.add(this);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent me) {

                SlidingPuzzleField freeField = getFreeField();
                SlidingPuzzleField actualField = getField();

                if (freeField != null) {

                    int fromX = actualField.getFieldCol();
                    int fromY = actualField.getFieldRow();

                    int toX = freeField.getFieldCol();
                    int toY = freeField.getFieldRow();

                    System.out.println("From: " + fromX + "-" + fromY);
                    System.out.println("To: " + toX + "-" + toY);

                    boolean moveField = false;

                    if ((((fromX + 1) == toX
                            || (fromX - 1) == toX) & (fromY == toY))
                            ^ (((fromY + 1) == toY
                            || (fromY - 1) == toY) & (fromX == toX))) {
                        moveField = true;
                    }

                    if (moveField) {
                        System.out.println("Moving..");

                        double freeX = freeField.getFieldCoordX();
                        double freeY = freeField.getFieldCoordY();

                        double shuffleX = (freeX - actualField.getFieldCoordX())
                                + getTranslateX();
                        double shuffleY = (freeY - actualField.getFieldCoordY())
                                + getTranslateY();

                        setTranslateX(shuffleX);
                        setTranslateY(shuffleY);

                        freeField.setFieldCoordX(actualField.getFieldCoordX());
                        freeField.setFieldCoordY(actualField.getFieldCoordY());
                        freeField.setFieldCol(fromX);
                        freeField.setFieldRow(fromY);

                        actualField.setFieldCoordX(freeX);
                        actualField.setFieldCoordY(freeY);
                        actualField.setFieldCol(toX);
                        actualField.setFieldRow(toY);

                        int moveCount = SlidingPuzzleGame.getMoveCount();
                        SlidingPuzzleGame.setMoveCount(moveCount++);

                    } else {
                        System.out.println("no Neighbour..");
                    }
                    SlidingPuzzleGame.puzzleReady();
                    System.out.println("----------");
                }
            }
        });
    }

    /**
     * Creates the piece.
     *
     * @return the shape
     */
    private Shape createPiece() {
        final double d50 = 50f;
        Shape shape = createPieceFrame();
        shape.setTranslateX(getField().getFieldCoordX());
        shape.setTranslateY(getField().getFieldCoordY());
        shape.setLayoutX(d50);
        shape.setLayoutY(d50);
        return shape;
    }

    /**
     * Creates the piece frame.
     *
     * @return the rectangle
     */
    private Rectangle createPieceFrame() {
        final int i50n = -50;
        Rectangle frame = new Rectangle();
        frame.setX(i50n);
        frame.setY(i50n);
        frame.setWidth(puzzleSize);
        frame.setHeight(puzzleSize);
        return frame;
    }

    /**
     * Gets the field.
     *
     * @return the field
     */
    public final SlidingPuzzleField getField() {
        return field;
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public final ImageView getImage() {
        return image;
    }

    /**
     * Gets the checks if is visible.
     *
     * @return the checks if is visible
     */
    public final Boolean getIsVisible() {
        return isVisible;
    }

    /**
     * Gets the piece border.
     *
     * @return the piece border
     */
    public final Shape getPieceBorder() {
        return pieceBorder;
    }

    /**
     * Gets the piece cut.
     *
     * @return the piece cut
     */
    public final Shape getPieceCut() {
        return pieceCut;
    }

    /**
     * Gets the root x.
     *
     * @return the root x
     */
    public final double getRootX() {
        return rootX;
    }

    /**
     * Gets the root y.
     *
     * @return the root y
     */
    public final double getRootY() {
        return rootY;
    }

    /**
     * Gets the visible.
     *
     * @return the visible
     */
    public final Boolean getVisible() {
        return isVisible;
    }

    /**
     * Sets the active.
     */
    public final void setActive() {
        setDisable(false);
        setEffect(new DropShadow());
        toFront();
    }

    /**
     * Sets the field.
     *
     * @param feld the new field
     */
    public final void setField(final SlidingPuzzleField feld) {
        this.field = feld;
    }

    /**
     * Sets the image.
     *
     * @param newImage the new image
     */
    public final void setImage(final ImageView newImage) {
        this.image = newImage;
    }

    /**
     * Sets the inactive.
     */
    public final void setInactive() {
        setEffect(null);
        setDisable(true);
        toBack();
    }

    /**
     * Sets the checks if is visible.
     *
     * @param newIsVisible the new checks if is visible
     */
    public final void setIsVisible(final Boolean newIsVisible) {
        this.isVisible = newIsVisible;
    }

    /**
     * Sets the piece border.
     *
     * @param newPieceBorder the new piece border
     */
    public final void setPieceBorder(final Shape newPieceBorder) {
        this.pieceBorder = newPieceBorder;
    }

    /**
     * Sets the piece cut.
     *
     * @param newPieceCut the new piece cut
     */
    public final void setPieceCut(final Shape newPieceCut) {
        this.pieceCut = newPieceCut;
    }

    /**
     * Sets the visible.
     *
     * @param visible the new visible
     */
    public final void setVisible(final Boolean visible) {
        pieceBorder.setVisible(visible);
        pieceCut.setVisible(visible);
        image.setVisible(visible);
        field.setFree(!visible);
        this.isVisible = visible;
    }

    /*
     * (non-Javadoc)
     * @see javafx.scene.Node#toString()
     */
    @Override
    public final String toString() {
        return "PuzzlePiece [rootX=" + rootX + ", rootY=" + rootY + ", field="
                + field + ", image=" + image + ", visible=" + isVisible + "]";
    }
}
