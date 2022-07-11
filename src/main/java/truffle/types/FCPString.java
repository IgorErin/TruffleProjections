package truffle.types;

public record FCPString(String str) {
    @Override
    public String toString() {
        return str;
    }
}
