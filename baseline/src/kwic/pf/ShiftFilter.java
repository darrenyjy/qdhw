package kwic.pf;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShiftFilter extends Filter
{
	static HashSet<String> noiseList;
	private static Log log = LogFactory.getLog(ShiftFilter.class);

	public ShiftFilter(Pipe input, Pipe output)
	{
		super(input, output);
		noiseList = new HashSet<String>();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(
					"noise.txt"));
			String noise = reader.readLine();
			while (null != noise)
			{
				noiseList.add(noise);
				noise = reader.readLine();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		log.debug("SF created");

	}

	@Override
	protected void transform()
	{
		log.debug("ShiftFilter start.");
		// keeps the characters
		CharArrayWriter writer = new CharArrayWriter();

		int c;
		try
		{
			log.debug("reading");
			c = input_.read();
			while (c != -1)
			{
				// line has been read
				if (((char) c) == '\n')
				{
					log.debug("Got a line");
					// current line
					String line = writer.toString();
					log.debug(line);
					// convert the current line in array of words
					StringTokenizer tokenizer = new StringTokenizer(line);
					String[] words = new String[tokenizer.countTokens()];
					int i = 0;

					while (tokenizer.hasMoreTokens())
					{
						String word = tokenizer.nextToken();
						// handle if it is noise word.
						if (!noiseList.contains(word))
							words[i++] = word;
						else
						{
							log.debug("Filtered noise : " + word);
						}
					}
					int length = i;
					for (i = 0; i < length; i++)
					{
						char[] chars = words[i].toCharArray();
						for (int j = 0; j < chars.length; j++)
							output_.write(chars[j]);
						if (i < length - 1)
						{
							output_.write(' ');
						} else
						{
							output_.write('\n');
						}

					}
					writer.reset();
				} else
					writer.write(c);

				c = input_.read();
			}
			output_.closeWriter();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
