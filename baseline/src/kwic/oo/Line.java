package kwic.oo;

import java.util.ArrayList;

public class Line
{
	ArrayList<String> words_;

	public Line()
	{
		super();
		words_ = new ArrayList<String>();
	}

	public void addEmptyWord()
	{
		words_.add(new String());

	}

	public void addWord(char[] w)
	{
		String temp = new String(w);
		words_.add(temp);
	}

	public void addWord(String w)
	{
		words_.add(w);
	}

	public void deleteWord(int word)
	{
		words_.remove(word);
	}

	public char getChar(int position, int word)
	{
		return words_.get(word).charAt(position);
	}

	public int getCharCount(int word)
	{
		return words_.get(word).length();
	}

	public String getWord(int word)
	{
		return words_.get(word);
	}

	public int getWordCount()
	{
		return words_.size();
	}

	public void setWord(char[] w, int word)
	{
		String temp = new String(w);
		words_.set(word, temp);
	}

	public void setWord(String w, int word)
	{
		words_.set(word, w);
	}

	public void clear()
	{
		words_.clear();
	}
}
