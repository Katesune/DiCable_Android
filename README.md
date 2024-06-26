# DiCable_Android

Приложение на базе Android

Реализовано подключение к собственному API - https://github.com/Katesune/DiCable_API.

Аутентификация:

<img height="438.75" width="202.5" alt="auth" src="https://user-images.githubusercontent.com/72875986/172069704-3fba7500-fa25-4931-9f82-f7ecb6425ba2.jpg">

Запрос к API осуществляется с главного экрана по IP-адресу и порту коммутатора, после чего результат диагностики отображается на виджете карты и сохраняется в базу данных: 

<img height="438.75" width="202.5" alt="main" src="https://user-images.githubusercontent.com/72875986/172069750-da39e15b-ba36-41c8-a9cf-4d0ad8626508.jpg">

На каждом виджете расположены две кнопки, при нажатии элемент добавляется в коллекцию, левая кнопка добавляет элемент в коллекцию "Просмотрено", правый - "Фиксировано".
Если кнопка черного цвета, элемент уже находится в коллекции. В правом нижнем углу виджета есть кнопка, которая раскрывает или скрывает элемент. Элементы данных диагностики реализованы с помощью фрагментов.

<img height="438.75" width="202.5" alt="collection" src="https://user-images.githubusercontent.com/72875986/172069833-5c57ccfd-2b0d-44d5-93ba-221cc1725591.jpg">   <img height="438.75" width="202.5" alt="open" src="https://user-images.githubusercontent.com/72875986/172069847-357bbe02-0faa-43e8-8464-7ca50ab7a28e.jpg">

Добавлен также поиск элементов по IP-адресу и небольшой график, считающий количество запросов по IP-адресам:

<img height="438.75" width="202.5" alt="search" src="https://user-images.githubusercontent.com/72875986/172069882-7d9ab136-8c3a-4506-9301-d27ee656792a.jpg">   <img height="438.75" width="202.5" alt="search" src="https://user-images.githubusercontent.com/72875986/172071047-53f4de2b-3019-4ab1-b652-800ccb0f8123.jpg">
