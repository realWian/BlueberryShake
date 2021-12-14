import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import java.security.MessageDigest;

class BlueberryShake {
    static char[] rawCounterElements = {
        'a',
        '?',
        'Z',
        '/',
        'b',
        '=',
        'Y',
        'c',
        'x',
        'X',
        'D',
        'y',
        'B',
        '<',
        'z',
        'A'
    };

    static char[][] rawCaesarCypers = {
        {'?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a'},
        {'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?'},
        {'/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z'},
        {'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/'},
        {'=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b'},
        {'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '='},
        {'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y'},
        {'x', 'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c'},
        {'X', 'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x'},
        {'D', 'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X'},
        {'y', 'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D'},
        {'B', '<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y'},
        {'<', 'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B'},
        {'z', 'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<'},
        {'A', 'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z'},
        {'a', '?', 'Z', '/', 'b', '=', 'Y', 'c', 'x', 'X', 'D', 'y', 'B', '<', 'z', 'A'}
    };

    static ArrayList<Character> counterElements = new ArrayList<Character>();
    static ArrayList<ArrayList<Character>> caesarCypers = new ArrayList<ArrayList<Character>>();

    static String concat(String str1, char char1) {
        StringBuilder sb  = new StringBuilder(str1);
        sb.append(char1);
        return sb.toString();
    }

    static String concatChar(char char1, char char2) {
        StringBuilder sb  = new StringBuilder("");
        sb.append(char1);
        sb.append(char2);
        return sb.toString();
    }

    static String counter(int asciiValue) {
        String returnString = "";
        int base0 = asciiValue % 16;
        int base1 = (asciiValue - base0) / 16;
        returnString = concat(returnString, counterElements.get(base1));
        returnString = concat(returnString, counterElements.get(base0));
        return returnString;
    }

    static char uncounter(String toUncount) {
        return (char) ((16 * counterElements.indexOf(toUncount.charAt(0))) + counterElements.indexOf(toUncount.charAt(1)));
    }

    static String encodeMyCeasarCypher(String stringToEncode, String encodingKey) {
        String returnString = "";
        for (int i = 0; i < stringToEncode.length(); i++) {
            returnString += caesarCypers.get(((int) encodingKey.charAt(i)) % 16).get(counterElements.indexOf(stringToEncode.charAt(i)));
        }
        return returnString;
    }

    static String decodeMyCaesarCypher(String stringToDecode, String encodingKey) {
        String returnString = "";
        for (int i = 0; i < stringToDecode.length(); i++) {
            returnString += counterElements.get(caesarCypers.get(((int) encodingKey.charAt(i)) % 16).indexOf(stringToDecode.charAt(i)));
        }
        return returnString;
    }

    static ArrayList<Integer> convertStringtoIntArray(String toConvert) {
        ArrayList<Integer> returnArray = new ArrayList<Integer>();
        for (int i = 0; i < toConvert.length(); i++) {
            returnArray.add((int) toConvert.charAt(i));
        }
        return returnArray;
    }

    static String getEncodingKey(String rawKey, String toEncode) {
        String returnString = "";
        int iterations = 0;
        for (int i = 0; i < toEncode.length(); i++) {
            returnString += rawKey.charAt(iterations);
            if (iterations == rawKey.length()) {
                iterations = 0;
            }
        }
        return returnString;
    }

    static String encode(String stringToEncode, String password) {
        for (char element : rawCounterElements) {
            counterElements.add(element);
        }
        for (char[] element : rawCaesarCypers) {
            caesarCypers.add(new ArrayList<Character>());
            for (char subElement : element) {
                caesarCypers.get(caesarCypers.size() - 1).add(subElement);
            }
        }  
        String returnString = "";
        String firstEncode = "";
        ArrayList<Integer> intArray = convertStringtoIntArray(stringToEncode);
        for (int item : intArray) {
            firstEncode += counter(item);
        }
        returnString = encodeMyCeasarCypher(firstEncode, getEncodingKey(password, firstEncode));
        return returnString;
    }

    static String decode(String encodedString, String password) {
        for (char element : rawCounterElements) {
            counterElements.add(element);
        }
        for (char[] element : rawCaesarCypers) {
            caesarCypers.add(new ArrayList<Character>());
            for (char subElement : element) {
                caesarCypers.get(caesarCypers.size() - 1).add(subElement);
            }
        }
        String returnString = "";
        String firstDecode = decodeMyCaesarCypher(encodedString, getEncodingKey(password, encodedString));
        for (int i = 0; i < firstDecode.length(); i += 2) {
            returnString = concat(returnString, uncounter(concatChar(firstDecode.charAt(i), firstDecode.charAt(i + 1))));
        }
        return returnString;
    }

    static String input() {
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine();
    }

    static ArrayList<String> readFileToArray(String fileName) {
        try {
            File assetFile = new File(fileName);
            Scanner assetReader = new Scanner(assetFile);
            ArrayList<String> assets = new ArrayList<String>();
            while (assetReader.hasNextLine()) {
                assets.add(assetReader.nextLine());
            }
            assetReader.close();
            return assets;
        } catch (FileNotFoundException FileError) {
            try {
                File assetFile = new File(fileName);
                assetFile.createNewFile();
                Scanner assetReader = new Scanner(assetFile);
                ArrayList<String> assets = new ArrayList<String>();
                while (assetReader.hasNextLine()) {
                    assets.add(assetReader.nextLine());
                }
                assetReader.close();
                return assets;
            } catch (IOException IOError) {
                System.out.println("An IO error has occured.");
                System.exit(0);
                return null;
            }
        }
    }

    static Hashtable<String, String> loadHashtable(ArrayList<String> inputArray, String password) {
        Hashtable<String, String> returnHashtable = new Hashtable<String, String>();
        for (int i = 1; i < inputArray.size(); i += 2) {
            returnHashtable.put(decode(inputArray.get(i), password), decode(inputArray.get(i + 1), password));
        }
        return returnHashtable;
    }

    static ArrayList<String> loadIds(ArrayList<String> inputArray, String password) {
        ArrayList<String> returnArray = new ArrayList<String>();
        for (int i = 1; i < inputArray.size(); i += 2) {
            returnArray.add(decode(inputArray.get(i), password));
        }
        return returnArray;
    }

    static String sha256sum(String toHash) {
        String returnString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(toHash.getBytes("ASCII"));
            for (byte element : hash) {
                returnString = returnString + String.format("%02X", element);
            }
        } catch (Exception error) {
            System.out.println("Oops");
        }
        return returnString;
    }

