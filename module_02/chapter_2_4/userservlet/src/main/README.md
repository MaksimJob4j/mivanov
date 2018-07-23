# chapter_009 / UserServlet

## Приложение для работы с пользователями.

#### Приложение является базой данных пользователей.  
 Пользователь может иметь одну из 2х ролей - **Admin** или **User**:  
 * **User** имеет право просматривать список пользователей и редактировать свои данные _(кроме роли)_.  
 * **Admin** имеет право создавать, удалять и редактировать любого пользователя _(в том числе роль)_.  

#### Приложение содержит 4 экрана:  
Экран авторизации:  
![](https://github.com/MaksimJob4j/mivanov/raw/master/modul_02/chapter_2_4/userservlet/src/main/images/Login.png)  
Экран списка пользователей в зависимости от роли владельца имеет разный вид. 
Список пользователей ADMIN:  
 ![](https://github.com/MaksimJob4j/mivanov/raw/master/modul_02/chapter_2_4/userservlet/src/main/images/AdminsList.png)   
 Список пользователей USER:  
 ![](https://github.com/MaksimJob4j/mivanov/raw/master/modul_02/chapter_2_4/userservlet/src/main/images/UsersList.png)   
Экран создания нового пользователя:  
  ![](https://github.com/MaksimJob4j/mivanov/raw/master/modul_02/chapter_2_4/userservlet/src/main/images/CreatUser.png)   
Экран редактирования пользователя:  
  ![](https://github.com/MaksimJob4j/mivanov/raw/master/modul_02/chapter_2_4/userservlet/src/main/images/EditUser.png)  
