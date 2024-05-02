# Documentación de proyecto de Gestión de Reservas de Vuelos y Reservas de Habitación de Hotel con Spring Boot
Esta aplicación nos permite gestionar la inserción y manejo de datos de usuarios registrados, hoteles con sus respectivas habitaciones y reservas de éstas y también de vuelos y sus reservas. Este manejo de datos lo llevaremos a cabo mediante un CRUD (Create, Read, Update, Delete) de cada una de las entidades que hemos mencionado.
El manejo de la aplicación lo podremos llevar a cabo mediante la utilización de la aplicación postman o swagger UI ya que, al ser un proyecto de backEnd no contará con una interfaz gráfica de usuario, pero contamos con incluirla en versiones posteriores de la aplicación cuando el cliente nos dé una idea de como quiere que sea dicha interfaz.

## Estado del proyecto
Esta aplicación está aún en desarrollo, pero ya se han implementado las funciones necesarias para realizar las pruebas pertinentes y comprobar que todo lo que nos solicita el cliente funciona correctamente.
En versiones posteriores mejoraremos la recepción de datos para que el JSON recibido, o que haya que mandar, sea lo más básico y fácil de interpretar posible para el usuario o incluso para el compañero del frontEnd.
En esta primera versión hemos implementado 2 test unitarios para comprobar que pasan pero en versiones futuras tenemos intención de cumplir al menos la cobertura del 80-85% de cobertura. Los test se han realizado con mock investigando a parte de los conocimientos que poseíamos para implementar unos test de calidad.
El sistema cuenta con la seguridad de Spring Security para permitir al usuario que interactúe sólo con los endPoints que nos ha facilitado el cliente. En el futuro se estudiará el caso de los demás endPoints y el posible cambio del sistema de seguridad a JWT (Json Web Token).

