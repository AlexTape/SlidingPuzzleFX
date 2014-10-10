package de.alextape.slidingpuzzle;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Class SlidingPuzzleGame.
 */
public class SlidingPuzzleGame extends Application {

    /** The flow. */
    private static Timeline flow;

    /** The game board. */
    private static SlidingPuzzleBoard gameBoard;

    /** The merge procedures. */
    private static int mergeProcedures = 0;

    /** The move count. */
    private static int moveCount;

    /** The this stage. */
    private static Stage thisStage;

    /** The user name. */
    private static String userName = null;

    /**
     * Activate fields.
     */
    public static void activateFields() {
        for (final SlidingPuzzlePiece puzzleTeil : SlidingPuzzlePiece
                .getPuzzlePiece()) {
            puzzleTeil.setActive();
        }
    }

    /**
     * Gets the flow.
     *
     * @return the flow
     */
    public static Timeline getFlow() {
        return flow;
    }

    /**
     * Gets the game board.
     *
     * @return the game board
     */
    public static SlidingPuzzleBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Gets the merge procedures.
     *
     * @return the merge procedures
     */
    public static int getMergeProcedures() {
        return mergeProcedures;
    }

    /**
     * Gets the move count.
     *
     * @return the move count
     */
    public static int getMoveCount() {
        return moveCount;
    }

