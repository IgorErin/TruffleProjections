package parser.statements

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.profiles.ConditionProfile
import parser.expressions.Expression

class IfStatement(@Child var  exp: Expression,
                  @Child var firstJumpLabel: JumpStatement,
                  @Child var secondJumpLabel: JumpStatement) : Statement() {
    private var conditionProfile = ConditionProfile.createBinaryProfile()

    override fun execute(frame: VirtualFrame): Int {
        return if (conditionProfile.profile(exp.execute(frame) == 1)) {
            firstJumpLabel.execute(frame)
        } else {
            secondJumpLabel.execute(frame)
        }
    }
}
