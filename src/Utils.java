import java.util.*;

/**
 * Created by afusco on 23/02/17.
 */
public class Utils {

    /**
     * Remove and return the first element of a collection.
     * @param fringe The collection
     * @return
     */
    public static <T> T pop(Collection<T> fringe) {
        T ret;

        if (fringe instanceof Queue) {
            return ((Queue<T>) fringe).remove();
        }

        if (fringe instanceof Stack) {
            return ((Stack<T>) fringe).pop();
        }

        try {
            ret = fringe.iterator().next();
            fringe.remove(ret);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException("Collection doesn't support pop.");
        }
    }

}
