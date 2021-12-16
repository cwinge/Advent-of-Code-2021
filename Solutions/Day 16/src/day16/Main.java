package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static String binary;
    public static List<Character> binaryAsCharList;

    public static void main(String[] args) {
        getInput();
        binaryAsCharList = binary.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        partOne();
        partTwo();
    }

    public static void partOne() {
        Packet packet = parsePacket(new ArrayDeque<Character>(binaryAsCharList));
        System.out.printf("Part one solution - sum of all packet versions: %d%n", packet.sum());
    }

    public static void partTwo() {
        Packet packet = parsePacket(new ArrayDeque<Character>(binaryAsCharList));
        System.out.printf("Part two solution - result of evaluating all packets with their " +
                "corresponding operations: %d%n", packet.evaluate());

    }

    public static Packet parsePacket(ArrayDeque<Character> str) {
        int version = readBits(str, 3);
        int type = readBits(str, 3);
        if (type == 4) {
            long literal = 0;
            while (true) {
                int packet = readBits(str, 5);
                literal = (literal << 4) | (packet & 0b1111);
                if ((packet & 0b10000) == 0)
                    break;
            }
            return new Packet(version, type, OptionalLong.of(literal), Optional.empty());
        } else {
            int bit = readBits(str, 1);
            List<Packet> packets = new ArrayList<>();
            if (bit == 1) {
                int numOfPackets = readBits(str, 11);
                for (int i = 0; i < numOfPackets; i++) {
                    packets.add(parsePacket(str));
                }
            } else {
                int length = readBits(str, 15);
                int readUntil = str.size() - length;
                while (str.size() != readUntil) {
                    packets.add(parsePacket(str));
                }
            }
            return new Packet(version, type, OptionalLong.empty(), Optional.of(packets));
        }
    }

    public static int readBits(ArrayDeque<Character> str, int bits) {
        int decimal = 0;
        for (int i = 0; i < bits; i++) {
            if (str.removeFirst() == '1')
                decimal = (decimal << 1) | 1;
            else
                decimal <<= 1;
        }
        return decimal;
    }


    public static record Packet(int version, int type, OptionalLong literal, Optional<List<Packet>> subPackets) {
        public int sum() {
            if (!literal.isPresent())
                return version + subPackets.get().stream().collect(Collectors.summingInt(Packet::sum));
            else
                return version;
        }

        public long evaluate() {
            switch (type) {
                case 0: // +=
                    return subPackets.get().stream().collect(Collectors.summingLong(Packet::evaluate));
                case 1: // *=
                    return subPackets.get().stream().mapToLong(Packet::evaluate).reduce(1L, (a, b) -> a * b);
                case 2: // min
                    return subPackets.get().stream().mapToLong(Packet::evaluate).min().getAsLong();
                case 3: // max
                    return subPackets.get().stream().mapToLong(Packet::evaluate).max().getAsLong();
                case 4: // literal
                    return literal.getAsLong();
                case 5: // >
                    return (subPackets.get().get(0).evaluate() > subPackets.get().get(1).evaluate()) ? 1 : 0;
                case 6: // <
                    return (subPackets.get().get(0).evaluate() < subPackets.get().get(1).evaluate()) ? 1 : 0;
                case 7: // ==
                    return (subPackets.get().get(0).evaluate() == subPackets.get().get(1).evaluate()) ? 1 : 0;
                default:
                    throw new IllegalStateException("Invalid type " + type);
            }
        }
    }

    public static void getInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            binary = hexToBinary(br.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String hexToBinary(String hexStr) {
        return hexStr.chars().mapToObj(ch -> switch ((char) ch) {
                    case '0' -> "0000";
                    case '1' -> "0001";
                    case '2' -> "0010";
                    case '3' -> "0011";
                    case '4' -> "0100";
                    case '5' -> "0101";
                    case '6' -> "0110";
                    case '7' -> "0111";
                    case '8' -> "1000";
                    case '9' -> "1001";
                    case 'A' -> "1010";
                    case 'B' -> "1011";
                    case 'C' -> "1100";
                    case 'D' -> "1101";
                    case 'E' -> "1110";
                    case 'F' -> "1111";
                    default -> throw new NumberFormatException("Invalid character "
                            + (char) ch);
                })
                .collect(Collectors.joining());
    }
}