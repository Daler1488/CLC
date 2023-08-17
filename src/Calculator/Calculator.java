package Calculator;
import java.util.Scanner;
import java.util.TreeMap;

    public class Calculator {
        public static void main(String[] args) {
            try {
                Converter converter = new Converter();
                String[] actions = {"+", "-", "/", "*"};
                String[] regexActions = {"\\+", "-", "/", "\\*"};
                Scanner scn = new Scanner(System.in);
                System.out.print("Введите выражение: ");
                String exp = scn.nextLine();
                //Определяем арифметическое действие:
                int actionIndex = -1;
                for (int i = 0; i < actions.length; i++) {
                    if (exp.contains(actions[i])) {
                        actionIndex = i;
                        break;
                    }
                }
                //Если не нашли арифметического действия, завершаем программу
                if (actionIndex == -1) {
                    System.out.println("Некорректное выражение");
                    return;
                }
                //Делим строчку по найденному арифметическому знаку


                String[] data = exp.split(regexActions[actionIndex]);
                if (data.length != 2) {
                    throw new Exception(" Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                }
                //Определяем, находятся ли числа в одном формате (оба римские или оба арабские)
                if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
                    int a, b;
                    //Определяем, римские ли это числа
                    boolean isRoman = converter.isRoman(data[0]);
                    if (isRoman) {
                        //если римские, то конвертируем их в арабские
                        //X+V
                        //x-10
                        //v - 5
                        a = converter.romanToInt(data[0]);
                        b = converter.romanToInt(data[1]);

                    } else {
                        //если арабские, конвертируем их из строки в число
                        a = Integer.parseInt(data[0]);
                        b = Integer.parseInt(data[1]);
                    }
                    if (!cNumbers(a) | !cNumbers(b)) {
                        throw new Exception("Значения больше или меньше допустимого диапазона  ");
                    }
                    //выполняем с числами арифметическое действие
                    int result;
                    switch (actions[actionIndex]) {
                        case "+":
                            result = a + b;
                            break;
                        case "-":
                            result = a - b;
                            break;
                        case "*":
                            result = a * b;
                            break;
                        default:
                            result = a / b;
                            break;
                    }
                    //15->XV
                    if (isRoman) {
                        //если числа были римские, возвращаем результат в римском числе
                        System.out.println(converter.intToRoman(result));
                    } else {
                        //если числа были арабские, возвращаем результат в арабском числе
                        System.out.println(result);
                    }
                } else {
                    System.out.println("Используются одновременно разные системы счисления");
                }
            } catch (Exception i) {
                System.out.println(i);
            }

        }

        private static boolean cNumbers(int i) {

            return i > 0 & i < 11;
        }


        private static class Converter {

            TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
            TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

            public Converter() {
                romanKeyMap.put('I', 1);
                romanKeyMap.put('V', 5);
                romanKeyMap.put('X', 10);
                romanKeyMap.put('L', 50);
                romanKeyMap.put('C', 100);
                romanKeyMap.put('D', 500);
                romanKeyMap.put('M', 1000);

                arabianKeyMap.put(1000, "M");
                arabianKeyMap.put(900, "CM");
                arabianKeyMap.put(500, "D");
                arabianKeyMap.put(400, "CD");
                arabianKeyMap.put(100, "C");
                arabianKeyMap.put(90, "XC");
                arabianKeyMap.put(50, "L");
                arabianKeyMap.put(40, "XL");
                arabianKeyMap.put(10, "X");
                arabianKeyMap.put(9, "IX");
                arabianKeyMap.put(5, "V");
                arabianKeyMap.put(4, "IV");
                arabianKeyMap.put(1, "I");

            }


            public boolean isRoman(String number) {
                return romanKeyMap.containsKey(number.charAt(0));
            }

            //15
            public String intToRoman(int number) {
                String roman = "";
                int arabianKey;
                do {
                    arabianKey = arabianKeyMap.floorKey(number);
                    roman += arabianKeyMap.get(arabianKey);
                    number -= arabianKey;
                } while (number != 0);
                return roman;


            }

            //XV
            public int romanToInt(String s) {
                int end = s.length() - 1;
                char[] arr = s.toCharArray();
                int arabian;
                int result = romanKeyMap.get(arr[end]);
                for (int i = end - 1; i >= 0; i--) {
                    arabian = romanKeyMap.get(arr[i]);

                    if (arabian < romanKeyMap.get(arr[i + 1]))
                    {
                        result -= arabian;
                    } else {
                        result += arabian;
                    }
                }
                return result;
            }
        }
    }




