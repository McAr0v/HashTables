public class Program {

    public static void main (String[] args){

        // Создаем массив с бакетами
        HashMap<String, String> hashMap1 = new HashMap<>(4);

        String oldValue;
        oldValue = hashMap1.put("7771789636", "Егор");
        oldValue = hashMap1.put("7771789621", "Петр");
        oldValue = hashMap1.put("7771739636", "Жанна");
        oldValue = hashMap1.put("7771789633", "Люба");
        oldValue = hashMap1.put("7771789634", "Майя");
        oldValue = hashMap1.put("7771789635", "Ольга");
        oldValue = hashMap1.put("7771789637", "Влад");
        oldValue = hashMap1.put("7771789638", "Александр");
        oldValue = hashMap1.put("7771789639", "Сергей");

        System.out.println(hashMap1);

        for (HashMap<String, String>.Entity element: hashMap1) {

            //element.key;
            //element.value;

        }


    }
}
