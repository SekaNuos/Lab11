package com.example.lab11;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class TabulateBean implements Serializable {
    private double start;
    private double finish;
    private double step;

    private List<Point> points;

    TabulateBean() {
        points = new ArrayList<>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getFinish() {
        return finish;
    }

    public void setFinish(double finish) {
        this.finish = finish;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public String tabulate() {
        points = new ArrayList<>();
        int n = size();
        for (int i = 0; i < n; i++) {
            double x = start + i*step;
            x = round(x);
            double y = calc(x, 2.2);
            y = round(y);
            points.add(new Point(x, y));
        }
        return "tabulation";
    }

    private int size() {
        if(finish < start || step <= 0)
            return 0;
        return (int)((finish - start) / step + 1);
    }

    private double calc(double x, double t) {
        double exp = 0.00001;

        if (x <0.9+exp) {
            return (Math.log10(x) * Math.log10(x) * Math.log10(x) + x * x)/(Math.sqrt(x+t)) ;
        } if (x == 0.9 + exp ) {
            return Math.sqrt(x+t) + 1/x ;
        } else {
            return Math.cos(x) + t *  (Math.sin(x) * Math.sin(x)) ;
        }
    }

    private double round(double n) {
        return (double) Math.round(n * 1000) / 1000;
    }

    public int min_index() {
        double min = Double.MAX_VALUE;
        int index = 0;
        for(int i = 0; i < points.size(); i++) {
            if(points.get(i).getY() < min) {
                min = points.get(i).getY();
                index = i;
            }
        }
        return index;
    }

    public int max_index() {
        double max = Double.MIN_VALUE;
        int index = 0;
        for(int i = 0; i < points.size(); i++) {
            if(points.get(i).getY() > max) {
                max = points.get(i).getY();
                index = i;
            }
        }
        return index;
    }

    public Point min() {
        return points.get(min_index());
    }

    public Point max() {
        return points.get(max_index());
    }

    public double sum() {
        double s = 0;
        for(Point p : points) {
            s += p.getY();
        }
        return round(s);
    }

    public double avg() {
        return round(sum() / points.size());
    }
}