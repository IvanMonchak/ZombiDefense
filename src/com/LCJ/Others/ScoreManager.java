package com.LCJ.Others;


public class ScoreManager extends Object 
{
	int m_nScore=0;
	public static ScoreManager _sharedScore = null;
	
	public static ScoreManager sharedScoreManager() 
	{
		if (_sharedScore == null) 
		{
			_sharedScore = new ScoreManager();
		}
		
		return _sharedScore;
	}
	
	public ScoreManager()
	{
		super();
	}

	public static void releaseScoreManager()
	{
		if (_sharedScore!=null) 
		{
			_sharedScore = null;
		}
	}
	
	public void setCurrentScore(int score)
	{
		m_nScore = score;
	}
	
	public int getCurrentScore()
	{
		return m_nScore;
	}
}
