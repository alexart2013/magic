package ws.kyberorg.magic.unsafe;

import sun.misc.Unsafe;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * Fun with sun.misc.Unsafe class
 */
public class UnsafeOps {

    public static Unsafe unsafe;

    public static void init() throws Exception{
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe) f.get(null);
    }

    /**
     * Return memory address of given object
     *
     * @param o given object
     * @return memory address (pointer)
     */
    public static long getPointer(Object o){
        if(isNotInited()){ return 0L; }

        Object[] arr = new Object[] {o};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        return normalize(unsafe.getInt(arr, baseOffset));
    }

    public static Object fromMemoryAddress(long pointer){
        if(isNotInited()){ return null; }

        Object[] arr = new Object[] { null };
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        unsafe.putLong(arr, baseOffset, pointer);
        return arr[0];
    }

    /**
     * Mishadoff's code
     *
     * @param o given object
     * @return size
     */
    public static long sizeOf(Object o) {
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = unsafe.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize / 8) + 1) * 8;   // padding
    }

    /**
     * Casts signed int to unsigned long
     *
     * @param value signed int
     * @return unsigned long
     */
    private static long normalize(int value){
        if(value >=0) return value;
        return (~0L >>> 32) & value;
    }

    private static boolean isNotInited(){
        if(unsafe == null) {
            System.err.println("Seems like Unsafe was not init. Have you init it?");
            return true;
        }
        return false;
    }
}