    /**
     * Gets the this stage.
     *
     * @return the this stage
     */
    public static Stage getThisStage() {
        return thisStage;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Hide one field.
     */
    public static void hideOneField() {
        int emptyIndex = randomDigit(1, SlidingPuzzlePiece.getPuzzlePiece()
                .size());
        SlidingPuzzlePiece.getPuzzlePiece().get(emptyIndex).setVisible(false);
        SlidingPuzzlePiece.getPuzzlePiece().get(emptyIndex).getField()
                .setFree(true);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    /**
     * Puzzle ready.
     */
    public static void puzzleReady() {
        boolean isWin = true;
        for (SlidingPuzzlePiece thisPiece
                : SlidingPuzzlePiece.getPuzzlePiece()) {
            if (thisPiece.getField().getFieldCoordX() != thisPiece.getRootX()) {
                isWin = false;
            }
            if (thisPiece.getField().getFieldCoordY() == thisPiece.getRootY()) {
                isWin = false;
            }
        }
        if (isWin) {
            System.out.println("Game won.");
            try {
                SlidingPuzzleHighscore.readXML();
                new SlidingPuzzleHighscore(userName, String.valueOf(moveCount));
                SlidingPuzzleHighscore.writeXML();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Random digit.
     *
     * @param a the a
     * @param b the b
     * @return the int
     */
    public static int randomDigit(final int a, final int b) {
        return (int) (Math.random() * (b - a) + a);
    }

    /**
     * Reset game board.
     */
    public static void resetGameBoard() {
        for (SlidingPuzzlePiece puzzleTeil : SlidingPuzzlePiece
                .getPuzzlePiece()) {
            if (puzzleTeil.getVisible() == null) {
                puzzleTeil.setVisible(true);
            }
        }
    }

    /**
     * Sets the flow.
     *
     * @param newFlow the new flow
     */
    public static void setFlow(final Timeline newFlow) {
        SlidingPuzzleGame.flow = newFlow;
    }

    /**
     * Sets the game board.
     *
     * @param newGameBoard the new game board
     */
    public static void setGameBoard(final SlidingPuzzleBoard newGameBoard) {
        SlidingPuzzleGame.gameBoard = newGameBoard;
    }

    /**
     * Sets the merge count.
     *
     * @param mergeCount the new merge count
     */
    public static void setMergeCount(final int mergeCount) {
        SlidingPuzzleGame.mergeProcedures = mergeCount;
    }

    /**
     * Sets the merge procedures.
     *
     * @param newMergeProcedures the new merge procedures
     */
    public static void setMergeProcedures(final int newMergeProcedures) {
        SlidingPuzzleGame.mergeProcedures = newMergeProcedures;
    }

    /**
     * Sets the move count.
     *
     * @param newMoveCount the new move count
     */
    public static void setMoveCount(final int newMoveCount) {
        SlidingPuzzleGame.moveCount = newMoveCount;
    }

    /**
     * Sets the this stage.
     *
     * @param newStage the new this stage
     */
    public static void setThisStage(final Stage newStage) {
        SlidingPuzzleGame.thisStage = newStage;
    }

    /**
     * Sets the user name.
     *
     * @param newUserName the new user name
     */
    public static void setUserName(final String newUserName) {
        SlidingPuzzleGame.userName = newUserName;
    }

    /**
     * Creates the game board.
     *
     * @return the group
     */
    public final Group createGameBoard() {

        flow = new Timeline();
        Group group = new Group();

        Image image = new Image(getClass().
                getResourceAsStream("socialite.jpg"));

        int colCount = (int) (image.getWidth()
                / SlidingPuzzlePiece.getPuzzleSize());
        int rowCount = (int) (image.getHeight()
                / SlidingPuzzlePiece.getPuzzleSize());

        gameBoard = new SlidingPuzzleBoard(colCount, rowCount);

        for (int col = 0; col < colCount; col++) {
            for (int row = 0; row < rowCount; row++) {
                int rootX = col * SlidingPuzzlePiece.getPuzzleSize();
                int rootY = row * SlidingPuzzlePiece.getPuzzleSize();
                new SlidingPuzzlePiece(image, rootX, rootY,
                        gameBoard.getWidth(), gameBoard.getHeight(),
                        new SlidingPuzzleField(col, row, rootX, rootY));
            }
        }

        gameBoard.getChildren().addAll(SlidingPuzzlePiece.getPuzzlePiece());

        resetGameBoard();
        mergePuzzlePieces(gameBoard, SlidingPuzzlePiece.getPuzzlePiece());
        hideOneField();
        activateFields();

        final int i10 = 10;
        VBox displayField = new VBox(i10);
        displayField.setPadding(new Insets(i10, i10, i10, i10));
        displayField.getChildren().addAll(createMenu(), gameBoard);
        group.getChildren().addAll(displayField);

        return group;
    }

    /**
     * Creates the highscore.
     *
     * @return the group
     */
    @SuppressWarnings("unchecked")
    public final Group createHighscore() {

        try {
            SlidingPuzzleHighscore.readXML();
            // new PuzzleHighscore("TEST", "999");
            // PuzzleHighscore.writeXML();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final int i20 = 20;
        final Label label = new Label("Highscores");
        label.setFont(new Font("Arial", i20));

        TableView<SlidingPuzzleHighscore> tableView =
                new TableView<SlidingPuzzleHighscore>();

        tableView.setEditable(false);

        ObservableList<SlidingPuzzleHighscore> observableList = FXCollections
                .observableList(SlidingPuzzleHighscore.getPuzzleHighscores());

        TableColumn<SlidingPuzzleHighscore, String> scoreColumn =
                new TableColumn<SlidingPuzzleHighscore, String>(
                        "Score");
        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<SlidingPuzzleHighscore, String>(
                        "userScore"));
        TableColumn<SlidingPuzzleHighscore, String> userColumn =
                new TableColumn<SlidingPuzzleHighscore, String>(
                        "User");
        userColumn.setCellValueFactory(
                new PropertyValueFactory<SlidingPuzzleHighscore, String>(
                        "userName"));

        tableView.setItems(observableList);

        scoreColumn.setSortType(SortType.DESCENDING);

        tableView.getColumns().addAll(scoreColumn, userColumn);

        final int i5 = 5;
        final int i10 = 10;

        final VBox highscoreVBox = new VBox();
        highscoreVBox.setSpacing(i5);
        highscoreVBox.setPadding(new Insets(i10, i10, i10, i10));
        highscoreVBox.getChildren().addAll(label, tableView);

        Group highscoreBox = new Group(highscoreVBox);

        return highscoreBox;
    }

    /**
     * Creates the menu.
     *
     * @return the v box
     */
    public final VBox createMenu() {

        flow.play();

        final int i20 = 20;
        VBox menuBox = new VBox(i20);

        final MenuBar menuBar = new MenuBar();

        final MenuItem newMenu = MenuItemBuilder.create().text("New").build();
        newMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                newGame();
            }
        });

        final MenuItem mergeMenu = MenuItemBuilder.create().text("Merge")
                .build();
        mergeMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                newGame();
                mergePuzzlePieces(gameBoard,
                        SlidingPuzzlePiece.getPuzzlePiece());
                flow.playFromStart();
            }
        });

