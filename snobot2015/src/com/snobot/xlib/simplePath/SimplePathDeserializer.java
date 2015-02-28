package com.snobot.xlib.simplePath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SimplePathDeserializer
{
    public List<SimplePathPoint> read(String aFileDeserialPath)
    {
        List<SimplePathPoint> output = new ArrayList<>();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(aFileDeserialPath));

            boolean firstLine = true;

            String line;
            while ((line = br.readLine()) != null)
            {
                if (firstLine)
                {
                    firstLine = false;
                    continue;
                }

                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                SimplePathPoint point = new SimplePathPoint();
                point.mSegment = Integer.parseInt(tokenizer.nextToken());
                point.mTime = Double.parseDouble(tokenizer.nextToken());
                point.mPosition = Double.parseDouble(tokenizer.nextToken());
                point.mVelocity = Double.parseDouble(tokenizer.nextToken());
                point.mAcceleration = Double.parseDouble(tokenizer.nextToken());

                output.add(point);
            }

            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return output;

    }
}
