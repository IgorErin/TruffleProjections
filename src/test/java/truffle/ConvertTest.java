package truffle;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConvertTest {
    Context context;

    @BeforeEach
    public void setUpContext() {
        context = Context.create();
    }

    @Test
    public void ifTest() {
        Value value = context.eval("tf", "(define value #f) (if value #t #f)");

        Assertions.assertFalse(value.asBoolean());
    }

    @Test
    public void fibTest() {
        Value value = context.eval("tf", Programs.fibRecursive);

        Assertions.assertEquals((long) 10946, value.asLong());
    }

    @Test
    public void ifArifTest() {
        Value value = context.eval("tf", "(if (< 4 3) #t #f)");

        Assertions.assertFalse(value.asBoolean());
    }

    @Test
    public void minusTest() {
        Value value = context.eval("tf", "(- 3 4)");

        Assertions.assertEquals((long) -7, value.asLong());
    }

    @Test
    public void mulTest() {
        Value value = context.eval("tf", "(* 4 3)");

        Assertions.assertEquals((long) 12, value.asLong());
    }

    @Test
    public void plusTest() {
        Value value = context.eval("tf", "(+ 4 3)");

        Assertions.assertEquals((long) 7, value.asLong());
    }

    @Test
    public void trueBooleanDefTest() {
        Value value = context.eval("tf", "(define val #t) (if val #t #f)");

        Assertions.assertTrue(value.asBoolean());
    }

    @Test
    public void intVarDefTest() {
        Value value = context.eval("tf", "(define name 4) (+ name)");

        Assertions.assertEquals((long) 4, value.asLong());
    }
}