        final MenuItem resolveMenu = MenuItemBuilder.create().text("Resolve")
                .build();
        resolveMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent actionEvent) {
                for (final SlidingPuzzlePiece puzzlePiece : SlidingPuzzlePiece
                        .getPuzzlePiece()) {
                    puzzlePiece.setInactive();
                    flow.getKeyFrames().add(
                            new KeyFrame(Duration.seconds(1), new KeyValue(
                                    puzzlePiece.translateXProperty(), 0),
                                    new KeyValue(puzzlePiece
                                            .translateYProperty(), 0)));
                }
                flow.playFromStart();
            }
        });

        MenuItem easyLevel = MenuItemBuilder.create().text("Easy").build();
        easyLevel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                final int i100 = 100;
                final int i10 = 10;
                SlidingPuzzlePiece.setPuzzleSize(i100);
                setMergeCount((gameBoard.getCols() * gameBoard.getRows())
                        * i10);
                newGame();
            }
        });
        MenuItem normalLevel = MenuItemBuilder.create().text("Normal").build();
        normalLevel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                final int i75 = 75;
                final int i50 = 50;
                SlidingPuzzlePiece.setPuzzleSize(i75);
                setMergeCount((gameBoard.getCols() * gameBoard.getRows())
                        * i50);
                newGame();
            }
        });
        MenuItem hardLevel = MenuItemBuilder.create().text("Hard").build();
        hardLevel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                final int i50 = 50;
                final int i100 = 100;
                SlidingPuzzlePiece.setPuzzleSize(i50);
                setMergeCount((gameBoard.getCols() * gameBoard.getRows())
                        * i100);
                newGame();
            }
        });
        MenuItem harderLevel = MenuItemBuilder.create().text("Harder").build();
        harderLevel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                final int i25 = 25;
                final int i200 = 200;
                SlidingPuzzlePiece.setPuzzleSize(i25);
                setMergeCount((gameBoard.getCols() * gameBoard.getRows())
                        * i200);
                newGame();
            }
        });

        final Menu levelMenu = MenuBuilder.create().text("GameLevel")
                .items(easyLevel, normalLevel, hardLevel, harderLevel).build();

        final MenuItem highscoreMenu = MenuItemBuilder.create()
                .text("Highscore").build();
        highscoreMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                Stage stage = new Stage();
                Scene highscore = new Scene(createHighscore());
                stage.setTitle("Sliding Puzzle - Highscores");
                stage.sizeToScene();
                stage.setScene(highscore);
                stage.toFront();
                stage.show();
                thisStage = stage;
                thisStage.centerOnScreen();
            }
        });

        Menu menuGame = MenuBuilder.create().text("Game")
                .items(newMenu, mergeMenu, resolveMenu).build();
        Menu menuOptions = MenuBuilder.create().text("Options")
                .items(levelMenu, highscoreMenu).build();

        menuBar.getMenus().addAll(menuGame, menuOptions);
        menuBox.getChildren().addAll(menuBar);
        menuBar.isCache();

        return menuBox;
    }

    /**
     * Inits the.
     *
     * @param primaryStage the primary stage
     */
    private void init(final Stage primaryStage) {

        thisStage = primaryStage;
        thisStage.setTitle("Sliding Puzzle");

        StackPane sPane = new StackPane();

        Label label = new Label("Name: ");
        final TextField nameField = new TextField();
        final Button setNameField = new Button("OK");

        final int i5 = 5;
        final int i10 = 10;
        final int i20 = 20;
        final int i100 = 100;

        nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    setNameField.fire();
                }
            }
        });
        setNameField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent actionEvent) {
                if (nameField.getText() != "" && nameField.getText() != null) {
                    userName = nameField.getText();
                }
                SlidingPuzzlePiece.setPuzzleSize(i100);
                newGame();
            }
        });

        VBox welcomeBox = new VBox(i5);
        Text welcomeText = new Text(i100, i100,
                "Welcome to the Sliding Puzzle\n\nWhats your name?\n");
        welcomeText.setFont(new Font(i20));
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeBox.getChildren().add(welcomeText);
        welcomeBox.setPadding(new Insets(i10, i10, i10, i10));

        HBox displayBox = new HBox(i5);
        displayBox.getChildren().addAll(label, nameField, setNameField);
        displayBox.setAlignment(Pos.CENTER);

        welcomeBox.getChildren().add(displayBox);

        sPane.getChildren().add(welcomeBox);

        thisStage.centerOnScreen();
        thisStage.toFront();
        thisStage.setScene(new Scene(sPane));
        thisStage.show();

    }

    /**
     * Merge puzzle pieces.
     *
     * @param newGameBoard the new game board
     * @param puzzlePiece the puzzle piece
     */
    public final void mergePuzzlePieces(final SlidingPuzzleBoard newGameBoard,
            final List<SlidingPuzzlePiece> puzzlePiece) {

        for (int i = 0; i <= mergeProcedures; i++) {

            SlidingPuzzlePiece firstPiece = SlidingPuzzlePiece.getPuzzlePiece()
                    .get(randomDigit(0, SlidingPuzzlePiece.getPuzzlePiece()
                            .size()));
            SlidingPuzzleField firstField = firstPiece.getField();

            SlidingPuzzlePiece secondPiece = SlidingPuzzlePiece
                    .getPuzzlePiece().get(
                            randomDigit(0, SlidingPuzzlePiece.getPuzzlePiece()
                                    .size()));
            SlidingPuzzleField secondField = secondPiece.getField();

            int firstFromX = firstField.getFieldCol();
            int firstFromY = firstField.getFieldRow();

            int firstToX = secondField.getFieldCol();
            int firstToY = secondField.getFieldRow();

            System.out.println("switching " + firstFromX + "-" + firstFromY
                    + " with " + firstToX + "-" + firstToY);

            double firstFreeX = secondField.getFieldCoordX();
            double firstFreeY = secondField.getFieldCoordY();

            double secondFreeX = firstField.getFieldCoordX();
            double secondFreeY = firstField.getFieldCoordY();

            double firstShuffleX = (firstFreeX - firstField.getFieldCoordX())
                    + firstPiece.getTranslateX();
            double firstShuffleY = (firstFreeY - firstField.getFieldCoordY())
                    + firstPiece.getTranslateY();

            double secondShuffleX = (secondFreeX - secondField.getFieldCoordX())
                    + secondPiece.getTranslateX();
            double secondShuffleY = (secondFreeY - secondField.getFieldCoordY())
                    + secondPiece.getTranslateY();

            firstPiece.setTranslateX(firstShuffleX);
            firstPiece.setTranslateY(firstShuffleY);

            secondPiece.setTranslateX(secondShuffleX);
            secondPiece.setTranslateY(secondShuffleY);

            secondField.setFieldCoordX(firstField.getFieldCoordX());
            secondField.setFieldCoordY(firstField.getFieldCoordY());
            secondField.setFieldCol(firstFromX);
            secondField.setFieldRow(firstFromY);

            firstField.setFieldCoordX(firstFreeX);
            firstField.setFieldCoordY(firstFreeY);
            firstField.setFieldCol(firstToX);
            firstField.setFieldRow(firstToY);

        }
        System.out.println("merged..");

    }

    /**
     * New game.
     */
    public final void newGame() {

        if (SlidingPuzzlePiece.getPuzzlePiece() != null) {
            SlidingPuzzlePiece.getPuzzlePiece().clear();
        }

        Group newGroup = new Group();
        newGroup = createGameBoard();
        thisStage.setTitle("Sliding Puzzle - " + userName);
        thisStage.sizeToScene();
        thisStage.centerOnScreen();
        thisStage.toFront();
        thisStage.setScene(new Scene(newGroup));
        thisStage.show();

        moveCount = 0;
        flow.play();
    }

    /*
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public final void start(final Stage stage) throws Exception {
        init(stage);
        stage.show();
    }
}
