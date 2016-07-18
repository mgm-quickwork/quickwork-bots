package io.gs.botkit.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

import io.gs.botkit.konstants.StateMachineConstants;
import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.model.statemachine.Component;
import io.gs.botkit.model.statemachine.InputTransactionError;
import io.gs.botkit.model.statemachine.InputTransactionResult;
import io.gs.botkit.model.statemachine.TransactionResult;
import io.gs.botkit.nlp.GupshupNLPEngine;
import io.gs.botkit.model.statemachine.State;
import io.gs.botkit.model.statemachine.TransactionError;
import io.gs.botkit.util.GupshupHelper;

public class GupshupTransactionStateProcessor
{

	private HashMap<String, TransactionStateCache> userTransactionStateMap;
	private static GupshupTransactionStateProcessor bsm;
	private JSONObject componentJSON;

	public HashMap<String, TransactionStateCache> getUserTransactionStateMap()
	{
		return userTransactionStateMap;
	}

	private GupshupTransactionStateProcessor()
	{};

	public void setBotKit(GupshupBotKit botkit)
	{
		bsm.setBotKit(botkit);
	}

	public static GupshupTransactionStateProcessor getInstance()
	{

		if (bsm == null)
		{
			bsm = new GupshupTransactionStateProcessor();
			bsm.userTransactionStateMap = new HashMap<String, TransactionStateCache>();

		}

		return bsm;

	}

	public JSONObject getComponentJSON()
	{
		return bsm.componentJSON;
	}

	public Transaction getNewTransactionState(String uid)
	{
		Transaction tx = new Transaction();
		tx.uid(uid);
		tx.state(new State().start());
		return tx;
	}

	public GupshupTransactionStateProcessor loadComponentJsonFromClassPath(String filename) throws IOException
	{
		if (bsm.componentJSON == null)
		{
			bsm.componentJSON = GupshupHelper.getComponentData(filename);
			System.out.println("loded component: " + filename + " | length: " + bsm.componentJSON.toString().length());
		} else
		{
			System.out.println("The Component JSON is already loaded:" + filename);
		}

		return this;

	}

	public Component getComponentById(String id)
	{
		JSONObject jo = componentJSON.getJSONObject("components").getJSONObject(id);
		Component c = new Component().build(jo);
		return c;
	}

	public GupshupTransactionStateProcessor loadComponentJsonFromFile(String filepath)
	{
		return this;

	}

	public GupshupTransactionStateProcessor loadComponentJsonFromUrl(String url)
	{
		return this;

	}

	public Transaction getCurrentTransaction(String id)
	{
		TransactionStateCache txCache = bsm.userTransactionStateMap.get(id);

		if (txCache != null)
		{
			System.out.println("Peeking In from Transaction Stack");
			return txCache.peek();
		}

		return null;

	}

	private void saveTransaction(Transaction tx)
	{
		TransactionStateCache txCache = bsm.userTransactionStateMap.get(tx.uid());
		if (txCache == null)
		{

			txCache = new TransactionStateCache(tx.uid());

		}
		txCache.push(tx);
		userTransactionStateMap.put(tx.uid(), txCache);

	}

	public static void resetTransactions(String id)
	{
		TransactionStateCache txCache = bsm.userTransactionStateMap.get(id);
		if (txCache != null)
		{
			txCache = null;
		}
	}

	public void process(Transaction tx, ITransactionHandler handler, GupshupBotKit botkit, GupshupContext gscontext, GupshupNLPEngine nlp)
	{
		log_start(tx);
		//save the tx 2.process tx 3.update the state 4. Callback
		
		JSONObject stateData = (JSONObject) tx.state().data();
		stateData = stateData == null ? new JSONObject() : stateData;
		stateData.put("channelType", gscontext.getChannelType());
		tx.state().data(stateData);
		
		saveTransaction(tx);
		String type = tx.component().getType();
		TransactionError error = new TransactionError();
		TransactionResult result = new TransactionResult();
		

		ComponentProcessor cp = new ComponentProcessor(tx, gscontext, botkit, handler, this, nlp);

		if (tx.state().isCompleted())
		{
			handler.handle(tx, null, this, botkit, gscontext, nlp);
			return;
		}
		switch (type)
		{
		case StateMachineConstants.COMPONENT_TYPE.DISPLAY:

			if (!cp.processDisplay())
				return;

			break;

		case StateMachineConstants.COMPONENT_TYPE.INPUT:
			error = new InputTransactionError();
			result = new InputTransactionResult();
			cp.setError(error);
			cp.setResult(result);
			
			JSONObject comObj = tx.component().getComponentObj();
			boolean processWhole =GupshupHelper.getBoolean("processWhole", comObj);
			
			if(processWhole)
			{
				if (!cp.processInputWhole())
					return;
			}
			else
			{
				if (!cp.processInputStep())
					return;
			}
			
			

			break;
		case StateMachineConstants.COMPONENT_TYPE.MENU:

			if (!cp.processMenu())
				return;

			break;
		case StateMachineConstants.COMPONENT_TYPE.POLL:

			if (!cp.processPoll())
				return;

			break;
		case StateMachineConstants.COMPONENT_TYPE.SURVEY:

			break;

		case StateMachineConstants.COMPONENT_TYPE.CATALOG:

			break;

		case StateMachineConstants.COMPONENT_TYPE.HANDLER:

			tx.state().inProgress();
			if (!cp.processHandler())
				return;

			break;

		default:
			break;
		}

		handler.handle(tx, result, this, botkit, gscontext, nlp);

	}

	public void printTransactionMap()
	{
		for (Entry<String, TransactionStateCache> tc : bsm.userTransactionStateMap.entrySet())
		{
			System.out.println(tc.getValue().getUid() + ":" + tc.getValue().getTransactionStack().size());
			// tc.getValue().print();
		}
	}

	private void log_start(Transaction tx)
	{
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("  STATE MACHINE STARTED PROCESSING TRANSACTION FOR:" + tx.uid());
		System.out.println("--------------------------------------------------------------------------");
	}

}