    public static void main(String[] args) {
        String userPassword;
        String searchInput;
        String mainInput;
        String inputA;
        String newID;
        String newPassword;
        String changeID;
        String changePassword;
        String deleteID;
        String deleteChoice;
        String checkAdmin;
        String changeAdmin;
        ArrayList<String> rawAssets = readFileToArray("BlueberryShakeAssets.txt");
        System.out.println("Welcome to Blueberry Shake!!!");
        if (rawAssets.isEmpty()) {
            System.out.println("Setup:\n Please enter an Admin password (You will use this password to access all of your other passwords)");
            String newAdminPassword = input();
            rawAssets.add(sha256sum(sha256sum(newAdminPassword)));
        }
        String adminPassword = rawAssets.get(0);
        Hashtable<String, String> assets = new Hashtable<String, String>();
        ArrayList<String> assetIds = new ArrayList<String>();
        for (int i = 4; i > -1; i--) {
            System.out.println("Please enter your Admin password:");
            userPassword = input();
            if (sha256sum(sha256sum(userPassword)).equals(adminPassword)) {
                adminPassword = userPassword;
                assets = loadHashtable(rawAssets, adminPassword);
                assetIds = loadIds(rawAssets, adminPassword);
                System.out.println("Correct!!");
                break;
            } else {
                System.out.println("Incorrect. \nYou have " + i + " guesses remaining.");
                if (i == 0) {
                    System.exit(0);
                }
            }
        }
        while (true) {
            System.out.println("\nMain Menu (Please pick an Option):");
            System.out.println("A. View Passwords.");
            System.out.println("B. Add/Change/Delete Passwords.");
            System.out.println("C. Change Admin Password.");
            System.out.println("D. Quit (default).");
            mainInput = input().toUpperCase();
            if (mainInput.equals("A")) {
                Collections.sort(assetIds);
                System.out.println("\nHere is a list of all your passwords:");
                for (String assetID : assetIds) {
                    System.out.println(assetID + ": " + assets.get(assetID));
                }
                while (true) {
                    System.out.println("\nEnter the ID of the password you wish to search for. (ENTER to return to Main Menu)");
                    searchInput = input();
                    if (searchInput.equals("")) {
                        break;
                    }
                    if (assets.containsKey(searchInput)) {
                        System.out.println(searchInput + ": " + assets.get(searchInput));
                    } else {
                        System.out.println("There does not seem to be any password matching your query. (Check for spellings errors)");
                    }
                }
            } else if (mainInput.equals("B")) {
                while (true) {
                    System.out.println("\nDo you want to:");
                    System.out.println("A. Add a password.");
                    System.out.println("B. Change a password.");
                    System.out.println("C. Delete a password.");
                    System.out.println("D. Return to Main Menu (default).");
                    inputA = input().toUpperCase();
                    if (inputA.equals("A")) {
                        System.out.println("\nPlease enter the ID of the new password:");
                        newID = input();
                        System.out.println("Please enter the new password:");
                        newPassword = input();
                        assetIds.add(newID);
                        assets.put(newID, newPassword);
                        System.out.println("Password successfully added!!");
                    } else if (inputA.equals("B")) {
                        System.out.println("\nPlease enter the ID of the password you would like to change:");
                        changeID = input();
                        if (assetIds.contains(changeID)) {
                            System.out.println("Please enter the new password for the ID:");
                            changePassword = input();
                            assets.replace(changeID, changePassword);
                            System.out.println("Password Successfully changed.");
                        } else {
                            System.out.println("There does not seem to be any passwords matching this ID.");
                        }
                    } else if (inputA.equals("C")) {
                        System.out.println("\nPlease enter the ID of the password you would like to delete.");
                        deleteID = input();
                        System.out.println("Are you sure you want to delete the password with ID: " + deleteID + "? (y/N)");
                        deleteChoice = input().toLowerCase();
                        if (deleteChoice.equals("y")) {
                            assetIds.remove(deleteID);
                            assets.remove(deleteID);
                            System.out.println("Password Successfully removed.");
                        } else {
                            System.out.println("Phew, that was close. ;-)");
                        }
                    } else {
                        break;
                    }
                }
            } else if (mainInput.toUpperCase().equals("C")) {
                System.out.println("\nPlease enter your Admin password:");
                checkAdmin = input();
                if (checkAdmin.equals(adminPassword)) {
                    System.out.println("Please enter your new Admin password:");
                    changeAdmin = input();
                    adminPassword = changeAdmin;
                    System.out.println("Admin password successfully changed.");
                } else {
                    System.out.println("Incorrect Admin Password.");
                }
            } else {
                try {
                    FileWriter modifiedFile = new FileWriter("BlueberryShakeAssets.txt");
                    modifiedFile.write(sha256sum(sha256sum(adminPassword)) + "\n");
                    for (String assetID : assetIds) {
                        modifiedFile.write(encode(assetID, adminPassword) + "\n");
                        modifiedFile.write(encode(assets.get(assetID), adminPassword) + "\n");
                    }
                    modifiedFile.close();
                } catch (IOException error) {
                    System.out.println("An IO error has occured.");
                }
                System.out.println("Goodbye!!!");
                System.exit(0);
            }
        }
    }
}