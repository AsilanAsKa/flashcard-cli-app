Flashcard CLI App — Бие даалт №1 (CSA311)
Энэ бол энгийн **Flashcard сургах систем** бөгөөд командын мөрийн интерфейс (CLI)-тэйгээр ажилладаг. Энэхүү даалгавар нь Жава хэл, объект хандалтат программчлал, CLI аргумент, интерфэйс, composition ойлголтууд дээр суурилсан болно.


-  Жава хэл
-  Объект хандалтат программчлал
-  CLI аргумент задлах
-  Интерфэйс болон composition
-  GitHub + Maven дэд бүтэц  гэсэн ойлголтууд дээр суурилсан болно.


## Төслийн бүтэц
flashcard-app/
├── pom.xml                      Maven төслийн тохиргооны файл
├── README.md                    Төслийн тайлбар
├── cards.txt                    Асуулт-хариултын өгөгдлийн файл
└── src/
    ├── main/
    │   └── java/
    │       └── flashcard/
    │           ├── Main.java                       Програм эхлэх цэг
    │           ├── Card.java                        Нэг flashcard-г илэрхийлнэ
    │           ├── CardOrganizer.java               Карт зохион байгуулагч интерфэйс
    │           └── RecentMistakesFirstSorter.java   Сүүлд буруу хариулсан картыг эхэнд харуулна
    └── test/
        └── java/
            └── flashcard/
                └── AppTest.java (optional)          Нэгж тестүүд байж болно

Compile хийх:
mvn compile 
mvn exec:java "-Dexec.mainClass=flashcard.Main" "-Dexec.args=cards.txt --repetitions 3"


