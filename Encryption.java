import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Encryption {
    public static String binaryNumberConversion(String starterString) {
        // Convert Characters to binary and return the string of 0s and 1s
        StringBuilder binaryStringBuilder = new StringBuilder();
        char[] charArray = starterString.toCharArray();
        for (int i = 0; i < starterString.length(); i++) {

            binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(charArray[i])).replace(" ", "0"));
        }
        return binaryStringBuilder.toString();
    }

    public static List<String> splitString(String binaryString, int lengthOfSplits) {
        // Split the string into smaller chunks, and return an array of said chunks.
        StringBuilder binaryStringBuilder = new StringBuilder();
        binaryStringBuilder.append(binaryString);
        List<String> splitString = new ArrayList<>();
        int length = binaryString.length();
        int start = 0;
        int missing = length % lengthOfSplits;
        if (missing != 0) {
            // adds extra zeros to the end of any string that isn't of a divisible length.
            for (int i = 0; i < (lengthOfSplits - missing); i++) {
                binaryStringBuilder.append("0");
            }
        }
        while (start < length) {
            int end = start + lengthOfSplits;
            String subString = binaryStringBuilder.substring(start, end);
            splitString.add(subString);
            start = end;
        }
        return splitString;
    }

    public static String concatenate(List<String> binaryArray) {
        // function to combine strings from an array.
        StringBuilder concatenatedString = new StringBuilder();
        for (int i = 0; i < binaryArray.size(); i++) {
            concatenatedString.append(binaryArray.get(i));
        }
        return concatenatedString.toString();
    }

    public static List<String> keyScheduleTransform(String inputKey) {
        ArrayList<String> arrayOfSubkeys = new ArrayList<>();
        StringBuilder currentK = new StringBuilder(inputKey);
        // split inputKey in half, and use shift it to transform it then combine them
        // back together
        for (int i = 0; i < 10; i++) {
            List<String> splitK = splitString(currentK.toString(), 28);
            String c = splitK.get(0);
            String d = splitK.get(1);
            String cAfterShift = shiftIt(c);
            String dAfterShift = shiftIt(d);
            currentK.setLength(0);
            currentK.append(cAfterShift);
            currentK.append(dAfterShift);

            arrayOfSubkeys.add(currentK.toString().substring(0, 32));

        }
        return arrayOfSubkeys;
    }

    public static String shiftIt(String binaryInput) {
        ArrayList<Character> binaryInputChars = new ArrayList<>();
        // adds all of the chars in binary input
        for (int i = 0; i < binaryInput.length(); i++) {
            binaryInputChars.add(binaryInput.charAt(i));
        }
        // take the first element, remove it from an array, and add it to the back
        char firstElement = binaryInputChars.get(0);
        binaryInputChars.add(firstElement);
        binaryInputChars.remove(0);
        StringBuilder builder = new StringBuilder();
        // add everything into a stringbuilder for output
        for (char c : binaryInputChars) {
            builder.append(c);
        }
        return builder.toString();
    }

    public static String xorIt(String binaryNum1, String binaryNum2) {
        // convert both strings into arrays for comparing
        char[] binaryNum1chars = binaryNum1.toCharArray();
        char[] binaryNum2chars = binaryNum2.toCharArray();
        char[] xored_char_array = new char[binaryNum1chars.length];

        // compare each element of the two arrays and add the XOR value to the new array
        for (int i = 0; i < binaryNum1chars.length; i++) {
            if ((binaryNum1chars[i] == '1' && binaryNum2chars[i] == '0')
                    || (binaryNum1chars[i] == '0' && binaryNum2chars[i] == '1')) {
                xored_char_array[i] = '1';
            } else {
                xored_char_array[i] = '0';
            }
        }
        // string builder for outputting a string
        StringBuilder xoredStringBuilder = new StringBuilder();
        for (int i = 0; i < binaryNum1chars.length; i++) {
            xoredStringBuilder.append(xored_char_array[i]);
        }

        return xoredStringBuilder.toString();
    }

    public static String permuteIt(String binaryInput, int[] permutationValues) {
        // place the value of input into a new array according to the value
        // permutationValues
        char[] binaryInputChars = binaryInput.toCharArray();
        char[] postpermutation = new char[binaryInputChars.length];
        for (int i = 0; i < permutationValues.length; i++) {
            postpermutation[i] = binaryInputChars[permutationValues[i] - 1];
        }
        StringBuilder builder = new StringBuilder();
        for (char c : postpermutation) {
            builder.append(c);
        }
        return builder.toString();
    }

    public static String substitutionS(String binaryInput) {
        // Splits binary string into 4 bits, and adds them to an array.
        List<String> hexDigitArray = splitString(binaryInput, 4);
        ArrayList<Integer> digits = new ArrayList<>();
        for (String s : hexDigitArray) {
            // Turns binary string into a number.
            digits.add(Integer.parseInt(s, 2));
        }

        String[][] S = new String[][] {
                { "01100011", "01111100", "01110111", "01111011", "11110010", "01101011", "01101111", "11000101",
                        "00110000", "00000001", "01100111", "00101011", "11111110", "11010111", "10101011",
                        "01110110" },
                { "11001010", "10000010", "11001001", "01111101", "11111010", "01011001", "01000111", "11110000",
                        "10101101", "11010100", "10100010", "10101111", "10011100", "10100100", "01110010",
                        "11000000" },
                { "10110111", "11111101", "10010011", "00100110", "00110110", "00111111", "11110111", "11001100",
                        "00110100", "10100101", "11100101", "11110001", "01110001", "11011000", "00110001",
                        "00010101" },
                { "00000100", "11000111", "00100011", "11000011", "00011000", "10010110", "00000101", "10011010",
                        "00000111", "00010010", "10000000", "11100010", "11101011", "00100111", "10110010",
                        "01110101" },
                { "00001001", "10000011", "00101100", "00011010", "00011011", "01101110", "01011010", "10100000",
                        "01010010", "00111011", "11010110", "10110011", "00101001", "11100011", "00101111",
                        "10000100" },
                { "01010011", "11010001", "00000000", "11101101", "00100000", "11111100", "10110001", "01011011",
                        "01101010", "11001011", "10111110", "00111001", "01001010", "01001100", "01011000",
                        "11001111" },
                { "11010000", "11101111", "10101010", "11111011", "01000011", "01001101", "00110011", "10000101",
                        "01000101", "11111001", "00000010", "01111111", "01010000", "00111100", "10011111",
                        "10101000" },
                { "01010001", "10100011", "01000000", "10001111", "10010010", "10011101", "00111000", "11110101",
                        "10111100", "10110110", "11011010", "00100001", "00010000", "11111111", "11110011",
                        "11010010" },
                { "11001101", "00001100", "00010011", "11101100", "01011111", "10010111", "01000100", "00010111",
                        "11000100", "10100111", "01111110", "00111101", "01100100", "01011101", "00011001",
                        "01110011" },
                { "01100000", "10000001", "01001111", "11011100", "00100010", "00101010", "10010000", "10001000",
                        "01000110", "11101110", "10111000", "00010100", "11011110", "01011110", "00001011",
                        "11011011" },
                { "11100000", "00110010", "00111010", "00001010", "01001001", "00000110", "00100100", "01011100",
                        "11000010", "11010011", "10101100", "01100010", "10010001", "10010101", "11100100",
                        "01111001" },
                { "11100111", "11001000", "00110111", "01101101", "10001101", "11010101", "01001110", "10101001",
                        "01101100", "01010110", "11110100", "11101010", "01100101", "01111010", "10101110",
                        "00001000" },
                { "10111010", "01111000", "00100101", "00101110", "00011100", "10100110", "10110100", "11000110",
                        "11101000", "11011101", "01110100", "00011111", "01001011", "10111101", "10001011",
                        "10001010" },
                { "01110000", "00111110", "10110101", "01100110", "01001000", "00000011", "11110110", "00001110",
                        "01100001", "00110101", "01010111", "10111001", "10000110", "11000001", "00011101",
                        "10011110" },
                { "11100001", "11111000", "10011000", "00010001", "01101001", "11011001", "10001110", "10010100",
                        "10011011", "00011110", "10000111", "11101001", "11001110", "01010101", "00101000",
                        "11011111" },
                { "10001100", "10100001", "10001001", "00001101", "10111111", "11100110", "01000010", "01101000",
                        "01000001", "10011001", "00101101", "00001111", "10110000", "01010100", "10111011", "00010110" }
        };
        return S[digits.get(0)][digits.get(1)];
    }

    public static String functionF(String rightHalf, String subKey) {
        // XORs the right half of the string with the subkey.
        String xorString = xorIt(rightHalf, subKey);
        // splits string into string of length 8 in order to use the S-Box
        List<String> eightBitStrings = splitString(xorString, 8);
        // Array for the output of the S-box substitutions and a for loop to complete
        ArrayList<String> substitutedArray = new ArrayList<>();
        // Compute substitutions
        for (int i = 0; i < eightBitStrings.size(); i++) {
            String substitutedString = substitutionS(eightBitStrings.get(i));
            substitutedArray.add(substitutedString);
        }
        // Add the strings back together to end up back at the 32 bit strings.
        String permuteString = concatenate(substitutedArray);
        // Permute permuteString
        int[] permuationArray = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3,
                9, 19, 13, 30, 6, 22, 11, 4, 25
        };
        String completedFunctionString = permuteIt(permuteString, permuationArray);
        return completedFunctionString;
    }

    public static String encryptBlock(String block, String inputKey) {
        // create an array and split the input string into two halfs
        List<String> splitArray = splitString(block, 32);
        // create an array of our subkeys
        List<String> subKeyArray = keyScheduleTransform(inputKey);
        // assign halves
        String leftHalf = splitArray.get(0);
        String rightHalf = splitArray.get(1);
        // encrypt 10 times using functionF xorIt function and then swap each half.
        for (int i = 0; i < 10; i++) {
            String tempHalf = rightHalf;
            rightHalf = xorIt(functionF(rightHalf, subKeyArray.get(i)), leftHalf);
            leftHalf = tempHalf;
        }
        // combine the two halves
        StringBuilder combined = new StringBuilder(leftHalf);
        combined.append(rightHalf);
        return combined.toString();
    }

    public static String decryptBlock(String block, String inputKey) {
        // create an array and split the input string into two halfs
        List<String> splitArray = splitString(block, 32);
        // create subkeys
        List<String> subKeyArray = keyScheduleTransform(inputKey);
        // assign halves
        String leftHalf = splitArray.get(0);
        String rightHalf = splitArray.get(1);
        // decrypt 10 times using functionF xorIt function and then swap each half.
        for (int i = 0; i < 10; i++) {
            String tempHalf = leftHalf;
            // backwards through the subkeys and the halves are opposite
            leftHalf = xorIt(functionF(leftHalf, subKeyArray.get(9 - i)), rightHalf);
            rightHalf = tempHalf;
        }
        // recombine halves
        StringBuilder combined = new StringBuilder(leftHalf);
        combined.append(rightHalf);
        return combined.toString();
    }

    public static String encryption(String longBinaryInput, String inputKey) {
        // encrypt more than 64 bits by splitting into array and iterating through each
        // element
        List<String> binaryBlocks = splitString(longBinaryInput, 64);
        ArrayList<String> cipherCodeArray = new ArrayList<>();
        for (String binaryStrings : binaryBlocks) {
            cipherCodeArray.add(encryptBlock(binaryStrings, inputKey));
        }
        return concatenate(cipherCodeArray);
    }

    public static String decryption(String longBinaryInput, String inputKey) {
        // decrypt more than 64 bits by splitting into array and iterating through each
        // element
        List<String> binaryBlocks = splitString(longBinaryInput, 64);
        ArrayList<String> cipherCodeArray = new ArrayList<>();
        for (String binaryStrings : binaryBlocks) {
            cipherCodeArray.add(decryptBlock(binaryStrings, inputKey));
        }
        return concatenate(cipherCodeArray);
    }

    public static void runTests() {
        // test cases
        String zeros = "0000000000000000000000000000000000000000000000000000000000000000";
        String ones = "1111111111111111111111111111111111111111111111111111111111111111";
        String zerosKey = "00000000000000000000000000000000000000000000000000000000";
        String onesKey = "11111111111111111111111111111111111111111111111111111111";
        String block1 = "1100110010000000000001110101111100010001100101111010001001001100";
        System.out.println("Running Tests: ");
        System.out.println("Output for encryption(ones, onesKey): ");
        System.out.println(encryption(ones, onesKey));
        System.out.println("Output for encryption(zeros, onesKey): ");
        System.out.println(encryption(zeros, onesKey));
        System.out.println("Output for encryption(zeros, zerosKey):");
        System.out.println(encryption(zerosKey, zerosKey));
        System.out.println("Output for encryption(block1, zerosKey):");
        System.out.println(encryption(block1, zerosKey));
        System.out.println();
        System.out.println("Output for: decryption(all ones, all ones)");
        System.out.println(decryption(ones, onesKey));
        System.out.println("Output for: decryption(all zeroes, all ones)");
        System.out.println(decryption(zeros, onesKey));
        System.out.println("Output for: decryption(all zeros, all zeros)");
        System.out.println(decryption(zeros, zerosKey));
        System.out.println("Output for: decryption(block,all ones), where: block = 0101011010001110111001000111100001001110010001100110000011110101");
        System.out.println(decryption("0101011010001110111001000111100001001110010001100110000011110101", onesKey));
        System.out.println("Output for: decryption(block,all zeros), where: block = 0011000101110111011100100101001001001101011010100110011111010111");
        System.out.println(decryption("0011000101110111011100100101001001001101011010100110011111010111", zerosKey));
    }

    public static String readFile(String filePath) {
        // read a file using buffered reader
        StringBuilder lineBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineBuilder.toString();
    }

    public static void writeFile(String fileContents, String fileName) {
        // write the file using a buffered writer
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(fileContents);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileEncryption(String filePath, String inputKey, String outputName) {
        // read the file then convert it to binary then encrypt the binary and write it
        // back into a file
        String fileAsString = readFile(filePath);
        String binaryConversion = binaryNumberConversion(fileAsString);
        String cipherText = encryption(binaryConversion, inputKey);
        writeFile(cipherText, outputName);
    }

    public static void fileDecryption(String filePath, String inputKey, String outputName) {
        // read the file, then decrypt it and write it to a file
        String fileAsString = readFile(filePath);
        String cipherText = decryption(fileAsString, inputKey);
        writeFile(cipherText, outputName);
    }

    public static void main(String[] args) {
        runTests();
        Scanner s = new Scanner(System.in);
        System.out.println("Do you want to encrypt or decrypt (E/D): ");
        String e_d = s.next();
        System.out.println("Filename: ");
        String fileName = s.next();
        System.out.println("Secret key: ");
        String secretKey = s.next();
        System.out.println("Output file: ");
        String outputFile = s.next();
        if (e_d.equalsIgnoreCase("E")) {
            fileEncryption(fileName, secretKey, outputFile);
        } else if (e_d.equalsIgnoreCase("D")) {
            fileDecryption(fileName, secretKey, outputFile);
        } else {
            System.out.println("Incorrect input.");
        }
       }
}