// -*- Java -*-
/*
 * <copyright>
 * 
 *  Copyright (c) 2002
 *  Institute for Information Processing and Computer Supported New Media (IICM),
 *  Graz University of Technology, Austria.
 * 
 * </copyright>
 * 
 * <file>
 * 
 *  Name:    Input.java
 *  
 *  Purpose: Input reads and parses the KWIC input file
 * 
 *  Created: 05 Nov 2002 
 * 
 *  $Id$
 * 
 *  Description:
 *    Input reads and parses the KWIC input file
 * </file>
 */

package kwic.es;

/*
 * $Log$
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * An object of the Input class is responsible for reading and parsing the
 * content of a KWIC input file. The format of the KWIC input file is as
 * follows:
 * <ul>
 * <li>Lines are separated by the line separator character(s) (on Unix '\n', on
 * Windows '\r\n')
 * <li>Each line consists of a number of words. Words are delimited by any
 * number and combination of the space chracter (' ') and the horizontal
 * tabulation chracter ('\t').
 * </ul>
 * The entered data is parsed and stored in memory as an instance of the
 * LineStorageWrapper class. The data is parsed in the following way:
 * <ul>
 * <li>All line separators are removed from the data; for each new line in the
 * file a new line is added to the LineStorageWrapper instance
 * <li>All horizontal tabulation word delimiters are removed
 * <li>All space character word delimiters are removed
 * <li>From characters between any two word delimiters a new string is created;
 * the new string is added to the LineStorageWrapper instance.
 * </ul>
 * 
 * @author dhelic
 * @version $Id$
 */

public class Input
{

	// ----------------------------------------------------------------------
	/**
	 * Fields
	 * 
	 */
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	/**
	 * Constructors
	 * 
	 */
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	/**
	 * Methods
	 * 
	 */
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	/**
	 * This method reads and parses a KWIC input file. If an I/O exception
	 * occurs during the execution of this method, an error message is shown and
	 * program exits.
	 * 
	 * @param file
	 *            name of KWIC input file
	 * @param line_storage
	 *            holds the parsed data
	 * @return void
	 */

	public void parse(String file, LineStorageWrapper line_storage)
	{
		try
		{

			// open the file for reading
			BufferedReader reader = new BufferedReader(new FileReader(file));

			// read all lines until EOF occurs
			// (Note that all line feed chracters are removed by the readLine()
			// method)
			String line = reader.readLine();
			while (line != null)
			{
				addLine(line_storage, line);
				// read next line
				line = reader.readLine();
			}

		} catch (FileNotFoundException exc)
		{

			// handle the exception if the file could not be found
			exc.printStackTrace();
			System.err.println("KWIC Error: Could not open " + file + "file.");
			System.exit(1);

		} catch (IOException exc)
		{

			// handle other system I/O exception
			exc.printStackTrace();
			System.err.println("KWIC Error: Could not read " + file + "file.");
			System.exit(1);

		}
	}

	/**
	 * Interact using command line interface
	 * 
	 * @param lines
	 */
	public void interact(LineStorageWrapper lines)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line;

		boolean exit = false;
		do
		{
			try
			{
				line = printPromt(reader);
				// exit
				if (line.equalsIgnoreCase("Q"))
				{
					exit = true;
				}

				else if (line.equalsIgnoreCase("A"))
				{
					line = printPromt("Input words: ", reader);
					addLine(lines, line);
				} else if (line.equalsIgnoreCase("D"))
				{
					printAllLines(lines);

					try
					{
						line = printPromt("Index to delete: ", reader);
						int index = Integer.parseInt(line);
						lines.deleteLine(index);
					} catch (NumberFormatException e)
					{
						System.out.println("Wrong Input! Aborted");
					} catch (IndexOutOfBoundsException e)
					{
						System.out.println("Invalid Index! Aborted");
					}

				} else if (line.equalsIgnoreCase("P"))
				{
					// TODO
				} else
				{
					System.out.println("Unknown Command! Input Q to exit");
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} while (!exit);

	}

	private void printAllLines(LineStorageWrapper line_storage)
	{
		for (int i = 0; i < line_storage.getLineCount(); i++)
		{
			System.out.print(i + ". ");
			System.out.println(line_storage.getLineAsString(i));
		}
	}

	private String printPromt(BufferedReader reader) throws IOException
	{
		return printPromt("Add, Print, Delete, Quit: ", reader);
	}

	private String printPromt(String promt, BufferedReader reader)
			throws IOException
	{
		System.out.print(promt);
		return reader.readLine();
	}

	private void addLine(LineStorageWrapper line_storage, String line)
	{
		// parse the line
		// the default delimiter set for StringTokenizer
		// is the set " \t\n\r\f" of characters
		// (Note that the delimiter characters are not
		// themselves treated as tokens)
		StringTokenizer tokenizer = new StringTokenizer(line);

		// if this is not an empty line keep the words so that we can
		// add the line
		// to the line storage
		if (tokenizer.countTokens() > 0)
		{

			// keep the words
			ArrayList words = new ArrayList();
			while (tokenizer.hasMoreTokens())
				words.add(tokenizer.nextToken());

			// create a String[] representation of the line
			String[] line_rep = new String[words.size()];
			for (int i = 0; i < line_rep.length; i++)
				line_rep[i] = (String) words.get(i);

			// add the new line to the storage
			line_storage.addLine(line_rep);
		}
	}

	// ----------------------------------------------------------------------
	/**
	 * Inner classes
	 * 
	 */
	// ----------------------------------------------------------------------

}