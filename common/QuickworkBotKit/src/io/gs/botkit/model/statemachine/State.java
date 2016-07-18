package io.gs.botkit.model.statemachine;

import io.gs.botkit.util.GupshupHelper;

public class State
{
	@Override
	public String toString()
	{
		return "State [isStarted=" + isStarted + ", isInProgress=" + isInProgress + ", isBotWaiting=" + isBotWaiting + ", isUserWaiting=" + isUserWaiting + ", isCompleted=" + isCompleted
				+ ", isAborted=" + isAborted + ", startTime=" + startTime + ", completedTime=" + completedTime + ", lastUpdatedTime=" + lastUpdatedTime + ", data=" + data + "]";
	}

	private boolean isStarted;
	private boolean isInProgress;
	private boolean isBotWaiting;
	private boolean isUserWaiting;
	private boolean isCompleted;
	private boolean isAborted;
	private long startTime;
	private long completedTime;
	private long lastUpdatedTime;
	private Object data;

	public State()
	{
		resetOnComplete();
		this.startTime = GupshupHelper.getCurrentTime();
		this.lastUpdatedTime = GupshupHelper.getCurrentTime();
	}

	public long getLastUpdatedTime()
	{
		return lastUpdatedTime;
	}

	public boolean isStarted()
	{
		return isStarted;
	}

	public boolean isAborted()
	{
		return isAborted;
	}

	public State start()
	{
		resetOnComplete();
		this.isStarted = true;
		return this;
	}

	public State abort()
	{
		this.isAborted = true;
		return this;
	}

	public boolean isInProgress()
	{
		return isInProgress;
	}
	
	public State setInProgress(boolean isInProgress)
	{
		this.isInProgress = isInProgress;
		return this;
	}

	public State inProgress()
	{
		this.isInProgress = true;
		return this;
	}

	public boolean isBotWaiting()
	{
		return isBotWaiting;
	}

	public State botWaiting()
	{
		this.isBotWaiting = true;
		return this;
	}

	public boolean isUserWaiting()
	{
		return isUserWaiting;
	}

	public State userWaiting()
	{
		this.isUserWaiting = true;
		return this;
	}

	public boolean isCompleted()
	{
		return isCompleted;
	}

	public State completed()
	{
		
		resetOnComplete();
		this.isCompleted = true;
		return this;
	}

	public long getStartTime()
	{
		return startTime;
	}

	public long getCompletedTime()
	{
		return completedTime;
	}

	public Object data()
	{
		return data;
	}

	public State data(Object data)
	{
		this.data = data;
		return this;
	}
	private void resetOnComplete()
	{
		this.isBotWaiting = false;
		this.isUserWaiting = false;
		this.isStarted = false;
		this.isAborted = false;
		this.isCompleted = false;
		
		
	}
}
