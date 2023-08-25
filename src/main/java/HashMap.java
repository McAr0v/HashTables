
// K - key, ключ
// V - value, значение
public class HashMap<K, V> {

    // ВЫВЕСТИ ВСЕ ЭЛЕМЕНТЫ ХЭЩТАБЛИЦЫ НА ЭКРАН через toString()
    @Override
    public String toString() {

        // Создаем стрингбилдер, который будет склеивать наши значения
        StringBuilder stringBuilder = new StringBuilder();

        // В самом начале ставим квадратную скобку для красоты)
        stringBuilder.append('[');
        // Делаем переход на новую строчку
        stringBuilder.append('\n');

        // Проходимся по нашей HashMap
        for (int i = 0; i<buckets.length; i++){

            // Если на i-м индексе не налл,
            // Значит там лежит бакет
            // Берем этот бакет и проваливаемся в него
            if (buckets[i] != null){

                // Начинаем с головы
                Bucket.Node node = buckets[i].head;

                // Пока ноды не кончатся
                while (node != null){

                    // Добавляем к стринг-билдеру:

                    stringBuilder.append("Номер индекса хранения бакета: ");

                    stringBuilder.append(i); // Подставляем индекс, в котором лежат бакеты

                    stringBuilder.append(", Ключ ноды: ");

                    stringBuilder.append(node.value.key); // подставляем ключ

                    stringBuilder.append(", Значение ноды: ");

                    stringBuilder.append(node.value.value); // подставляем значение

                    // Переходим на следующую строку
                    stringBuilder.append('\n');

                    // идем по элементам дальше
                    node = node.next;
                }

            }

        }

        // Ставим закрывающую скобку
        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    // Entity - сущность,
    // Элемент Хэштаблицы
    // Так как значение Node состоит из 2х элементов
    // Ключа и собвственно значения
    class Entity{
        K key;
        V value;
    }

    // В массиве будут хранится связанные списки
    // Назовем их Бакет

    class Bucket {

        // Указатель на главный элемент связного списка
        Node head;

        // Узлы нашего бакета, ноды
        class Node{

            // Указатель на следующий элемент связного списка
            Node next;

            Entity value; // Значение узла, указывающее на элемент хэш таблицы


        }


        // Функция добавления в бакет новой ноды
        // Если в бакете есть значение, то функция
        // возвращает это старое значение.

        // Если значения не было, возвращаем нал
        public V add (Entity entity){
            Node node = new Node();
            node.value = entity;

            // Если бакет был создан только сейчас и элементов там нет
            // То мы его создаем и немедленно выходим
            if (head == null){
                head = node;

                // Возвращаем налл, так как никакого раньше элемента не было
                // и мы возвращаем пустое значение нал
                return null;
            }

            // Дальше рассматриваем коллизию
            // Когда в бакете уже есть значения

            // Создаем переменную для хранения головы
            Node currentNode = head;

            // Бесконечный цикл)
            while (true){

                // Если у головы значение ключа равно ключу сущности
                if (currentNode.value.key.equals(entity.key)){
                    // Сохраняем истиное значение головы в переменную
                    V buf = currentNode.value.value;
                    // Перезаписываем значение головы на новую сущность
                    currentNode.value.value = entity.value;

                    // Возвращаем старое значение
                    return buf;
                }

                // Если не совпали ключи, перебираем следующие узлы бакета

                // Если есть следующие элементы, то в цикл их прогоняем
                // и меняем значение головы на следующий, потом на следующий итд
                if (currentNode.next != null){

                    currentNode = currentNode.next;
                }
                else {
                    // если next равен налл, мы дошли до конца списка
                    // и ключей совпадающих нет, то тогда мы в конец
                    // вставляем нашу ноду и возвращаем налл
                    currentNode.next = node;
                    return null; // говоря, что элемента с подобным ключем еще не было добавлено в хэш таблицу
                }

            }

        }

        // Функция перебора бакета и поиска значения по ключу
        public V get (K key){
            // Начинаем с головы
            Node node = head;

            // Пока ноды не кончатся
            while (node != null){
                // Если ключ ноды и искомый ключ совпадают
                if (node.value.key.equals(key)){
                    // Возвращаем значение ноды
                    return node.value.value;
                }
                // Если ключи первого элемента не совпали
                // идем по элементам дальше
                node = node.next;
            }
            // если по всем прошлись и ниче не нашли
            // возвращаем нал
            return null;

        }

        public V remove(K key){
            if (head == null){
                return null;
            }

            if (head.value.key.equals(key)){
                V buf = head.value.value;
                head = head.next;
                return buf;
            } else {
                Node node = head;
                while (node != null){
                    if (node.value.key.equals(key)){
                        V buf = node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
            }

            return null;

        }

    }

    // Наш массив бакетов
    // В которые будем класть Nodы
    private Bucket[] buckets;

    // Переменная будет хранить истинное количество
    // элементов, добавленных в мою хэш таблицу (массиве)
    private int size;

    // Константа изначального размера массива бакетов
    private static final int INIT_BUCKET_COUNT = 16;

    // Константа - если заполненность массива будет больше чем эта константа
    // то мы должны увеличить размер массива с бакетами
    private static final double LOAD_FACTOR = 0.5;

    // Первый вариант создания массива размерности по умолчанию
    public HashMap(){
        buckets = new HashMap.Bucket[INIT_BUCKET_COUNT];
    }

    // Второй вариант создания массива с возможностью самостоятельно задавать
    // размерность массива
    public HashMap(int initCount){
        buckets = new HashMap.Bucket[initCount];
    }

    // Возвращает значение по ключу
    public V get(K key){
        int index = calculateBucket(key); // Вычисляем индекс, где наша нода в массиве

        // Берем бакет под нашим индексом
        Bucket bucket = buckets[index];
        // Если бакета не существует
        if (bucket == null){
            return null; // то и возвращаем нал
        } else {
            // Если есть бакет, то заходим в бакет и перебираем
            return bucket.get(key);
        }
    }

    // Удаление элемента по ключу

    public V remove(K key){
        int index = calculateBucket(key); // Вычисляем индекс, где наша нода в массиве
        // Берем бакет под нашим индексом
        Bucket bucket = buckets[index];
        // Если бакета не существует
        if (bucket == null){
            return null; // то и возвращаем нал
        }
        V buf = bucket.remove(key);
        if (buf != null){
            size--;
        }
        return buf;
    }

    // Возвращает актуальный индекс в массиве
    private int calculateBucket (K key){
        // Берем по модулю (т.е само значение, без знака - или +)
        // Наш хэшкод ключа и берем остаток от деления на длину
        // Массива с бакетами
        // Так получится индекс, куда поместить нашу Nodу
        return Math.abs(key.hashCode()) % buckets.length;
    }

    // Метод рекалькуляции размерности нашего массива
    private void recalculate(){
        // Обнуляем счетчик кол-ва элементов
        size = 0;

        // Делаем копию старого массива
        Bucket[] old = buckets;

        // Перезаписываем наш массив с новой размерностью
        buckets = new HashMap.Bucket[old.length * 2];

        // Проходим по всем элементам старого массива
        for (int i = 0; i<old.length; i++){
            // Достаем каждый бакет из старого массива
            Bucket bucket = old[i];
            // Если бакеты есть
            if(bucket != null){
                // Берем в качестве начала голову
                Bucket.Node node = bucket.head;
                // Пока есть ноды проходим в цикле
                while (node != null){
                    // Берем каждую ноду и перезаписываем на новое место
                    put(node.value.key, node.value.value);
                    // переходим к следующей ноде
                    node = node.next;
                }
            }
        }

    }

    // Функция добавления в массив с бакетами
    // Добавляем значение по ключу и возвращает
    // Старое значение, которое находилось на этом месте
    public V put(K key, V value){
        // Для начала проверяем, размерность массива еще нормальная или
        // пора увеличивать его в размерах, чтобы уменьшить вероятность
        // попадания в 1 бакет больше 1 элемента
        if (size >= buckets.length * LOAD_FACTOR){
            recalculate();
        }

        int index = calculateBucket(key); // Вычисляем индекс, куда поместить нашу ноду в массив

        // Обращаемся к элементу под вычесленным индексом
        Bucket bucket = buckets[index];
        // Проверяем, лежит ли какое то уже значение под этим индексом,
        // или там ничего нет, т.е нал?

        if (bucket == null){
            // Если ничего не лежит в ячейке под индексом
            // создаем новый бакет
            bucket = new Bucket();
            //  Помещаем в ячейку новый бакет
            buckets[index] = bucket;
        }

        // Готовим Entity - сущность
        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        // Добавляем новую сущность в массив по индексу
        // А функция add возвращает старое значение,
        // которое хранилось в этой ячейке массива
        V buf = bucket.add(entity);

        // Когда buf равен нал, надо увеличить переменную size
        if (buf == null){
            size++;
        }

        return buf;
    }

}
