package truffle.nodes;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import truffle.types.Label;

import java.awt.font.LineBreakMeasurer;

@NodeField(name = "label", type = Label.class)
public abstract class LabelNode extends FCPNode {
    public abstract Label getLabel();

    private boolean scopeSet = false;

    @Specialization(guards = "scopeSet")
    public Label getScopedLabel(VirtualFrame frame) {
        return this.getLabel();
    }

    @Specialization(replaces = "getScopedLabel")
    public Label getLabel(VirtualFrame frame) {
        Label label = this.getLabel();
        label.setLexicalScope(frame.materialize());
        return label;
    }

    protected boolean isScoped() {
        return scopeSet == true;
    }

    static public Label createLabel(RootNode node) {
        return new Label(Truffle.getRuntime().createCallTarget(node));
    }
}
