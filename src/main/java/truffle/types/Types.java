package truffle.types;

import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem({int.class, boolean.class, Function.class, FCPString.class})
public abstract class Types {
    //TODO type check;
}
