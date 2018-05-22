package it.polimi.se2018.utils;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
import it.polimi.se2018.model.objectives.PrivateObjective;
import it.polimi.se2018.model.toolcards.ToolCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    private String dbName = "db.json";

    private File dbFile;

    private DiceContainer diceContainer;

    private JSONObject dbJsonObject;

    /**
     *
     * @param diceContainer
     */
    public Database(DiceContainer diceContainer) {
        this.diceContainer = diceContainer;
        ClassLoader classLoader = getClass().getClassLoader();
        dbFile = new File(classLoader.getResource(dbName).getFile());

        StringBuilder result = new StringBuilder("");
        try (Scanner scanner = new Scanner(dbFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbJsonObject = new JSONObject(result.toString());
    }

    /**
     * Creates an ArrayList of ToolCards from db.json object defined in the contructor
     * @return ArrayList containing all ToolCards
     */
    public ArrayList<ToolCard> loadToolCards() {
        ArrayList<ToolCard> toolCards = new ArrayList<>();

        JSONArray toolCardObjects = dbJsonObject.getJSONArray("toolcards");

        for (int i = 0; i < toolCardObjects.length(); i++) {
            JSONObject currentToolCardObject = toolCardObjects.getJSONObject(i);
            toolCards.add(new ToolCard( currentToolCardObject.getInt("id"),
                                        currentToolCardObject.getString("name"),
                                        currentToolCardObject.getString("description")));
        }
        return toolCards;
    }

    /**
     * Creates an ArrayList of PatternCards from db.json object defined in the contructor
     * @return ArrayList containing all PatternCards
     */
    public ArrayList<PatternCard> loadPatternCard() {
        ArrayList<PatternCard> patternCard = new ArrayList<>();

        JSONArray patternCardList = dbJsonObject.getJSONArray("patterncards");

        for (int i = 0; i < patternCardList.length(); i++) {
            JSONObject currentPatternCard = patternCardList.getJSONObject(i);
            patternCard.add(new PatternCard(diceContainer, currentPatternCard.getInt("id"),
                    currentPatternCard.getString("name"),
                    currentPatternCard.getInt("difficulty"),
                    currentPatternCard.getString("cells")));
        }
        return patternCard;
    }

    public ArrayList<PrivateObjective> loadPrivateObjective() {
        ArrayList<PrivateObjective> privateObjectives = new ArrayList<>();

        JSONArray privateObjectiveList = dbJsonObject.getJSONArray("privateobjectives");

        for (int i = 0; i < privateObjectiveList.length(); i++) {
            JSONObject currentPrivateObjective = privateObjectiveList.getJSONObject(i);
            privateObjectives.add(new PrivateObjective(diceContainer, currentPrivateObjective.getInt("id"),
                    currentPrivateObjective.getString("name"),
                    currentPrivateObjective.getString("description")));
        }
        return privateObjectives;
    }
}