## Tecnologías utilizadas
* JAVA 17 (JDK 17).
* Conexión a Bases de Datos con JPA (Java Persistence API) con Hibernate.
* Tomcat.
* Postman.
* [Swagger UI.](http://localhost:8080/doc/swagger-ui.html)
* Spring Security.
* Java Mockito.
* MySQL phpMyAdmin.
* XAMPP.

## Instalación
Para poder instalar esta aplicación basta con poseer un IDE con versión de JAVA 17 y JDK 17. En nuestro caso nos hemos decantado por IntelliJ debido a su expléndida interfaz gráfica y su fácil manejo, así como la sencilled y comodidad a lal hora de instalar plugins.
Hay que tener instalado también XAMP para la conexión con la base de datos y una aplicación web como por ejemplo phpMyAdmin para la administración de la base de datos.
Tendremos abierta la aplicación Postman para realizar las pruebas pertinentes e interactuar con la base de datos.
Una vez que comprobamos que tenemos todo iniciamos XAMP y entramos en phpMyAdmin y lanzamos el proyecto y pulsamos en Run, y en este momento se puede comenzar a utilizar la aplicación.

## Uso
Una vez iniciada la aplicación podremos abrir postman o swagger(la que más nos guste), en el caso de postman habrá que importar la collection de endpoints que hemos facilitado para que su uso sea más cómodo. Si nos decantamos con Swagger podremos ver, por medio de la documentación facilitada los diferentes mensajes que nos mandará la aplicación tanto si funciona correctamente como si falla por cualuqier asunto.
Dicho esto y sin importar la aplicación que queramos utilizar tendremos ante nosotros las siguientes Historias con las que tratar:
### 1. Historias de Usuario.
Nos permitirá realizar un CRUD completo de los usuarios que interactuen con la aplicación.
### 2. Historias de Hotel.
Nos permitirá realizar un CRUD completo de los Hoteles que se van a manejar en la agencia.
### 3. Historias de Habitación.
Nos permitirá realizar un CRUD completo de las Habitaciones que corresponden a cada hotel.
### 4. Historias de Reservas de Habitación.
Nos permitirá realizar un CRUD completo de las reservas que se realicen en las distintas habitaciones existentes en la base de datos.
### 5. Historias de Vuelos.
Nos permitirá realizar un CRUD completo de los vuelos que gestiona la agencia.
### 6. Historias de Reservas de Vuelos.
Nos permitirá realizar un CRUD completo de las reservas que se realicen de los diferentes vuelos manejados por la agencia.

## Configuración
Para la configuración basta con lo solicitado en el apartado de instalación ya que se descargará el proyecto completo y si se tiene todo lo indicado en ese punto el programa funcionará correctamente.

## Estructura de carpetas y Archivos
La estructura de carpetas y archivos se ha basado en la arquitectura multicapas:
* **Controller**
  - FlightBookingController
  - FlightController
  - HotelController
  - RoomBookingController
  - RoomController
  - UserController
* **Dto**
  - FlightBookingDto
  - FlightDto
  - HotelDto
  - RoomBookingDto
  - RoomDto
  - UserDto
* **Exception**
  - AgenciaException
* **Model**
  - Booking
  - Flight
  - FlightBooking
  - Hotel
  - Room
  - RoomBooking
  - User
* **Repository**
  - FlightBookingRepository
  - FlightRepository
  - HotelRepository
  - RoomBookingRepository
  - RoomRepository
  - UserRepository
* **Security**
  - SecurityConfig
* **Service**
  - FlightBookingService
  - FlightService
  - HotelService
  - IFlightBookingService
  - IFlightService
  - IHotelService
  - IRoomBookingService
  - IRoomService
  - IUserService
  - RoomBookingService
  - RoomService
  - UserService

 ## Contribuciones
Si deseas contribuir al proyecto, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tu funcionalidad o corrección de errores.
3. Realiza tus cambios y haz commits.
4. Sube tus cambios a tu fork.
5. Crea una solicitud de extracción.
   ¡Gracias por contribuir!

## Documentación de Métodos y clases
Al estar basado el modelo en la arquitectura multicapas todas las clases de la carpeta controller van a interactuar de la misma manera con sus services y estos a su vez con sus repository.
Por lo tanto, vamos a explicar las dos únicas clases que cambian un poco debido a los endPoints que nos ha solicitado el cliente:

### Clases Controller
```java
public Class FlightController{
```
Todas las clases del tipo Controller contendrán los endPoints necesarios para la realización del CRUD completo para interactuar con la base de datos. Recibe y envía la información a tratar a través de Postman e interactúa con el FlightService para respetar la arquitectura multicapas. En Las clases de reservas tendremos que acceder a otros services ya que interactúan con la información que ellas mismas no poseen debido a las relaciones establecidas en las entidades.
En estas clases se lleva a cabo la recepción de datos y validación de los mismos, posteriormente se envía al service para su procesamiento.
### Clases Dto
```java
public class FlightDto {
```
Estas clases se utilizan para simplificar la información que queremos que reciba el usuario a la hora de visualizar por ejemplo los vuelos disponibles, ya que muchos datos de la clase original no son necesarios para una simple visualización.
### Clase AgenciaException
```java
public class AgenciaException extends Exception {
```
Recoge las excepciones personalizadas que queremos mandar al usuario cuando realiza alguna operación de forma incorrecta o incompleta, por ejemplo, si no selecciona un usuario al hacer una reserva.
### Clases Model
```java
public class Flight {
```
Estas clases recogen las entidades que figurarán en la base de datos. En función de como definamos cada una de estas clases recibiremos una información u otra para luego poder trabajar con ella.
En el caso de las Bookings hemos optado por la herencia ya que la base de las dos clases era la misma y por cumplir con principios de buena práctica hemos decidido utilizar este sistema para las reservas.
### Clases Repository
```java
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
```
Estas clases son las encargadas de, por medio de peticiones hechas por el service, acceder a la base de datos y devolver la información solicitada. Un detalle a tener en cuenta cuando utilicemos Hibernate es que podemos realizar la Qery directamene en el método del repository si escribimos todos los atributos que queremos buscar o filtrar en la consulta, lo que nos facilita mucho el trabajo y acorta el código de manera significativa.
### Clase SecurityConfig
```java
public class SecurityConfig {
```
En esta clase se configura el acceso a los endPoints en los que queremos que se solicite usuario y contraseña y los que no hace falta los dejaremos públicos.
### Clases Service
```java
public class FlightService implements IFlightService {
```
Implementan una interfaz para sobreescribir sus métodos y así facilitar la reutilización de código y la inyección de dependencias, ya que algunos controller pueden llamar a más de un service.
Esta clase es la encargada de recibir la entrada de datos proporcionada por el controller y hacer las peticiones al repository. En esta clase se lleva a cabo la lógica de la aplicación.
### Clase AgenciaTurismoApplicationTest
```java
class AgenciaTurismoApplicationTests {
```
Esta clase es la que se encarga de realizar los test unitarios y mejorar la calidad del software identificando posibles errores y arantiza que se cumplen con los requisitos establecidos.
Como hemos comentado antes hemos realizado únicamente dos test para probar la solicitud de listado de hoteles y de vuelos registrados, pero la intención es cubrir en torno al 80-85% del código. Esto lo realizaremos en versiones posteriores.

### Métodos más importantes a tener en cuenta
Los métodos que nos ha solicitado el cliente, que son los que van a utilizar los usuarios y, por tanto, son los que no necesitan autenticación son:
* **getHotels()**: Obtener un listado de todos los hoteles registrados.
* **getAvailableRooms()**: Obtener un listado de todos las habitaciones disponibles en un determinado rango de fechas y según el destino seleccionado.
* **saveRoomBooking()**: Realizar una reserva de un habitación, indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación.
* **getFlights()**: Obtener un listado de todos los vuelos registrados.
* **getAvailableFlights()**: Obtener un listado de todos los vuelos disponibles para una fecha de ida y su correspondiente fecha de vuelta,  según el destino y el origen seleccionados.
* **saveFlightBooking()**: Realizar una reserva de un vuelo, indicando cantidad de personas, origen, destino y fecha de ida.

## Supuestos
* Se asume que los datos ingresados por el usuario (nombres, apellidos, fecha, etc.) son válidos y están formateados correctamente.
* No se implementa validación exhaustiva de datos de entrada para simplificar el ejemplo.
* Se asume que el usuario conoce el ID de todas las entidades ya que en la entrada de datos por medio de los JSON se necesita o se referencia de varias de ellas como por ejemplo en la reserva de un vuelo que tenemos que conocer el usuario y el vuelo a relacionar.
* Como al editar cualquier entidad, debido a que tenemos que enviar el JSON completo y figura el atributo "deleted", que es el que utilizamos para el borrado lógico, se asume que ese atributo tendrá que estar en estado "false" indicando que está activo y para borrar ya tenemos un método en el que seteamos a "true" dicho atributo.
* Se nos ha solicitado que para poder borrar un vuelo o una habitación no tienen que tener ninguna reserva activa, y se cumple, pero no se nos ha dicho nada de que haya que realizar esta comprobación con los hoteles ya que tienen relacionadas las habitaciones, por tanto se asume que para poder borrar un hotel es necesario que estén borradas todas las habitaciones y para que esto pase ninguna habitación del hotel puede tener ni una sóla reserva.
* La validación de datos sólo se controla que sea correcta en los endPoints sin autenticación, por lo que se asume que el gestor de la app llevará a cabo la correcta inserción de los datos.
* Se asume que el usuario sabe utilizar el IDE y que sabe como se inicia el programa.

## Problemas conocidos
Los más importantes son que a la hora de que el gestor de la app no inserte bien los registros puede setear algunos campos a "null" por lo que recalcamos que es muy importante que esta persona tenga esto en cuenta a la hora de editar cualquier entidad.
También somos conscientes de que no hemos cubierto todo el programa con los test que tenemos por lo que puede que en algunos casos falle la app o no funcione como esperamos. Esto lo solucionaremos en versiones posteriores.

## Licencia
Este proyecto pertenece a **Iván Arrabé Jiménez**.

## Contacto
Para contactar con el equipo del proyecto enviar un email a **[ivanarrabe@gmail.com](mailto:ivanarrabe@gmail.com)**
