package it.polimi.se2018.utils;

import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.container.DiceContainer;
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

}
