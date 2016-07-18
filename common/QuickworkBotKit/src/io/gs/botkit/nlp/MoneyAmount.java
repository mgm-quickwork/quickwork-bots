package io.gs.botkit.nlp;

import io.gs.botkit.konstants.NLPConstants;

public class MoneyAmount extends Entity
{
	private String unit;
	private double value;

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public MoneyAmount()
	{
		this.setName(NLPConstants.ENTITY_TYPE_AMOUNT_OF_MONEY);
	}
}