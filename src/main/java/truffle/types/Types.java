package truffle.types;

import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem({int.class, boolean.class, TFFunction.class, String.class})
public abstract class Types {
    //TODO type check;
}
