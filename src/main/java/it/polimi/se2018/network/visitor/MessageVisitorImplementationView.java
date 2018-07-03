package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;
import it.polimi.se2018.view.console.ViewClientConsole;

public class MessageVisitorImplementationView implements MessageVisitorInterface {

    private ViewClientConsole viewClientConsole;

    public MessageVisitorImplementationView(ViewClientConsole viewClientConsole){ this.viewClientConsole = viewClientConsole;}

    @Override
    public void visitMethodCallMessage(MethodCallMessage methodCallMessage) {

    }

    @Override
    public void visitModelChangedMessageConnected(ModelChangedMessageConnected modelChangedMessageConnected) {
        //todo
    }

    @Override
    public void visitModelChangedMessageDiceArena(ModelChangedMessageDiceArena modelChangedMessageDiceArena) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessageDiceArena);
    }

    @Override
    public void visitModelChangedMessageDiceOnPatternCard(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessageDiceOnPatternCard);
    }

    @Override
    public void visitModelChangedMessageEndGame(ModelChangedMessageEndGame modelChangedMessageEndGame) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessageEndGame);
    }

    @Override
    public void visitModelChangedMessageMoveFailed(ModelChangedMessageMoveFailed modelChangedMessageMoveFailed) {
        viewClientConsole.update(modelChangedMessageMoveFailed);
    }

    @Override
    public void visitModelChangedMessagePatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessagePatternCard);
    }

    @Override
    public void visitModelChangedMessagePrivateObjective(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessagePrivateObjective);
    }

    @Override
    public void visitModelChangedMessagePublicObjective(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessagePublicObjective);
    }

    @Override
    public void visitModelChangedMessageChangeGamePhase(ModelChangedMessageChangeGamePhase modelChangedMessageChangeGamePhase) {
        viewClientConsole.update(modelChangedMessageChangeGamePhase);
    }

    @Override
    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh) {
        viewClientConsole.update(modelChangedMessageRefresh);
    }

    @Override
    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessageRound);
    }

    @Override
    public void visitModelChangedMessageNewEvent(ModelChangedMessageNewEvent modelChangedMessageNewEvent) {
        viewClientConsole.update(modelChangedMessageNewEvent);
    }

    @Override
    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessageToolCard);
    }

    @Override
    public void visitModelChangedMessageTokensLeft(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft) {
        viewClientConsole.getViewClientConsolePrint().update(modelChangedMessageTokensLeft);
    }

    @Override
    public void visitModelChangedMessagePlayerAFK(ModelChangedMessagePlayerAFK modelChangedMessagePlayerAFK) {
        viewClientConsole.update(modelChangedMessagePlayerAFK);
    }

    @Override
    public void visitPlayerMessageDie(PlayerMessageDie playerMessageDie) {

    }

    @Override
    public void visitPlayerMessageEndTurn(PlayerMessageEndTurn playerMessageEndTurn) {

    }

    @Override
    public void visitPlayerMessageSetup(PlayerMessageSetup playerMessageSetup) {

    }

    @Override
    public void visitPlayerMessageToolCard(PlayerMessageToolCard playerMessageToolCard) {

    }

    @Override
    public void visitPlayerMessageNotAFK(PlayerMessageNotAFK playerMessageNotAFK) {

    }

    @Override
    public void visitHeartbeatMessage(HeartbeatMessage heartbeatMessage) {

    }
}
