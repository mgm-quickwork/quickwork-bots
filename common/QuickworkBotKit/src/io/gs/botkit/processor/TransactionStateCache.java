/**
 * @author Madhusudhan Mahale
 */
package io.gs.botkit.processor;

import java.util.Stack;

public class TransactionStateCache
{
	public Stack<Transaction> getTransactionStack()
	{
		return transactionStack;
	}

	private Stack<Transaction> transactionStack;
	private String uid;
	
	

	public TransactionStateCache(String uid)
	{
		transactionStack = new Stack<Transaction>();
		//transactionStack.setSize(StateMachineConstants.STATE_CACHE_COUNT);
		this.uid = uid;
	}

	public String getUid()
	{
		return uid;
	}

	public void push(Transaction tx)
	{
		//System.out.println("Pushing tx:"+tx.toString());
		transactionStack.push(tx);
		//System.out.println(transactionStack.isEmpty());
	}

	public Transaction pop()
	{
		return transactionStack.pop();
	}

	public Transaction peek()
	{
		return transactionStack.peek();
	}

	public void purge()
	{
		
		
	}
	
	public void print()
	{
		
		System.out.println(transactionStack.toString());
		
	}

}
