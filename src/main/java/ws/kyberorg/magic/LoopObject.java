package ws.kyberorg.magic;

/**
 * Object contains itself
 *
 * @author Alexander Muravya <asm@virtalab.net>
 * @since 0.0
 */
public class LoopObject {
    private static int counter = 0;
    private LoopObject self = new LoopObject();

    public LoopObject(){
        counter++;
    }

    public static int getCounter(){
        return counter;
    }

}
