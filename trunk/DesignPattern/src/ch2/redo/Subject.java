package ch2.redo;

public interface Subject
{
	public void registerObserver(Observer o);

	public void removeObserver(Observer o);

	public void notifyObservers();

	public void changeNumber(String number);

}