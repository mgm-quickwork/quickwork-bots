/**
 * @author Madhusudhan Mahale
 */
package io.gs.botkit.processor;

import org.json.JSONArray;
import org.json.JSONObject;

import io.gs.botkit.konstants.EntityStateConstants;
import io.gs.botkit.konstants.NLPConstants;
import io.gs.botkit.konstants.StateMachineConstants;
import io.gs.botkit.main.GupshupBotKit;
import io.gs.botkit.model.GupshupContext;
import io.gs.botkit.model.statemachine.Component;
import io.gs.botkit.model.statemachine.InputTransactionError;
import io.gs.botkit.model.statemachine.State;
import io.gs.botkit.model.statemachine.TransactionError;
import io.gs.botkit.model.statemachine.TransactionResult;
import io.gs.botkit.nlp.Contact;
import io.gs.botkit.nlp.DateTime;
import io.gs.botkit.nlp.GupshupNLPEngine;
import io.gs.botkit.nlp.Location;
import io.gs.botkit.nlp.NLPResponse;
import io.gs.botkit.nlp.Number;
import io.gs.botkit.util.GupshupHelper;

public class ComponentProcessor
{
	private Transaction tx;
	private GupshupContext gscontext;
	private GupshupBotKit botkit;
	private State state;
	private Component component;
	private TransactionResult result;
	private TransactionError error;
	private ITransactionHandler handler;
	private GupshupTransactionStateProcessor statemachine;
	private GupshupNLPEngine nlp;

	public void setError(TransactionError error)
	{
		this.error = error;
	}

	public void setResult(TransactionResult result)
	{
		this.result = result;
	}

	public ComponentProcessor(Transaction tx, GupshupContext gscontext, GupshupBotKit botkit, ITransactionHandler handler, GupshupTransactionStateProcessor statemachine, GupshupNLPEngine nlp)
	{
		this.botkit = botkit;
		this.gscontext = gscontext;
		this.tx = tx;
		this.state = tx.state();
		this.component = tx.component();
		this.handler = handler;
		this.statemachine = statemachine;
		this.nlp = nlp;
	}

	public boolean processPoll()
	{

		try

		{

			String userMessage = gscontext.getMessage();

			if (!state.isBotWaiting() && state.isStarted())
			{
				state.inProgress();
				botkit.gupshupAPI().sendMessage(component.getStartText(), gscontext.getContextObj());
				state.botWaiting();

			} else
			{
				// this can be the response is a response to the poll
				String res = assertPoll(userMessage);

				if (res != null)
				{
					JSONObject poll = component.getComponentObj().getJSONObject(res);
					String cType = poll.getString("type");
					// display & handler is possible actions

					if (cType.equalsIgnoreCase(StateMachineConstants.COMPONENT_TYPE.DISPLAY))
					{
						if (poll.has("message"))
						{
							String txt = poll.getString("message");
							botkit.gupshupAPI().sendMessage(txt, gscontext.getContextObj());
						}
					} else
					{
						if (poll.has("componentId"))
						{
							result.setHasNext(true);
							result.setNextComponentId(poll.getString("componentId"));
						}
					}
					state.completed();

				} else
				{

					boolean shouldLoop = handler.shouldLoop(tx, null, statemachine, botkit, gscontext, nlp);
					if (shouldLoop)
					{
						String msg = component.getLoopText();
						botkit.gupshupAPI().sendMessage(msg, gscontext.getContextObj());
					} else
					{
						state.abort();
					}

				}

			}

			return true;

		}

		catch (Exception ex)
		{
			TransactionError error = new TransactionError();
			handler.onError(tx, error, statemachine, botkit, gscontext, nlp);
			return false;
		}
	}

	public boolean processDisplay()
	{
		botkit.gupshupAPI().sendMessage(tx.component().getStartText(), gscontext.getContextObj());
		tx.state().completed();
		return true;

	}

