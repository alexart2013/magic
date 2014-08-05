package ws.kyberorg.magic.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

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
     * Object size
     *
     * @param o target
     * @return size of target
     */
    public static long sizeOf(Object o){
        if(isNotInited()){ return 0L; }

        return unsafe.getAddress(normalize(unsafe.getInt(o, 4L)) + 12L);
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
