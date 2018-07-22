# chapter_009 / Музыкальная площадка

#### Приложение является базой данных пользователей и их музыкальных предпочтений.  
 Пользователь может иметь одну из 3х ролей - **Admin**, **Mandator** или **User**:  
 Роли наделены следующими правами:  
 * **User**
   * просматривать список пользователей;
   * редактировать свои данные _(кроме роли)_.  
 * **Mandator** 
   * права User;
   * редактировать данные пользователей User _(кроме роли)_;
   * создавать User.
 * **Admin**
   * права Mandator;
   * редактировать любых пользователей _(в том числе менять роль)_;
   * создавать любых пользователей;
   * удалять любых пользователей.
 
#### Приложение содержит 4 экрана:  
Экран авторизации:  
![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/Login.png)  

Экран списка пользователей в зависимости от роли владельца имеет разный вид. 
Список пользователей  (под ADMIN):  
 ![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/AdminsList.png)   
 * Если пользователь зашел в приложение под MANDATOR, то в списке будут показаны только кнопки INFO напротив USER. Кнопки DELETE отсутствуют;
 * Если пользователь зашел в приложение под USER, то в списке отсутствуют все кнопки;
 * Кнопка CREATE NEW USER у USER отсутствует.


Экран создания нового пользователя:  
  ![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/CreateUser.png)   
При создании и редактировании пользователей проводится проверка на пустые поля LOGIN, PASSWORD и ROLE, а так же на уникальность LOGIN пользователя в базе.


Экран информации пользователя (под ADMIN):  
  ![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/UserInfo.png)  
 * Кнопка DELETE USER присутствует только у ADMIN.

При добавлении музыкального стиля предлагается выбрать его из списка или добавить новый.

  ![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/SelectType.png)
  ![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/EnterType.png)  

Если после удаления музыкального стиля из предпочтений пользователя он не присутствует больше ни у одного из пользователей, то этот стиль удаляется из базы.

Экран редактирования пользователя (под ADMIN):  
  ![png](https://github.com/MaksimJob4j/mivanov/raw/master/chapter_009/music/src/main/images/EditUser.png)  
  
