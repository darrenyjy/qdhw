package com.lblabs.sync;

import java.util.ArrayList;

public class Locker
{
	ArrayList readLockList = new ArrayList();
	ArrayList writeLockList = new ArrayList();

	public void setReadLock(String readerThreadID)
	{
		readLockList.add(readerThreadID);
	}
	
	public void setWriteLock(String writerThreadID)
	{
		writeLockList.add(writerThreadID);
	}

	public void releaseReadLock(String readerThreadID)
	{
		readLockList.remove(readerThreadID);
	}

	public void releaseWriteLock(String writerThreadID)
	{
		writeLockList.remove(writerThreadID);
	}

	public boolean getReadPermission()
	{
		return writeLockList.isEmpty();
	}

	public boolean getWritePermission()
	{
		return readLockList.isEmpty() & writeLockList.isEmpty();
	}
}
