package truffle;

import com.oracle.truffle.api.dsl.TypeSystem;
import truffle.types.Label;


@TypeSystem({int.class, boolean.class, Label.class})
public class FCPTypeSystem { }