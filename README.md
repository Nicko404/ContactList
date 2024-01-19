<h1>Приложение "Список контактов"</h1>
<p>
Веб приложение для сохранения контактов. Контакт должен иметь имя, фамилию, email, номер телефона. 
Сохранение и изменение контактов происходит через форму. На главной странице выводится список сохраненных контактов.
</p>

<h3>Конфигурационный файл application.yaml</h3>

<p>В файле конфигурации application.yaml можно указать настройки для подключения к базе данных. <br>Свойство 
"application:init:download-default-contacts" служит для загрузки стандартных контактов.<br>
Свойство "logging:level:root" служит для указания уровня логирования.</p>

<h3>Docker compose</h3>
<p>Файл docker-compose.yaml содержит настрйку запуска PostgreSQL в Docker. Для запуска 
контейнера используйте команду:<br>

    docker compose up

</p>
