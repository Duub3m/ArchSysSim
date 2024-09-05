public class RegisterOperation {
    private String operationType;
    private int reg1;  // First register
    private int reg2;  // Second register
    private Integer reg3;  // Third register

    // Constructor for two-register operations
    public RegisterOperation(String operationType, int reg1, int reg2) {
        this.operationType = operationType;
        this.reg1 = reg1;
        this.reg2 = reg2;
        this.reg3 = null;  
    }

    // Constructor for three register operations
    public RegisterOperation(String operationType, int reg1, int reg2, int reg3) {
        this.operationType = operationType;
        this.reg1 = reg1;
        this.reg2 = reg2;
        this.reg3 = reg3;
    }

    // Getters
    public String getOperationType() {
        return operationType;
    }

    public int getReg1() {
        return reg1;
    }

    public int getReg2() {
        return reg2;
    }

    public Integer getReg3() {
        return reg3;  
    }

    // method that uses register values in an output
    public String toString() {
        if (reg3 != null) {
            return String.format("%s %d, %d, %d", operationType, reg1, reg2, reg3);
        } else {
            return String.format("%s %d, %d", operationType, reg1, reg2);
        }
    }
}
