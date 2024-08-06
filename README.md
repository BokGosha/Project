## Инструкция по запуску и использованию приложения-переводчика
1. Убедитесь, что на Вашем компьютере установлены PostgreSQL 14+, Maven 3.6.0+, JDK 19.0.1+ и Docker
2. Склонируйте репозиторий, перейдите в корневую директорию проекта, откройте терминал и введите команду docker-compose up --build
3. Перейдите на https://translate.googleapis.com/translate_a/single?client=gtx&sl={sourceLanguage}&tl={targetLanguage}&dt=t&q={word}. Вместо {sourceLanguage}, {targetLanguage} и {word} введите необходимые параметры для получения перевода