package de.alextape.slidingpuzzle;

/**
 * The Class SlidingPuzzleField.
 */
public class SlidingPuzzleField {

    /** The field col. */
    private int fieldCol;

    /** The field coord x. */
    private double fieldCoordX;

    /** The field coord y. */
    private double fieldCoordY;

    /** The field row. */
    private int fieldRow;

    /** The is free. */
    private boolean isFree;

    /**
     * Instantiates a new sliding puzzle field.
     *
     * @param col the col
     * @param row the row
     * @param coordX the coord x
     * @param coordY the coord y
     */
    SlidingPuzzleField(final int col, final int row,
            final double coordX, final double coordY) {

        this.fieldCol = col;
        this.fieldRow = row;
        this.fieldCoordX = coordX;
        this.fieldCoordY = coordY;
        this.isFree = false;

    }

    /**
     * Gets the field col.
     *
     * @return the field col
     */
    public final int getFieldCol() {
        return fieldCol;
    }

    /**
     * Gets the field coord x.
     *
     * @return the field coord x
     */
    public final double getFieldCoordX() {
        return fieldCoordX;
    }

    /**
     * Gets the field coord y.
     *
     * @return the field coord y
     */
    public final double getFieldCoordY() {
        return fieldCoordY;
    }

    /**
     * Gets the field row.
     *
     * @return the field row
     */
    public final int getFieldRow() {
        return fieldRow;
    }

    /**
     * Gets the free.
     *
     * @return the free
     */
    public final boolean getFree() {
        return isFree;
    }

    /**
     * Sets the field col.
     *
     * @param newFieldCol the new field col
     */
    public final void setFieldCol(final int newFieldCol) {
        this.fieldCol = newFieldCol;
    }

    /**
     * Sets the field coord x.
     *
     * @param newFieldCoordX the new field coord x
     */
    public final void setFieldCoordX(final double newFieldCoordX) {
        this.fieldCoordX = newFieldCoordX;
    }

    /**
     * Sets the field coord y.
     *
     * @param newFieldCoordY the new field coord y
     */
    public final void setFieldCoordY(final double newFieldCoordY) {
        this.fieldCoordY = newFieldCoordY;
    }

    /**
     * Sets the field row.
     *
     * @param newFieldRow the new field row
     */
    public final void setFieldRow(final int newFieldRow) {
        this.fieldRow = newFieldRow;
    }

    /**
     * Sets the free.
     *
     * @param free the new free
     */
    public final void setFree(final boolean free) {
        this.isFree = free;
    }

}