	public boolean processMenu()
	{

		try
		{
			TransactionError error = new TransactionError();
			String startText = component.getStartText();
			JSONObject comObj = component.getComponentObj();
			boolean enableTextMatch = GupshupHelper.getBoolean("enableTextMatch", comObj);
			JSONArray menuArray = comObj.getJSONArray("menu");
			if (state.isBotWaiting() && !state.isCompleted())
			{

				// capturing response to a menu item
				// 1. check match type
				String message = gscontext.getMessage();
				int maxMenuCount = menuArray.length();
				boolean isNumber = GupshupHelper.isNumber(message);

				if (isNumber)
				{
					int selectedItem = GupshupHelper.parseNumber(message.trim());
					if (selectedItem > -1 && selectedItem != 0 && selectedItem <= maxMenuCount)
					{
						// valid menu count by number

						result.setType(StateMachineConstants.COMPONENT_TYPE.MENU);
						JSONObject menuItemJO = menuArray.getJSONObject(selectedItem - 1);
						result.setValueJSON(menuItemJO);
						if (menuItemJO.has("componentId"))
						{
							result.setHasNext(true);
							result.setNextComponentId(menuItemJO.getString("componentId"));
						} else
						{
							botkit.gupshupAPI().sendMessage(menuItemJO.getString("message"), gscontext.getContextObj());
						}

						state.completed();

					} else
					{
						if (handler.shouldLoop(tx, error, statemachine, botkit, gscontext, nlp))
						{
							String loopText = component.getLoopText();
							botkit.gupshupAPI().sendMessage(loopText, gscontext.getContextObj());
						} else
						{
							state.abort();

						}

					}

				} else
				{
					// check if the match type is also by String
					if (enableTextMatch)
					{
						// text processing
						int matchedItem = matchMenuItem(menuArray, message);
						if (matchedItem >= 0)
						{
							JSONObject menuItemJO = menuArray.getJSONObject(matchedItem);
							result.setValueJSON(menuItemJO);
							if (menuItemJO.has("componentId"))
							{
								result.setHasNext(true);
								result.setNextComponentId(menuItemJO.getString("componentId"));
							} else
							{
								botkit.gupshupAPI().sendMessage(menuItemJO.getString("message"), gscontext.getContextObj());
							}

							state.completed();

						} else
						{
							if (handler.shouldLoop(tx, error, statemachine, botkit, gscontext, nlp))
							{
								String loopText = component.getLoopText();
								botkit.gupshupAPI().sendMessage(loopText, gscontext.getContextObj());
							} else
							{
								state.abort();

							}
						}
					}
				}

			}
			if (!state.isBotWaiting() && state.isStarted())
			{
				// TODO Handle menu errors
				state.inProgress();
				String menuStr = createMenuString(comObj.getJSONArray("menu"));
				String finalString = startText + menuStr;
				botkit.gupshupAPI().sendMessage(finalString, gscontext.getContextObj());
				state.botWaiting();

			}

			return true;
		} catch (Exception ex)
		{

			handler.onError(tx, error, statemachine, botkit, gscontext, nlp);
			return false;
		}

	}

	public boolean processInputStep()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean processInputWhole()
	{

		InputTransactionError inputTxError = (InputTransactionError) error;

		try
		{
			String startText = component.getStartText();
			JSONObject comObj = component.getComponentObj();

			if (state.isBotWaiting() && !state.isCompleted())
			{
				// we need to process the user response for the type input
				// isExtract and parse
				// parse completes all the entities ?
				// if no ask should loop ?

				boolean isExtract = GupshupHelper.getBoolean("extract", comObj);
				if (isExtract)
				{
					// do-something
					JSONObject entities = comObj.getJSONObject("entities");
					String[] entityNames = JSONObject.getNames(entities);
					NLPResponse response = nlp.parse(gscontext.getMessage());

					if (null != response)
					{
						// first pass
						for (int i = 0; i < entityNames.length; i++)
						{
							String name = entityNames[i];
							JSONObject entity = entities.getJSONObject(name);
							String value = GupshupHelper.getString("value", entity);
							if (value == null || value.equalsIgnoreCase("null"))
							{
								JSONObject processedEntity = matchEntity(name, entity, response);
								if (processedEntity != null)
								{

									processedEntity.put(EntityStateConstants.KEY_IS_COMPLETE_STATUS, EntityStateConstants.COMPLETED);
									entities.put(name, processedEntity);

								} else
								{
									entity.put(EntityStateConstants.KEY_IS_COMPLETE_STATUS,EntityStateConstants.INCOMPLETE);
								}

							}

						}

						// System.err.println(entities.toString());

						System.out.println("######################################################################");
						// Second pass
						for (int i = 0; i < entityNames.length; i++)
						{

							JSONObject entity = entities.getJSONObject(entityNames[i]);
							String isCompleted = entity.getString(EntityStateConstants.KEY_IS_COMPLETE_STATUS);
							System.out.println(entityNames[i] + ":" + GupshupHelper.getString("value", entity));
							if (isCompleted.equalsIgnoreCase(EntityStateConstants.INCOMPLETE))
							{
								inputTxError.setErrorType(InputTransactionError.DATA_NOT_FOUND);
								inputTxError.setEntity(entity);
								if (handler.shouldLoop(tx, error, statemachine, botkit, gscontext, nlp))
								{
									String loopText = GupshupHelper.getString("loopText", entity);
									botkit.gupshupAPI().sendMessage(loopText, gscontext.getContextObj());
									break;
								} 
								else
								{
									// donot loop on this error;
									entity.put(EntityStateConstants.KEY_IS_COMPLETE_STATUS, EntityStateConstants.SKIPPED);

								}
							}

						}
						System.out.println("######################################################################");

					} else
					{
						botkit.gupshupAPI().sendMessage("no match found", gscontext.getContextObj());
					}

				}

			}
			if (!state.isBotWaiting() && state.isStarted())
			{
				state.inProgress();
				botkit.gupshupAPI().sendMessage(startText, gscontext.getContextObj());
				state.botWaiting();
			}

			return true;
		} catch (Exception ex)
		{
			handler.onError(tx, error, statemachine, botkit, gscontext, nlp);
			return false;
		}

	}

