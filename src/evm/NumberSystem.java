package evm;

import java.util.Arrays;

public class NumberSystem {
    private Type type;
    private int[] baseArray;

    public NumberSystem(int[] baseArray, Type type) {
        this.setType(type);
        this.baseArray = baseArray;
    }

    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
    }

    public NumberSystem(String baseString, Type type) {
        this.baseArray = Arrays.stream(baseString.split(" ")).mapToInt(Integer::parseInt).toArray();
        this.setType(type);
    }

    public int[] getBase() {
        return baseArray;
    }

    enum Type {

        POSITIONAL(1), POLYODIC(2), MODULAR(3);

        private int typeId;

        private Type(int type) {
            this.typeId = type;
        }

        public int getType() {
            return typeId;
        }

        public static Integer[] allTypes = {1,2,3};

    }
}