package simple.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import simple.Environment;
import truffle.nodes.TFNode;
import truffle.parser.LexicalScope;


public interface Node {
    Object eval(Environment env);
    TFNode convert(FrameDescriptor.Builder descriptorBuilder, LexicalScope scope);
}
