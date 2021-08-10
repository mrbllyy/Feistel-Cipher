import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            String operation = args[0];
            String key = "";
            String input = "";
            String output = "";
            String mode = "";

            for (int i = 1; i < 9; i++) {
                if (args[i].equals("-K")) {
                    key = args[i + 1];
                }

                if (args[i].equals("-I")) {
                    input = args[i + 1];
                }

                if (args[i].equals("-O")) {
                    output = args[i + 1];
                }

                if (args[i].equals("-M")) {
                    mode = args[i + 1];
                }
            }

            if (operation.equals("enc") && mode.equals("ECB")) {
                encecb(input, output, key);
            }


        } catch (Exception e) {
            System.out.println("Please give arguments like: enc|dec -K key -I input -O output –M mode");
        }

    }

    public static void encecb(String input, String output, String key) {
        try {

            File inputFile = new File(input);
            File outputFile = new File(output);
            File keyFile = new File(key);
            Scanner inputF = new Scanner(inputFile);
            Scanner keyF = new Scanner(keyFile);

            StringBuilder plain = new StringBuilder();
            char[] chars = inputF.toString().toCharArray();
            for (char aChar : chars) {
                plain.append(String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0"));
            }

            while(plain.toString().length() % 96 != 0) {
                plain = plain.append("0");
            }

            StringBuilder base64key = new StringBuilder();
            char[] chars2 = keyF.nextLine().toCharArray();

            for (char aChar : chars2) {
                base64key.append(aChar);
            }

            byte[] decodedBytes = Base64.getDecoder().decode(base64key.toString());
            String decodedKey = new String(decodedBytes);

            System.out.println("Plaintext: " + plain.toString());
            System.out.println("Length of plaintext: " + plain.toString().length());
            System.out.println("Base64 Key: " + base64key.toString());
            System.out.println("Key: " + decodedKey);

            scrambleFunction(plain.toString(), decodedKey);

            inputF.close();
            keyF.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please give files that we can find when we search!");
        }

    }

    public static String scrambleFunction(String substring, String subkey) {
        String result = "";
        for (int i = 0; i < 48; i++) { //xor op
            if (substring.charAt(i) == '1' && subkey.charAt(i) == '1') {
                result = result + "0";
            } else if (substring.charAt(i) == '1' && subkey.charAt(i) == '0') {
                result = result + "1";
            } else if (substring.charAt(i) == '0' && subkey.charAt(i) == '1') {
                result = result + "1";
            } else if (substring.charAt(i) == '0' && subkey.charAt(i) == '0') {
                result = result + "0";
            }
        }

        String p1 = String.valueOf(result.charAt(0)) + String.valueOf(result.charAt(1)) + String.valueOf(result.charAt(2)) + String.valueOf(result.charAt(3)) + String.valueOf(result.charAt(4)) + String.valueOf(result.charAt(5));
        String p2 = String.valueOf(result.charAt(6)) + String.valueOf(result.charAt(7)) + String.valueOf(result.charAt(8)) + String.valueOf(result.charAt(9)) + String.valueOf(result.charAt(10)) + String.valueOf(result.charAt(11));
        String p3 = String.valueOf(result.charAt(12)) + String.valueOf(result.charAt(13)) + String.valueOf(result.charAt(14)) + String.valueOf(result.charAt(15)) + String.valueOf(result.charAt(16)) + String.valueOf(result.charAt(17));
        String p4 = String.valueOf(result.charAt(18)) + String.valueOf(result.charAt(19)) + String.valueOf(result.charAt(20)) + String.valueOf(result.charAt(21)) + String.valueOf(result.charAt(22)) + String.valueOf(result.charAt(23));
        String p5 = String.valueOf(result.charAt(24)) + String.valueOf(result.charAt(25)) + String.valueOf(result.charAt(26)) + String.valueOf(result.charAt(27)) + String.valueOf(result.charAt(28)) + String.valueOf(result.charAt(29));
        String p6 = String.valueOf(result.charAt(30)) + String.valueOf(result.charAt(31)) + String.valueOf(result.charAt(32)) + String.valueOf(result.charAt(33)) + String.valueOf(result.charAt(34)) + String.valueOf(result.charAt(35));
        String p7 = String.valueOf(result.charAt(36)) + String.valueOf(result.charAt(37)) + String.valueOf(result.charAt(38)) + String.valueOf(result.charAt(39)) + String.valueOf(result.charAt(40)) + String.valueOf(result.charAt(41));
        String p8 = String.valueOf(result.charAt(42)) + String.valueOf(result.charAt(43)) + String.valueOf(result.charAt(44)) + String.valueOf(result.charAt(45)) + String.valueOf(result.charAt(46)) + String.valueOf(result.charAt(47));


        System.out.println(substring);
        System.out.println(subkey);
        System.out.println(result);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println(p6);
        System.out.println(p7);
        System.out.println(p8);

        String res48 = substitutionBox(p1) + substitutionBox(p2) + substitutionBox(p3) + substitutionBox(p4) + substitutionBox(p5) + substitutionBox(p6) + substitutionBox(p7) + substitutionBox(p8) + substitutionBox(xor6(p1, p2)) + substitutionBox(xor6(p3, p4)) + substitutionBox(xor6(p5, p6)) + substitutionBox(xor6(p7, p8));

        System.out.println("res48: " + res48);

        for (int i = 0; i < 48; i++) {
            char temp = res48.charAt(2 * i);
            res48.charAt(2 * i) = res48.charAt((2 * i) + 1);
            res48.
            res48.charAt((2 * i) + 1) = temp;
        }

        return result;
    }

    public static String substitutionBox(String input) {
        if (input.equals("000000")) {
            return "0010";
        } else if (input.equals("000010")) {
            return "1100";
        } else if (input.equals("000100")) {
            return "0100";
        } else if (input.equals("000110")) {
            return "0001";
        } else if (input.equals("001000")) {
            return "0111";
        } else if (input.equals("001010")) {
            return "1010";
        } else if (input.equals("001100")) {
            return "1011";
        } else if (input.equals("001110")) {
            return "0110";
        } else if (input.equals("010000")) {
            return "1000";
        } else if (input.equals("010010")) {
            return "0101";
        } else if (input.equals("010100")) {
            return "0011";
        } else if (input.equals("010110")) {
            return "1111";
        } else if (input.equals("011000")) {
            return "1101";
        } else if (input.equals("011010")) {
            return "0000";
        } else if (input.equals("011100")) {
            return "1110";
        } else if (input.equals("011110")) {
            return "1001";
        } else if (input.equals("000001")) {
            return "1110";
        } else if (input.equals("000011")) {
            return "1011";
        } else if (input.equals("000101")) {
            return "0010";
        } else if (input.equals("000111")) {
            return "1100";
        } else if (input.equals("001001")) {
            return "0100";
        } else if (input.equals("001011")) {
            return "0111";
        } else if (input.equals("001101")) {
            return "1101";
        } else if (input.equals("001111")) {
            return "0001";
        } else if (input.equals("010001")) {
            return "0101";
        } else if (input.equals("010011")) {
            return "0000";
        } else if (input.equals("010101")) {
            return "1111";
        } else if (input.equals("010111")) {
            return "1010";
        } else if (input.equals("011001")) {
            return "0011";
        } else if (input.equals("011011")) {
            return "1001";
        } else if (input.equals("011101")) {
            return "1000";
        } else if (input.equals("011111")) {
            return "0110";
        } else if (input.equals("100000")) {
            return "0100";
        } else if (input.equals("100010")) {
            return "0010";
        } else if (input.equals("100100")) {
            return "0001";
        } else if (input.equals("100110")) {
            return "1011";
        } else if (input.equals("101000")) {
            return "1010";
        } else if (input.equals("101010")) {
            return "1101";
        } else if (input.equals("101100")) {
            return "0111";
        } else if (input.equals("101110")) {
            return "1000";
        } else if (input.equals("110000")) {
            return "1111";
        } else if (input.equals("110010")) {
            return "1001";
        } else if (input.equals("110100")) {
            return "1100";
        } else if (input.equals("110110")) {
            return "0101";
        } else if (input.equals("111000")) {
            return "0110";
        } else if (input.equals("111010")) {
            return "0011";
        } else if (input.equals("111100")) {
            return "0000";
        } else if (input.equals("111110")) {
            return "1110";
        } else if (input.equals("100001")) {
            return "1011";
        } else if (input.equals("100011")) {
            return "1000";
        } else if (input.equals("100101")) {
            return "1100";
        } else if (input.equals("100111")) {
            return "0111";
        } else if (input.equals("101001")) {
            return "0001";
        } else if (input.equals("101011")) {
            return "1110";
        } else if (input.equals("101101")) {
            return "0010";
        } else if (input.equals("101111")) {
            return "1101";
        } else if (input.equals("110001")) {
            return "0110";
        } else if (input.equals("110011")) {
            return "1111";
        } else if (input.equals("110101")) {
            return "0000";
        } else if (input.equals("110111")) {
            return "1001";
        } else if (input.equals("111001")) {
            return "1010";
        } else if (input.equals("111011")) {
            return "0100";
        } else if (input.equals("111101")) {
            return "0101";
        } else if (input.equals("111111")) {
            return "0011";
        }
        return "";
    }

    public static String xor6(String p1, String p2) {
        String result = "";
        for (int i = 0; i < 6; i++) { //xor op
            if (p1.charAt(i) == '1' && p2.charAt(i) == '1') {
                result = result + "0";
            } else if (p1.charAt(i) == '1' && p2.charAt(i) == '0') {
                result = result + "1";
            } else if (p1.charAt(i) == '0' && p2.charAt(i) == '1') {
                result = result + "1";
            } else if (p1.charAt(i) == '0' && p2.charAt(i) == '0') {
                result = result + "0";
            }
        }
        return result;
    }

}
