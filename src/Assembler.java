import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class Assembler {
    public static List<Instruction> assemble(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        Map<String, Integer> labels = new HashMap<>();
        List<String[]> tokensList = new ArrayList<>();
        int addr = 0;
        // first pass: collect label addresses (instruction index)
        for (String raw : lines) {
            String line = raw.split(";",2)[0].trim();
            if (line.isEmpty()) continue;
            if (line.endsWith(":")) {
                String lbl = line.substring(0, line.length()-1).trim();
                labels.put(lbl, addr);
            } else {
                String[] parts = line.split("\\s+");
                tokensList.add(parts);
                addr += 1;
            }
        }
        // second pass: build instructions
        List<Instruction> program = new ArrayList<>();
        for (String[] parts : tokensList) {
            String op = parts[0].toUpperCase();
            Integer operand = null;
            if (parts.length > 1) {
                String tok = parts[1];
                if (tok.matches("-?\\d+")) operand = Integer.valueOf(tok);
                else if (labels.containsKey(tok)) operand = labels.get(tok);
                else throw new RuntimeException("Unknown label/operand: " + tok);
            }
            program.add(new Instruction(op, operand));
        }
        return program;
    }

    // helper: assemble from a string (for GUI)
    public static List<Instruction> assembleFromString(String src) {
        try {
            Path temp = Files.createTempFile("prog", ".asm");
            Files.writeString(temp, src);
            return assemble(temp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
