# Clone it
*git clone https://github.com/nestor-almarza/GestorRecursos.git*

Once in your local machine follow instructions. For any problem, bug
or improvement report to `nestor.almarza.m@gmail.com`. Thanks in advance.

# 1. Local setup 
# 2. Docker

# 1. local

# MariaDB
Connect to your mariaDB, create application.properties file:
[gestor/backend/src/main/resources/application.properties]
on this address you may use application.example as a base, just change what you need to conect.

On your SQL CLIENT load .sql dumps in [gestor/sql-data/] in this order;
1. user.sql 
2. candidate.sql 

# Backend 
[backend/]
*mvn install*
*mvn spring-boot:run*

# Frontal
[frontal/]
*npm install*
*npm start*

# Log in in browser
Et voilà. Just make sure the ports 8080 and 3306 are not blocked.
`http://localhost:4200/users/login`
user:        madrid@madrid.com
password:    madrid

ENJOY!!

# 2. Docker
Assuming you have installed docker-compose on your machine;

Create file [gestor/backend/src/main/resources/application.properties].
Copy the content of application.properties.docker and paste it on
application.properties.

Place yourself on the main folder [gestor/] and run on the terminal:

 *docker-compose build*

 *docker-compse up*

Et voilà. Just make sure the ports 8080 and 3306 are not blocked.
On your browser access  `http://localhost:4200/users/login`

If you get a timed out error exception it might be your firewall blocking the backend.

I haven't injected via code any user. So you'll have to access the mariaDB container's shell and run the script on [gestor/sql-data/user.sql] to be able to log in;

*docker exec -ti container_name_you_want_to_access /bin/bash*

In this case it's mariadb container. Once there:

*mysql*
*show databases*
*use gestorrecursos*
Now insert scripts user.sql and candidate.sql
Exit and login on browser.

user:        madrid@madrid.com
password:    madrid

ENJOY!!

# #################################################################
For any problem, bug or improvement report to 
`nestor.almarza.m@gmail.com`. Thanks in advance.
# #################################################################
