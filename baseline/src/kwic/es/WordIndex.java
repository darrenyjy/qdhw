package kwic.es;

import java.util.Observable;
import java.util.Observer;

public class WordIndex implements Observer
{

	public void update(Observable observable, Object arg)
	{
		LineStorageWrapper shifts = (LineStorageWrapper) observable;
		LineStorageChangeEvent event = (LineStorageChangeEvent) arg;
		switch (event.getType())
		{
		case LineStorageChangeEvent.ADD:

			// shifts.wordsIndex();

			break;

		case LineStorageChangeEvent.DELETE:

			// shifts.wordsIndex();

			break;

		default:

			break;

		}
	}

}
// ----------------------------------------------------------------------
/**
 * Adds a new line to this line storage wrapper. The line is added at the end of
 * the storage. After adding the line all observers of this object are notified
 * about the change.
 * 
 * @param words
 *            new line to be added
 * @see #deleteLine
 * @see #insertLine
 * @see #getLine
 */

