package evm;

class Number {
    private int decimalValue;
    private NumberSystem targetSystem;

    public Number(int decimalValue) {
        this(decimalValue, null);
    }

    public Number(int decimalValue, NumberSystem targetSystem) {
        this.decimalValue = decimalValue;
        this.targetSystem = targetSystem;
    }
}