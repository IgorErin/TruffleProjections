package truffle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ConvertTest {
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String fileName, Object expected) {
        Object value = TruffleMain.execute(fileName);
        System.out.println(expected + " " + value);

        Assertions.assertEquals(expected, value);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of("src/test/java/defTest.fcp", (long) 4),
                Arguments.of("src/test/java/ifTest.fcp", false),
                Arguments.of("src/test/java/fibTest.fcp", (long) 10946),
                Arguments.of("src/test/java/plusTest.fcp", (long) 7),
                Arguments.of("src/test/java/minusTest.fcp", (long) -7),
                Arguments.of("src/test/java/falseBooleanDefTest.fcp", false),
                Arguments.of("src/test/java/trueBooleanDefTest.fcp", true),
                Arguments.of("src/test/java/trueBooleanDefTest.fcp", true)
        );
    }
}
