package it.polimi.se2018.utils;

public class Triplet<X, Y, Z> {
    public final X x;
    public final Y y;
    public final Z z;

    public Triplet(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!Triplet.class.isAssignableFrom(object.getClass())) {
            return false;
        }
        final Triplet obj = (Triplet) object;
        return  this.x.equals(obj.x) &&
                this.y.equals(obj.y) &&
                this.z.equals(obj.z);
    }

    @Override
    public String toString() {
        return "[" + x.toString() + ", " + y.toString() + ", " + z.toString() + "]";
    }
}
