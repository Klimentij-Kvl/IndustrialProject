# IndustrialProject
#### **Команда разработчиков**: [Апанчёнок Александр](https://github.com/Corovinus),[ Ковалевский Климентий](https://github.com/Klimentij-Kvl), [Горошко Никита](https://github.com/NikitaHaroshka), [Сахоненко Мария](https://github.com/MariaSakhonenko).

## Описание проекта
Основная цель проекта - создать консольное приложение, которое читает данные из входного файла, обрабатывает их и записывает в выходной файл. Проект поддерживает различные форматы файлов (plain text, XML, JSON, YAML) и может обрабатывать заархивированные и зашифрованные файлы. Также предусмотрены различные способы обработки информации, включая использование регулярных выражений и сторонних библиотек.

### Возможности проекта:
1. Чтение файла (поддерживаемые форматы txt/json/xml/yaml)
2. Обработка математических выражений
    - Сведение к постфиксной форме записи и подсчет по правилам постфиксной формы записи
    - Использование библиотеки expr4j
3. Запись вычисленных математических выражений в файл (поддерживаемые форматы txt/json/xml/yaml)
4. Архивация файла (поддерживаемые форматы zip/rar/tar)
5. Извлечение из архива файла (поддерживаемые форматы zip/rar/tar)
6. Шифрование/расшифрование файла (алгоритм AES)

## Используемые технологии и паттерны:
- **Design Patterns**: Builder, Decorator, Singleton
- **Форматы файлов**: plain text, XML, JSON, YAML
- **Библиотеки**: expr4j, Jackson, StAX
- **Шифрование**: AES
- **Архивация**: ZIP, RAR, TAR
- **Тестирование**: JUnit

## Структура проекта:
### DataBase
- **DataStorage**: Singleton-класс, представляющий хранилище данных.

### DataProcessor
- **Calculator**: Интерфейс и классы для различных типов калькуляторов (RegexCalculator, LibraryCalculator).
- **Extractor**: Интерфейс и классы для различных типов экстракторов (RegexExtractor, NonRegexExtractor).
- **Replacer**: Интерфейс и классы для различных типов заменителей (RegexReplacer, NonRegexReplacer).

### FileProcessor
- **DiffReader**: Интерфейс и классы для чтения различных форматов файлов (TxtDiffFileReader, JsonDiffFileReader, XmlDiffFileReader, YamlDiffFileReader).
- **DiffWriter**: Интерфейс и классы для записи различных форматов файлов (TxtDiffFileWriter, JsonDiffFileWriter, XmlDiffFileWriter, YamlDiffFileWriter).
- **DiffReaderDecorator**: Классы-декораторы для расширения функциональности чтения (ZipDearchivingDiffReaderDecorator, DecryptionDiffReaderDecorator).
- **DiffWriterDecorator**: Классы-декораторы для расширения функциональности записи (ZipArchivingDiffWriterDecorator, EncryptionDiffWriterDecorator).

### UIProcessor
- **ConsoleUI**: Классы для консольного интерфейса.
- **GUI**: Классы для графического интерфейса с использованием JavaFX.

### Тесты:
Проект включает в себя обширное покрытие тестами:
- **CalculateExpressionTest**
- **LibraryCalculatorTest**
- **RegexCalculatorTest**
- **NonRegexExtractorTest**
- **RegexExtractorTest**
- **RegexReplacerTest**
- **DataProcessorTest**
- **DiffReaderTest**
- **DiffWriterTest**
- **DiffWriterReaderIntegratedTest**
- **ConsoleUITest**
- **GUITest**

## Примечания:
- Конвертация файлов реализована с использованием паттерна Builder.
- Для работы с JSON используется библиотека Jackson.
- Для работы с XML используется технология StAX.
- Для работы с ZIP, RAR, TAR используются стандартные Java-утилиты.
- Вложенность операций: архивация, шифрование и т.д. поддерживается.
- **expr4j**: это библиотека, которая позволяет выполнять вычисления математических выражений. Она поддерживает базовые арифметические операции, функции и скобки. expr4j также может обрабатывать более сложные выражения, такие как тригонометрические функции и логарифмы.

## Книги и методологии:
Команда изучала следующие книги и методологии для создания проекта:
- "Чистая архитектура: принципы дизайна"
- "Экстремальное программирование"
- Методология Test-Driven Development (TDD)

## Запуск проекта:
Для запуска консольного приложения используйте основной класс Main;

Для запуска графического интерфейса используйте класс GUI;

Примеры файлов для парсинга находятся в папке resources.

