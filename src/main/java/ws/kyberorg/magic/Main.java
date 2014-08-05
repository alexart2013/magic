package ws.kyberorg.magic;

import ws.kyberorg.magic.reflect.Reflector;
import ws.kyberorg.magic.unsafe.UnsafeOps;

/**
 * Application starts here
 *
 */
public class Main
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello fair people of Java!" );
        System.out.println("Let's do some magic");
        System.out.println();
        //unsafe
        UnsafeOps.init();

        //a bit of reflection
        System.out.println("Void object cannot be initialized.");
        System.out.println("Are you sure?");

        Void voidObject = Reflector.returnVoid();
        long pointerToVoid = UnsafeOps.getPointer(voidObject);
        System.out.println("We just created object of Void. Its pointer is: "+pointerToVoid);

        //get Pointer of our Runtime
        Runtime ourRuntime = Runtime.getRuntime();
        long runtimePointer = UnsafeOps.getPointer(ourRuntime);
        System.out.println("Pointer to our runtime contains following memory address: "+runtimePointer);

        //TODO try to clone runtime

        //sizes
        //FIXME following code crashes JVM
        /*
        Object o = new Object();
        long sizeOfObject = UnsafeOps.sizeOf(o);
        System.out.println("Size of Object is: "+sizeOfObject);

        Integer i = 42;
        long sizeOfInt = UnsafeOps.sizeOf(i);
        System.out.println("Size of Integer is: "+sizeOfInt);

        Long l = 42L;
        long sizeOfLong = UnsafeOps.sizeOf(l);
        System.out.println("Size of Long is: "+sizeOfLong);

        Void v = Reflector.returnVoid();
        long sizeOfVoid = UnsafeOps.sizeOf(v);
        System.out.println("Size of Void is: "+sizeOfVoid);
        */
    }
}