	public boolean processHandler()
	{
		try

		{

			//String userMessage = gscontext.getMessage();
			if (!state.isBotWaiting() && state.isStarted())
			{
				state.inProgress();
				if (component.getStartText() != null && component.getStartText().length() > 0)
					botkit.gupshupAPI().sendMessage(component.getStartText(), gscontext.getContextObj());
				state.botWaiting();

			} 
			else
			{
				state.completed();
			}
			return true;

		} catch (Exception ex)
		{
			handler.onError(tx, error, statemachine, botkit, gscontext, nlp);
			return false;
		}

	}

	// HELPER METHODS

	private String assertPoll(String message)
	{

		// TODO:NLP yes/no parsing
		// 1.true,0:false,-1:unknown
		if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("no"))
			return message;
		return null;
	}

	private String createMenuString(JSONArray menu)
	{
		String menuStr = "\n";
		for (int i = 0; i < menu.length(); i++)
		{
			int num = i + 1;
			JSONObject jo = menu.getJSONObject(i);
			String label = jo.getString("label");
			menuStr += num + "." + label + "\n";
		}

		return menuStr;
	}

	private int matchMenuItem(JSONArray menu, String message)
	{

		for (int i = 0; i < menu.length(); i++)
		{
			String item = menu.getJSONObject(i).getString("label");
			item = item.toLowerCase();
			if (item.matches("(.*)" + message.toLowerCase() + "(.*)"))
			{
				System.out.println("menu item matched:" + item);

				return i;
			} else
			{
				System.out.println("Did not match menu item for query:" + item);
			}
		}

		return -1;

	}

	private JSONObject matchEntity(String entityName, JSONObject entity, NLPResponse response)
	{
		switch (entityName)
		{
		case NLPConstants.ENTITY_TYPE_CONTACT:

			// TODO: check for i, me , if multiple check for confidence level.
			if (response.hasContacts())
			{
				Contact c = response.getContacts().get(0);
				entity.put("value", c.getValue());
				return entity;
			}
			break;

		case NLPConstants.ENTITY_TYPE_DATETIME:
			if (response.hasDateTimes())
			{
				DateTime d = response.getDateTimes().get(0);
				entity.put("value", d.getValue());
				return entity;
			}
			break;

		case NLPConstants.ENTITY_TYPE_LOCATION:
			if (response.hasLocations())
			{
				Location d = response.getLocations().get(0);
				entity.put("value", d.getLocation());
				return entity;
			}
			break;
		case NLPConstants.ENTITY_TYPE_NUMBER:
			if (response.hasNumbers())
			{
				Number d = response.getNumbers().get(0);
				entity.put("value", d.getValue() + "");
				return entity;
			}
			break;
		default:
			break;
		}

		return null;
	}

}
