import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import sun.misc.Unsafe;

public class Main {
  private static final Objenesis OBJENESIS = new ObjenesisStd();

  public static void main(String[] args) throws ReflectiveOperationException {
    Class<?> unsafeClass = Class.forName("jdk.internal.misc.Unsafe");
    Object unsafe = OBJENESIS.newInstance(unsafeClass);
    Unsafe sunMiscUnsafe = OBJENESIS.newInstance(sun.misc.Unsafe.class);
    Method unalignedAccess = unsafeClass.getDeclaredMethod("unalignedAccess");
    long overrideOffset = sunMiscUnsafe.objectFieldOffset(AccessibleObject.class.getDeclaredField("override"));
    sunMiscUnsafe.putBoolean(
        unsafeClass,
        overrideOffset,
        true);
    sunMiscUnsafe.putBoolean(
        unalignedAccess,
        overrideOffset,
        true);
    System.out.println(unalignedAccess.invoke(unsafe));
  }
}
