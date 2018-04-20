import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.AbstractList;

public class Circle {
    int x, y, r;
    Color c;

   public static ArrayList<Circle> circles = new ArrayList<Circle>();

    Circle(int x, int y, Color c, int r){
        this.x=x;
        this.y=y;
        this.c=c;
        this.r=r;

    }

    public Circle() {}
}
