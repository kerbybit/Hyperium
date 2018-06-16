package cc.hyperium.mods.orangemarshall.enhancements.util;

public class Pair<A, B>
{
    public final A first;
    public final B second;
    
    public static <A, B> Pair<A, B> of(final A first, final B second) {
        return new Pair<A, B>(first, second);
    }
    
    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }
    
    public A getFirst() {
        return this.first;
    }
    
    public B getSecond() {
        return this.second;
    }
    
    public Pair<A, B> clone() {
        try {
            return (Pair)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }
    
    @Override
    public boolean equals(final Object object) {
        if (object instanceof Pair) {
            final Pair<?, ?> other = (Pair<?, ?>)object;
            return eq(this.first, other.first) && eq(this.second, other.second);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return hash(this.first, 0) - 559038737 ^ hash(this.second, 0);
    }
    
    @Override
    public String toString() {
        return String.format("(%s, %s)", this.first, this.second);
    }
    
    private static boolean eq(final Object a, final Object b) {
        return a == b || (a != null && a.equals(b));
    }
    
    private static int hash(final Object object, final int nullHash) {
        return (object == null) ? nullHash : object.hashCode();
    }
}
