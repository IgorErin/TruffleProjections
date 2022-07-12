package truffle.types;

import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem({int.class, boolean.class, Function.class, String.class})
public abstract class Types {
    //TODO type check;
}
