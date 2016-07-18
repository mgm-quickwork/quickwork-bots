package io.gs.botkit.processor;

import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.model.statemachine.TransactionError;
import io.gs.botkit.model.statemachine.TransactionResult;
import io.gs.botkit.nlp.GupshupNLPEngine;

public interface ITransactionHandler
{

	boolean shouldLoop(Transaction tx, TransactionError error, GupshupTransactionStateProcessor gupshupTransactionStateProcessor, GupshupBotKit botkit,GupshupContext gscontext,GupshupNLPEngine nlp);

	void handle(Transaction tx, TransactionResult result, GupshupTransactionStateProcessor statemachine, GupshupBotKit botkit, GupshupContext gscontext,GupshupNLPEngine nlp);
	
	void onError(Transaction tx, TransactionError error, GupshupTransactionStateProcessor statemachine, GupshupBotKit botkit, GupshupContext gscontext,GupshupNLPEngine nlp);
}
