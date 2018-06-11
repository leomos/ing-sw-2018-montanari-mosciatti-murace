package it.polimi.se2018.network.visitor;

import it.polimi.se2018.model.events.*;

public interface MessageVisitorInterface {

    public void visitMethodCallMessage(MethodCallMessage methodCallMessage);

    public void visitModelChangedMessageConnected(ModelChangedMessageConnected modelChangedMessageConnected);

    public void visitModelChangedMessageDiceArena(ModelChangedMessageDiceArena modelChangedMessageDiceArena);

    public void visitModelChangedMessageDiceOnPatternCard(ModelChangedMessageDiceOnPatternCard modelChangedMessageDiceOnPatternCard);

    public void visitModelChangedMessageEndGame(ModelChangedMessageEndGame modelChangedMessageEndGame);

    public void visitModelChangedMessageMoveFailed(ModelChangedMessageMoveFailed modelChangedMessageMoveFailed);

    public void visitModelChangedMessagePatternCard(ModelChangedMessagePatternCard modelChangedMessagePatternCard);

    public void visitModelChangedMessagePrivateObjective(ModelChangedMessagePrivateObjective modelChangedMessagePrivateObjective);

    public void visitModelChangedMessagePublicObjective(ModelChangedMessagePublicObjective modelChangedMessagePublicObjective);

    public void visitModelChangedMessageRefresh(ModelChangedMessageRefresh modelChangedMessageRefresh);

    public void visitModelChangedMessageRound(ModelChangedMessageRound modelChangedMessageRound);

    public void visitModelChangedMessageToolCard(ModelChangedMessageToolCard modelChangedMessageToolCard);

    public void visitModelChangedMessageTokensLeft(ModelChangedMessageTokensLeft modelChangedMessageTokensLeft);

    public void visitPlayerMessageDie(PlayerMessageDie playerMessageDie);

    public void visitPlayerMessageEndTurn(PlayerMessageEndTurn playerMessageEndTurn);

    public void visitPlayerMessageSetup(PlayerMessageSetup playerMessageSetup);

    public void visitPlayerMessageToolCard(PlayerMessageToolCard playerMessageToolCard);

    public void visitHeartbeatMessage(HeartbeatMessage heartbeatMessage);
}
