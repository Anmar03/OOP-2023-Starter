package ie.tudublin;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;

public class DANI extends PApplet {

	private ArrayList<Word> model;
    private Random random;

	public void settings() {
		size(1000, 1000);
		//fullScreen(SPAN);
	}

	public void setup() {
		colorMode(HSB);
		loadFile("shakespeare.txt");
		printModel();
	}

	String[] sonnet;

	public DANI() 
	{
        model = new ArrayList<>();
        random = new Random();
    }

    public String[] writeSonnet()
    {
        String[] sonnet = new String[14];

		

        return sonnet;
    }

	public String writeLine() 
	{
		StringBuilder sb = new StringBuilder();
        Word currentWord = model.get(random.nextInt(model.size()));

		sb.append(currentWord.getWord());
        for(int i = 0; i < 7; i++) 
        {
            Follow nextFollow = currentWord.getFollows().isEmpty() ? null : currentWord.getFollows().get(random.nextInt(currentWord.getFollows().size()));
                    
            if(nextFollow == null) 
            {
                break;
            }
            sb.append(" ").append(nextFollow.getWord());
            currentWord = findWord(nextFollow.getWord());

            if(currentWord == null) 
            {
                break;
            }
        }
        return sb.toString();

    }

	public void loadFile(String file)
	{
		String[] lines = loadStrings(file);

		for(String line:lines) 
		{
			String[] words = split(line, ' ');
	
			for(int i = 0; i < words.length - 1; i++) 
			{
				String currentWord = words[i].replaceAll("[^\\w\\s]", "").toLowerCase();
				String nextWord = words[i + 1].replaceAll("[^\\w\\s]", "").toLowerCase();

				
				Word word = findWord(currentWord);
				
				word.addFollow(nextWord);
			}
		}
	}

	public Word findWord(String word) 
	{
        for(Word w:model) 
		{
            if(w.getWord().equals(word)) 
			{
                return w;
            }
        }
        return null;
    }

	public void printModel() 
	{
        for (Word word:model) 
		{
			System.out.print(word.getWord() + ": ");
			ArrayList<Follow> follows = word.getFollows();


			for (int i = 0; i < follows.size(); i++) 
			{
				Follow follow = follows.get(i);
				System.out.print(follow.getWord() + "(" + follow.getCount() + ")");
				if(i < follows.size() - 1) 
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
    }


	public void keyPressed() {
		if(key == ' ') 
		{
            sonnet = writeSonnet();
            for(String line:sonnet) 
			{
                System.out.println(line);
            }
        }
	}

	float off = 0;

	public void draw() 
    {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
        textAlign(CENTER, CENTER);
        
		if(sonnet != null) 
		{
            for(int i = 0; i < sonnet.length; i++) 
			{
                text(sonnet[i], width / 2, (i + 1) * 50);
            }
        }
	}
}
