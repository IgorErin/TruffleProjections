package truffle;

import com.oracle.truffle.api.dsl.TypeSystem;
import truffle.types.Label;
import type.FCPFunction;

@TypeSystem({int.class, boolean.class, Label.class})
public class FCPTypeSystem { }