package com.lblabs.tools;

/*************************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue
 *
 *  A queue, implemented using a linked list.
 *
 *************************************************************************/

public class Queue
{
	private Node first;        // beginning of queue
	private Node last;         // end of queue
	private int cnt = 0;

	// helper linked list class
	private static class Node
	{
		private Object item;
		private Node next;
	}

	// is the queue empty?
	public boolean isEmpty() { return (first == null); }

	// add anItem to the queue
	public void enqueue(Object anItem)
	{
		cnt ++;
		Node x = new Node();
		x.item = anItem;
		x.next = null;
		if (isEmpty()) { first = x; last = x; }
		else { last.next = x; last = x; }
	}

	// remove and return the least recently added item
	public Object dequeue()
	{
		cnt --;
		if (isEmpty()) throw new RuntimeException("Queue underflow");
		Object val = first.item;
		first = first.next;
		return val;
	}

	public Object peek()
	{
		if (isEmpty()) throw new RuntimeException("Queue underflow");
		Object val = first.item;
//		first = first.next;
		return val;
	}

	public int count()
	{
		return cnt;
	}

	// a test client
	/*
	public static void main(String[] args)
	{
		Queue q = new Queue();
		q.enqueue("Vertigo");
		q.enqueue("Just Lose It");
		q.enqueue("Pieces of Me");
		q.enqueue("Pieces of Me");
		System.out.println(q.dequeue());
		q.enqueue("Drop It Like It's Hot");
		while (!q.isEmpty())
		{
			System.out.println(q.dequeue());
		}
	}
	*/
}