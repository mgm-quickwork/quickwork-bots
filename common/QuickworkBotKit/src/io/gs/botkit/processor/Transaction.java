package io.gs.botkit.processor;

import io.gs.botkit.model.statemachine.Component;
import io.gs.botkit.model.statemachine.State;

public class Transaction
{
	@Override
	public String toString()
	{
		return "Transaction [uid=" + uid + ", component=" + component + ", state=" + state + "]";
	}

	private String uid;
	private Component component;
	private State state;

	public State state()
	{
		return this.state;
	}

	public String uid()
	{
		return this.uid;
	}

	public void uid(String uid)
	{
		 this.uid = uid;
	}

	public Component component()
	{
		return this.component;
	}

	public void component(Component component)
	{
		this.component = component;
	}

	public void state(State state2)
	{
		this.state = state2;

	}
}
