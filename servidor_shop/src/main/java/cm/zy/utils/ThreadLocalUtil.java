package cm.zy.utils;

/**
 * ThreadLocal utils
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    // ofrece un mapeo entre pares clave-valor
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // get value from ThreadLocal
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
	
    // set value to ThreadLocal
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }


    // remove value from ThreadLocal
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
