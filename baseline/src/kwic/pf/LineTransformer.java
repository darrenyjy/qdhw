package kwic.pf;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LineTransformer extends Filter
{
	private static Log log = LogFactory.getLog(LineTransformer.class);

	public LineTransformer(Pipe input, Pipe output)
	{
		super(input, output);
	}

	@Override
	protected void transform()
	{
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
						words[i++] = tokenizer.nextToken();

					// make first word upper class
					if (words[0] != null)
					{
						words[0] = words[0].toUpperCase();
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
