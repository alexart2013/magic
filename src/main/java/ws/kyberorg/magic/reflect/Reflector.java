package ws.kyberorg.magic.reflect;

import java.lang.reflect.Constructor;

/**
 * Operations with reflection
 */
public class Reflector {

    public static Void returnVoid() throws Exception{
        Constructor<Void> constructor;
        constructor = Void.class.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        Void newVoid = constructor.newInstance();
        return newVoid;
    }
}
