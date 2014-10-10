package de.alextape.slidingpuzzle;

import java.io.File;
import java.io.PrintStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class SlidingPuzzleHighscore.
 */
public class SlidingPuzzleHighscore {

    /** The puzzle highscores. */
    private static Vector<SlidingPuzzleHighscore> puzzleHighscores;

    /**
     * Gets the puzzle highscores.
     *
     * @return the puzzle highscores
     */
    public static Vector<SlidingPuzzleHighscore> getPuzzleHighscores() {
        return puzzleHighscores;
    }

    /**
     * Read xml.
     *
     * @throws Exception the exception
     */
    public static void readXML() throws Exception {

        puzzleHighscores = new Vector<SlidingPuzzleHighscore>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new File("highscore.xml"));

        Element rootElement = doc.getDocumentElement();
        System.out.println("root element: " + rootElement.getNodeName());

        NodeList list = rootElement.getElementsByTagName("user");

        for (int i = 0; i < list.getLength(); i++) {

            Node userNode = list.item(i);
            new SlidingPuzzleHighscore(userNode.getAttributes()
                    .getNamedItem("name").getNodeValue(), userNode
                    .getAttributes().getNamedItem("score").getNodeValue());
        }
    }

    /**
     * Sets the puzzle highscores.
     *
     * @param newPuzzleHighscores the new puzzle highscores
     */
    public static final void setPuzzleHighscores(
            final Vector<SlidingPuzzleHighscore> newPuzzleHighscores) {
        SlidingPuzzleHighscore.puzzleHighscores = newPuzzleHighscores;
    }

    /**
     * Write xml.
     *
     * @throws Exception the exception
     */
    public static void writeXML() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document highScoreListe = builder.newDocument();

        Element root = highScoreListe.createElement("root");

        for (SlidingPuzzleHighscore thisScore : puzzleHighscores) {

            Element userElement = highScoreListe.createElement("user");

            userElement.setAttribute("name", thisScore.getUserName());
            userElement.setAttribute("score", thisScore.getUserScore());

            root.appendChild(userElement);
        }

        highScoreListe.appendChild(root);

        DOMSource source = new DOMSource(highScoreListe);

        PrintStream ps = new PrintStream("highscore.xml");
        StreamResult result = new StreamResult(ps);

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.transform(source, result);
    }

    /** The user name. */
    private String userName;

    /** The user score. */
    private String userScore;

    /**
     * Instantiates a new sliding puzzle highscore.
     *
     * @param newUserName the new user name
     * @param newUserScore the new user score
     */
    SlidingPuzzleHighscore(final String newUserName,
            final String newUserScore) {
        this.userName = newUserName;
        this.userScore = newUserScore;

        puzzleHighscores.add(this);
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Gets the user score.
     *
     * @return the user score
     */
    public final String getUserScore() {
        return userScore;
    }

    /**
     * Sets the user name.
     *
     * @param newUserName the new user name
     */
    public final void setUserName(final String newUserName) {
        this.userName = newUserName;
    }

    /**
     * Sets the user score.
     *
     * @param newUserScore the new user score
     */
    public final void setUserScore(final String newUserScore) {
        this.userScore = newUserScore;
    }

}
