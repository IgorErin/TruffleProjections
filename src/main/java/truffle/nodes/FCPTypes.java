package truffle.nodes;

import com.oracle.truffle.api.dsl.TypeSystem;
import truffle.types.FCPFunction;

@TypeSystem({int.class, boolean.class, FCPFunction.class})
public class FCPTypes {}
