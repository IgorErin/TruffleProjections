package truffle.nodes;

import com.oracle.truffle.api.dsl.TypeSystem;
import truffle.nodes.types.FCPFunction;

@TypeSystem({int.class, boolean.class, FCPFunction.class})
public class FCPTypes {}
