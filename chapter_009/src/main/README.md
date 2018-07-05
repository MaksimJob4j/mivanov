# chapter_009 / userservlet

## Приложение для работы с пользователями.

##### Приложение является базой данных пользователей.  
 Пользователь может иметь одну из 2х ролей - **Admin** или **User**:  
 * **User** имеет право просматривать список пользователей и редактировать свои данные _(кроме роли)_.  
 * **Admin** имеет право создавать, удалять и редактировать любого пользователя _(в том числе роль)_.  

##### Проложение содержит 4 экрана:  
Экран авторизации:  
![Image alt](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/src/main/images/Login.png)  
Экран списка пользователей в зависимости от роли владельца имеет разный вид. 
Список пользователей ADMIN:  
 ![Image alt](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/src/main/images/AdminsList.png)   
 Список пользователей USER:  
 ![Image alt](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/src/main/images/UsersList.png)   
Экран саздания нового пользоваделя:  
  ![Image alt](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/src/main/images/CreatUser.png)   
Экран редактирования пользоваделя:  
  ![Image alt](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/src/main/images/EditUser.png)  
