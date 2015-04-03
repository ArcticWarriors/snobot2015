package com.snobot.sd2015.spline_plotter;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class StandaloneMain
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();

        final SplinePlotterPanel panel = new SplinePlotterPanel();

        final List<SplineSegment> path_points = new ArrayList<SplineSegment>();

        SplineSegment p;

        p = new SplineSegment();
        // p.mVelocity = 0;
        // p.mPosition = 0;
        path_points.add(p);

        for (int i = 1; i < 10; ++i)
        {
            p = new SplineSegment();
            p.left_vel = 0 + i * .7;
            p.left_pos = path_points.get(path_points.size() - 1).left_pos + p.left_vel * .02;
            p.right_vel = p.left_vel;
            p.right_pos = p.left_pos;
            p.heading = i;
            path_points.add(p);
        }
        for (int i = 0; i < 20; ++i)
        {
            p = new SplineSegment();
            p.left_vel = 7.0;
            p.left_pos = path_points.get(path_points.size() - 1).left_pos + p.left_vel * .02;
            p.right_vel = p.left_vel;
            p.right_pos = p.left_pos;
            p.heading = i;
            path_points.add(p);
        }
        for (int i = 0; i < 10; ++i)
        {
            p = new SplineSegment();
            p.left_vel = 7 - i * .7;
            p.left_pos = path_points.get(path_points.size() - 1).left_pos + p.left_vel * .02;
            p.right_vel = p.left_vel;
            p.right_pos = p.left_pos;
            p.heading = i;
            path_points.add(p);
        }

        p = new SplineSegment();
        p.left_vel = 0;
        p.left_pos = path_points.get(path_points.size() - 1).left_pos + p.left_vel * .02;
        p.right_vel = p.left_vel;
        p.right_pos = p.left_pos;
        p.heading = 0;
        path_points.add(p);

        panel.setPath(path_points);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent arg0)
            {
            }
        });

        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                List<SplineSegment> actuals = new ArrayList<SplineSegment>();

                for (int i = 0; i < path_points.size(); ++i)
                {
                    SplineSegment p = path_points.get(i);
                    p.left_vel *= .9;
                    p.left_pos = 0;
                    p.right_vel = p.left_vel * .5;
                    p.right_pos = 0;
                    p.heading = 0;

                    if (i != 0)
                    {
                        p.left_pos = actuals.get(i - 1).left_pos + p.left_vel * .02;
                        p.right_pos = p.left_pos * .5;
                        p.heading = i * .8;
                    }

                    actuals.add(p);
                }

                for (int i = 0; i < actuals.size(); ++i)
                {
                    panel.setPoint(i, actuals.get(i));

                    try
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
